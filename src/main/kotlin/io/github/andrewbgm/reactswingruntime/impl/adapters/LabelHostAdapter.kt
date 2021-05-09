package io.github.andrewbgm.reactswingruntime.impl.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

class LabelHostAdapter : IHostAdapter<JLabel> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext,
  ): JLabel = JLabel().apply {
    update(this, props, ctx)
  }

  override fun update(
    host: JLabel,
    changedProps: Map<String, Any?>,
    ctx: IHostContext,
  ) = with(host) {
    text = changedProps.getOrDefault("children", text) as String?
  }

  override fun setChildren(
    host: JLabel,
    children: List<Any>,
    ctx: IHostContext,
  ) = error("Cannot set children for $host")

  override fun appendChild(
    host: JLabel,
    child: Any,
    ctx: IHostContext,
  ) = error("Cannot append $child to $host")

  override fun appendToContainer(
    host: JLabel,
    ctx: IHostContext,
  ) = error("Cannot append $host to container")

  override fun removeChild(
    host: JLabel,
    child: Any,
    ctx: IHostContext,
  ) = error("Cannot remove $child from $host")

  override fun removeFromContainer(
    host: JLabel,
    ctx: IHostContext,
  ) = error("Cannot remove $host from container")

  override fun insertChild(
    host: JLabel,
    child: Any,
    beforeChild: Any,
    ctx: IHostContext,
  ) = error("Cannot insert $child in $host before $beforeChild")

  override fun insertInContainer(
    host: JLabel,
    beforeChild: Any,
    ctx: IHostContext,
  ) = error("Cannot insert $host in container before $beforeChild")
}
