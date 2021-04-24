package io.github.andrewbgm.reactswingserver.impl

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.gson.*
import io.github.andrewbgm.reactswingserver.impl.handlers.*
import io.github.andrewbgm.reactswingserver.impl.messages.*
import io.javalin.plugin.json.*
import io.javalin.websocket.*

class ReactSwingServerBridge {
  private val gson = configureGson()
  private val messageBus = configureMessageBus()

  /**
   * Attach event handlers
   *
   * @param ws web-socket handler
   */
  fun attach(
    ws: WsHandler,
  ) {
    ws.onMessage(::handleMessage)
  }

  /**
   * Calls event handlers for incoming messages
   *
   * @param ctx current context
   */
  private fun handleMessage(
    ctx: WsMessageContext,
  ) {
    val message = ctx.message<Message>()
    messageBus.handleMessage(message)
  }

  /**
   * Configures a MessageBus instance and handlers and returns it
   */
  private fun configureMessageBus(): MessageBus = MessageBus()
    .registerMessageHandler(AppendChildMessageHandler())
    .registerMessageHandler(AppendInitialChildMessageHandler())
    .registerMessageHandler(AppendChildToContainerMessageHandler())
    .registerMessageHandler(ClearContainerMessageHandler())
    .registerMessageHandler(CommitTextUpdateMessageHandler())
    .registerMessageHandler(CommitUpdateMessageHandler())
    .registerMessageHandler(CreateInstanceMessageHandler())
    .registerMessageHandler(CreateTextInstanceMessageHandler())
    .registerMessageHandler(FreeCallbackMessageHandler())
    .registerMessageHandler(HideInstanceMessageHandler())
    .registerMessageHandler(HideTextInstanceMessageHandler())
    .registerMessageHandler(InsertBeforeMessageHandler())
    .registerMessageHandler(InsertInContainerBeforeMessageHandler())
    .registerMessageHandler(InvokeCallbackMessageHandler())
    .registerMessageHandler(RemoveChildFromContainerMessageHandler())
    .registerMessageHandler(RemoveChildMessageHandler())
    .registerMessageHandler(StartApplicationMessageHandler())
    .registerMessageHandler(StopApplicationMessageHandler())
    .registerMessageHandler(UnhideInstanceMessageHandler())
    .registerMessageHandler(UnhideTextInstanceMessageHandler())
    .apply {
      // TODO: Custom MessageAdapter can be hooked here.
    }

  /**
   * Configures a GSON instance for mapping messages and returns it
   */
  private fun configureGson(): Gson = GsonBuilder()
    .excludeFieldsWithoutExposeAnnotation()
    .registerTypeAdapter(Message::class.java, GsonMessageAdapter()
      .registerMessageType<AppendChildMessage>(MessageTypes.APPEND_CHILD)
      .registerMessageType<AppendInitialChildMessage>(MessageTypes.APPEND_INITIAL_CHILD)
      .registerMessageType<AppendChildToContainerMessage>(MessageTypes.APPEND_CHILD_TO_CONTAINER)
      .registerMessageType<ClearContainerMessage>(MessageTypes.CLEAR_CONTAINER)
      .registerMessageType<CommitTextUpdateMessage>(MessageTypes.COMMIT_TEXT_UPDATE)
      .registerMessageType<CommitUpdateMessage>(MessageTypes.COMMIT_UPDATE)
      .registerMessageType<CreateInstanceMessage>(MessageTypes.CREATE_INSTANCE)
      .registerMessageType<CreateTextInstanceMessage>(MessageTypes.CREATE_TEXT_INSTANCE)
      .registerMessageType<FreeCallbackMessage>(MessageTypes.FREE_CALLBACK)
      .registerMessageType<HideInstanceMessage>(MessageTypes.HIDE_INSTANCE)
      .registerMessageType<HideTextInstanceMessage>(MessageTypes.HIDE_TEXT_INSTANCE)
      .registerMessageType<InsertBeforeMessage>(MessageTypes.INSERT_BEFORE)
      .registerMessageType<InsertInContainerBeforeMessage>(MessageTypes.INSERT_IN_CONTAINER_BEFORE)
      .registerMessageType<InvokeCallbackMessage>(MessageTypes.INVOKE_CALLBACK)
      .registerMessageType<RemoveChildFromContainerMessage>(MessageTypes.REMOVE_CHILD_FROM_CONTAINER)
      .registerMessageType<RemoveChildMessage>(MessageTypes.REMOVE_CHILD)
      .registerMessageType<StartApplicationMessage>(MessageTypes.START_APPLICATION)
      .registerMessageType<StopApplicationMessage>(MessageTypes.STOP_APPLICATION)
      .registerMessageType<UnhideInstanceMessage>(MessageTypes.UNHIDE_INSTANCE)
      .registerMessageType<UnhideTextInstanceMessage>(MessageTypes.UNHIDE_TEXT_INSTANCE)
      .apply {
        // TODO: Custom MessageType can be hooked here.
      }
    )
    .create()
    .also(::configureJavalinJson)

  /**
   * Maps Javalin JSON operations to GSON
   *
   * @param gson GSON instance to use for mapping
   */
  private fun configureJavalinJson(
    gson: Gson,
  ) {
    JavalinJson.fromJsonMapper = object : FromJsonMapper {
      override fun <T> map(
        json: String,
        targetClass: Class<T>,
      ): T = gson.fromJson(json, targetClass)
    }

    JavalinJson.toJsonMapper = object : ToJsonMapper {
      override fun map(
        obj: Any,
      ): String = if (obj is Message) gson.toJson(obj,
        Message::class.java) else gson.toJson(obj)
    }
  }
}
