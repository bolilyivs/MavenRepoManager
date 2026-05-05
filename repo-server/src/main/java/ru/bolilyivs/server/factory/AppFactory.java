package ru.bolilyivs.server.factory;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Factory
@RequiredArgsConstructor
public class AppFactory {

    @Named("downloadExecutorService")
    @Singleton
    public ExecutorService downloadExecutorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
