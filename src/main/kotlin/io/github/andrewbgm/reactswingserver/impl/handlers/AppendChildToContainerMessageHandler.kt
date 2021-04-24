package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class AppendChildToContainerMessageHandler :
  MessageHandler<AppendChildToContainerMessage> {
  override fun handleMessage(
    message: AppendChildToContainerMessage,
  ) {
    val (parentId, childId) = message

    println("appendChildToContainer($parentId, $childId)")
  }
}
