package io.github.andrewbgm.reactswingserver.bridge.adapter

import io.github.andrewbgm.reactswingserver.bridge.*
import java.awt.*
import javax.swing.*

class JFrameHostAdapter : IHostAdapter<JFrame> {
  override fun create(
    bridge: Bridge,
    props: Map<String, Any?>
  ): JFrame = JFrame().also {
    update(bridge, it, null, props)
  }

  override fun update(
    bridge: Bridge,
    host: JFrame,
    oldProps: Map<String, Any?>?,
    newProps: Map<String, Any?>
  ) {
    host.title = newProps.getOrDefault("title", host.title) as String?
  }

  override fun applyText(
    bridge: Bridge,
    host: JFrame,
    text: String
  ) = error("Cannot apply text to $host")

  override fun appendToContainer(
    bridge: Bridge,
    host: JFrame
  ) {
    host.pack()
    host.setLocationRelativeTo(null)
    host.isVisible = true
  }

  override fun insertInContainerBefore(
    bridge: Bridge,
    host: JFrame,
    beforeChild: Container
  ) {
    // noop
  }

  override fun removeFromContainer(
    bridge: Bridge,
    host: JFrame
  ) {
    host.dispose()
  }

  override fun appendChild(
    bridge: Bridge,
    host: JFrame,
    child: Container
  ) {
    /* TODO
      Revisit this, might end up making sense for this to be
      handled another way.
    */
    if (child is JMenuBar) {
      host.jMenuBar = child
    } else host.contentPane = child
  }

  override fun insertBefore(
    bridge: Bridge,
    host: JFrame,
    child: Container,
    beforeChild: Container
  ) {
    appendChild(bridge, host, child)
  }

  override fun removeChild(
    bridge: Bridge,
    host: JFrame,
    child: Container
  ) {
    if (child is JMenuBar) {
      host.jMenuBar = null
    } else host.contentPane = null
  }
}
