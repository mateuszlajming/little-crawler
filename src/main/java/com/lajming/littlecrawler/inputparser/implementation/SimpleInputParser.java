package com.lajming.littlecrawler.inputparser.implementation;

import com.google.common.base.Strings;
import com.lajming.littlecrawler.inputparser.ArgsParser;
import com.lajming.littlecrawler.inputparser.Input;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleInputParser implements ArgsParser {

    @Value("${starting.url:}")
    private String startingUrl;

    @Override
    public Input parseInput() {
        if(Strings.isNullOrEmpty(startingUrl)) {
            log.error("Parameter required. E.g. --starting.url=http://example.com");
            fail();
        }
        return Input.of(startingUrl);
    }

    private void fail() {
        System.exit(1);
    }
}
