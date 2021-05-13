package io.github.andrewbgm.reactswingruntime.impl.message.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*
import io.github.andrewbgm.reactswingruntime.impl.view.*

class InsertChildMessageHandler(
  private val viewManager: ViewManager,
) : IMessageHandler<InsertChildMessage> {
  override fun handleMessage(
    message: InsertChildMessage,
    ctx: IMessageContext,
  ) {
    val (parentId, childId, beforeChildId) = message
    viewManager.insertChild(parentId, childId, beforeChildId, ctx)
  }
}
