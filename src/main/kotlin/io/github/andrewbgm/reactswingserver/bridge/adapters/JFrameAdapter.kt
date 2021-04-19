package io.github.andrewbgm.reactswingserver.bridge.adapters

import io.github.andrewbgm.reactswingserver.bridge.*
import java.awt.*
import javax.swing.*

class JFrameAdapter : IHostAdapter<JFrame> {
  override fun create(
    bridge: Bridge,
    props: Map<String, Any?>
  ): JFrame = JFrame().also {
    update(bridge, it, props)
  }

  override fun update(
    bridge: Bridge,
    host: JFrame,
    changedProps: Map<String, Any?>
  ) {
    host.title = changedProps.getOrDefault("title", host.title) as String?
  }

  override fun appendToContainer(
    bridge: Bridge,
    child: JFrame
  ) {
    child.pack()
    child.setLocationRelativeTo(null)
    child.isVisible = true
  }

  override fun removeFromContainer(
    bridge: Bridge,
    child: JFrame
  ) {
    child.dispose()
  }

  override fun appendChild(
    bridge: Bridge,
    parent: JFrame,
    child: Container
  ) {
    if (child is JMenuBar) {
      parent.jMenuBar = child
    } else parent.contentPane = child
  }

  override fun insertBefore(
    bridge: Bridge,
    parent: JFrame,
    child: Container,
    beforeChild: Container
  ) {
    appendChild(bridge, parent, child)
  }

  override fun removeChild(
    bridge: Bridge,
    parent: JFrame,
    child: Container
  ) {
    when (child) {
      parent.jMenuBar -> parent.jMenuBar = null
      parent.contentPane -> parent.contentPane = null
    }
  }

  override fun updateText(
    bridge: Bridge,
    parent: JFrame,
    text: String
  ) = error("Cannot set text $text on $parent")
}
