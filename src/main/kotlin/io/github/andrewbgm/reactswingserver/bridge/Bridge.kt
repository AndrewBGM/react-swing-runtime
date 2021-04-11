package io.github.andrewbgm.reactswingserver.bridge

import io.github.andrewbgm.reactswingserver.message.*
import io.javalin.websocket.*

class Bridge {
  fun appendChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {

  }

  fun appendChildToContainer(
    ws: WsContext,
    containerId: Int,
    childId: Int,
  ) {

  }

  fun appendInitialChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {

  }

  fun clearContainer(
    ws: WsContext,
    containerId: Int,
  ) {

  }

  fun commitTextUpdate(
    ws: WsContext,
    instanceId: Int,
    text: String,
  ) {

  }

  fun commitUpdate(
    ws: WsContext,
    instanceId: Int,
    changedProps: Map<String, Any?>,
  ) {

  }

  fun createInstance(
    ws: WsContext,
    instanceId: Int,
    type: String,
    props: Map<String, Any?>,
  ) {

  }

  fun createTextInstance(
    ws: WsContext,
    instanceId: Int,
    text: String,
  ) {

  }

  fun freeCallback(
    ws: WsContext,
    callbackId: Int,
  ) {
    ws.send(FreeCallbackMessage(callbackId))
  }

  fun insertBefore(
    ws: WsContext,
    parentId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {

  }

  fun insertInContainerBefore(
    ws: WsContext,
    containerId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {

  }

  fun invokeCallback(
    ws: WsContext,
    callbackId: Int,
    args: List<Any?>,
  ) {
    ws.send(InvokeCallbackMessage(callbackId, args))
  }

  fun removeChildFromContainer(
    ws: WsContext,
    containerId: Int,
    childId: Int,
  ) {

  }

  fun removeChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {

  }
}
