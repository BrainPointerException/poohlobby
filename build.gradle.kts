plugins {
    id("java")
}

group = "de.poohscord.poohlobby"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("eu.cloudnetservice.cloudnet:bridge:4.0.0-RC11.1")
    compileOnly("eu.cloudnetservice.cloudnet:driver:4.0.0-RC11.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}