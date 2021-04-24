package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class StopApplicationMessageHandler : MessageHandler<StopApplicationMessage> {
  override fun handleMessage(
    message: StopApplicationMessage,
  ) {
    println("stopApplication()")
  }
}
