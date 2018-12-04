package com.lajming.littlecrawler.resultconsumer.implementations;

import com.lajming.littlecrawler.resultconsumer.ResultConsumer;
import com.lajming.littlecrawler.urlcontainer.CrawlResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Lists.newLinkedList;
import static java.lang.System.lineSeparator;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

@Slf4j
@Component
public class OutputToFile implements ResultConsumer {

    private static final String FILE_NAME = "sitemap.txt";

    @Override
    public void consumeResult(CrawlResult crawlResult) {
        var external = crawlResult.getExternalUrls();
        var map = crawlResult.getUrlToStaticContentMap();

        var internalLinks = map.entrySet().stream().map(e -> {
            var link = of("  url: " + e.getKey());
            var staticContentLinks = e.getValue().stream().map(scLink -> "    static-content: " + scLink);
            return concat(link, staticContentLinks);
        })
            .flatMap(identity())
            .collect(toList());

        var externalLinks = external.stream().map(link -> "  url: " + link).collect(toList());

        var lines = newLinkedList();
        lines.add("internal-links:");
        lines.addAll(internalLinks);
        lines.add("external-links:");
        lines.addAll(externalLinks);

        var output = on(lineSeparator()).join(lines);

        try {
            write(
                get(FILE_NAME),
                output.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
