package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class InsertInContainerBeforeMessageHandler :
  MessageHandler<InsertInContainerBeforeMessage> {
  override fun handleMessage(
    message: InsertInContainerBeforeMessage
  ) {
    val (parentId, childId, beforeChildId) = message

    println("insertInContainerBefore($parentId, $childId, $beforeChildId)")
  }
}
