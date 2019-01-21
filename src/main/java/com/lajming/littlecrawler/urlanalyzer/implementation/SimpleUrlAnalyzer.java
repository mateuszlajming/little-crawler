package com.lajming.littlecrawler.urlanalyzer.implementation;

import com.github.rozidan.springboot.logger.Loggable;
import com.lajming.littlecrawler.urlanalyzer.AnalyzedUrl;
import com.lajming.littlecrawler.urlanalyzer.UrlAnalyzeContext;
import com.lajming.littlecrawler.urlanalyzer.UrlAnalyzer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.lajming.littlecrawler.urlanalyzer.implementation.Const.MEDIA_CONTENT;
import static com.lajming.littlecrawler.urlanalyzer.implementation.Const.NO_FOLLOW;

@Slf4j
@Getter
@Component
public class SimpleUrlAnalyzer implements UrlAnalyzer {

    @Override
    @Loggable(value = LogLevel.DEBUG, skipArgs = true)
    public AnalyzedUrl analyzeUrl(UrlAnalyzeContext context) {
        checkNotNull(context);

        var url = context.getUrl();
        var domain = context.getDomain();
        var parentUrl = context.getParentUrl();
        var mainDomain = context.getMainDomain();

        var underMainDomain = domain.equalsIgnoreCase(mainDomain);
        var noFollow = isNoFollow(url);
        var staticContent = isStaticContent(url);

        return AnalyzedUrl.of(url, parentUrl, underMainDomain, noFollow, staticContent);
    }

    private boolean isNoFollow(String url) {
        return NO_FOLLOW.matcher(url.toLowerCase()).matches();
    }

    private boolean isStaticContent(String url) {
        return MEDIA_CONTENT.matcher(url.toLowerCase()).matches();
    }
}
