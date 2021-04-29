package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class RemoveChildFromContainerMessageHandler :
  MessageHandler<RemoveChildFromContainerMessage> {
  override fun handleMessage(
    message: RemoveChildFromContainerMessage
  ) {
    val (parentId, childId) = message

    println("removeChildFromContainer($parentId, $childId)")
  }
}
