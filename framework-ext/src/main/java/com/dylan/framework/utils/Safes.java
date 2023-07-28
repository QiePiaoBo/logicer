package com.dylan.framework.utils;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @Classname Safes
 * @Description Safe工具类
 * @Date 5/6/2022 5:56 PM
 */
public class Safes {

    public static <T> T of(T value, T defalt) {
        return Optional.ofNullable(value).orElse(defalt);
    }

    public static <K, V> Map<K, V> of(Map<K, V> source) {
        return Optional.ofNullable(source).orElse(new HashMap<>());
    }

    public static <T> Iterator<T> of(Iterator<T> source) {
        return Optional.ofNullable(source).orElse(new ArrayList<T>().iterator());
    }

    public static <T> Collection<T> of(Collection<T> source) {
        return Optional.ofNullable(source).orElse(new ArrayList<>());
    }

    public static <T> Iterable<T> of(Iterable<T> source) {
        return Optional.ofNullable(source).orElse(new ArrayList<T>());
    }

    public static <T> List<T> of(List<T> source) {
        return Optional.ofNullable(source).orElse(new ArrayList<T>());
    }

    public static <T> Set<T> of(Set<T> source) {
        return Optional.ofNullable(source).orElse(new HashSet<T>());
    }

    public static BigDecimal of(BigDecimal source) {
        return Optional.ofNullable(source).orElse(BigDecimal.ZERO);
    }

    public static String of(String source) {
        return Optional.ofNullable(source).orElse(StringUtils.EMPTY);
    }

    public static String of(String source, String defaultStr) {
        return Optional.ofNullable(source).orElse(defaultStr);
    }

    public static <T> T first(Collection<T> source) {
        if (CollectionUtils.isEmpty(source)) {
            return null;
        }
        T t = null;
        Iterator<T> iterator = source.iterator();
        if (iterator.hasNext()) {
            t = iterator.next();
        }
        return t;
    }

    public static BigDecimal toBigDecimal(String source, BigDecimal defaultValue) {
        if (Objects.isNull(defaultValue)){
            defaultValue = BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(StringUtils.trimToEmpty(source));
        } catch (Throwable t) {
            return defaultValue;
        }
    }

    public static int toInt(String source, int defaultValue) {
        if (StringUtils.isBlank(source)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(StringUtils.trimToEmpty(source));
        } catch (Throwable t) {
            return defaultValue;
        }
    }

    public static long toLong(String source, long defaultValue) {
        if (StringUtils.isBlank(source)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(StringUtils.trimToEmpty(source));
        } catch (Throwable t) {
            return defaultValue;
        }
    }

    public static boolean toBoolean(String source, boolean defaultValue) {
        if (StringUtils.isBlank(source)) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(StringUtils.trimToEmpty(source));
        } catch (Throwable t) {
            return defaultValue;
        }
    }

    public static void run(Runnable runnable, Consumer<Throwable> error) {
        try {
            runnable.run();
        } catch (Throwable t) {
            error.accept(t);
        }
    }


}
