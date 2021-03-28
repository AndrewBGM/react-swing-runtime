plugins {
  kotlin("jvm") version "1.4.31"

  `java-library`
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(platform(kotlin("bom")))
  implementation(kotlin("stdlib-jdk8"))

  testImplementation(kotlin("test"))
  testImplementation(kotlin("test-junit"))
}
