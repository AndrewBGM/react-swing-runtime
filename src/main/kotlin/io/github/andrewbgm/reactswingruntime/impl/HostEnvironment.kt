package io.github.andrewbgm.reactswingruntime.impl

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

private const val ROOT_CONTAINER_ID = "00000000-0000-0000-0000-000000000000"

class HostEnvironment {
  private val adapterByTypeName = mutableMapOf<String, IHostAdapter<out Any>>()
  private val hostTypeById = mutableMapOf<String, IHostType>()
  private val hostById = mutableMapOf<String, Any>()
  private val childrenById = mutableMapOf<String, List<String>>()

  fun createView(
    id: String,
    type: IHostType,
    props: Map<String, Any?>,
    ctx: IHostContext
  ) = SwingUtilities.invokeLater {
    hostTypeById[id] = type

    val adapter = findAdapter(type)
    hostById[id] = adapter.create(props, ctx)
  }

  fun updateView(
    id: String,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  ) = SwingUtilities.invokeLater {
    val host = findHostView(id)
    val type = findHostType(id)

    val adapter = findAdapter(type)
    adapter.update(host, changedProps, ctx)
  }

  fun setChildren(
    parentId: String,
    childrenIds: List<String>,
    ctx: IHostContext
  ) = SwingUtilities.invokeLater {
    childrenById[parentId] = childrenIds

    if (parentId != ROOT_CONTAINER_ID) {
      val parentHost = findHostView(parentId)
      val parentType = findHostType(parentId)

      val parentAdapter = findAdapter(parentType)
      val children = childrenIds.map(::findHostView)

      parentAdapter.setChildren(parentHost, children, ctx)
    }
  }

  fun appendChild(
    parentId: String,
    childId: String,
    ctx: IHostContext
  ) = SwingUtilities.invokeLater {
    val childHost = findHostView(childId)

    val children = childrenById.getOrPut(parentId) { listOf() }
    childrenById[parentId] = children + childId

    if (parentId == ROOT_CONTAINER_ID) {
      val childType = findHostType(childId)

      val childAdapter = findAdapter(childType)
      childAdapter.appendToContainer(childHost, ctx)
    } else {
      val parentHost = findHostView(parentId)
      val parentType = findHostType(parentId)

      val parentAdapter = findAdapter(parentType)
      parentAdapter.appendChild(parentHost, childHost, ctx)
    }
  }

  fun removeChild(
    parentId: String,
    childId: String,
    ctx: IHostContext
  ) = SwingUtilities.invokeLater {
    val childHost = findHostView(childId)

    val children = childrenById.getOrPut(parentId) { listOf() }
    childrenById[parentId] = children - childId

    if (parentId == ROOT_CONTAINER_ID) {
      val childType = findHostType(childId)

      val childAdapter = findAdapter(childType)
      childAdapter.removeFromContainer(childHost, ctx)
    } else {
      val parentHost = findHostView(parentId)
      val parentType = findHostType(parentId)

      val parentAdapter = findAdapter(parentType)
      parentAdapter.removeChild(parentHost, childHost, ctx)
    }

    removeHostReferences(childId)
  }

  fun insertChild(
    parentId: String,
    childId: String,
    beforeChildId: String,
    ctx: IHostContext
  ) = SwingUtilities.invokeLater {
    val childHost = findHostView(childId)
    val beforeChildHost = findHostView(beforeChildId)

    val existingChildren = childrenById.getOrPut(parentId) { listOf() }
      .toMutableList()

    if (existingChildren.contains(childId)) {
      existingChildren -= childId
    }

    val idx = existingChildren.indexOf(beforeChildId)
    existingChildren.add(idx, childId)
    childrenById[parentId] = existingChildren.toList()

    if (parentId == ROOT_CONTAINER_ID) {
      val childType = findHostType(childId)

      val childAdapter = findAdapter(childType)
      childAdapter.insertInContainer(childHost, beforeChildHost, ctx)
    } else {
      val parentHost = findHostView(parentId)
      val parentType = findHostType(parentId)

      val parentAdapter = findAdapter(parentType)
      parentAdapter.insertChild(parentHost, childHost, beforeChildHost, ctx)
    }
  }

  fun registerHostType(
    type: IHostType,
    adapter: IHostAdapter<out Any>
  ): HostEnvironment = this.apply {
    val typeName = type.name
    require(!adapterByTypeName.containsKey(typeName)) { "$type already has an associated IHostAdapter" }

    adapterByTypeName[typeName] = adapter
  }

  private fun removeHostReferences(
    id: String
  ) {
    childrenById[id]?.forEach(::removeHostReferences)
    childrenById.remove(id)
    hostById.remove(id)
    hostTypeById.remove(id)
  }

  @Suppress("UNCHECKED_CAST")
  private fun findAdapter(
    type: IHostType
  ): IHostAdapter<Any> {
    val typeName = type.name
    return requireNotNull(adapterByTypeName[typeName]) { "$type has no associated IHostAdapter" } as IHostAdapter<Any>
  }

  private fun findHostView(
    id: String
  ): Any = requireNotNull(hostById[id]) { "#$id has no associated view" }

  private fun findHostType(
    id: String
  ): IHostType = requireNotNull(hostTypeById[id]) { "#$id has no associated IHostType" }
}
