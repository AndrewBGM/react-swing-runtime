package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class InsertBeforeMessageHandler : MessageHandler<InsertBeforeMessage> {
  override fun handleMessage(
    message: InsertBeforeMessage
  ) {
    val (parentId, childId, beforeChildId) = message

    println("insertBefore($parentId, $childId, $beforeChildId)")
  }
}
