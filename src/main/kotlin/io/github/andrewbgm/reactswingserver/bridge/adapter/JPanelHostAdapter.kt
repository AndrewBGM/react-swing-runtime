package io.github.andrewbgm.reactswingserver.bridge.adapter

import io.github.andrewbgm.reactswingserver.bridge.*
import java.awt.*
import javax.swing.*

class JPanelHostAdapter : IHostAdapter<JPanel> {
  override fun create(
    bridge: Bridge,
    props: Map<String, Any?>
  ): JPanel = JPanel().also {
    update(bridge, it, null, props)
  }

  override fun update(
    bridge: Bridge,
    host: JPanel,
    oldProps: Map<String, Any?>?,
    newProps: Map<String, Any?>
  ) {

  }

  override fun applyText(
    bridge: Bridge,
    host: JPanel,
    text: String
  ) = error("Cannot apply text to $host")

  override fun appendToContainer(
    bridge: Bridge,
    host: JPanel
  ) = error("Cannot append $host to container")

  override fun insertBeforeInContainer(
    bridge: Bridge,
    host: JPanel,
    beforeChild: Container
  ) = error("Cannot insert $host before $beforeChild in container")

  override fun removeFromContainer(
    bridge: Bridge,
    host: JPanel
  ) = error("Cannot remove $host from container")

  override fun appendChild(
    bridge: Bridge,
    host: JPanel,
    child: Container
  ) {
    host.add(child)
  }

  override fun insertBefore(
    bridge: Bridge,
    host: JPanel,
    child: Container,
    beforeChild: Container
  ) {
    if (host.components.contains(child)) {
      host.remove(child)
    }

    val idx = host.components.indexOf(beforeChild)
    host.add(child, idx)
  }

  override fun removeChild(
    bridge: Bridge,
    host: JPanel,
    child: Container
  ) {
    host.remove(child)
  }
}
