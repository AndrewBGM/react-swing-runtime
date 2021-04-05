package io.github.andrewbgm.reactswingserver

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.gson.*
import io.github.andrewbgm.reactswingserver.messages.*
import io.javalin.*
import io.javalin.plugin.json.*
import io.javalin.websocket.*

class ReactSwingServer {
  private val app: Javalin by lazy { configureApp() }

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
    println("Connection closed.")
  }

  private fun handleConnect(
    ctx: WsConnectContext
  ) {
    println("Connection opened.")
  }

  private fun handleError(
    ctx: WsErrorContext
  ) {
    println("Connection error: ${ctx.error()}")
  }

  private fun handleMessage(
    ctx: WsMessageContext
  ) {
    val message = ctx.message<IMessage>()
    println("Connection message: $message")
    ctx.send(message)
  }

  private fun handleServerStarting() {
    println("Server starting...")
  }

  private fun handleServerStarted() {
    println("Server started!")
  }

  private fun handleServerStartFailed() {
    println("Server failed to start!")
  }

  private fun handleServerStopping() {
    println("Server stopping...")
  }

  private fun handleServerStopped() {
    println("Server stopped!")
  }

  private fun configureApp(): Javalin {
    configureGson()

    return Javalin.create()
      .events {
        it.serverStarting(::handleServerStarting)
        it.serverStarted(::handleServerStarted)
        it.serverStartFailed(::handleServerStartFailed)

        it.serverStopping(::handleServerStopping)
        it.serverStopped(::handleServerStopped)
      }
      .ws("/") { ws ->
        ws.onClose(::handleClose)
        ws.onConnect(::handleConnect)
        ws.onError(::handleError)
        ws.onMessage(::handleMessage)
      }
  }

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
