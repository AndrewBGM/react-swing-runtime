package io.github.andrewbgm.reactswingruntime.impl

import io.github.andrewbgm.reactswingruntime.api.*
import io.javalin.websocket.*

class MessageContext(
  private val ctx: WsMessageContext
) : IMessageContext {
  override fun send(
    message: IMessage
  ) {
    ctx.send(message)
  }
}
