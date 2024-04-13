import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.aquilon"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Adding JDA Library
    implementation("net.dv8tion:JDA:5.0.0-beta.22")

    // Adding SLF4J Library
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("ch.qos.logback:logback-classic:1.5.5")

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
    archiveFileName.set("Aquilon-${project.version}.jar")
    destinationDirectory.set(file("build/libs"))

    // Optionally, set the main class for the JAR manifest
    manifest {
        attributes["Main-Class"] = "org.aquilon.MainKt"
    }
}
