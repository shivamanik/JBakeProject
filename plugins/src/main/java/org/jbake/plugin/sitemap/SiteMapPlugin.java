/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbake.plugin.sitemap;

import org.jbake.api.plugin.AbstractJBakePlugin;


/**
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public class SiteMapPlugin extends AbstractJBakePlugin {
    
    

    //TODO: All thise plugin impl need to get all necessary object of the JBake framework.
    @Override
    public String getPluginName() {
        return "SiteMap Plugin";
    }
    
    @Override
    public void execute() {
        System.out.println(getPluginName() + ": " +getCrawler().getPosts().size() + " Posts found in the project");
        System.out.println(getPluginName() + ": " +getCrawler().getPages().size() + " Pages found in the project");

    }
    
}
