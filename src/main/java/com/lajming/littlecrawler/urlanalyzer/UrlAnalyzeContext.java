package com.lajming.littlecrawler.urlanalyzer;

import lombok.Value;

@Value(staticConstructor = "of")
public class UrlAnalyzeContext {
    private final String url;
    private final String domain;
    private final String parentUrl;
    private final String mainDomain;
}
