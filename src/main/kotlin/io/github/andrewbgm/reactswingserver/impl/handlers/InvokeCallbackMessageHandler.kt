package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class InvokeCallbackMessageHandler : MessageHandler<InvokeCallbackMessage> {
  override fun handleMessage(
    message: InvokeCallbackMessage,
  ) {
    val (callbackId, args) = message

    println("invokeCallback($callbackId, $args)")
  }
}
