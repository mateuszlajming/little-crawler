package com.lajming.littlecrawler.resultconsumer;

import com.lajming.littlecrawler.urlcontainer.CrawlResult;

public interface ResultConsumer {
    void consumeResult(CrawlResult crawlResult);
}
