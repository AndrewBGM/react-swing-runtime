package io.github.andrewbgm.reactswingruntime.impl.message.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*
import io.github.andrewbgm.reactswingruntime.impl.view.*

class RemoveChildMessageHandler(
  private val viewManager: ViewManager,
) : IMessageHandler<RemoveChildMessage> {
  override fun handleMessage(
    message: RemoveChildMessage,
    ctx: IMessageContext,
  ) {
    val (parentId, childId) = message
    viewManager.removeChild(parentId, childId, ctx)
  }
}
