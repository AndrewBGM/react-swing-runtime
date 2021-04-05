package io.github.andrewbgm.reactswingserver.bridge

import io.github.andrewbgm.reactswingserver.messages.*
import io.javalin.websocket.*
import java.awt.*
import javax.swing.*

class ReactSwingServerBridge {
  private val rootInstances: MutableList<HostInstance> = mutableListOf()
  private val instanceById: MutableMap<Int, Instance> = mutableMapOf()

  fun createTextInstance(
    ws: WsContext,
    instanceId: Int,
    text: String,
  ) {
    instanceById[instanceId] = TextInstance(text, null)
  }

  fun createInstance(
    ws: WsContext,
    instanceId: Int,
    type: String,
    props: Map<String, Any?>,
  ) {
    val component = createComponent(ws, type, props)
    instanceById[instanceId] = HostInstance(component)
  }

  fun appendInitialChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {
    appendChild(ws, parentId, childId)
  }

  fun appendChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {
    val parentInstance =
      instanceById[parentId] ?: error("#$parentId is not a valid instance ID.")
    val childInstance =
      instanceById[childId] ?: error("#$childId is not a valid instance ID.")

    if (parentInstance is HostInstance && childInstance is TextInstance) {
      parentInstance += childInstance
      childInstance.host = parentInstance

      val updatedText =
        parentInstance.textInstances.joinToString("") { it.text }

      when (val component = parentInstance.component) {
        is JButton -> component.text = updatedText
      }
    } else if (parentInstance is HostInstance && childInstance is HostInstance) {
      parentInstance.component.add(childInstance.component)
    } else error("Not sure what you want me to do here, chief.")
  }

  fun clearContainer(
    ws: WsContext,
    containerId: Int,
  ) {
    // NOOP
  }

  fun appendChildToContainer(
    ws: WsContext,
    containerId: Int,
    childId: Int,
  ) {
    if (containerId != 0) {
      error("#$containerId is not a valid container ID.")
    }

    when (val childInstance = instanceById[childId]
      ?: error("#$childId is not a valid instance ID.")) {
      is HostInstance -> when (childInstance.component) {
        is JFrame -> rootInstances.add(childInstance)
        else -> error("Cannot add $childId to $containerId (not a JFrame)")
      }
      else -> error("Cannot add $childId to #$containerId")
    }
  }

  fun startApplication(
    ws: WsContext,
    containerId: Int,
  ) {
    if (containerId != 0) {
      error("#$containerId is not a valid container ID.")
    }

    rootInstances.forEach {
      val component = it.component as JFrame
      component.pack()
      component.setLocationRelativeTo(null)
      component.isVisible = true
    }
  }

  fun commitTextUpdate(
    instanceId: Int,
    oldText: String,
    newText: String,
  ) {
    val instance = instanceById[instanceId]
    if (instance !is TextInstance) {
      error("#$instanceId is not a valid text instance ID.")
    }

    instance.text = newText

    val parentInstance = instance.host
    if (parentInstance != null) {
      val updatedText =
        parentInstance.textInstances.joinToString("") { it.text }

      when (val component = parentInstance.component) {
        is JButton -> component.text = updatedText
      }
    }
  }

  fun removeChild(
    parentId: Int,
    childId: Int,
  ) {
    val parentInstance =
      instanceById[parentId] ?: error("#$parentId is not a valid instance ID.")
    val childInstance =
      instanceById[childId] ?: error("#$childId is not a valid instance ID.")

    if (parentInstance is HostInstance && childInstance is TextInstance) {
      parentInstance -= childInstance
      childInstance.host = null

      val updatedText =
        parentInstance.textInstances.joinToString("") { it.text }

      when (val component = parentInstance.component) {
        is JButton -> component.text = updatedText
      }
    } else if (parentInstance is HostInstance && childInstance is HostInstance) {
      parentInstance.component.remove(childInstance.component)
    } else error("Not sure what you want me to do here, chief.")
  }

  private fun createComponent(
    ws: WsContext,
    type: String,
    props: Map<String, Any?>
  ): Container = when (type) {
    "JFrame" -> JFrame().also {
      it.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
      val title = props["title"] as String?

      it.title = title ?: it.title
    }
    "JPanel" -> JPanel()
    "JButton" -> JButton().also {
      val onAction = props["onAction"] as Double?

      if (onAction != null) {
        it.addActionListener {
          ws.send(InvokeCallbackMessage(onAction.toInt(), listOf()))
        }
      }
    }
    else -> error("Cannot create Swing component for $type.")
  }
}
