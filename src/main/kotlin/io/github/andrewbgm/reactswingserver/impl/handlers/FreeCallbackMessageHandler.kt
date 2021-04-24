package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class FreeCallbackMessageHandler : MessageHandler<FreeCallbackMessage> {
  override fun handleMessage(
    message: FreeCallbackMessage,
  ) {
    val (callbackId) = message

    println("freeCallback($callbackId)")
  }
}
