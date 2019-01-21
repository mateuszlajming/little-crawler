package com.lajming.littlecrawler.urlcontainer;

public interface UrlContainer {
    void addInternalSiteUrl(String url);
    void addExternalSiteUrl(String url);
    void addStaticContentUrl(String staticContentUrl, String parentUrl);

    CrawlResult generateResult();
}
