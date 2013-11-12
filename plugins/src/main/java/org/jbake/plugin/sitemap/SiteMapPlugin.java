/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbake.plugin.sitemap;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
        System.out.println(getPluginName() + ": " + getCrawler().getPosts().size() + " Posts found in the project");
        System.out.println(getPluginName() + ": " + getCrawler().getPages().size() + " Pages found in the project");

        List<Map<String, Object>> list = getCrawler().getPosts();

        for (Map<String, Object> map : list) {
            Set<String> vlist = map.keySet();

            for (Object ob : vlist) {
                // System.out.println(ob + " = " + map.get(ob));
            }
            //System.out.println("**********************************");
        }

    }

}
