package io.github.andrewbgm.reactswingserver

import com.google.gson.GsonBuilder
import io.github.andrewbgm.reactswingserver.gson.MessageAdapter
import io.github.andrewbgm.reactswingserver.messages.IMessage
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
        ws.onMessage {
          val message = it.message<IMessage>()
          logger.info(message.toString())
        }
      }
  }

  private fun configureGsonMappers() {
    val gson = GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .registerTypeAdapter(IMessage::class.java, MessageAdapter(
        // TODO
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
      ): String = when (obj) {
        is IMessage -> gson.toJson(obj, IMessage::class.java)
        else -> gson.toJson(obj)
      }
    }
  }
}
