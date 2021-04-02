package io.github.andrewbgm.reactswingserver

import com.google.gson.GsonBuilder
import io.github.andrewbgm.reactswingserver.gson.*
import io.github.andrewbgm.reactswingserver.messages.*
import io.javalin.Javalin
import io.javalin.plugin.json.FromJsonMapper
import io.javalin.plugin.json.JavalinJson
import io.javalin.plugin.json.ToJsonMapper
import org.slf4j.LoggerFactory

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
        ws.onMessage { logger.info("Message received: ${it.message()}") }
        ws.onClose { logger.info("Connection closed.") }
        ws.onError { logger.error("Connection error: ${it.error()}") }
      }
  }

  private fun configureGsonMappers() {
    val gson = GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .registerTypeAdapter(Message::class.java, MessageAdapter(
        AppendChildMessage::class,
        AppendChildToContainerMessage::class,
        ClearContainerMessage::class,
        CommitUpdateMessage::class,
        CreateInstanceMessage::class,
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
