package org.jbake.service.plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.jbake.service.plugin.impl.DefaultJBakePluginService;
import org.jbake.service.plugin.utils.PluginServiceUtil;

/**
 *
 *
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public class JBakePluginServiceFactory {

    private static final Logger logger = Logger.getLogger(JBakePluginServiceFactory.class.getName());

    // TODO: We need to use this class in two ways. 1. Read from 'plugins' folder to get plugins 2. Do not use plugins folder instead just read from classpath. (Used to running from maven.)
    private static void addPluginJarsToApp() {
        try {
            PluginServiceUtil.initializePluginJars(new File("plugins"));
        } catch (IOException ioException) {

        }
    }

    public static JBakePluginService createPluginService() {
        addPluginJarsToApp();
        return DefaultJBakePluginService.getInstance();
    }
    
    public static JBakePluginService getDefaultPluginService() {
      
        return DefaultJBakePluginService.getInstance();
    }

}
