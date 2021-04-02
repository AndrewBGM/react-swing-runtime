package io.github.andrewbgm.reactswingserver.bridge

import io.javalin.websocket.WsConnectContext
import io.javalin.websocket.WsConnectHandler
import io.javalin.websocket.WsContext
import io.javalin.websocket.WsHandler
import io.javalin.websocket.WsMessageContext
import io.javalin.websocket.WsMessageHandler
import org.slf4j.LoggerFactory

class ReactSwingBridge : WsConnectHandler, WsMessageHandler {
  private val logger = LoggerFactory.getLogger(ReactSwingBridge::class.java)
  private lateinit var ws: WsContext

  fun attach(
    ws: WsHandler,
  ) {
    ws.onMessage(this)
  }

  override fun handleConnect(
    ctx: WsConnectContext,
  ) {
    ws = ctx
  }

  override fun handleMessage(
    ctx: WsMessageContext,
  ) {
    when (val message = ctx.message<IMessage>()) {
      is AppendChildMessage -> appendChild(message.parentId, message.childId)
      is AppendChildToContainerMessage ->
        appendChildToContainer(message.containerId, message.childId)
      is AppendInitialChildMessage -> appendInitialChild(message.parentId,
        message.childId)
      is ClearContainerMessage -> clearContainer(message.containerId)
      is CommitMountMessage -> commitMount(message.instance,
        message.type,
        message.props)
      is CommitTextUpdateMessage -> commitTextUpdate(message.textInstance,
        message.oldText,
        message.newText)
      is CommitUpdateMessage ->
        commitUpdate(message.instanceId,
          message.type,
          message.prevProps,
          message.prevProps)
      is CreateInstanceMessage -> createInstance(message.instanceId,
        message.type,
        message.props)
      is CreateTextInstanceMessage -> createTextInstance(message.instanceId,
        message.text)
      is HideInstanceMessage -> hideInstance(message.instanceId)
      is HideTextInstanceMessage -> hideTextInstance(message.textInstance)
      is InsertBeforeMessage ->
        insertBefore(message.parentId, message.childId, message.beforeChildId)
      is InsertInContainerBeforeMessage ->
        insertInContainerBefore(message.containerId,
          message.childId,
          message.beforeChildId)
      is InvokeCallbackMessage -> invokeCallback(message.callbackId,
        message.args)
      is PreparePortalMountMessage -> preparePortalMount(message.containerInfo)
      is RemoveChildFromContainerMessage ->
        removeChildFromContainer(message.containerId, message.childId)
      is RemoveChildMessage -> removeChild(message.parentId, message.childId)
      is ResetAfterCommitMessage -> resetAfterCommit(message.containerInfo)
      is ResetTextContentMessage -> resetTextContent(message.instance)
      is UnhideInstanceMessage -> unhideInstance(message.instanceId,
        message.props)
      is UnhideTextInstanceMessage -> unhideTextInstance(message.textInstance,
        message.text)
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

  private fun commitMount(
    instance: Int,
    type: String,
    props: Map<String, Any?>,
  ) {
    logger.info("commitMount($instance, $type, $props)")
  }

  private fun commitTextUpdate(
    textInstance: Int,
    oldText: String,
    newText: String,
  ) {
    logger.info("commitTextUpdate($textInstance, $oldText, $newText)")
  }

  private fun commitUpdate(
    instanceId: Int,
    type: String,
    prevProps: Map<String, Any?>,
    nextProps: Map<String, Any?>,
  ) {
    logger.info("commitUpdate($instanceId, $type, $prevProps, $nextProps)")
  }

  private fun createInstance(
    instanceId: Int,
    type: String,
    props: Map<String, Any?>,
  ) {
    logger.info("createInstance($instanceId, $type, $props)")
  }

  private fun createTextInstance(
    instanceId: Int,
    text: String,
  ) {
    logger.info("createTextInstance($instanceId, $text)")
  }

  private fun hideInstance(
    instanceId: Int,
  ) {
    logger.info("hideInstance($instanceId)")
  }

  private fun hideTextInstance(
    textInstance: Int,
  ) {
    logger.info("hideTextInstance($textInstance)")
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

  private fun preparePortalMount(
    containerInfo: Int,
  ) {
    logger.info("preparePortalMount($containerInfo)")
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

  private fun resetAfterCommit(
    containerInfo: Int,
  ) {
    logger.info("resetAfterCommit($containerInfo)")
  }

  private fun resetTextContent(
    instance: Int,
  ) {
    logger.info("resetTextContent($instance)")
  }

  private fun unhideInstance(
    instanceId: Int,
    props: Map<String, Any?>,
  ) {
    logger.info("unhideInstance($instanceId, $props)")
  }

  private fun unhideTextInstance(
    textInstance: Int,
    text: String,
  ) {
    logger.info("unhideTextInstance($textInstance, $text)")
  }
}
