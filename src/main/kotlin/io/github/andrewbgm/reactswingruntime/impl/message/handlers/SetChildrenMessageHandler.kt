package io.github.andrewbgm.reactswingruntime.impl.message.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*
import io.github.andrewbgm.reactswingruntime.impl.view.*

class SetChildrenMessageHandler(
  private val viewManager: ViewManager,
) : IMessageHandler<SetChildrenMessage> {
  override fun handleMessage(
    message: SetChildrenMessage,
    ctx: IMessageContext,
  ) {
    val (parentId, childrenIds) = message
    viewManager.setChildren(parentId, childrenIds, ctx)
  }
}
