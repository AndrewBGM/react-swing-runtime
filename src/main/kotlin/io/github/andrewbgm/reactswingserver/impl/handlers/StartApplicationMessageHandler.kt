package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class StartApplicationMessageHandler : MessageHandler<StartApplicationMessage> {
  override fun handleMessage(
    message: StartApplicationMessage,
  ) {
    println("startApplication()")
  }
}
