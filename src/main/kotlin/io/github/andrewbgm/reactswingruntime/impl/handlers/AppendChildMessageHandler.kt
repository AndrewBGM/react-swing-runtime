package io.github.andrewbgm.reactswingruntime.impl.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.*
import io.github.andrewbgm.reactswingruntime.impl.messages.*

class AppendChildMessageHandler(
  private val env: HostEnvironment
) : IMessageHandler<AppendChildMessage> {
  override fun handleMessage(
    message: AppendChildMessage,
    ctx: IMessageContext
  ) {
    val (parentId, childId) = message
    env.appendChild(parentId, childId, HostContext(parentId, ctx))
  }
}
