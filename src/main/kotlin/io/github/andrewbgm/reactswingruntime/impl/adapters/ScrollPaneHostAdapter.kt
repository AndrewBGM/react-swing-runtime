package io.github.andrewbgm.reactswingruntime.impl.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import java.awt.*
import javax.swing.*

class ScrollPaneHostAdapter : IHostAdapter<JScrollPane> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext,
  ): JScrollPane = JScrollPane().apply {
    update(this, props, ctx)
  }

  override fun update(
    host: JScrollPane,
    changedProps: Map<String, Any?>,
    ctx: IHostContext,
  ) = with(host) {
    // noop
  }

  override fun setChildren(
    host: JScrollPane,
    children: List<Any>,
    ctx: IHostContext,
  ) = children.forEach { appendChild(host, it, ctx) }

  override fun appendChild(
    host: JScrollPane,
    child: Any,
    ctx: IHostContext,
  ) {
    when (child) {
      is Container -> host.setViewportView(child)
      else -> error("Cannot append $child to $host")
    }
  }

  override fun appendToContainer(
    host: JScrollPane,
    ctx: IHostContext,
  ) = error("Cannot append $host to container")

  override fun removeChild(
    host: JScrollPane,
    child: Any,
    ctx: IHostContext,
  ) = when (child) {
    is Container -> host.setViewportView(null)
    else -> error("Cannot append $child to $host")
  }

  override fun removeFromContainer(
    host: JScrollPane,
    ctx: IHostContext,
  ) = error("Cannot remove $host from container")

  override fun insertChild(
    host: JScrollPane,
    child: Any,
    beforeChild: Any,
    ctx: IHostContext,
  ) = when (child) {
    is Container -> host.setViewportView(child)
    else -> error("Cannot append $child to $host")
  }

  override fun insertInContainer(
    host: JScrollPane,
    beforeChild: Any,
    ctx: IHostContext,
  ) = error("Cannot insert $host in container before $beforeChild")
}
