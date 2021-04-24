package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class AppendInitialChildMessageHandler :
  MessageHandler<AppendInitialChildMessage> {
  override fun handleMessage(
    message: AppendInitialChildMessage,
  ) {
    val (parentId, childId) = message

    println("appendInitialChild($parentId, $childId)")
  }
}
