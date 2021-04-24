package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class UnhideTextInstanceMessageHandler :
  MessageHandler<UnhideTextInstanceMessage> {
  override fun handleMessage(
    message: UnhideTextInstanceMessage,
  ) {
    val (instanceId, text) = message

    println("unhideTextInstance($instanceId, $text)")
  }
}
