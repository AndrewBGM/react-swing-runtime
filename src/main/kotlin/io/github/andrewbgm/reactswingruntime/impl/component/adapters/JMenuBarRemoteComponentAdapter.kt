package io.github.andrewbgm.reactswingruntime.impl.component.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

class JMenuBarRemoteComponentAdapter : IRemoteComponentAdapter<JMenuBar> {
  override fun create(
    view: IRemoteComponentView,
    props: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ): JMenuBar = JMenuBar().apply {
    update(view, this, props, ctx)
  }

  override fun update(
    view: IRemoteComponentView,
    obj: JMenuBar,
    changedProps: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ) = with(obj) {
    // noop
  }

  override fun setChildren(
    view: IRemoteComponentView,
    parent: JMenuBar,
    children: List<Any>,
    ctx: IRemoteComponentContext,
  ) {
    parent.removeAll()
    children.forEach { appendChild(view, parent, it, ctx) }
  }

  override fun appendChild(
    view: IRemoteComponentView,
    parent: JMenuBar,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is JMenu) { "Cannot append non JMenu $child to $parent" }

    parent.add(child)

    parent.revalidate()
    parent.repaint()
  }

  override fun removeChild(
    view: IRemoteComponentView,
    parent: JMenuBar,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is JMenu) { "Cannot remove non JMenu $child from $parent" }

    parent.remove(child)

    parent.revalidate()
    parent.repaint()
  }

  override fun insertChild(
    view: IRemoteComponentView,
    parent: JMenuBar,
    child: Any,
    beforeChild: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is JMenu) { "Cannot insert non JMenu $child in $parent" }
    require(beforeChild is JMenu) { "Cannot insert before non JMenu $beforeChild in $parent" }

    if (child.parent == parent) {
      parent.remove(child)
    }

    val idx = parent.components.indexOf(beforeChild)
    parent.add(child, idx)

    parent.revalidate()
    parent.repaint()
  }
}
