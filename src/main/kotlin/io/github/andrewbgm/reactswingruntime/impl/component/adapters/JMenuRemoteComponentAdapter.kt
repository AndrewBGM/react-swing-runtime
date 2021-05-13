package io.github.andrewbgm.reactswingruntime.impl.component.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

class JMenuRemoteComponentAdapter : IRemoteComponentAdapter<JMenu> {
  override fun create(
    view: IRemoteComponentView,
    props: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ): JMenu = JMenu().apply {
    addActionListener {
      ctx.invokeCallback("onAction")
    }

    update(view, this, props, ctx)
  }

  override fun update(
    view: IRemoteComponentView,
    obj: JMenu,
    changedProps: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ) = with(obj) {
    text = changedProps.getOrDefault("text", text) as String?
  }

  override fun setChildren(
    view: IRemoteComponentView,
    parent: JMenu,
    children: List<Any>,
    ctx: IRemoteComponentContext,
  ) {
    parent.removeAll()
    children.forEach { appendChild(view, parent, it, ctx) }
  }

  override fun appendChild(
    view: IRemoteComponentView,
    parent: JMenu,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is JMenuItem) { "Cannot append non JMenuItem $child to $parent" }

    parent.add(child)

    parent.revalidate()
    parent.repaint()
  }

  override fun removeChild(
    view: IRemoteComponentView,
    parent: JMenu,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is JMenuItem) { "Cannot remove non JMenuItem $child from $parent" }

    parent.remove(child)

    parent.revalidate()
    parent.repaint()
  }

  override fun insertChild(
    view: IRemoteComponentView,
    parent: JMenu,
    child: Any,
    beforeChild: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is JMenuItem) { "Cannot insert non JMenuItem $child in $parent" }
    require(beforeChild is JMenuItem) { "Cannot insert before non JMenuItem $beforeChild in $parent" }

    if (child.parent == parent) {
      parent.remove(child)
    }

    val idx = parent.components.indexOf(beforeChild)
    parent.add(child, idx)

    parent.revalidate()
    parent.repaint()
  }
}
