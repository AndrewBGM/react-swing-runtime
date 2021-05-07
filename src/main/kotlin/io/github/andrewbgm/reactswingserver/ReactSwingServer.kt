package io.github.andrewbgm.reactswingserver

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.message.*
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
   * Configured Gson instance
   */
  private val gson = configureGson()

  /**
   * Message adapter instance.
   */
  private val messageAdapter = MessageAdapter()

  /**
   * Message bus instance.
   */
  private val messageBus = MessageBus()

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
   * Reads and dispatches incoming messages.
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
