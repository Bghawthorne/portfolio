package com.hawthorne_labs.springboot.utils.build;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Resource {
    private final String method;
    private final String url;
}
