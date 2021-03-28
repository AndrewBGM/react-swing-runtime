package io.github.andrewbgm.reactswingserver

import com.google.gson.GsonBuilder
import io.github.andrewbgm.reactswingserver.gson.MessageAdapter
import io.github.andrewbgm.reactswingserver.messages.AppendChildMessage
import io.github.andrewbgm.reactswingserver.messages.AppendChildToContainerMessage
import io.github.andrewbgm.reactswingserver.messages.AppendInitialChildMessage
import io.github.andrewbgm.reactswingserver.messages.ClearContainerMessage
import io.github.andrewbgm.reactswingserver.messages.CommitUpdateMessage
import io.github.andrewbgm.reactswingserver.messages.CreateInstanceMessage
import io.github.andrewbgm.reactswingserver.messages.HideInstanceMessage
import io.github.andrewbgm.reactswingserver.messages.IMessage
import io.github.andrewbgm.reactswingserver.messages.InsertBeforeMessage
import io.github.andrewbgm.reactswingserver.messages.InsertInContainerBeforeMessage
import io.github.andrewbgm.reactswingserver.messages.InvokeCallbackMessage
import io.github.andrewbgm.reactswingserver.messages.RemoveChildFromContainerMessage
import io.github.andrewbgm.reactswingserver.messages.RemoveChildMessage
import io.github.andrewbgm.reactswingserver.messages.UnhideInstanceMessage
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
        AppendChildMessage::class,
        AppendChildToContainerMessage::class,
        AppendInitialChildMessage::class,
        ClearContainerMessage::class,
        CommitUpdateMessage::class,
        CreateInstanceMessage::class,
        HideInstanceMessage::class,
        InsertBeforeMessage::class,
        InsertInContainerBeforeMessage::class,
        InvokeCallbackMessage::class,
        RemoveChildFromContainerMessage::class,
        RemoveChildMessage::class,
        UnhideInstanceMessage::class,
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
