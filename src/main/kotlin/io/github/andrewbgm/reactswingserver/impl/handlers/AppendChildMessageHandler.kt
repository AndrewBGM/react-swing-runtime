package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class AppendChildMessageHandler : MessageHandler<AppendChildMessage> {
  override fun handleMessage(
    message: AppendChildMessage,
  ) {
    val (parentId, childId) = message

    println("appendChild($parentId, $childId)")
  }
}
