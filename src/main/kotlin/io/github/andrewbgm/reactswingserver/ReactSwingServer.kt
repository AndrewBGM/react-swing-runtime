package io.github.andrewbgm.reactswingserver

import com.google.gson.GsonBuilder
import io.github.andrewbgm.reactswingserver.bridge.*
import io.javalin.Javalin
import io.javalin.plugin.json.FromJsonMapper
import io.javalin.plugin.json.JavalinJson
import io.javalin.plugin.json.ToJsonMapper

class ReactSwingServer {
  private val bridge: ReactSwingBridge = ReactSwingBridge()
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
      .ws("/ws") { ws -> bridge.attach(ws) }
  }

  private fun configureGsonMappers() {
    val gson = GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .registerTypeAdapter(IMessage::class.java, MessageAdapter(
        AppendChildMessage::class,
        AppendChildToContainerMessage::class,
        AppendInitialChildMessage::class,
        ClearContainerMessage::class,
        CommitMountMessage::class,
        CommitTextUpdateMessage::class,
        CommitUpdateMessage::class,
        CreateInstanceMessage::class,
        CreateTextInstanceMessage::class,
        HideInstanceMessage::class,
        HideTextInstanceMessage::class,
        InsertBeforeMessage::class,
        InsertInContainerBeforeMessage::class,
        InvokeCallbackMessage::class,
        PreparePortalMountMessage::class,
        RemoveChildFromContainerMessage::class,
        RemoveChildMessage::class,
        ResetAfterCommitMessage::class,
        ResetTextContentMessage::class,
        UnhideInstanceMessage::class,
        UnhideTextInstanceMessage::class
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
