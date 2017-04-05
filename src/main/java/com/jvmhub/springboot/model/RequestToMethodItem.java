package com.jvmhub.springboot.model;

/**
 * Created by chenghui on 2017/4/5.
 */
public class RequestToMethodItem {
    public String controllerName;
    public String methodName;
    public String requestType;
    public String requestUrl;
    public Class<?>[] methodParmaTypes;

    public RequestToMethodItem(String requestUrl, String requestType, String controllerName, String methodName, Class<?>[] methodParmaTypes) {
        this.controllerName = controllerName;
        this.methodName = methodName;
        this.requestType = requestType;
        this.requestUrl = requestUrl;
        this.methodParmaTypes = methodParmaTypes;
    }
}
