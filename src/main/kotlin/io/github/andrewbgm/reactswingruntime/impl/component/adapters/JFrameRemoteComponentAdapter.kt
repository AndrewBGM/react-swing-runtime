package io.github.andrewbgm.reactswingruntime.impl.component.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import java.awt.*
import java.awt.event.*
import javax.swing.*

class JFrameRemoteComponentAdapter : IRemoteComponentAdapter<JFrame> {
  override fun create(
    view: IRemoteComponentView,
    props: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ): JFrame = JFrame().apply {
    defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
    addWindowListener(object : WindowAdapter() {
      override fun windowClosing(
        e: WindowEvent?,
      ) = ctx.invokeCallback("onClose")
    })

    update(view, this, props, ctx)
  }

  override fun update(
    view: IRemoteComponentView,
    obj: JFrame,
    changedProps: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ) = with(obj) {
    title = changedProps.getOrDefault("title", title) as String?
  }

  override fun setChildren(
    view: IRemoteComponentView,
    parent: JFrame,
    children: List<Any>,
    ctx: IRemoteComponentContext,
  ) {
    parent.jMenuBar = null
    parent.contentPane.removeAll()

    children.forEach { appendChild(view, parent, it, ctx) }
  }

  override fun appendChild(
    view: IRemoteComponentView,
    parent: JFrame,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    when (child) {
      is JMenuBar -> parent.jMenuBar = child
      is Container -> {
        parent.contentPane.add(child)

        parent.revalidate()
        parent.repaint()
      }
      else -> super.appendChild(view, parent, child, ctx)
    }
  }

  override fun appendToContainer(
    view: IRemoteComponentView,
    obj: JFrame,
    ctx: IRemoteComponentContext,
  ) {
    obj.pack()
    obj.setLocationRelativeTo(null)
    obj.isVisible = true
  }

  override fun removeChild(
    view: IRemoteComponentView,
    parent: JFrame,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    when (child) {
      is JMenuBar -> parent.jMenuBar = null
      is Container -> {
        parent.contentPane.remove(child)

        parent.revalidate()
        parent.repaint()
      }
      else -> super.removeChild(view, parent, child, ctx)
    }
  }

  override fun removeFromContainer(
    view: IRemoteComponentView,
    obj: JFrame,
    ctx: IRemoteComponentContext,
  ) {
    obj.dispose()
  }

  override fun insertChild(
    view: IRemoteComponentView,
    parent: JFrame,
    child: Any,
    beforeChild: Any,
    ctx: IRemoteComponentContext,
  ) {
    when {
      child is JMenuBar && beforeChild is JMenuBar -> parent.jMenuBar = child
      child is JMenuBar && beforeChild is Container -> parent.jMenuBar = child
      child is Container && beforeChild is Container -> {
        if (child.parent == parent) {
          parent.remove(child)
        }

        val idx = parent.components.indexOf(beforeChild)
        parent.add(child, idx)

        parent.revalidate()
        parent.repaint()
      }
      else -> super.insertChild(view, parent, child, beforeChild, ctx)
    }
  }

  override fun insertInContainer(
    view: IRemoteComponentView,
    obj: JFrame,
    beforeObj: Any,
    ctx: IRemoteComponentContext,
  ) {
    if (!obj.isVisible) {
      obj.pack()
      obj.setLocationRelativeTo(null)
      obj.isVisible = true
    }
  }
}
