package io.github.andrewbgm.reactswingserver.bridge

import io.github.andrewbgm.reactswingserver.message.*
import io.javalin.websocket.*
import org.slf4j.*

class Bridge {
  private val logger = LoggerFactory.getLogger(Bridge::class.java)

  fun appendChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {
    logger.info("appendChild($parentId, $childId)")
  }

  fun appendChildToContainer(
    ws: WsContext,
    containerId: Int,
    childId: Int,
  ) {
    logger.info("appendToContainerChild($containerId, $childId)")
  }

  fun appendInitialChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {
    logger.info("appendInitialChild($parentId, $childId)")
  }

  fun clearContainer(
    ws: WsContext,
    containerId: Int,
  ) {
    logger.info("clearContainer($containerId)")
  }

  fun commitTextUpdate(
    ws: WsContext,
    instanceId: Int,
    text: String,
  ) {
    logger.info("commitTextUpdate($instanceId, $text)")
  }

  fun commitUpdate(
    ws: WsContext,
    instanceId: Int,
    changedProps: Map<String, Any?>,
  ) {
    logger.info("commitUpdate($instanceId, $changedProps)")
  }

  fun createInstance(
    ws: WsContext,
    instanceId: Int,
    type: String,
    props: Map<String, Any?>,
  ) {
    logger.info("createInstance($instanceId, $type, $props)")
  }

  fun createTextInstance(
    ws: WsContext,
    instanceId: Int,
    text: String,
  ) {
    logger.info("createTextInstance($instanceId, $text)")
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
    logger.info("insertBefore($parentId, $childId, $beforeChildId)")
  }

  fun insertInContainerBefore(
    ws: WsContext,
    containerId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    logger.info("insertInContainerBefore($containerId, $childId, $beforeChildId)")
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
    logger.info("removeChildFromContainer($containerId, $childId)")
  }

  fun removeChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {
    logger.info("removeChild($parentId, $childId)")
  }

  fun startApplication() {
    logger.info("startApplication()")
  }
}
