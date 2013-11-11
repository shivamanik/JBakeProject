/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbake.service.plugin.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

/**
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public class PluginServiceUtil {

    private static Logger logger = Logger.getLogger(PluginServiceUtil.class.getName());
    private static Class[] parameters;

    public static void initializePluginJars(File directory) throws IOException {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                addURL(file.toURI().toURL());
            }
        }
    }

    public static void addURL(URL u) throws IOException {
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL urls[] = sysLoader.getURLs();
        for (URL url : urls) {
            if (url.toString().equalsIgnoreCase(u.toString())) {
                return;
            }
        }
        Class sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysLoader, new Object[]{
                u
            });
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }
    }

}
