import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.22"
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "org.aquilon"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Adding JDA Library
    implementation("net.dv8tion:JDA:5.0.0-beta.11")

    // Adding SLF4J Library
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.8")

    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}

// Configure the Shadow JAR task
tasks.named<ShadowJar>("shadowJar") {
    archiveFileName.set("Aquilon-$version.jar")
    destinationDirectory.set(file("build/libs"))

    // Optionally, set the main class for the JAR manifest
    manifest {
        attributes["Main-Class"] = "org.aquilon.MainKt"
    }
}
