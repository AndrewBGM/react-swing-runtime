package io.github.andrewbgm.reactswingserver

import io.github.andrewbgm.reactswingserver.impl.*
import io.javalin.*

class ReactSwingServer {
  private val server: Javalin by lazy { configureApp() }

  private val bridge: ReactSwingServerBridge by lazy { configureBridge() }

  fun start(
    port: Int,
  ): ReactSwingServer = this.apply {
    server.start(port)
  }

  fun stop(): ReactSwingServer = this.apply {
    server.stop()
  }

  private fun configureApp(): Javalin =
    Javalin.create()
      .ws("/", bridge::attach)

  private fun configureBridge(): ReactSwingServerBridge =
    ReactSwingServerBridge()
}
