package io.github.andrewbgm.reactswingserver

suspend fun main() {
  val server = ReactSwingServer()
  server.start(8080)
}
