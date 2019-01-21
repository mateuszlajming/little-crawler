package com.lajming.littlecrawler;

import com.github.rozidan.springboot.logger.EnableLogger;
import com.lajming.littlecrawler.crawler.Crawler;
import com.lajming.littlecrawler.inputparser.ArgsParser;
import com.lajming.littlecrawler.resultconsumer.ResultConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableLogger
@SpringBootApplication
public class LittleCrawlerConsoleApp implements CommandLineRunner {

    @Autowired
    private ArgsParser inputParser;

    @Autowired
    private Crawler crawler;

    @Autowired
    private ResultConsumer resultConsumer;

    @Value("${test.mode:false}")
    private boolean testMode;

    public static void main(String[] args) {
        runWithSpringBoot(args);
    }

    @Override
    public void run(String... args) {
        if(testMode) {
            return;
        }

        var input = inputParser.parseInput();
        var result = crawler.crawl(input.getStartingUrl());
        resultConsumer.consumeResult(result);
        success();
    }

    private static void runWithSpringBoot(String[] args) {
        var app = new SpringApplication(LittleCrawlerConsoleApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    private static void success() {
        System.exit(0);
    }
}
