package ru.bolilyivs.dependency.manager.model;

public record Repository(
        String name,
        String url
) {
    public static Repository of(String name, String url) {
        return new Repository(name, url);
    }
}