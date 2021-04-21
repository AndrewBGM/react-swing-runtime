package io.github.andrewbgm.reactswingserver

import javax.swing.*

fun main() = SwingUtilities.invokeLater {
  val server = ReactSwingServer()
  server.start(8080)
}
