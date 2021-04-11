package io.github.andrewbgm.reactswingserver

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.message.*
import io.javalin.*
import io.javalin.plugin.json.*
import io.javalin.websocket.*
import org.slf4j.*

class ReactSwingServer {
  private val logger: Logger =
    LoggerFactory.getLogger(ReactSwingServer::class.java)

  private val app: Javalin by lazy { configureApp() }

  private val bridge: ReactSwingServerBridge by lazy { configureBridge() }

  fun start(
    port: Int
  ) {
    app.start(port)
  }

  fun stop() {
    app.stop()
  }

  private fun handleClose(
    ctx: WsCloseContext
  ) {
    logger.info("Session #${ctx.sessionId} closed.")
  }

  private fun handleConnect(
    ctx: WsConnectContext
  ) {
    logger.info("Session #${ctx.sessionId} opened.")
  }

  private fun handleError(
    ctx: WsErrorContext
  ) {
    logger.error("Connection error: ${ctx.error()}")
  }

  private fun handleMessage(
    ws: WsMessageContext
  ) {
    when (val message = ws.message<IMessage>()) {
      is AppendChildMessage -> bridge.appendChild(
        ws,
        message.parentId,
        message.childId
      )
      is AppendChildToContainerMessage -> bridge.appendChildToContainer(
        ws,
        message.containerId,
        message.childId
      )
      is AppendInitialChildMessage -> bridge.appendInitialChild(
        ws,
        message.parentId,
        message.childId
      )
      is ClearContainerMessage -> bridge.clearContainer(ws, message.containerId)
      is CommitTextUpdateMessage -> bridge.commitTextUpdate(
        ws,
        message.instanceId,
        message.text
      )
      is CommitUpdateMessage -> bridge.commitUpdate(
        ws,
        message.instanceId,
        message.type,
        message.changedProps
      )
      is CreateInstanceMessage -> bridge.createInstance(
        ws,
        message.instanceId,
        message.type,
        message.props
      )
      is CreateTextInstanceMessage -> bridge.createTextInstance(
        ws,
        message.instanceId,
        message.text
      )
      is InsertBeforeMessage -> bridge.insertBefore(
        ws,
        message.parentId,
        message.childId,
        message.beforeChildId
      )
      is InsertInContainerBeforeMessage -> bridge.insertInContainerBefore(
        ws,
        message.containerId,
        message.childId,
        message.beforeChildId
      )
      is RemoveChildFromContainerMessage -> bridge.removeChildFromContainer(
        ws,
        message.containerId,
        message.childId
      )
      is RemoveChildMessage -> bridge.removeChild(
        ws,
        message.parentId,
        message.childId
      )
      else -> error("Unsupported message: $message")
    }
  }

  private fun configureApp(): Javalin {
    configureGson()

    return Javalin.create()
      .ws("/") { ws ->
        ws.onClose(::handleClose)
        ws.onConnect(::handleConnect)
        ws.onError(::handleError)
        ws.onMessage(::handleMessage)
      }
  }

  private fun configureBridge(): ReactSwingServerBridge =
    ReactSwingServerBridge()

  private fun configureGson() {
    val gson = GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .registerTypeAdapter(
        IMessage::class.java, MessageAdapter(
          AppendChildMessage::class,
          AppendChildToContainerMessage::class,
          AppendInitialChildMessage::class,
          ClearContainerMessage::class,
          CommitTextUpdateMessage::class,
          CommitUpdateMessage::class,
          CreateInstanceMessage::class,
          CreateTextInstanceMessage::class,
          FreeCallbackMessage::class,
          InsertBeforeMessage::class,
          InsertInContainerBeforeMessage::class,
          InvokeCallbackMessage::class,
          RemoveChildFromContainerMessage::class,
          RemoveChildMessage::class,
        )
      )
      .create()

    JavalinJson.fromJsonMapper = object : FromJsonMapper {
      override fun <T> map(
        json: String,
        targetClass: Class<T>,
      ) = gson.fromJson(json, targetClass)
    }

    JavalinJson.toJsonMapper = object : ToJsonMapper {
      override fun map(
        obj: Any,
      ): String = if (obj is IMessage) gson.toJson(
        obj,
        IMessage::class.java
      ) else gson.toJson(obj)
    }
  }
}
