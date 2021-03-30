package io.github.andrewbgm.reactswingserver.network

import io.github.andrewbgm.reactswingserver.network.messages.*
import io.javalin.websocket.WsHandler
import io.javalin.websocket.WsMessageContext
import io.javalin.websocket.WsMessageHandler
import org.slf4j.LoggerFactory

class ReactSwingBridge : WsMessageHandler {
  private val logger = LoggerFactory.getLogger(ReactSwingBridge::class.java)

  fun attach(
    ws: WsHandler,
  ) {
    ws.onMessage(this)
  }

  override fun handleMessage(
    ctx: WsMessageContext,
  ) {
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

  private fun appendChild(
    parentId: Int,
    childId: Int,
  ) {
    logger.info("appendChild($parentId, $childId)")
  }

  private fun appendChildToContainer(
    containerId: Int,
    childId: Int,
  ) {
    logger.info("appendChildToContainer($containerId, $childId)")
  }

  private fun appendInitialChild(
    parentId: Int,
    childId: Int,
  ) {
    logger.info("appendInitialChild($parentId, $childId)")
  }

  private fun clearContainer(
    containerId: Int,
  ) {
    logger.info("clearContainer($containerId)")
  }

  private fun commitUpdate(
    instanceId: Int,
    prevProps: Map<String, Any?>,
    nextProps: Map<String, Any?>,
  ) {
    logger.info("commitUpdate($instanceId, $prevProps, $nextProps)")
  }

  private fun createInstance(
    instanceId: Int,
    type: String,
    props: Map<String, Any?>,
  ) {
    logger.info("createInstance($instanceId, $type, $props)")
  }

  private fun hideInstance(
    instanceId: Int,
  ) {
    logger.info("hideInstance($instanceId)")
  }

  private fun insertBefore(
    parentId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    logger.info("insertBefore($parentId, $childId, $beforeChildId)")
  }

  private fun insertInContainerBefore(
    containerId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    logger.info("insertInContainerBefore($containerId, $childId, $beforeChildId)")
  }

  private fun invokeCallback(
    callbackId: Int,
    args: List<Any?>,
  ) {
    logger.info("invokeCallback($callbackId, $args)")
  }

  private fun removeChildFromContainer(
    containerId: Int,
    childId: Int,
  ) {
    logger.info("removeChildFromContainer($containerId, $childId)")
  }

  private fun removeChild(
    parentId: Int,
    childId: Int,
  ) {
    logger.info("removeChild($parentId, $childId)")
  }

  private fun unhideInstance(
    instanceId: Int,
    props: Map<String, Any?>,
  ) {
    logger.info("unhideInstance($instanceId, $props)")
  }
}
