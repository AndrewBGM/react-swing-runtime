package io.github.andrewbgm.reactswingserver.bridge.adapters

import io.github.andrewbgm.reactswingserver.bridge.*
import java.awt.*
import javax.swing.*

class JButtonAdapter : IHostAdapter<JButton> {
  override fun create(
    bridge: Bridge,
    props: Map<String, Any?>
  ): JButton = JButton().also {
    update(bridge, it, props)
  }

  override fun update(
    bridge: Bridge,
    host: JButton,
    changedProps: Map<String, Any?>
  ) {
    host.text = changedProps.getOrDefault("text", host.text) as String?

    val onAction = changedProps["onAction"] as Double?
    if (onAction !== null) {
      host.actionListeners.forEach { host.removeActionListener(it) }
      host.addActionListener {
        bridge.invokeCallback(bridge.ws, onAction.toInt(), listOf())
      }
    }
  }

  override fun appendToContainer(
    bridge: Bridge,
    child: JButton
  ) = error("Cannot have a top level $child")

  override fun removeFromContainer(
    bridge: Bridge,
    child: JButton
  ) = error("Cannot have a top level $child")

  override fun appendChild(
    bridge: Bridge,
    parent: JButton,
    child: Container
  ) = error("Cannot append $child to $parent")

  override fun insertBefore(
    bridge: Bridge,
    parent: JButton,
    child: Container,
    beforeChild: Container
  ) = error("Cannot insert $child before $beforeChild in $parent")

  override fun removeChild(
    bridge: Bridge,
    parent: JButton,
    child: Container
  ) = error("Cannot remove $child from $parent")

  override fun updateText(
    bridge: Bridge,
    parent: JButton,
    text: String
  ) {
    parent.text = text
  }
}
