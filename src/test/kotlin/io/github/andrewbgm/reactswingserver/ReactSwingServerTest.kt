package io.github.andrewbgm.reactswingserver

import kotlinx.coroutines.*

suspend fun main() = withContext(Dispatchers.Default) {
  val server = ReactSwingServer()
    .start(8080)

  Runtime.getRuntime().addShutdownHook(Thread {
    server.stop()
  })
}
