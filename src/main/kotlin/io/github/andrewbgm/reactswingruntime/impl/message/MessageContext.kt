package io.github.andrewbgm.reactswingruntime.impl.message

import io.github.andrewbgm.reactswingruntime.api.*
import io.javalin.websocket.*

class MessageContext(
  private val ctx: WsContext,
) : IMessageContext {
  override fun send(
    message: IMessage,
  ) {
    ctx.send(message)
  }
}
