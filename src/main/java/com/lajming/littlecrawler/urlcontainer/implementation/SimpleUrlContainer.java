package com.lajming.littlecrawler.urlcontainer.implementation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.lajming.littlecrawler.urlcontainer.CrawlResult;
import com.lajming.littlecrawler.urlcontainer.UrlContainer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.EMPTY_SET;
import static java.util.concurrent.ConcurrentHashMap.newKeySet;

@Component
class SimpleUrlContainer implements UrlContainer {

    private final Set<String> externalUrls = newKeySet();
    private final Map<String, Set<String>> urlToStaticContentMap = new ConcurrentHashMap<>();

    @Override
    public void addInternalSiteUrl(String url) {
        urlToStaticContentMap.putIfAbsent(url, EMPTY_SET);
    }

    @Override
    public void addExternalSiteUrl(String url) {
        externalUrls.add(url);
    }

    @Override
    public void addStaticContentUrl(String staticContentUrl, String parentUrl) {
        urlToStaticContentMap.compute(parentUrl, (url, scUrls) -> {
            if(scUrls == EMPTY_SET || scUrls == null){
                var newSet = ConcurrentHashMap.<String>newKeySet();
                newSet.add(staticContentUrl);
                return newSet;
            } else {
                scUrls.add(staticContentUrl);
                return scUrls;
            }
        });
    }

    @Override
    public CrawlResult generateResult() {
        var external = ImmutableSet.copyOf(externalUrls);
        var map = ImmutableMap.copyOf(urlToStaticContentMap);

        return CrawlResult.of(external, map);
    }
}