package io.github.andrewbgm.reactswingruntime

fun main() {
  System.setProperty("sun.java2d.opengl", "true")

  ReactSwingRuntime()
    .start(8080)
}
