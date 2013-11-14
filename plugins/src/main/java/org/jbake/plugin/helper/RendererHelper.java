/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbake.plugin.helper;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.configuration.CompositeConfiguration;
import org.jbake.app.Crawler;

/**
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public class RendererHelper {

    private Configuration templateCfg;
    private final Crawler crawler;
    private final CompositeConfiguration config;

    public RendererHelper(Crawler crawler, CompositeConfiguration config) {
        this.crawler = crawler;
        this.config = config;
    }

    public void render(Map<String, Object> model, String templateFilename, File outputFile) throws Exception {
        model.put("version", config.getString("version"));
        model.put("posts", crawler.getPosts());
        model.put("pages", crawler.getPages());
        Map<String, Object> configModel = new HashMap<String, Object>();
        Iterator<String> configKeys = config.getKeys();
        while (configKeys.hasNext()) {
            String key = configKeys.next();
            //replace "." in key so you can use dot notation in templates
            configModel.put(key.replace(".", "_"), config.getProperty(key));
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

}
