/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbake.plugin.feed;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jbake.api.plugin.AbstractJBakePlugin;
import org.jbake.app.Renderer;
import org.jbake.plugin.helper.RendererHelper;

/**
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public class FeedPlugin extends AbstractJBakePlugin {

    Renderer renderer;
    private Configuration templateCfg;

    @Override
    public String getPluginName() {
        return "Feed Plugin";
    }

    @Override
    public void execute() {

        File outputFile = new File(getOutputFolder().getPath() + File.separator +  getConfig().getString("feed.file"));
        System.out.print("Rendering feed [" + outputFile + "]... ");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("published_posts", getCrawler().getPosts());
        model.put("published_date", new Date());

        try {
            RendererHelper rendererHelper = new RendererHelper(getCrawler(), getConfig());
            rendererHelper.render(model, "feed.ftl", outputFile);
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
