package io.github.andrewbgm.reactswingruntime.impl.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

class ButtonHostAdapter : IHostAdapter<JButton> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext
  ): JButton = JButton().apply {
    addActionListener {
      ctx.invokeCallback("onAction")
    }

    update(this, props, ctx)
  }

  override fun update(
    host: JButton,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  ) = with(host) {
    text = changedProps.getOrDefault("children", text) as String?
  }

  override fun setChildren(
    host: JButton,
    children: List<Any>,
    ctx: IHostContext
  ) = error("Cannot set children for $host")

  override fun appendChild(
    host: JButton,
    child: Any,
    ctx: IHostContext
  ) = error("Cannot append $child to $host")

  override fun appendToContainer(
    host: JButton,
    ctx: IHostContext
  ) = error("Cannot append $host to container")

  override fun removeChild(
    host: JButton,
    child: Any,
    ctx: IHostContext
  ) = error("Cannot remove $child from $host")

  override fun removeFromContainer(
    host: JButton,
    ctx: IHostContext
  ) = error("Cannot remove $host from container")

  override fun insertChild(
    host: JButton,
    child: Any,
    beforeChild: Any,
    ctx: IHostContext
  ) = error("Cannot insert $child in $host before $beforeChild")

  override fun insertInContainer(
    host: JButton,
    beforeChild: Any,
    ctx: IHostContext
  ) = error("Cannot insert $host in container before $beforeChild")
}
