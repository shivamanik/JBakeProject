package org.jbake.service.plugin;

import java.io.File;
import java.util.Iterator;
import org.apache.commons.configuration.CompositeConfiguration;
import org.jbake.api.plugin.AbstractJBakePlugin;
import org.jbake.app.Crawler;

/**
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public interface JBakePluginService {

    /**
     * Retrieves all the plugins found in the current runtime.
     *
     * @return Iterator List of all the class extends AbstractJBakePlugin
     */
    Iterator<AbstractJBakePlugin> getPlugins();

    /**
     * This method Lists all the plugins in the current scope.
     */
    void listPlugins();

    /**
     * Invokes each plugin and passes necessary object for the plugin.
     *
     * @param crawler crawler object for the site
     * @param config property config object
     * @param projectFolder Current site folder
     * @param outputFolder output folder of the site
     */
    void invokePlugins(final Crawler crawler, final CompositeConfiguration config, File projectFolder, File outputFolder);
}
