plugins {
    kotlin("jvm") version "2.1.20"
    application
}

group = "vintage.flow"
version = "1.0.0"

application {
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("junit:junit:4.13.2")
}

kotlin {
    jvmToolchain(25)
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(23)
}

sourceSets {
    main {
        kotlin.srcDirs(".")
        kotlin.exclude("src/test/**")
    }
}

tasks.test {
    useJUnit()
}
