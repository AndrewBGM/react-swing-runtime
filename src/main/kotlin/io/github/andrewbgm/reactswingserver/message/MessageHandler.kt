package io.github.andrewbgm.reactswingserver.message

import io.javalin.websocket.WsMessageContext
import io.javalin.websocket.WsMessageHandler
import org.slf4j.LoggerFactory

class MessageHandler : WsMessageHandler {
  private val logger = LoggerFactory.getLogger(MessageHandler::class.java)

  override fun handleMessage(
    ctx: WsMessageContext,
  ) {
    val message = ctx.message<IMessage>()
    logger.info(message.toString())
  }
}
