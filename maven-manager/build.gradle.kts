plugins {
    id("java")
}

group = "ru.bolilyivs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    implementation(libs.jsoup)
    implementation(libs.commons.io)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
}