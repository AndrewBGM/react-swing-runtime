plugins {
  kotlin("jvm") version "1.5.0"

  `java-library`
  `maven-publish`
}
group = "io.github.andrewbgm"
version = "0.0.1"

repositories {
  mavenLocal()
  mavenCentral()
}

java {
  withJavadocJar()
  withSourcesJar()
}

dependencies {
  implementation(platform(kotlin("bom")))
  implementation(kotlin("stdlib-jdk8"))

//  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
//  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.5.0-RC")

  implementation("org.slf4j:slf4j-simple:1.7.30")
  implementation("com.google.code.gson:gson:2.8.6")
  implementation("io.javalin:javalin:3.13.6")

  testImplementation(kotlin("test"))
  testImplementation(kotlin("test-junit"))
}

fun findProperty(
  propertyName: String,
  fallbackName: String
): String? =
  project.findProperty(propertyName) as String? ?: System.getenv(fallbackName)

publishing {
  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/andrewbgm/react-swing-runtime")
      credentials {
        username = findProperty("gpr.user", "USERNAME")
        password = findProperty("gpr.key", "TOKEN")
      }
    }
  }

  publications {
    create<MavenPublication>("mavenJava") {
      artifactId = "react-swing-runtime"
      from(components["java"])
    }
  }
}

tasks.register<JavaExec>("sample") {
  classpath = sourceSets.test.get().runtimeClasspath
  main = "io.github.andrewbgm.reactswingruntime.ReactSwingRuntimeSampleKt"
}
