package io.github.andrewbgm.reactswingserver

import io.github.andrewbgm.reactswingserver.messages.*
import io.javalin.websocket.*
import org.slf4j.*

class ReactSwingServerBridge : WsConnectHandler, WsCloseHandler, WsErrorHandler,
  WsMessageHandler {
  private val logger =
    LoggerFactory.getLogger(ReactSwingServerBridge::class.java)

  fun attach(
    ws: WsHandler,
  ) {
    ws.onConnect(this)
    ws.onClose(this)
    ws.onError(this)
    ws.onMessage(this)
  }

  override fun handleConnect(
    ctx: WsConnectContext,
  ) {
    logger.info("Client connected.")
  }

  override fun handleClose(
    ctx: WsCloseContext,
  ) {
    logger.info("Client disconnected.")
  }

  override fun handleError(
    ctx: WsErrorContext,
  ) {
    logger.info("Client error: ${ctx.error()}")
  }

  override fun handleMessage(
    ctx: WsMessageContext,
  ) {
    logger.info("Client message: ${ctx.message<Message>()}")
  }
}
