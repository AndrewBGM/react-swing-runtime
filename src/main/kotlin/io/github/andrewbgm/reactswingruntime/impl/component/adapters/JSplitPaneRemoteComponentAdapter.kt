package io.github.andrewbgm.reactswingruntime.impl.component.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import java.awt.*
import javax.swing.*

class JSplitPaneRemoteComponentAdapter : IRemoteComponentAdapter<JSplitPane> {
  override fun create(
    view: IRemoteComponentView,
    props: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ): JSplitPane = JSplitPane().apply {
    update(view, this, props, ctx)
  }

  override fun update(
    view: IRemoteComponentView,
    obj: JSplitPane,
    changedProps: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ) = with(obj) {
    orientationName = changedProps.getOrDefault("orientation", orientationName) as String
  }

  override fun setChildren(
    view: IRemoteComponentView,
    parent: JSplitPane,
    children: List<Any>,
    ctx: IRemoteComponentContext,
  ) {
    parent.removeAll()
    children.forEach { appendChild(view, parent, it, ctx) }
  }

  override fun appendChild(
    view: IRemoteComponentView,
    parent: JSplitPane,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is Container) { "Cannot append non Container $child to $parent" }

    when (parent.orientation) {
      JSplitPane.HORIZONTAL_SPLIT -> {
        when {
          parent.leftComponent == null -> parent.leftComponent = child
          parent.rightComponent == null -> parent.rightComponent = child
          else -> error("Cannot append more than 2 children to $parent")
        }
      }
      JSplitPane.VERTICAL_SPLIT -> {
        when {
          parent.topComponent == null -> parent.topComponent = child
          parent.bottomComponent == null -> parent.bottomComponent = child
          else -> error("Cannot append more than 2 children to $parent")
        }
      }
    }

    parent.updateUI()
    parent.revalidate()
    parent.repaint()
  }

  override fun removeChild(
    view: IRemoteComponentView,
    parent: JSplitPane,
    child: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is Container) { "Cannot remove non Container $child from $parent" }

    when (parent.orientation) {
      JSplitPane.HORIZONTAL_SPLIT -> {
        when {
          parent.leftComponent == child -> parent.leftComponent = null
          parent.rightComponent == child -> parent.rightComponent = null
          else -> error("Cannot remove non child $child from $parent")
        }
      }
      JSplitPane.VERTICAL_SPLIT -> {
        when {
          parent.topComponent == child -> parent.topComponent = null
          parent.bottomComponent == child -> parent.bottomComponent = null
          else -> error("Cannot remove non child $child from $parent")
        }
      }
    }

    parent.updateUI()
    parent.revalidate()
    parent.repaint()
  }

  override fun insertChild(
    view: IRemoteComponentView,
    parent: JSplitPane,
    child: Any,
    beforeChild: Any,
    ctx: IRemoteComponentContext,
  ) {
    require(child is Container) { "Cannot insert non Container $child in $parent" }
    require(beforeChild is Container) { "Cannot insert before non Container $beforeChild in $parent" }

    when (parent.orientation) {
      JSplitPane.HORIZONTAL_SPLIT -> {
        when (parent.rightComponent) {
          beforeChild -> parent.leftComponent = child
          else -> error("Cannot insert $child before $beforeChild, it is not the 'rightComponent'")
        }
      }
      JSplitPane.VERTICAL_SPLIT -> {
        when (parent.bottomComponent) {
          beforeChild -> parent.topComponent = child
          else -> error("Cannot insert $child before $beforeChild, it is not the 'bottomComponent'")
        }
      }
    }

    parent.updateUI()
    parent.revalidate()
    parent.repaint()
  }
}

private var JSplitPane.orientationName: String
  set(value) = when (value) {
    "HORIZONTAL" -> orientation = JSplitPane.HORIZONTAL_SPLIT
    "VERTICAL" -> orientation = JSplitPane.VERTICAL_SPLIT
    else -> error("Invalid orientation value")
  }
  get() = when (orientation) {
    JSplitPane.HORIZONTAL_SPLIT -> "HORIZONTAL"
    JSplitPane.VERTICAL_SPLIT -> "VERTICAL"
    else -> "UNKNOWN"
  }
