package io.github.andrewbgm.reactswingserver.bridge.adapter

import io.github.andrewbgm.reactswingserver.bridge.*
import java.awt.*
import java.awt.event.*
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
    host.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE

    host.title = newProps.getOrDefault("title", host.title) as String?

    val oldOnClose = oldProps?.getOrDefault("onClose", null) as Double?
    val newOnClose = newProps.getOrDefault("onClose", null) as Double?

    if (oldOnClose !== null && oldOnClose != newOnClose) {
      bridge.freeCallback(oldOnClose)
    }

    if (newOnClose !== null && oldOnClose != newOnClose) {
      host.windowListeners.forEach { host.removeWindowListener(it) }
      host.addWindowListener(object : WindowAdapter() {
        override fun windowClosing(e: WindowEvent?) {
          bridge.invokeCallback(newOnClose)
        }
      })
    }
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
