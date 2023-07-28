package com.dylan.logicer.base.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Dylan
 * @Date : 2022/4/4 - 16:35
 * @Description : from https://gist.github.com/danielemaddaluno/302b6226613d08bb684517e36806f96a
 * @Function :
 */
public class ClassScanner {
    /**
     * 从pckgname包下获取所有添加了annotationClass注解的类对象
     *
     * @param pckgname
     * @param annotationClass
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Class<?>> getAnnotatedClassesForPackage(String pckgname, Class<? extends Annotation> annotationClass) throws ClassNotFoundException {
        if (pckgname.contains("-")){
            pckgname = pckgname.replaceAll("-", "");
        }
        List<Class<?>> classes = getClassesForPackage(pckgname);
        List<Class<?>> annotatedClasses = new ArrayList<Class<?>>();

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                annotatedClasses.add(clazz);
            }
        }
        return annotatedClasses;
    }

    /**
     * 根据pckgname获取该包下所有的类
     *
     * @param pckgname
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Class<?>> getClassesForPackage(String pckgname) throws ClassNotFoundException {
        final List<Class<?>> classes = new ArrayList<Class<?>>();

        try {
            final ClassLoader cld = Thread.currentThread().getContextClassLoader();

            if (cld == null) throw new ClassNotFoundException("Can't get class loader.");

            final Enumeration<URL> resources = cld.getResources(pckgname.replace('.', '/'));
            URLConnection connection;

            for (URL url = null; resources.hasMoreElements() && ((url = resources.nextElement()) != null); ) {
                try {
                    connection = url.openConnection();

                    if (connection instanceof JarURLConnection) {
                        checkJarFile((JarURLConnection) connection, pckgname, classes);
                    } else {
                        try {
                            checkDirectory(new File(URLDecoder.decode(url.getPath(), "UTF-8")), pckgname, classes);
                        } catch (final UnsupportedEncodingException ex) {
                            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Unsupported encoding)", ex);
                        }
                    }
                } catch (final IOException ioex) {
                    throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname, ioex);
                }
            }
        } catch (final NullPointerException ex) {
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Null pointer exception)", ex);
        } catch (final IOException ioex) {
            throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname, ioex);
        }

        return classes;
    }

    /**
     * 字符串首字母小写
     * @param str
     * @return
     */
    public static String lowerFirst(String str){
        char[] cs = str.toCharArray();
        // 如果是大写字母就转成小写
        if (cs[0]>='A' && cs[0]<='Z'){
            cs[0] += 32;
        }
        return String.valueOf(cs);
    }

    private static void checkJarFile(JarURLConnection connection, String pckgname, List<Class<?>> classes) throws ClassNotFoundException, IOException {
        final JarFile jarFile = connection.getJarFile();
        final Enumeration<JarEntry> entries = jarFile.entries();
        String name;

        for (JarEntry jarEntry = null; entries.hasMoreElements() && ((jarEntry = entries.nextElement()) != null); ) {
            name = jarEntry.getName();

            if (name.contains(".class")) {
                name = name.substring(0, name.length() - 6).replace('/', '.');

                if (name.contains(pckgname)) {
                    classes.add(Class.forName(name));
                }
            }
        }
    }

    private static void checkDirectory(File directory, String pckgname, List<Class<?>> classes) throws ClassNotFoundException {
        File tmpDirectory;

        if (directory.exists() && directory.isDirectory()) {
            final String[] files = directory.list();

            for (final String file : files) {
                if (file.endsWith(".class")) {
                    try {
                        classes.add(Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)));
                    } catch (final NoClassDefFoundError e) {
                        // do nothing. this class hasn't been found by the
                        // loader, and we don't care.
                    }
                } else if ((tmpDirectory = new File(directory, file)).isDirectory()) {
                    checkDirectory(tmpDirectory, pckgname + "." + file, classes);
                }
            }
        }
    }
}
