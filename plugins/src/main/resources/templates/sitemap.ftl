<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
 <#list published_posts as post>
  <url>
    <loc>${config.site_host}${post.uri}</loc>
    <lastmod>${post.date?string("yyyy-MM-dd")}</lastmod>
    <changefreq>monthly</changefreq>
    <priority>0.8</priority>
  </url>
  </#list>
<#list published_pages as page>
  <url>
    <loc>${config.site_host}${page.uri}</loc>
    <lastmod>${page.date?string("yyyy-MM-dd")}</lastmod>
    <changefreq>yearly</changefreq>
    <priority>0.4</priority>
  </url>
  </#list>
</urlset>