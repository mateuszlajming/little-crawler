package com.lajming.littlecrawler.urlanalyzer;

import lombok.Value;

@Value(staticConstructor = "of")
public class AnalyzedUrl {
    private final String url;
    private final String parentUrl;
    private final boolean underMainDomain;
    private final boolean noFollow;
    private final boolean staticContent;
}
