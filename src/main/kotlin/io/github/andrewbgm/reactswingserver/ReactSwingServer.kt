package io.github.andrewbgm.reactswingserver

import com.google.gson.GsonBuilder
import io.github.andrewbgm.reactswingserver.message.*
import io.javalin.Javalin
import io.javalin.plugin.json.FromJsonMapper
import io.javalin.plugin.json.JavalinJson
import io.javalin.plugin.json.ToJsonMapper
import java.awt.Container
import javax.swing.JFrame
import javax.swing.JMenuBar

class ReactSwingServer {
  private val app: Javalin by lazy { configureApp() }
  private val topLevelFrames: MutableList<JFrame> = mutableListOf()
  private val componentsById: MutableMap<Int, Container?> = mutableMapOf()

  fun start(
    port: Int,
  ) {
    app.start(port)
  }

  fun stop() {
    app.stop()
  }

  private fun appendChild(
    parentId: Int,
    childId: Int,
  ) {
    val parentComponent = componentsById[parentId] ?: error("No component #$parentId.")
    val childComponent = componentsById[childId] ?: error("No component #$childId.")

    if (parentComponent is JFrame) {
      if (childComponent is JMenuBar) {
        parentComponent.jMenuBar = childComponent
      } else {
        parentComponent.contentPane = childComponent
      }
    } else {
      parentComponent.add(childComponent)
    }
  }

  private fun appendChildToContainer(
    containerId: Int,
    childId: Int,
  ) {
    val childComponent = componentsById[childId] ?: error("No component #$childId.")

    assert(containerId == 0) { "Container should always be 0." }
    assert(childComponent is JFrame) { "Top level component #$childId must be JFrame." }

    topLevelFrames.add(childComponent as JFrame)
  }

  private fun appendInitialChild(
    parentId: Int,
    childId: Int,
  ) {
    appendChild(parentId, childId)
  }

  private fun clearContainer(
    containerId: Int,
  ) {
    assert(containerId == 0) { "Container should always be 0." }

    topLevelFrames.forEach { it.dispose() }
    topLevelFrames.clear()
  }

  private fun commitUpdate(
    instanceId: Int,
    prevProps: Map<String, Any?>,
    nextProps: Map<String, Any?>,
  ) {
    val component = componentsById[instanceId] ?: error("No component #$instanceId.")
    updateComponent(component, prevProps, nextProps)
  }

  private fun createInstance(
    instanceId: Int,
    type: String,
    props: Map<String, Any?>,
  ) {
    val component = createComponent(type, props)
    componentsById[instanceId] = component
  }

  private fun hideInstance(
    instanceId: Int,
  ) {
    TODO("Not yet implemented")
  }

  private fun insertBefore(
    parentId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    val parentComponent = componentsById[parentId] ?: error("No component #$parentId.")
    val childComponent = componentsById[childId] ?: error("No component #$childId.")
    val beforeChildComponent = componentsById[childId] ?: error("No component #$beforeChildId.")

    val idx = parentComponent.components.indexOf(beforeChildComponent)
    if (parentComponent.components.contains(childComponent)) {
      parentComponent.remove(childComponent)
    }

    parentComponent.add(childComponent, idx)
  }

  private fun insertInContainerBefore(
    containerId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    val childComponent = componentsById[childId] ?: error("No component #$childId.")
    val beforeChildComponent = componentsById[childId] ?: error("No component #$beforeChildId.")

    assert(containerId == 0) { "Container should always be 0." }
    assert(childComponent is JFrame) { "Top level component #$childId must be JFrame." }
    assert(beforeChildComponent is JFrame) { "Top level component #$beforeChildId must be JFrame." }

    // This is probably unnecessary, but nice to keep the order at least.
    val idx = topLevelFrames.indexOf(beforeChildComponent as JFrame)
    topLevelFrames.add(idx, childComponent as JFrame)
  }

  private fun invokeCallback(
    callbackId: Int,
    args: List<Any?>,
  ) {
    TODO("Not yet implemented")
  }

  private fun removeChildFromContainer(
    containerId: Int,
    childId: Int,
  ) {
    val childComponent = componentsById[childId] ?: error("No component #$childId.")

    assert(containerId == 0) { "Container should always be 0." }
    assert(childComponent is JFrame) { "Top level component #$childId must be JFrame." }

    childComponent as JFrame
    childComponent.dispose()
    topLevelFrames.remove(childComponent)
  }

  private fun removeChild(
    parentId: Int,
    childId: Int,
  ) {
    val parentComponent = componentsById[parentId] ?: error("No component #$parentId.")
    val childComponent = componentsById[childId] ?: error("No component #$childId.")

    if (parentComponent is JFrame) {
      if (childComponent is JMenuBar) {
        parentComponent.jMenuBar = null
      } else {
        parentComponent.contentPane = null
      }
    } else {
      parentComponent.remove(childComponent)
    }
  }

  private fun unhideInstance(
    instanceId: Int,
    props: Map<String, Any?>,
  ) {
    TODO("Not yet implemented")
  }

  private fun createComponent(
    type: String,
    props: Map<String, Any?>,
  ): Container {
    TODO("Not yet implemented")
  }

  private fun updateComponent(
    component: Container,
    prevProps: Map<String, Any?>,
    nextProps: Map<String, Any?>,
  ) {
    TODO("Not yet implemented")
  }

  private fun configureApp(): Javalin {
    configureGsonMappers()

    return Javalin
      .create()
      .ws("/ws") { ws ->
        ws.onMessage { ctx ->
          when (val message = ctx.message<IMessage>()) {
            is AppendChildMessage -> appendChild(message.parentId, message.childId)
            is AppendChildToContainerMessage ->
              appendChildToContainer(message.containerId, message.childId)
            is AppendInitialChildMessage -> appendInitialChild(message.parentId, message.childId)
            is ClearContainerMessage -> clearContainer(message.containerId)
            is CommitUpdateMessage ->
              commitUpdate(message.instanceId, message.prevProps, message.prevProps)
            is CreateInstanceMessage -> createInstance(message.instanceId,
              message.type,
              message.props)
            is HideInstanceMessage -> hideInstance(message.instanceId)
            is InsertBeforeMessage ->
              insertBefore(message.parentId, message.childId, message.beforeChildId)
            is InsertInContainerBeforeMessage ->
              insertInContainerBefore(message.containerId, message.childId, message.beforeChildId)
            is InvokeCallbackMessage -> invokeCallback(message.callbackId, message.args)
            is RemoveChildFromContainerMessage ->
              removeChildFromContainer(message.containerId, message.childId)
            is RemoveChildMessage -> removeChild(message.parentId, message.childId)
            is UnhideInstanceMessage -> unhideInstance(message.instanceId, message.props)
            else -> error("Unsupported message $message.")
          }
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
