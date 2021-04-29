package io.github.andrewbgm.reactswingserver

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.*
import io.github.andrewbgm.reactswingserver.impl.handlers.*
import io.github.andrewbgm.reactswingserver.impl.messages.*
import io.javalin.*
import io.javalin.plugin.json.*
import io.javalin.websocket.*

class ReactSwingServer {
  private val gson: Gson = configureGson()
  private val messageBus: MessageBus = configureMessageBus()

  private val app: Javalin by lazy(::configureApp)

  fun start(
    port: Int,
  ): ReactSwingServer = this.apply {
    app.start(port)
  }

  fun stop(): ReactSwingServer = this.apply {
    app.stop()
  }

  private fun configureApp(): Javalin =
    Javalin.create()
      .ws("/") { ws ->
        ws.onConnect(::handleConnect)
        ws.onMessage(::handleMessage)
        ws.onClose(::handleClose)
        ws.onError(::handleError)
      }

  private fun handleConnect(
    ctx: WsConnectContext
  ) {

  }

  private fun handleMessage(
    ctx: WsMessageContext
  ) {
    val message = ctx.message<Message>()
    messageBus.handleMessage(message)
  }

  private fun handleClose(ctx: WsCloseContext) {

  }

  private fun handleError(
    ctx: WsErrorContext
  ) {

  }

  private fun configureGson(): Gson {
    val gson = GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .registerTypeAdapter(
        Message::class.java, GsonMessageAdapter()
          .registerMessageType<AppendChildMessage>("APPEND_CHILD")
          .registerMessageType<AppendChildToContainerMessage>("APPEND_CHILD_TO_CONTAINER")
          .registerMessageType<AppendInitialChildMessage>("APPEND_INITIAL_CHILD")
          .registerMessageType<ClearContainerMessage>("CLEAR_CONTAINER")
          .registerMessageType<CommitTextUpdateMessage>("COMMIT_TEXT_UPDATE")
          .registerMessageType<CommitUpdateMessage>("COMMIT_UPDATE")
          .registerMessageType<CreateInstanceMessage>("CREATE_INSTANCE")
          .registerMessageType<CreateTextInstanceMessage>("CREATE_TEXT_INSTANCE")
          .registerMessageType<InsertBeforeMessage>("INSERT_BEFORE")
          .registerMessageType<InsertInContainerBeforeMessage>("INSERT_IN_CONTAINER_BEFORE")
          .registerMessageType<RemoveChildFromContainerMessage>("REMOVE_CHILD_FROM_CONTAINER")
          .registerMessageType<RemoveChildMessage>("REMOVE_CHILD")
      )
      .create()

    JavalinJson.fromJsonMapper = object : FromJsonMapper {
      override fun <T> map(
        json: String,
        targetClass: Class<T>
      ): T = gson.fromJson(json, targetClass)
    }

    JavalinJson.toJsonMapper = object : ToJsonMapper {
      override fun map(
        obj: Any
      ): String = if (obj is Message) gson.toJson(
        obj,
        Message::class.java
      ) else gson.toJson(obj)
    }

    return gson
  }

  private fun configureMessageBus(): MessageBus = MessageBus()
    .registerMessageHandler(AppendChildMessageHandler())
    .registerMessageHandler(AppendChildToContainerMessageHandler())
    .registerMessageHandler(AppendInitialChildMessageHandler())
    .registerMessageHandler(ClearContainerMessageHandler())
    .registerMessageHandler(CommitTextUpdateMessageHandler())
    .registerMessageHandler(CommitUpdateMessageHandler())
    .registerMessageHandler(CreateInstanceMessageHandler())
    .registerMessageHandler(CreateTextInstanceMessageHandler())
    .registerMessageHandler(InsertBeforeMessageHandler())
    .registerMessageHandler(InsertInContainerBeforeMessageHandler())
    .registerMessageHandler(RemoveChildFromContainerMessageHandler())
    .registerMessageHandler(RemoveChildMessageHandler())
}
