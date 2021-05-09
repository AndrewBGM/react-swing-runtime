package io.github.andrewbgm.reactswingruntime.impl

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

private const val ROOT_CONTAINER_ID = "00000000-0000-0000-0000-000000000000"

class HostEnvironment {
  private val adapterByTypeName = mutableMapOf<String, IHostAdapter<out Any>>()
  private val viewById = mutableMapOf<String, HostView>()

  fun createView(
    id: String,
    type: IHostType,
    props: Map<String, Any?>,
    ctx: IHostContext,
  ) = SwingUtilities.invokeLater {
    val adapter = findAdapter(type)
    viewById[id] = HostView(id, type, adapter.create(props, ctx))
  }

  fun updateView(
    id: String,
    changedProps: Map<String, Any?>,
    ctx: IHostContext,
  ) = SwingUtilities.invokeLater {
    val view = findView(id)

    val adapter = findAdapter(view.type)
    adapter.update(view.host, changedProps, ctx)
  }

  fun setChildren(
    parentId: String,
    childrenIds: List<String>,
    ctx: IHostContext,
  ) = SwingUtilities.invokeLater {
    if (parentId != ROOT_CONTAINER_ID) {
      val parentView = findView(parentId)
      val children = childrenIds.map(::findView)
      parentView.setChildren(children)

      val parentAdapter = findAdapter(parentView.type)
      parentAdapter.setChildren(parentView.host, children.map { it.host }, ctx)
    }
  }

  fun appendChild(
    parentId: String,
    childId: String,
    ctx: IHostContext,
  ) = SwingUtilities.invokeLater {
    val childView = findView(childId)
    if (parentId == ROOT_CONTAINER_ID) {
      val childAdapter = findAdapter(childView.type)
      childAdapter.appendToContainer(childView.host, ctx)
    } else {
      val parentView = findView(parentId)
      parentView += childView

      val parentAdapter = findAdapter(parentView.type)
      parentAdapter.appendChild(parentView.host, childView.host, ctx)
    }
  }

  fun removeChild(
    parentId: String,
    childId: String,
    ctx: IHostContext,
  ) = SwingUtilities.invokeLater {
    val childView = findView(childId)
    if (parentId == ROOT_CONTAINER_ID) {
      val childAdapter = findAdapter(childView.type)
      childAdapter.removeFromContainer(childView.host, ctx)
    } else {
      val parentView = findView(parentId)
      parentView -= childView

      val parentAdapter = findAdapter(parentView.type)
      parentAdapter.removeChild(parentView.host, childView.host, ctx)
    }

    removeHostReferences(childView)
  }

  fun insertChild(
    parentId: String,
    childId: String,
    beforeChildId: String,
    ctx: IHostContext,
  ) = SwingUtilities.invokeLater {
    val childView = findView(childId)
    val beforeChildView = findView(beforeChildId)

    if (parentId == ROOT_CONTAINER_ID) {
      val childAdapter = findAdapter(childView.type)
      childAdapter.insertInContainer(childView.host, beforeChildView.host, ctx)
    } else {
      val parentView = findView(parentId)
      parentView.insertChild(childView, beforeChildView)

      val parentAdapter = findAdapter(parentView.type)
      parentAdapter.insertChild(parentView.host, childView.host, beforeChildView.host, ctx)
    }
  }

  fun registerHostType(
    type: IHostType,
    adapter: IHostAdapter<out Any>,
  ): HostEnvironment = this.apply {
    val typeName = type.name
    require(!adapterByTypeName.containsKey(typeName)) { "$type already has an associated IHostAdapter" }

    adapterByTypeName[typeName] = adapter
  }

  private fun removeHostReferences(
    view: HostView,
  ) {
    view.children.forEach(::removeHostReferences)
    viewById.remove(view.id)
  }

  @Suppress("UNCHECKED_CAST")
  private fun findAdapter(
    type: IHostType,
  ): IHostAdapter<Any> {
    val typeName = type.name
    return requireNotNull(adapterByTypeName[typeName]) { "$type has no associated IHostAdapter" } as IHostAdapter<Any>
  }

  private fun findView(
    id: String,
  ): HostView = requireNotNull(viewById[id]) { "#$id has no associated HostView" }
}
