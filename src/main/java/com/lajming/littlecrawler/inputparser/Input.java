package com.lajming.littlecrawler.inputparser;

import lombok.Value;

@Value(staticConstructor = "of")
public class Input {
    private final String startingUrl;
}
