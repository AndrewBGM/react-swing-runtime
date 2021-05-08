package io.github.andrewbgm.reactswingruntime.impl.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.*
import javax.swing.*

class MenuHostAdapter : IHostAdapter<JMenu> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext
  ): JMenu = JMenu().apply {
    addActionListener {
      ctx.invokeCallback("onAction")
    }

    update(this, props, ctx)
  }

  override fun update(
    host: JMenu,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  ) = with(host) {
    text = changedProps.getOrDefault("text", text) as String?
  }

  override fun setChildren(
    host: JMenu,
    children: List<Any>,
    ctx: IHostContext
  ) = children.forEach { appendChild(host, it, ctx) }

  override fun appendChild(
    host: JMenu,
    child: Any,
    ctx: IHostContext
  ) {
    when (child) {
      is JMenuItem -> host.add(child)
      else -> error("Cannot append $child to $host")
    }
  }

  override fun appendToContainer(
    host: JMenu,
    ctx: IHostContext
  ) = error("Cannot append $host to container")

  override fun removeChild(
    host: JMenu,
    child: Any,
    ctx: IHostContext
  ) = when (child) {
    is JMenuItem -> host.remove(child)
    else -> error("Cannot remove $child from $host")
  }

  override fun removeFromContainer(
    host: JMenu,
    ctx: IHostContext
  ) = error("Cannot remove $host from container")

  override fun insertChild(
    host: JMenu,
    child: Any,
    beforeChild: Any,
    ctx: IHostContext
  ) {
    when {
      child is JMenuItem && beforeChild is JMenuItem -> host.insertBefore(child, beforeChild)
      else -> error("Cannot insert $child in $host before $beforeChild")
    }
  }

  override fun insertInContainer(
    host: JMenu,
    beforeChild: Any,
    ctx: IHostContext
  ) = error("Cannot insert $host in container before $beforeChild")
}
