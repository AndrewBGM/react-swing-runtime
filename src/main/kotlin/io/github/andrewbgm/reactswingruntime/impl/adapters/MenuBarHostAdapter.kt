package io.github.andrewbgm.reactswingruntime.impl.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.*
import javax.swing.*

class MenuBarHostAdapter : IHostAdapter<JMenuBar> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext
  ): JMenuBar = JMenuBar().apply {
    update(this, props, ctx)
  }

  override fun update(
    host: JMenuBar,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  ) = with(host) {
    // noop
  }

  override fun setChildren(
    host: JMenuBar,
    children: List<Any>,
    ctx: IHostContext
  ) = children.forEach { appendChild(host, it, ctx) }

  override fun appendChild(
    host: JMenuBar,
    child: Any,
    ctx: IHostContext
  ) {
    when (child) {
      is JMenu -> host.add(child)
      else -> error("Cannot append $child to $host")
    }
  }

  override fun appendToContainer(
    host: JMenuBar,
    ctx: IHostContext
  ) = error("Cannot append $host to container")

  override fun removeChild(
    host: JMenuBar,
    child: Any,
    ctx: IHostContext
  ) = when (child) {
    is JMenu -> host.remove(child)
    else -> error("Cannot remove $child from $host")
  }

  override fun removeFromContainer(
    host: JMenuBar,
    ctx: IHostContext
  ) = error("Cannot remove $host from container")

  override fun insertChild(
    host: JMenuBar,
    child: Any,
    beforeChild: Any,
    ctx: IHostContext
  ) {
    when {
      child is JMenu && beforeChild is JMenu -> host.insertBefore(child, beforeChild)
      else -> error("Cannot insert $child in $host before $beforeChild")
    }
  }

  override fun insertInContainer(
    host: JMenuBar,
    beforeChild: Any,
    ctx: IHostContext
  ) = error("Cannot insert $host in container before $beforeChild")
}
