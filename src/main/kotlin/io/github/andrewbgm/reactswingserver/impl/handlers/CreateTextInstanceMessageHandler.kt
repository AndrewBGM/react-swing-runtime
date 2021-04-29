package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class CreateTextInstanceMessageHandler :
  MessageHandler<CreateTextInstanceMessage> {
  override fun handleMessage(
    message: CreateTextInstanceMessage
  ) {
    val (instanceId, text) = message

    println("createTextInstance($instanceId, $text)")
  }
}
