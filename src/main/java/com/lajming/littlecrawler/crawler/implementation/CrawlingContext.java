package com.lajming.littlecrawler.crawler.implementation;

import lombok.Value;

@Value(staticConstructor = "of")
class CrawlingContext {
    private String startingUrlDomain;
}
