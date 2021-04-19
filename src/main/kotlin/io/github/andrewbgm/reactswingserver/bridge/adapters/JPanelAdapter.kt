package io.github.andrewbgm.reactswingserver.bridge.adapters

import io.github.andrewbgm.reactswingserver.bridge.*
import java.awt.*
import javax.swing.*

class JPanelAdapter : IHostAdapter<JPanel> {
  override fun create(
    bridge: Bridge,
    props: Map<String, Any?>
  ): JPanel = JPanel().also {
    update(bridge, it, props)
  }

  override fun update(
    bridge: Bridge,
    host: JPanel,
    changedProps: Map<String, Any?>
  ) {
    // NOOP
  }

  override fun appendToContainer(
    bridge: Bridge,
    child: JPanel
  ) = error("Cannot have a top level $child")

  override fun removeFromContainer(
    bridge: Bridge,
    child: JPanel
  ) = error("Cannot have a top level $child")

  override fun appendChild(
    bridge: Bridge,
    parent: JPanel,
    child: Container
  ) {
    parent.add(child)
  }

  override fun insertBefore(
    bridge: Bridge,
    parent: JPanel,
    child: Container,
    beforeChild: Container
  ) {
    if (parent.components.contains(child)) {
      parent.remove(child)
    }

    val idx = parent.components.indexOf(beforeChild)
    parent.add(child, idx)
  }

  override fun removeChild(
    bridge: Bridge,
    parent: JPanel,
    child: Container
  ) {
    TODO("Not yet implemented")
  }

  override fun updateText(
    bridge: Bridge,
    parent: JPanel,
    text: String
  ) = error("Cannot set text $text on $parent")
}
