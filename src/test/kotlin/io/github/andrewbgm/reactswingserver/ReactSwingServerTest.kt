package io.github.andrewbgm.reactswingserver

import kotlinx.coroutines.*
import kotlinx.coroutines.swing.*

suspend fun main() = withContext(Dispatchers.Swing) {
  val server = ReactSwingServer()
  server.start(8080)
}
