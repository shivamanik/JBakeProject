/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbake.plugin.sitemap;

import freemarker.template.Configuration;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.jbake.api.plugin.AbstractJBakePlugin;
import org.jbake.app.Renderer;
import org.jbake.plugin.helper.RendererHelper;

/**
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public class SiteMapPlugin extends AbstractJBakePlugin {
    
        Renderer renderer;
    private Configuration templateCfg;


    //TODO: All thise plugin impl need to get all necessary object of the JBake framework.
    @Override
    public String getPluginName() {
        return "SiteMap Plugin";
    }

    @Override
    public void execute() {
        File outputFile = new File(getOutputFolder().getPath() + File.separator + "sitemapt.xml");
        System.out.print("Rendering sitemap [" + outputFile + "]... ");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("published_posts", getCrawler().getPosts());
        model.put("published_pages", getCrawler().getPages());
        model.put("published_date", new Date());

        try {
            RendererHelper rendererHelper = new RendererHelper(getCrawler(), getConfig());
            rendererHelper.render(model, "sitemap.ftl", outputFile);
            System.out.println("done!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed!");
        }

    }

    @Override
    public void init() {

        renderer = new Renderer(getProjectFolder(), getOutputFolder(), getTemplateFolder(), getConfig(), getCrawler().getPosts(), getCrawler().getPages());
    }

}
