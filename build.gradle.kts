import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "app.androwara"
version = "1.0"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

dependencies {
    // Okhttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.8")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.8")
    implementation("com.squareup.okhttp3:okhttp-dnsoverhttps:5.0.0-alpha.8")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // JSOUP
    implementation("org.jsoup:jsoup:1.14.3")

    // 协程核心库
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    // 协程Java8支持库
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.3")
}