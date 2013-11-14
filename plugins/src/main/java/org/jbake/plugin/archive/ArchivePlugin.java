/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbake.plugin.archive;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.jbake.api.plugin.AbstractJBakePlugin;
import org.jbake.plugin.helper.RendererHelper;

/**
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public class ArchivePlugin extends AbstractJBakePlugin {

    @Override
    public String getPluginName() {
        return "Archive Plugin";
    }

    @Override
    public void execute() {
     /*   File outputFile = new File(getOutputFolder().getPath() + File.separator + getConfig().getString("archive.file"));
        System.out.print("Rendering archive [" + outputFile + "]... ");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("published_posts",  getCrawler().getPosts());

        try {
            RendererHelper rendererHelper = new RendererHelper(getCrawler(), getConfig());
            rendererHelper.render(model, "archive.ftl", outputFile);
            System.out.println("done!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed!");
        }*/
    }

    @Override
    public void init() {

    }

}
