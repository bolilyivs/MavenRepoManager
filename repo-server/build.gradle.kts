plugins {
    id("java")
    id("io.micronaut.application") version "4.6.2"
    id("com.gradleup.shadow") version "9.4.1"
}

group = "ru.bolilyivs.server"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":maven-manager"))
    annotationProcessor(libs.micronaut.http.validation)
    annotationProcessor(libs.micronaut.serde.processor)
    annotationProcessor(libs.micronaut.validation.processor)
    implementation(libs.micronaut.serde.jackson)
    implementation(libs.micronaut.validation)
    implementation(libs.jakarta.validation.api)
    compileOnly(libs.micronaut.http.client)

    implementation(libs.micronaut.data.hibernate.jpa)
    implementation(libs.micronaut.jdbc.hikari)
    annotationProcessor(libs.micronaut.data.processor)
    annotationProcessor(libs.micronaut.data.document.processor)
    runtimeOnly(libs.h2)

    annotationProcessor(libs.micronaut.openapi)
    compileOnly(libs.micronaut.openapi.annotations)

    runtimeOnly(libs.logback.classic)
    testImplementation(libs.micronaut.http.client)
    testRuntimeOnly(libs.junit.platform.launcher)

    runtimeOnly(libs.snakeyaml)

    testImplementation(libs.micronaut.test.junit5)
    testImplementation(libs.mockito.core)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

application {
    mainClass.set("ru.bolilyivs.server.RepoServerApplication")
}

tasks.test {
    useJUnitPlatform()
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("ru.bolilyivs.server.*")
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}