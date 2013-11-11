package org.jbake.app;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

/**
 * Deals with assets (static files such as css, js or image files).
 *
 * @author Jonathan Bullock <jonbullock@gmail.com>
 *
 */
public class Asset {

    private final File source;
    private final File destination;

    /**
     * Creates an instance of Asset.
     *
     * @param source
     * @param destination
     */
    public Asset(File source, File destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Copy all files from supplied path.
     *
     * @param path	The starting path
     */
    public void copy(File path) {
        File[] assets = path.listFiles();
        if (assets != null) {
            Arrays.sort(assets);
            for (File asset : assets) {
                if (asset.isFile()) {
                    System.out.print("Copying [" + asset.getPath() + "]... ");
                    File sourceFile = asset;
                    File destFile = new File(sourceFile.getPath().replace(source.getPath() + File.separator + "assets", destination.getPath()));
                    try {
                        FileUtils.copyFile(sourceFile, destFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("done!");
                }
                if (asset.isDirectory()) {
                    copy(asset);
                }
            }
        }
    }
}
