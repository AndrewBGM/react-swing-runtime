package io.github.andrewbgm.reactswingruntime.impl.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*

class RemoveChildMessageHandler(
  private val env: HostEnvironment,
) : IMessageHandler<RemoveChildMessage> {
  override fun handleMessage(
    message: RemoveChildMessage,
    ctx: IMessageContext,
  ) {
    val (parentId, childId) = message
    env.removeChild(parentId, childId, HostContext(parentId, ctx))
  }
}
