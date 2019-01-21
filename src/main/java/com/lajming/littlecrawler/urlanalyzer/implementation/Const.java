package com.lajming.littlecrawler.urlanalyzer.implementation;

import com.google.common.collect.ImmutableSet;

import java.util.Set;
import java.util.regex.Pattern;

import static com.google.common.base.Joiner.on;
import static java.util.regex.Pattern.compile;

class Const {
    private final static Set<String> MEDIA_EXTENSIONS = ImmutableSet.of("" +
        "tif", "gif", "jpg", "jpeg", "png", "bmp", "mp4", "flv", "mp3", "wav",
        "ogg", "mov", "avi", "mpg", "pdf", "tex", "xls", "xlsx", "doc", "docx",
        "odt", "txt", "ppt", "pptx", "rtf"
    );
    private final static Set<String> NO_FOLLOW_EXTENSIONS = ImmutableSet.of(
        "css", "js", "zip", "gz", "ico"
    );

    private final static String MEDIA_EXTENSIONS_PATTERN = on("|").join(MEDIA_EXTENSIONS);
    private final static String NO_FOLLOW_PATTERN = on("|").join(NO_FOLLOW_EXTENSIONS);

    final static Pattern MEDIA_CONTENT = compile(".*(\\.(" + MEDIA_EXTENSIONS_PATTERN + "))$");
    final static Pattern NO_FOLLOW = compile(".*(\\.(" + NO_FOLLOW_PATTERN + "))$");
}
