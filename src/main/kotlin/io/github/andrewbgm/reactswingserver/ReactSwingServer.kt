package io.github.andrewbgm.reactswingserver

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.gson.*
import io.github.andrewbgm.reactswingserver.messages.*
import io.javalin.*
import io.javalin.plugin.json.*

class ReactSwingServer {
  private val app: Javalin by lazy { configureApp() }

  private val bridge: ReactSwingServerBridge = ReactSwingServerBridge()

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
      .ws("/ws") { ws -> bridge.attach(ws) }
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
