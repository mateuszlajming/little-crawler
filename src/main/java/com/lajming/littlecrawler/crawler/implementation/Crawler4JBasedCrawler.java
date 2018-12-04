package com.lajming.littlecrawler.crawler.implementation;

import com.lajming.littlecrawler.crawler.Crawler;
import com.lajming.littlecrawler.urlanalyzer.UrlAnalyzer;
import com.lajming.littlecrawler.urlcontainer.CrawlResult;
import com.lajming.littlecrawler.urlcontainer.UrlContainer;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
class Crawler4JBasedCrawler implements Crawler {

    private final UrlContainer urlContainer;
    private final UrlAnalyzer urlAnalyzer;

    public CrawlResult crawl(String startingUrl) {
        try {
            var crawlStorageFolder = "tmp";
            var numberOfCrawlers = 7;

            var config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            var pageFetcher = new PageFetcher(config);
            var robotstxtConfig = new RobotstxtConfig();
            var robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            var controller = new CrawlController(config, pageFetcher, robotstxtServer);

            var webURL = new WebURL();
            webURL.setURL(startingUrl);
            var startingUrlDomain = webURL.getDomain();

            var crawlerFactory = CrawlerFactory.of(
                CrawlingContext.of(startingUrlDomain),
                urlContainer,
                urlAnalyzer
            );

            controller.addSeed(startingUrl);
            controller.start(crawlerFactory, numberOfCrawlers);

            return urlContainer.generateResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
