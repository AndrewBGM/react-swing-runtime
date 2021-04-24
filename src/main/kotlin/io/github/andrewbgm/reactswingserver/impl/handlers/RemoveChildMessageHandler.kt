package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class RemoveChildMessageHandler : MessageHandler<RemoveChildMessage> {
  override fun handleMessage(
    message: RemoveChildMessage,
  ) {
    val (parentId, childId) = message

    println("removeChild($parentId, $childId)")
  }
}
