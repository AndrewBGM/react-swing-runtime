package io.github.andrewbgm.reactswingruntime.impl.message.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*
import io.github.andrewbgm.reactswingruntime.impl.view.*

class CreateViewMessageHandler(
  private val viewManager: ViewManager,
) : IMessageHandler<CreateViewMessage> {
  override fun handleMessage(
    message: CreateViewMessage,
    ctx: IMessageContext,
  ) {
    val (id, type, props) = message
    viewManager.createView(id, type, props, ctx)
  }
}
