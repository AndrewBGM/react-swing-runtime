package io.github.andrewbgm.reactswingserver.bridge.adapter

import io.github.andrewbgm.reactswingserver.bridge.*
import java.awt.*
import javax.swing.*

class JButtonHostAdapter : IHostAdapter<JButton> {
  override fun create(
    bridge: Bridge,
    props: Map<String, Any?>
  ): JButton = JButton().also {
    update(bridge, it, null, props)
  }

  override fun update(
    bridge: Bridge,
    host: JButton,
    oldProps: Map<String, Any?>?,
    newProps: Map<String, Any?>
  ) {
    val oldOnAction = oldProps?.getOrDefault("onAction", null) as Double?
    val newOnAction = newProps.getOrDefault("onAction", null) as Double?

    if (oldOnAction !== null && oldOnAction !== newOnAction) {
      bridge.freeCallback(oldOnAction)
    }

    if (newOnAction !== null && oldOnAction !== newOnAction) {
      host.actionListeners.forEach { host.removeActionListener(it) }
      host.addActionListener {
        bridge.invokeCallback(newOnAction)
      }
    }
  }

  override fun applyText(
    bridge: Bridge,
    host: JButton,
    text: String
  ) {
    host.text = text
  }

  override fun appendToContainer(
    bridge: Bridge,
    host: JButton
  ) = error("Cannot append $host to container")

  override fun insertInContainerBefore(
    bridge: Bridge,
    host: JButton,
    beforeChild: Container
  ) = error("Cannot insert $host before $beforeChild in container")

  override fun removeFromContainer(
    bridge: Bridge,
    host: JButton
  ) = error("Cannot remove $host from container")

  override fun appendChild(
    bridge: Bridge,
    host: JButton,
    child: Container
  ) = error("Cannot append $child to $host")

  override fun insertBefore(
    bridge: Bridge,
    host: JButton,
    child: Container,
    beforeChild: Container
  ) = error("Cannot insert $child before $beforeChild in $host")

  override fun removeChild(
    bridge: Bridge,
    host: JButton,
    child: Container
  ) = error("Cannot remove $child from $host")
}
