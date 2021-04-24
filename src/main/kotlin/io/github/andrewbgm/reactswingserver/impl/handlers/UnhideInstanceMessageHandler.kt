package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class UnhideInstanceMessageHandler : MessageHandler<UnhideInstanceMessage> {
  override fun handleMessage(
    message: UnhideInstanceMessage,
  ) {
    val (instanceId, props) = message

    println("unhideInstance($instanceId, $props)")
  }
}
