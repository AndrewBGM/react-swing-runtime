package io.github.andrewbgm.reactswingruntime.impl.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.*
import io.github.andrewbgm.reactswingruntime.impl.messages.*

class UpdateViewMessageHandler(
  private val env: HostEnvironment
) : IMessageHandler<UpdateViewMessage> {
  override fun handleMessage(
    message: UpdateViewMessage,
    ctx: IMessageContext
  ) {
    val (id, changedProps) = message
    env.updateView(id, changedProps, HostContext(id, ctx))
  }
}
