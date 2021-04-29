package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class CreateInstanceMessageHandler : MessageHandler<CreateInstanceMessage> {
  override fun handleMessage(
    message: CreateInstanceMessage
  ) {
    val (instanceId, type, props) = message

    println("createInstance($instanceId, $type, $props)")
  }
}
