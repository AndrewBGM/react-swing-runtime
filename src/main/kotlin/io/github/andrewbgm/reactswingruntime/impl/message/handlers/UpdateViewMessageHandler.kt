package io.github.andrewbgm.reactswingruntime.impl.message.handlers

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*
import io.github.andrewbgm.reactswingruntime.impl.view.*

class UpdateViewMessageHandler(
  private val viewManager: ViewManager,
) : IMessageHandler<UpdateViewMessage> {
  override fun handleMessage(
    message: UpdateViewMessage,
    ctx: IMessageContext,
  ) {
    val (id, changedProps) = message
    viewManager.updateView(id, changedProps, ctx)
  }
}
