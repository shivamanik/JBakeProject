package org.jbake.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.CompositeConfiguration;
import org.jbake.api.util.FileUtil;

/**
 * Crawls a file system looking for content.
 *
 * @author Jonathan Bullock <jonbullock@gmail.com>
 *
 */
public class Crawler {

    // TODO: replace separate lists with custom impl of hashmap that provides methods
    // TODO: to get back certain types of content (i.e. pages or posts), this allows for 
    // TODO: support of extra types with very little extra dev 
    private final File source;
    private final CompositeConfiguration config;
    private final Parser parser;

    private List<Map<String, Object>> pages = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> posts = new ArrayList<Map<String, Object>>();
    private Map<String, List<Map<String, Object>>> postsByTags = new HashMap<String, List<Map<String, Object>>>();
//	private Map<String, List<Map<String, Object>>> postsByarchive = new HashMap<String, List<Map<String, Object>>>();

    /**
     * Creates new instance of Crawler.
     *
     * @param source
     * @param config
     */
    public Crawler(File source, CompositeConfiguration config) {
        this.source = source;
        this.config = config;
        this.parser = new Parser(config);
    }

    /**
     * Crawl all files and folders looking for content.
     *
     * @param path	Folder to start from
     */
    public void crawl(File path) {
        File[] contents = path.listFiles(FileUtil.getFileFilter());
        if (contents != null) {
            Arrays.sort(contents);
            for (File content : contents) {
                if (content.isFile()) {
                    System.out.print("Processing [" + content.getPath() + "]... ");
                    Map<String, Object> fileContents = parser.processFile(content);
                    if (fileContents != null) {
                        fileContents.put("file", content.getPath());
                        String uri = content.getPath().replace(source.getPath() + File.separator + config.getString("content.folder"), "");
                        uri = uri.substring(0, uri.lastIndexOf("."));
                        uri = uri.replaceAll("\\\\", "/");
                        fileContents.put("uri", uri + config.getString("output.extension"));
                        if (fileContents.get("type").equals("page")) {
                            pages.add(fileContents);
                        } else {
                            // everything else is considered a post
                            posts.add(fileContents);

                            if (fileContents.get("tags") != null) {
                                String[] tags = (String[]) fileContents.get("tags");
                                for (String tag : tags) {
                                    if (postsByTags.containsKey(tag)) {
                                        postsByTags.get(tag).add(fileContents);
                                    } else {
                                        List<Map<String, Object>> posts1 = new ArrayList<Map<String, Object>>();
                                        posts1.add(fileContents);
                                        postsByTags.put(tag, posts1);
                                    }
                                }
                            }

                            if (fileContents.get("status").equals("published-date")) {
                                if (fileContents.get("date") != null && (fileContents.get("date") instanceof Date)) {
                                    if (new Date().after((Date) fileContents.get("date"))) {
                                        fileContents.put("status", "published");
                                    }
                                }
                            }
                        }
                        System.out.println("done!");
                    }
                }
                if (content.isDirectory()) {
                    crawl(content);
                }
            }
        }
    }

    public List<Map<String, Object>> getPages() {
        return pages;
    }

    public void setPages(List<Map<String, Object>> pages) {
        this.pages = pages;
    }

    public List<Map<String, Object>> getPosts() {
        return posts;
    }

    public void setPosts(List<Map<String, Object>> posts) {
        this.posts = posts;
    }

    public Map<String, List<Map<String, Object>>> getPostsByTags() {
        return postsByTags;
    }

    public void setPostsByTags(Map<String, List<Map<String, Object>>> postsByTags) {
        this.postsByTags = postsByTags;
    }
}
