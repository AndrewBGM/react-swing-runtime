package io.github.andrewbgm.reactswingruntime

fun main() {
  val runtime = ReactSwingRuntime()
    .start(8080)

  Runtime.getRuntime().addShutdownHook(Thread {
    runtime.stop()
  })
}
