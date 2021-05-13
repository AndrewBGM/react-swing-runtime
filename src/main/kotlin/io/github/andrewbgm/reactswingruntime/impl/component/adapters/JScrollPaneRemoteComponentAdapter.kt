package io.github.andrewbgm.reactswingruntime.impl.component.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import java.awt.*
import javax.swing.*

class JScrollPaneRemoteComponentAdapter : IRemoteComponentAdapter<JScrollPane> {
  override fun create(
    view: IRemoteComponentView,
    props: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ): JScrollPane = JScrollPane().apply {
    update(view, this, props, ctx)
  }

  override fun update(
    view: IRemoteComponentView,
    obj: JScrollPane,
    changedProps: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ) = with(obj) {
    // noop
  }

  override fun setChildren(
    view: IRemoteComponentView,
    parent: JScrollPane,
    children: List<Any>,
    ctx: IRemoteComponentContext,
  ) {
    parent.removeAll()
    children.forEach { appendChild(view, parent, it, ctx) }
  }

  override fun appendChild(
    view: IRemoteComponentView,
    parent: JScrollPane,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is Container) { "Cannot append non Container $child to $parent" }
    require(parent.viewport.view == null) { "Cannot append more than one child to $parent" }

    parent.viewport.view = child

    parent.revalidate()
    parent.repaint()
  }

  override fun removeChild(
    view: IRemoteComponentView,
    parent: JScrollPane,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is Container) { "Cannot remove non Container $child from $parent" }
    require(parent.viewport.view == child) { "Cannot remove non child $child from $parent" }

    parent.viewport.view = child

    parent.revalidate()
    parent.repaint()
  }
}
