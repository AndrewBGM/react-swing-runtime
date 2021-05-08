package io.github.andrewbgm.reactswingruntime.impl.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*

class InsertChildMessageHandler(
  private val env: HostEnvironment
) : IMessageHandler<InsertChildMessage> {
  override fun handleMessage(
    message: InsertChildMessage,
    ctx: IMessageContext
  ) {
    val (parentId, childId, beforeChildId) = message
    env.insertChild(parentId, childId, beforeChildId, HostContext(parentId, ctx))
  }
}
