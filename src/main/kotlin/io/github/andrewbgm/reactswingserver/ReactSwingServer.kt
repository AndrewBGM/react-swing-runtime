package io.github.andrewbgm.reactswingserver

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.message.*
import io.github.andrewbgm.reactswingserver.impl.message.handlers.*
import io.github.andrewbgm.reactswingserver.impl.message.messages.*
import io.javalin.*
import io.javalin.plugin.json.*
import io.javalin.websocket.*
import kotlin.reflect.*

/**
 * Provides utilities for managing a Javalin server instance
 * configured for React Swing.
 */
class ReactSwingServer {
  /**
   * Configured Javalin instance.
   */
  private val app = configureApp()

  /**
   * Message adapter instance.
   */
  private val messageAdapter = MessageAdapter()

  /**
   * Message bus instance.
   */
  private val messageBus = MessageBus()

  init {
    configureGson()

    registerMessage(MessageType.CREATE_VIEW, CreateViewMessageHandler())
    registerMessage(MessageType.CREATE_TEXT_VIEW, CreateTextViewMessageHandler())
    registerMessage(MessageType.UPDATE_VIEW, UpdateViewMessageHandler())
    registerMessage(MessageType.UPDATE_TEXT_VIEW, UpdateTextViewMessageHandler())

    registerMessage(MessageType.SET_CHILDREN, SetChildrenMessageHandler())
    registerMessage(MessageType.APPEND_CHILD, AppendChildMessageHandler())
    registerMessage(MessageType.REMOVE_CHILD, RemoveChildMessageHandler())
    registerMessage(MessageType.INSERT_CHILD, InsertChildMessageHandler())

    registerMessage<InvokeCallbackMessage>(MessageType.INVOKE_CALLBACK)
  }

  /**
   * Registers a message type and corresponding class,
   * along with an optional handler.
   *
   * @param T Message class.
   * @param type Message type.
   * @param handler Optional message handler.
   */
  inline fun <reified T : IMessage> registerMessage(
    type: IMessageType,
    handler: IMessageHandler<T>? = null
  ): ReactSwingServer = registerMessage(type, T::class, handler)

  /**
   * Registers a message type and corresponding class,
   * along with an optional handler.
   *
   * @param type Message type.
   * @param clazz Message class.
   * @param handler Optional message handler.
   */
  fun <T : IMessage> registerMessage(
    type: IMessageType,
    clazz: KClass<T>,
    handler: IMessageHandler<T>? = null
  ): ReactSwingServer = this.apply {
    messageAdapter.registerType(type, clazz)
    handler?.let {
      messageBus.subscribe(clazz, it)
    }
  }

  /**
   * Synchronously starts the server.
   */
  fun start(
    port: Int,
  ): ReactSwingServer = this.apply {
    app.start(port)
  }

  /**
   * Synchronously stops the server.
   */
  fun stop(): ReactSwingServer = this.apply {
    app.stop()
  }

  /**
   * Configures a new Javalin instance and returns it.
   */
  private fun configureApp(): Javalin = Javalin.create()
    .ws("/") { ws ->
      ws.onMessage(::handleMessage)
    }

  /**
   * Configures a new Gson instance and returns it.
   */
  private fun configureGson(): Gson = GsonBuilder()
    .excludeFieldsWithoutExposeAnnotation()
    .registerTypeAdapter(IMessage::class.java, messageAdapter)
    .create().also(::configureJsonMapping)

  /**
   * Configures Javalin JSON mapping for use with Gson.
   *
   * @param gson Gson instance.
   */
  private fun configureJsonMapping(
    gson: Gson
  ) {
    JavalinJson.fromJsonMapper = object : FromJsonMapper {
      override fun <T> map(
        json: String,
        targetClass: Class<T>
      ): T = gson.fromJson(json, targetClass)
    }

    JavalinJson.toJsonMapper = object : ToJsonMapper {
      override fun map(
        obj: Any
      ): String = if (obj is IMessage) gson.toJson(
        obj,
        IMessage::class.java
      ) else gson.toJson(obj)
    }
  }

  /**
   * Decodes incoming messages and invokes message handlers.
   *
   * @param ctx Message context.
   */
  private fun handleMessage(
    ctx: WsMessageContext
  ) {
    val message = ctx.message<IMessage>()
    messageBus.publish(message)
  }
}
