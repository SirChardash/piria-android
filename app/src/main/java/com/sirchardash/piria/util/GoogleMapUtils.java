package com.sirchardash.piria.util;

import java.util.Arrays;

public class GoogleMapUtils {

    public static double latitudeFromEmbeddedUrl(String url) {
        return urlParamFromEmbeddedUrl(url, "3d");
    }

    public static double longitudeFromEmbeddedUrl(String url) {
        return urlParamFromEmbeddedUrl(url, "2d");
    }

    private static double urlParamFromEmbeddedUrl(String url, String param) {
        return Arrays.stream(url.split("[!]"))
                .filter(s -> s.startsWith(param))
                .map(s -> s.replaceAll(param, ""))
                .map(Double::new)
                .findAny().orElse(0d);
    }

}
