import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.domnikl"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

application {
    mainClassName = "dev.domnikl.corona.ServiceKt"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("ch.qos.logback:logback-core:1.4.6")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")
    implementation("com.fasterxml.woodstox:woodstox-core:6.5.0")
    implementation("net.dv8tion:JDA:4.2.0_247")
    implementation("com.typesafe:config:1.4.2")
    testImplementation("junit", "junit", "4.13.2")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    shadowJar {
        archiveFileName.set("${project.name}.jar")

        // defaults to all, so removing this overrides the normal, non-fat jar
        archiveClassifier.set("")
    }
}
