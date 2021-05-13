package io.github.andrewbgm.reactswingruntime.impl.message.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*
import io.github.andrewbgm.reactswingruntime.impl.view.*

class AppendChildMessageHandler(
  private val viewManager: ViewManager,
) : IMessageHandler<AppendChildMessage> {
  override fun handleMessage(
    message: AppendChildMessage,
    ctx: IMessageContext,
  ) {
    val (parentId, childId) = message
    viewManager.appendChild(parentId, childId, ctx)
  }
}
