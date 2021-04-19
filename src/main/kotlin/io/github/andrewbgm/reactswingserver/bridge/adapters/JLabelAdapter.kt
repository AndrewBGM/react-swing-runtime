package io.github.andrewbgm.reactswingserver.bridge.adapters

import io.github.andrewbgm.reactswingserver.bridge.*
import java.awt.*
import javax.swing.*

class JLabelAdapter : IHostAdapter<JLabel> {
  override fun create(
    bridge: Bridge,
    props: Map<String, Any?>
  ): JLabel = JLabel().also {
    update(bridge, it, props)
  }

  override fun update(
    bridge: Bridge,
    host: JLabel,
    changedProps: Map<String, Any?>
  ) {
    // NOOP
  }

  override fun appendToContainer(
    bridge: Bridge,
    child: JLabel
  ) = error("Cannot have a top level $child")

  override fun removeFromContainer(
    bridge: Bridge,
    child: JLabel
  ) = error("Cannot have a top level $child")

  override fun appendChild(
    bridge: Bridge,
    parent: JLabel,
    child: Container
  ) = error("Cannot append $child to $parent")

  override fun insertBefore(
    bridge: Bridge,
    parent: JLabel,
    child: Container,
    beforeChild: Container
  ) = error("Cannot insert $child before $beforeChild in $parent")

  override fun removeChild(
    bridge: Bridge,
    parent: JLabel,
    child: Container
  ) = error("Cannot remove $child from $parent")

  override fun updateText(
    bridge: Bridge,
    parent: JLabel,
    text: String
  ) {
    parent.text = text
  }
}
