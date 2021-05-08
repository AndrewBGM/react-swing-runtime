package io.github.andrewbgm.reactswingruntime.impl.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*

class SetChildrenMessageHandler(
  private val env: HostEnvironment
) : IMessageHandler<SetChildrenMessage> {
  override fun handleMessage(
    message: SetChildrenMessage,
    ctx: IMessageContext
  ) {
    val (parentId, childrenIds) = message
    env.setChildren(parentId, childrenIds, HostContext(parentId, ctx))
  }
}
