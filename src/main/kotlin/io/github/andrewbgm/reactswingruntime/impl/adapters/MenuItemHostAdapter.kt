package io.github.andrewbgm.reactswingruntime.impl.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

class MenuItemHostAdapter : IHostAdapter<JMenuItem> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext
  ): JMenuItem = JMenuItem().apply {
    addActionListener {
      ctx.invokeCallback("onAction")
    }

    update(this, props, ctx)
  }

  override fun update(
    host: JMenuItem,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  ) = with(host) {
    text = changedProps.getOrDefault("children", text) as String?
  }

  override fun setChildren(
    host: JMenuItem,
    children: List<Any>,
    ctx: IHostContext
  ) = error("Cannot set children for $host")

  override fun appendChild(
    host: JMenuItem,
    child: Any,
    ctx: IHostContext
  ) = error("Cannot append $child to $host")

  override fun appendToContainer(
    host: JMenuItem,
    ctx: IHostContext
  ) = error("Cannot append $host to container")

  override fun removeChild(
    host: JMenuItem,
    child: Any,
    ctx: IHostContext
  ) = error("Cannot remove $child from $host")

  override fun removeFromContainer(
    host: JMenuItem,
    ctx: IHostContext
  ) = error("Cannot remove $host from container")

  override fun insertChild(
    host: JMenuItem,
    child: Any,
    beforeChild: Any,
    ctx: IHostContext
  ) = error("Cannot insert $child in $host before $beforeChild")

  override fun insertInContainer(
    host: JMenuItem,
    beforeChild: Any,
    ctx: IHostContext
  ) = error("Cannot insert $host in container before $beforeChild")
}
