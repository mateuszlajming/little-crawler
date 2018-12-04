package com.lajming.littlecrawler.crawler.implementation;

import com.lajming.littlecrawler.urlanalyzer.AnalyzedUrl;
import com.lajming.littlecrawler.urlanalyzer.UrlAnalyzeContext;
import com.lajming.littlecrawler.urlanalyzer.UrlAnalyzer;
import com.lajming.littlecrawler.urlcontainer.UrlContainer;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Strings.isNullOrEmpty;

@Slf4j
@AllArgsConstructor
class WebCrawlerImpl extends WebCrawler {

    private final CrawlingContext crawlingContext;
    private final UrlContainer urlContainer;
    private final UrlAnalyzer urlAnalyzer;

    @Override
    public boolean shouldVisit(Page referringPage, WebURL webUrl) {
        AnalyzedUrl analyzedUrl = analyzeUrl(webUrl);

        if (analyzedUrl.isNoFollow()) {
            return false;
        }

        if (analyzedUrl.isStaticContent()) {
            urlContainer.addStaticContentUrl(analyzedUrl.getUrl(), analyzedUrl.getParentUrl());
            return false;
        }

        if (analyzedUrl.isUnderMainDomain()) {
            return true;
        } else {
            urlContainer.addExternalSiteUrl(analyzedUrl.getUrl());
            return false;
        }
    }

    @Override
    public void visit(Page page) {
        // add internal site link only after it is visited to avoid including pages which are not accessible
        urlContainer.addInternalSiteUrl(normalizeUrl(page.getWebURL().getURL()));
    }

    private AnalyzedUrl analyzeUrl(WebURL webUrl) {
        var url = normalizeUrl(webUrl.getURL());
        var domain = webUrl.getDomain();
        var parentUrl = normalizeUrl(webUrl.getParentUrl());
        var mainDomain = crawlingContext.getStartingUrlDomain();

        var urlAnalyzeContext = UrlAnalyzeContext.of(url, domain, parentUrl, mainDomain);
        return urlAnalyzer.analyzeUrl(urlAnalyzeContext);
    }

    private String normalizeUrl(String href) {
        if (!isNullOrEmpty(href) && lastCharacter(href).equals("/")) {
            href = href.substring(0, href.length() - 1);
        }

        return href;
    }

    private String lastCharacter(String href) {
        return href.substring(href.length() - 1);
    }

}
