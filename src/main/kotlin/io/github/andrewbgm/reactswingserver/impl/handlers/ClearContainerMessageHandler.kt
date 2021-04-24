package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class ClearContainerMessageHandler : MessageHandler<ClearContainerMessage> {
  override fun handleMessage(
    message: ClearContainerMessage,
  ) {
    val (instanceId) = message

    println("clearContainer($instanceId)")
  }
}
