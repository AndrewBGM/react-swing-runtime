package io.github.andrewbgm.reactswingserver.bridge

import io.javalin.websocket.*

class ReactSwingServerBridge {
  fun appendChild(
    ws: WsMessageContext,
    parentId: Int,
    childId: Int
  ) {
    TODO("Not yet implemented")
  }

  fun appendChildToContainer(
    ws: WsMessageContext,
    containerId: Int,
    childId: Int
  ) {
    TODO("Not yet implemented")
  }

  fun appendInitialChild(
    ws: WsMessageContext,
    parentId: Int,
    childId: Int
  ) {
    TODO("Not yet implemented")
  }

  fun clearContainer(
    ws: WsMessageContext,
    containerId: Int
  ) {
    TODO("Not yet implemented")
  }

  fun commitTextUpdate(
    ws: WsMessageContext,
    instanceId: Int,
    oldText: String,
    newText: String
  ) {
    TODO("Not yet implemented")
  }

  fun commitUpdate(
    ws: WsMessageContext,
    type: String,
    instanceId: Int,
    changedProps: Map<String, Any?>
  ) {
    TODO("Not yet implemented")
  }

  fun createInstance(
    ws: WsMessageContext,
    instanceId: Int,
    type: String,
    props: Map<String, Any?>
  ) {
    TODO("Not yet implemented")
  }

  fun createTextInstance(
    ws: WsMessageContext,
    instanceId: Int,
    text: String
  ) {
    TODO("Not yet implemented")
  }

  fun freeCallback(
    ws: WsMessageContext,
    callbackId: Int
  ) {
    TODO("Not yet implemented")
  }

  fun insertBefore(
    ws: WsMessageContext,
    parentId: Int,
    childId: Int,
    beforeChildId: Int
  ) {
    TODO("Not yet implemented")
  }

  fun insertInContainerBefore(
    ws: WsMessageContext,
    containerId: Int,
    childId: Int,
    beforeChildId: Int
  ) {
    TODO("Not yet implemented")
  }

  fun invokeCallback(
    ws: WsMessageContext,
    callbackId: Int,
    args: List<Any?>
  ) {
    TODO("Not yet implemented")
  }

  fun removeChildFromContainer(
    ws: WsMessageContext,
    containerId: Int,
    childId: Int
  ) {
    TODO("Not yet implemented")
  }

  fun removeChild(
    ws: WsMessageContext,
    parentId: Int,
    childId: Int
  ) {
    TODO("Not yet implemented")
  }

  fun startApplication(
    ws: WsMessageContext,
    containerId: Int
  ) {
    TODO("Not yet implemented")
  }
}
