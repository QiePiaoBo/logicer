package com.dylan.framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Classname StringSafeUtil
 * @Description StringSafeUtil
 * @Date 9/15/2022 5:50 PM
 */
public class StringSafeUtil {

    public static String hideMiddleString(String sourceStr){
        if (StringUtils.isBlank(sourceStr)){
            return "";
        }
        if (sourceStr.length() < 2){
            return "*";
        }
        char start = sourceStr.charAt(0);
        char end = sourceStr.charAt(sourceStr.length() - 1);
        return start + "***" + end;
    }
}
