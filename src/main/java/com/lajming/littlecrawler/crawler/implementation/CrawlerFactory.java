package com.lajming.littlecrawler.crawler.implementation;

import com.lajming.littlecrawler.urlanalyzer.UrlAnalyzer;
import com.lajming.littlecrawler.urlcontainer.UrlContainer;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
class CrawlerFactory implements CrawlController.WebCrawlerFactory {

    CrawlingContext crawlingContext;
    UrlContainer urlContainer;
    UrlAnalyzer urlAnalyzer;

    @Override
    public WebCrawler newInstance() {
        return new WebCrawlerImpl(crawlingContext, urlContainer, urlAnalyzer);
    }
}
