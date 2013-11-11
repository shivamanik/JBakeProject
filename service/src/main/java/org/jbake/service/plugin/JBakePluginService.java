
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

    Iterator<AbstractJBakePlugin> getPlugins();

    void invokePlugins();
    
    void imvokePlugins(final Crawler crawler,final CompositeConfiguration config,File projectFolder, File outputFolder );
}
