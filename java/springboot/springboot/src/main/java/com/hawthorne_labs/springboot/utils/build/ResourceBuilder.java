package com.hawthorne_labs.springboot.utils.build;

public class ResourceBuilder {

    public static Resource employee(Long id) {
        return Resource.builder()
                .method("GET")
                .url("/api/employees/" + id)
                .build();
    }

    public static Resource client(Long id) {
        return Resource.builder()
                .method("GET")
                .url("/api/clients/" + id)
                .build();
    }

    public static Resource schedule(Long id) {
        return Resource.builder()
                .method("GET")
                .url("/api/schedules/" + id)
                .build();
    }
}
