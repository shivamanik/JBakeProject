package org.jbake.api.plugin;

import java.io.File;
import org.apache.commons.configuration.CompositeConfiguration;
import org.jbake.app.Crawler;

/**
 *
 * This is a abstract plugin class. This class has all necessary methods to create and work with JBake
 *
 *
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public abstract class AbstractJBakePlugin {

    private Crawler crawler;
    private CompositeConfiguration config;
    private File projectFolder;
    private File outputFolder;

    public Crawler getCrawler() {
        return crawler;
    }

    public final void setCrawler(final Crawler crawler) {
        this.crawler = crawler;
    }

    public final CompositeConfiguration getConfig() {
        return config;
    }

    public final void setConfig(final CompositeConfiguration config) {
        this.config = config;
    }

    public final File getProjectFolder() {
        return projectFolder;
    }

    public final void setProjectFolder(File projectFolder) {
        this.projectFolder = projectFolder;
    }

    public final File getOutputFolder() {
        return outputFolder;
    }

    public final void setOutputFolder(File outputFolder) {
        this.outputFolder = outputFolder;
    }

    /**
     * This method tells the core plugin service about the plugin.
     *
     * @return String The name of the plugin.
     */
    public abstract String getPluginName();

    /**
     * Main executable method for the plugin.
     */
    public abstract void execute();

    /**
     * Initialize method for the plugin.
     */
    public abstract void init();

    /**
     * Empty method used by the child classes to provide content to append to main header content
     * 
     * @return String content to append to each header
     */
    public String provideContetToHeader() {
        return null;
    }

     /**
     * Empty method used by the child classes to provide content to append to main footer content
     * 
     * @return String content to append to each footer
     */
    public String provideContetToFooter() {
        return null;
    }

}
