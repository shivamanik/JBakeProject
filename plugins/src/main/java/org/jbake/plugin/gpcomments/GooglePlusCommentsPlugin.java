/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbake.plugin.gpcomments;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jbake.api.plugin.AbstractJBakePlugin;

/**
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public class GooglePlusCommentsPlugin extends AbstractJBakePlugin {
    
    private final static String GOOGLE_PLUS_COMMENTS_PLUGIN = "<script src='https://apis.google.com/js/plusone.js'></script><div class='g-comments' data-href = window.location  data-width = '650' data-first_party_property = 'BLOGGER' data-view_type = 'FILTERED_POSTMOD'></div>";
    private final static String GOOGLE_PLUS_COMMENTS = "";

    @Override
    public String getPluginName() {
        return "Google Plus Comments Plugin";
    }

    @Override
    public void execute() {
        String isComments = "",body = "";
        System.out.println(getPluginName() + ": " + getCrawler().getPosts().size() + " Posts found in the project");
        System.out.println(getPluginName() + ": " + getCrawler().getPages().size() + " Pages found in the project");
 System.out.print("Running plugin " + getPluginName());
        List<Map<String, Object>> list = getCrawler().getPosts();

        for (Map<String, Object> map : list) {
            Set<String> vlist = map.keySet();
            isComments = (String)map.get("comments");
            body = (String)map.get("body");
            if (isComments != null && isComments.equals("true")){
                 System.out.print("Rendering gpcomments [" + map.get("uri") + "]... ");
                map.put("body", body + "<br/><br/>" +GOOGLE_PLUS_COMMENTS_PLUGIN);
            }

            for (Object ob : vlist) {
       //         System.out.println(ob + " = " + map.get(ob));
            }
            System.out.println("**********************************");
        }

    }

    @Override
    public void init() {

    }

}
