package com.jvmhub.springboot.utils;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by chenghui on 2017/4/5.
 */
public class SetUtils {

    public static <T> T first(Set<T> set){
        ArrayList<T> ts = new ArrayList<T>(set);
        if(ts.size() > 0){
            return ts.get(0);
        } else {
            return null;
        }
    }
}
