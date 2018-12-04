package com.lajming.littlecrawler.urlcontainer;

import lombok.Value;

import java.util.Map;
import java.util.Set;

@Value(staticConstructor = "of")
public class CrawlResult {
    private final Set<String> externalUrls;
    private final Map<String, Set<String>> urlToStaticContentMap;
}
