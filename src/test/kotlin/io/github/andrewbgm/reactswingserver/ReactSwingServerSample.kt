package io.github.andrewbgm.reactswingserver

fun main() {
  val server = ReactSwingServer()
  server.start(8080)

  Runtime.getRuntime().addShutdownHook(Thread {
    server.stop()
  })
}
