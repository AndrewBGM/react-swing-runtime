package io.github.andrewbgm.reactswingserver

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.bridge.*
import io.github.andrewbgm.reactswingserver.bridge.adapter.*
import io.github.andrewbgm.reactswingserver.message.*
import io.javalin.*
import io.javalin.plugin.json.*
import io.javalin.websocket.*
import org.slf4j.*
import javax.swing.*

class ReactSwingServer {
  private val logger: Logger =
    LoggerFactory.getLogger(ReactSwingServer::class.java)

  private val app: Javalin by lazy { configureApp() }

  private val bridge: Bridge by lazy { configureBridge() }

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
    Bridge.setContext(bridge, ctx)

    logger.info("Session #${ctx.sessionId} closed.")
  }

  private fun handleConnect(
    ctx: WsConnectContext
  ) {
    Bridge.setContext(bridge, ctx)

    logger.info("Session #${ctx.sessionId} opened.")
  }

  private fun handleError(
    ctx: WsErrorContext
  ) {
    Bridge.setContext(bridge, ctx)

    logger.error("Connection error: ${ctx.error()}")
  }

  private fun handleMessage(
    ctx: WsMessageContext
  ) {
    Bridge.setContext(bridge, ctx)

    when (val message = ctx.message<IMessage>()) {
      is AppendChildMessage -> bridge.appendChild(
        message.parentId,
        message.childId
      )
      is AppendChildToContainerMessage -> bridge.appendChildToContainer(
        message.containerId,
        message.childId
      )
      is AppendInitialChildMessage -> bridge.appendInitialChild(
        message.parentId,
        message.childId
      )
      is ClearContainerMessage -> bridge.clearContainer(message.containerId)
      is CommitTextUpdateMessage -> bridge.commitTextUpdate(
        message.instanceId,
        message.text
      )
      is CommitUpdateMessage -> bridge.commitUpdate(
        message.instanceId,
        message.oldProps,
        message.newProps
      )
      is CreateInstanceMessage -> bridge.createInstance(
        message.instanceId,
        message.type,
        message.props
      )
      is CreateTextInstanceMessage -> bridge.createTextInstance(
        message.instanceId,
        message.text
      )
      is InsertBeforeMessage -> bridge.insertBefore(
        message.parentId,
        message.childId,
        message.beforeChildId
      )
      is InsertInContainerBeforeMessage -> bridge.insertInContainerBefore(
        message.containerId,
        message.childId,
        message.beforeChildId
      )
      is RemoveChildFromContainerMessage -> bridge.removeChildFromContainer(
        message.containerId,
        message.childId
      )
      is RemoveChildMessage -> bridge.removeChild(
        message.parentId,
        message.childId
      )
      is StartApplicationMessage -> bridge.startApplication()
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

  private fun configureBridge(): Bridge = Bridge(
    JFrame::class to JFrameHostAdapter()
  )

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
          StartApplicationMessage::class,
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
