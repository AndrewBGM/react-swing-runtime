package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class HideTextInstanceMessageHandler : MessageHandler<HideTextInstanceMessage> {
  override fun handleMessage(
    message: HideTextInstanceMessage,
  ) {
    val (instanceId) = message

    println("hideTextInstance($instanceId)")
  }
}
