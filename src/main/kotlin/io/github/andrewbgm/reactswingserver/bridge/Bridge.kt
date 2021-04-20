package io.github.andrewbgm.reactswingserver.bridge

import io.github.andrewbgm.reactswingserver.bridge.adapter.*
import io.github.andrewbgm.reactswingserver.message.*
import io.javalin.websocket.*
import java.awt.*
import kotlin.reflect.*

class Bridge(
  vararg pairs: Pair<KClass<out Container>, IHostAdapter<out Container>>,
) {
  companion object {
    fun setContext(
      bridge: Bridge,
      ctx: WsContext,
    ) {
      bridge._ctx = ctx
    }
  }

  private var _ctx: WsContext? = null
  private val ctx: WsContext
    get() = _ctx ?: error("No web-socket context configured for $this")

  private val hostAdapterByType: Map<KClass<out Container>, IHostAdapter<out Container>> =
    pairs.toMap()
  private val hostAdapterByTypeName: Map<String, IHostAdapter<out Container>> =
    hostAdapterByType.map { Pair(it.key.simpleName!!, it.value) }.toMap()

  fun appendChild(
    parentId: Int,
    childId: Int,
  ) {
    TODO("Not implemented yet")
  }

  fun appendChildToContainer(
    containerId: Int,
    childId: Int,
  ) {
    TODO("Not implemented yet")
  }

  fun appendInitialChild(
    parentId: Int,
    childId: Int,
  ) {
    TODO("Not implemented yet")
  }

  fun clearContainer(
    containerId: Int,
  ) {
    TODO("Not implemented yet")
  }

  fun commitTextUpdate(
    instanceId: Int,
    text: String,
  ) {
    TODO("Not implemented yet")
  }

  fun commitUpdate(
    instanceId: Int,
    oldProps: Map<String, Any?>?,
    newProps: Map<String, Any?>,
  ) {
    TODO("Not implemented yet")
  }

  fun createInstance(
    instanceId: Int,
    type: String,
    props: Map<String, Any?>,
  ) {
    TODO("Not implemented yet")
  }

  fun createTextInstance(
    instanceId: Int,
    text: String,
  ) {
    TODO("Not implemented yet")
  }

  fun freeCallback(
    callbackId: Int,
  ) {
    ctx.send(FreeCallbackMessage(callbackId))
  }

  fun insertBefore(
    parentId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    TODO("Not implemented yet")
  }

  fun insertInContainerBefore(
    containerId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    TODO("Not implemented yet")
  }

  fun invokeCallback(
    callbackId: Int,
    args: List<Any?>,
  ) {
    ctx.send(InvokeCallbackMessage(callbackId, args))
  }

  fun removeChildFromContainer(
    containerId: Int,
    childId: Int,
  ) {
    TODO("Not implemented yet")
  }

  fun removeChild(
    parentId: Int,
    childId: Int,
  ) {
    TODO("Not implemented yet")
  }

  fun startApplication() {
    TODO("Not implemented yet")
  }
}
