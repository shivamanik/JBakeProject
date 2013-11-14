/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbake.service.plugin.impl;

import java.io.File;
import java.util.Iterator;
import org.jbake.service.plugin.JBakePluginService;

import java.util.ServiceLoader;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.log4j.Logger;
import org.jbake.api.plugin.AbstractJBakePlugin;
import org.jbake.app.Crawler;

/**
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public class DefaultJBakePluginService implements JBakePluginService {

    private static final Logger logger = Logger.getLogger(DefaultJBakePluginService.class.getName());
    private static DefaultJBakePluginService bakePluginService;
    private final ServiceLoader<AbstractJBakePlugin> serviceLoader;

    private DefaultJBakePluginService() {
        //load all the classes in the classpath that have implemented the interface
        serviceLoader = ServiceLoader.load(AbstractJBakePlugin.class);
    }

    public static DefaultJBakePluginService getInstance() {
        if (bakePluginService == null) {
            bakePluginService = new DefaultJBakePluginService();
        }
        return bakePluginService;
    }

    @Override
    public Iterator<AbstractJBakePlugin> getPlugins() {
        return serviceLoader.iterator();
    }

    //    TODO: This method need to have parameter of pages,posts etc. Or may be one Context object contain all the objects.
    @Override
    public void listPlugins() {
        Iterator<AbstractJBakePlugin> iterator = getPlugins();

        while (iterator.hasNext()) {

            AbstractJBakePlugin bakePlugin = iterator.next();
            System.out.println(bakePlugin.getPluginName());
        }
    }

    @Override
    public void invokePlugins(Crawler crawler, CompositeConfiguration config, File projectFolder, File outputFolder, File assetFolder, File templateFolder,File contentFolder) {
        Iterator<AbstractJBakePlugin> iterator = getPlugins();

        while (iterator.hasNext()) {
            AbstractJBakePlugin bakePlugin = iterator.next();
            
            bakePlugin.setConfig(config);
            bakePlugin.setCrawler(crawler);
            bakePlugin.setOutputFolder(outputFolder);
            bakePlugin.setProjectFolder(projectFolder);
            bakePlugin.setAssetFolder(assetFolder);
            bakePlugin.setTemplateFolder(templateFolder);
            bakePlugin.setContentFolder(contentFolder);
            
            bakePlugin.init();
            bakePlugin.execute();
        }
    }

}
