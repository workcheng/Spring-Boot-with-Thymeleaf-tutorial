package com.jvmhub.springboot.controller;

import com.jvmhub.springboot.model.RequestToMethodItem;
import com.jvmhub.springboot.utils.SetUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenghui on 2017/4/5.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "index.html",method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();
        if (servletContext == null) {
            return null;
        }
        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        //请求url和处理方法的映射
        List<RequestToMethodItem> requestToMethodItemList = new ArrayList<RequestToMethodItem>();
        //获取所有的RequestMapping
        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext,
                HandlerMapping.class, true, false);

        for (HandlerMapping handlerMapping : allRequestMappings.values()) {
            //本项目只需要RequestMappingHandlerMapping中的URL映射
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
                    RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                    HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();

                    RequestMethodsRequestCondition methodCondition = requestMappingInfo.getMethodsCondition();

                    RequestMethod first = SetUtils.first(methodCondition.getMethods());
                    String requestType = "Unknown Method";
                    if(null != first){
                        requestType = first.name();
                    }

                    PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                    String requestUrl = SetUtils.first(patternsCondition.getPatterns());

                    String controllerName = mappingInfoValue.getBeanType().toString();
                    String requestMethodName = mappingInfoValue.getMethod().getName();
                    Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();
                    RequestToMethodItem item = new RequestToMethodItem(requestUrl, requestType, controllerName, requestMethodName,
                            methodParamTypes);

                    requestToMethodItemList.add(item);
                }
                break;
            }
        }
        return new ModelAndView("index").addObject("MethodList", requestToMethodItemList);
    }
}
