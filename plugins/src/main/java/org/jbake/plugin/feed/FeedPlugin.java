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

        File outputFile = new File(getOutputFolder().getPath() + File.separator + "feed.xml");
        System.out.print("Rendering feed [" + outputFile + "]... ");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("published_posts", getCrawler().getPosts());
        model.put("published_date", new Date());

        try {
            render(model, "feed.ftl", outputFile);
            System.out.println("done!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed!");
        }

    }

    private void render(Map<String, Object> model, String templateFilename, File outputFile) throws Exception {
        model.put("version", getConfig().getString("version"));
        model.put("posts", getCrawler().getPosts());
        model.put("pages", getCrawler().getPages());
        Map<String, Object> configModel = new HashMap<String, Object>();
        Iterator<String> configKeys = getConfig().getKeys();
        while (configKeys.hasNext()) {
            String key = configKeys.next();
            //replace "." in key so you can use dot notation in templates
            configModel.put(key.replace(".", "_"), getConfig().getProperty(key));
        }
        model.put("config", configModel);
        Template template = null;

        templateCfg = new Configuration();
        templateCfg.setTemplateLoader(new ClassTemplateLoader((this.getClass()), "/templates"));
        template = templateCfg.getTemplate(templateFilename);

        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
            outputFile.createNewFile();
        }

        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));
        template.process(model, out);
        out.close();
    }

    @Override
    public void init() {

        renderer = new Renderer(getProjectFolder(), getOutputFolder(), getTemplateFolder(), getConfig(), getCrawler().getPosts(), getCrawler().getPages());
    }

}
