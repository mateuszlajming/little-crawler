package com.lajming.littlecrawler.crawler;

import com.lajming.littlecrawler.urlcontainer.CrawlResult;

public interface Crawler {
    CrawlResult crawl(String startingUrl);
}
