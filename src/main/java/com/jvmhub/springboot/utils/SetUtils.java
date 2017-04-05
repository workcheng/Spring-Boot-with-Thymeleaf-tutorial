package com.jvmhub.springboot.utils;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by chenghui on 2017/4/5.
 */
public class SetUtils {

    public static String first(Set<String> set){
        ArrayList<String> ts = new ArrayList<>(set);
        if(ts.size() > 0){
            return ts.get(0);
        } else {
            return null;
        }
    }

    public static RequestMethod firsts(Set<RequestMethod> set){
        ArrayList<RequestMethod> ts = new ArrayList<>(set);
        if(ts.size() > 0){
            return ts.get(0);
        } else {
            return RequestMethod.TRACE;
        }

    }
}
