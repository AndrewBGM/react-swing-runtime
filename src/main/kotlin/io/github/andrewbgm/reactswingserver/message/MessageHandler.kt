package io.github.andrewbgm.reactswingserver.message

import io.javalin.websocket.WsMessageContext
import io.javalin.websocket.WsMessageHandler

class MessageHandler : WsMessageHandler {
  override fun handleMessage(
    ctx: WsMessageContext,
  ) {
    when (val message = ctx.message<IMessage>()) {
      is AppendChildMessage -> appendChild(message.childId, message.parentId)
      is AppendChildToContainerMessage ->
        appendChildToContainer(message.containerId, message.childId)
      is AppendInitialChildMessage -> appendInitialChild(message.parentId, message.childId)
      is ClearContainerMessage -> clearContainer(message.containerId)
      is CommitUpdateMessage ->
        commitUpdate(message.instanceId, message.prevProps, message.prevProps)
      is CreateInstanceMessage -> createInstance(message.instanceId, message.type, message.props)
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
    childId: Int,
    parentId: Int,
  ) {
    TODO("Not yet implemented")
  }

  private fun appendChildToContainer(
    containerId: Int,
    childId: Int,
  ) {
    TODO("Not yet implemented")
  }

  private fun appendInitialChild(
    parentId: Int,
    childId: Int,
  ) {
    TODO("Not yet implemented")
  }

  private fun clearContainer(
    containerId: Int,
  ) {
    TODO("Not yet implemented")
  }

  private fun commitUpdate(
    instanceId: Int,
    prevProps: Map<String, Any?>,
    prevProps1: Map<String, Any?>,
  ) {
    TODO("Not yet implemented")
  }

  private fun createInstance(
    instanceId: Int,
    type: String,
    props: Map<String, Any?>,
  ) {
    TODO("Not yet implemented")
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
    TODO("Not yet implemented")
  }

  private fun insertInContainerBefore(
    containerId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    TODO("Not yet implemented")
  }

  private fun invokeCallback(
    callbackId: Int,
    args: List<Any?>,
  ) {

  }

  private fun removeChildFromContainer(
    containerId: Int,
    childId: Int,
  ) {
    TODO("Not yet implemented")
  }

  private fun removeChild(
    parentId: Int,
    childId: Int,
  ) {
    TODO("Not yet implemented")
  }

  private fun unhideInstance(
    instanceId: Int,
    props: Map<String, Any?>,
  ) {
    TODO("Not yet implemented")
  }
}
