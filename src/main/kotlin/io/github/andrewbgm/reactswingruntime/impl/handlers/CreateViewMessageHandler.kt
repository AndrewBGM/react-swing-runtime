package io.github.andrewbgm.reactswingruntime.impl.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*

class CreateViewMessageHandler(
  private val env: HostEnvironment,
) : IMessageHandler<CreateViewMessage> {
  override fun handleMessage(
    message: CreateViewMessage,
    ctx: IMessageContext,
  ) {
    val (id, type, props) = message
    env.createView(id, HostType.byId(type), props, HostContext(id, ctx))
  }
}
