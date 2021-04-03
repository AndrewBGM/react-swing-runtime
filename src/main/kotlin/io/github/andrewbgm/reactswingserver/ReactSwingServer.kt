package io.github.andrewbgm.reactswingserver

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.gson.*
import io.github.andrewbgm.reactswingserver.messages.*
import io.javalin.*
import io.javalin.plugin.json.*
import org.slf4j.*

class ReactSwingServer {
  private val logger = LoggerFactory.getLogger(ReactSwingServer::class.java)

  private val app: Javalin by lazy { configureApp() }

  fun start(
    port: Int,
  ) {
    app.start(port)
  }

  fun stop() {
    app.stop()
  }

  private fun configureApp(): Javalin {
    configureGsonMappers()

    return Javalin
      .create()
      .ws("/ws") { ws ->
        ws.onConnect { logger.info("Connection opened.") }
        ws.onMessage { handleMessage(it.message<Message>()) }
        ws.onClose { logger.info("Connection closed.") }
        ws.onError { logger.error("Connection error: ${it.error()}") }
      }
  }

  private fun handleMessage(
    message: Message,
  ) {
    logger.info("Message received: $message")
  }

  private fun configureGsonMappers() {
    val gson = GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .registerTypeAdapter(Message::class.java, MessageAdapter(
        AppendChildMessage::class,
        AppendChildToContainerMessage::class,
        AppendInitialChildMessage::class,
        ClearContainerMessage::class,
        CommitTextUpdateMessage::class,
        CommitUpdateMessage::class,
        CreateInstanceMessage::class,
        CreateTextInstanceMessage::class,
        InsertBeforeMessage::class,
        InsertInContainerBeforeMessage::class,
        RemoveChildFromContainerMessage::class,
        RemoveChildMessage::class,
      ))
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
      ): String = if (obj is Message) gson.toJson(obj,
        Message::class.java) else gson.toJson(obj)
    }
  }
}
