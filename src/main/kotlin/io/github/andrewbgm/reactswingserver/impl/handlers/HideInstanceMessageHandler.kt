package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class HideInstanceMessageHandler : MessageHandler<HideInstanceMessage> {
  override fun handleMessage(
    message: HideInstanceMessage,
  ) {
    val (instanceId) = message

    println("hideInstance($instanceId)")
  }
}
