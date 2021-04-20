package io.github.andrewbgm.reactswingserver.bridge.adapter

import io.github.andrewbgm.reactswingserver.bridge.*
import java.awt.*
import javax.swing.*

class JFrameHostAdapter : IHostAdapter<JFrame> {
  override fun create(
    bridge: Bridge,
    props: HostProps
  ): JFrame = JFrame().also {
    update(bridge, it, null, props)
  }

  override fun update(
    bridge: Bridge,
    host: JFrame,
    oldProps: HostProps?,
    newProps: HostProps
  ) {
    host.title = newProps.getOrDefault("title", host.title) as String?
  }

  override fun appendToContainer(
    bridge: Bridge,
    host: JFrame
  ) {
    host.pack()
    host.setLocationRelativeTo(null)
    host.isVisible = true
  }

  override fun insertBeforeInContainer(
    bridge: Bridge,
    host: JFrame,
    beforeChild: Container
  ) {
    TODO("Not implemented yet")
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
