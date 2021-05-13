package io.github.andrewbgm.reactswingruntime.impl.component.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import java.awt.*
import javax.swing.*

class JPanelRemoteComponentAdapter : IRemoteComponentAdapter<JPanel> {
  override fun create(
    view: IRemoteComponentView,
    props: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ): JPanel = JPanel().apply {
    update(view, this, props, ctx)
  }

  override fun update(
    view: IRemoteComponentView,
    obj: JPanel,
    changedProps: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ) = with(obj) {
    // noop
  }

  override fun setChildren(
    view: IRemoteComponentView,
    parent: JPanel,
    children: List<Any>,
    ctx: IRemoteComponentContext,
  ) {
    parent.removeAll()
    children.forEach { appendChild(view, parent, it, ctx) }
  }

  override fun appendChild(
    view: IRemoteComponentView,
    parent: JPanel,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is Container) { "Cannot append non Container $child to $parent" }

    parent.add(child)

    parent.revalidate()
    parent.repaint()
  }

  override fun removeChild(
    view: IRemoteComponentView,
    parent: JPanel,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is Container) { "Cannot remove non Container $child from $parent" }

    parent.remove(child)

    parent.revalidate()
    parent.repaint()
  }

  override fun insertChild(
    view: IRemoteComponentView,
    parent: JPanel,
    child: Any,
    beforeChild: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is Container) { "Cannot insert non Container $child in $parent" }
    require(beforeChild is Container) { "Cannot insert before non Container $beforeChild in $parent" }

    if (child.parent == parent) {
      parent.remove(child)
    }

    val idx = parent.components.indexOf(beforeChild)
    parent.add(child, idx)

    parent.revalidate()
    parent.repaint()
  }
}
