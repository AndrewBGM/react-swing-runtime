package io.github.andrewbgm.reactswingruntime.impl.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import java.awt.*
import javax.swing.*

class PanelHostAdapter : IHostAdapter<JPanel> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext
  ): JPanel = JPanel().apply {
    update(this, props, ctx)
  }

  override fun update(
    host: JPanel,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  ) = with(host) {
    // noop
  }

  override fun setChildren(
    host: JPanel,
    children: List<Any>,
    ctx: IHostContext
  ) = children.forEach { appendChild(host, it, ctx) }

  override fun appendChild(
    host: JPanel,
    child: Any,
    ctx: IHostContext
  ) {
    when (child) {
      is Container -> host.add(child)
      else -> error("Cannot append $child to $host")
    }
  }

  override fun appendToContainer(
    host: JPanel,
    ctx: IHostContext
  ) = error("Cannot append $host to container")

  override fun removeChild(
    host: JPanel,
    child: Any,
    ctx: IHostContext
  ) {
    when (child) {
      is Container -> host.remove(child)
      else -> error("Cannot append $child to $host")
    }
  }

  override fun removeFromContainer(
    host: JPanel,
    ctx: IHostContext
  ) = error("Cannot remove $host from container")

  override fun insertChild(
    host: JPanel,
    child: Any,
    beforeChild: Any,
    ctx: IHostContext
  ) {
    when {
      child is Container && beforeChild is Container -> {
        if (host.components.contains(child)) {
          host.remove(child)
        }

        val idx = host.components.indexOf(beforeChild)
        host.add(child, idx)
      }
      else -> error("Cannot append $child to $host")
    }
  }

  override fun insertInContainer(
    host: JPanel,
    beforeChild: Any,
    ctx: IHostContext
  ) = error("Cannot insert $host in container before $beforeChild")
}
