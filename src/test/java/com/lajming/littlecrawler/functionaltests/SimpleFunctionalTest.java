package com.lajming.littlecrawler.functionaltests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.lajming.littlecrawler.crawler.Crawler;
import com.lajming.littlecrawler.urlcontainer.CrawlResult;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"--test.mode=true"})
public class SimpleFunctionalTest {

    private static final int PORT = 8089;

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private Crawler crawler;

    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(PORT));
        WireMock.configureFor(PORT);
        wireMockServer.start();
    }

    @Test
    public void test() {
        // Arrange
        stubFor(get(urlEqualTo("/robots.txt"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/plain")
                .withBodyFile("robots.txt")));
        stubFor(get(urlEqualTo("/index.html"))
            .willReturn(aResponse()
                .withBodyFile("index.html")));

        // Act
        var startingUrl = String.format("http://localhost:%d/index.html", PORT);
        CrawlResult crawl = crawler.crawl(startingUrl);

        // Assert
        assertThat(crawl.getUrlToStaticContentMap().keySet())
            .containsExactly(startingUrl);
        assertThat(crawl.getUrlToStaticContentMap().get(startingUrl))
            .containsExactly("http://images.com/logo.jpg");
        assertThat(crawl.getExternalUrls())
            .containsExactly("https://facebook.com");
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }
}