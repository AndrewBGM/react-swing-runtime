package io.github.andrewbgm.reactswingruntime.impl.view

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.component.*
import javax.swing.*

private const val ROOT_CONTAINER_ID = "00000000-0000-0000-0000-000000000000"

/**
 * View manager.
 */
class ViewManager {
  /**
   * Map of type name to remote component adapter.
   */
  private val adapterByTypeName = mutableMapOf<String, IRemoteComponentAdapter<out Any>>()

  /**
   * Map of View ID to View.
   */
  private val viewById = mutableMapOf<String, View>(
    ROOT_CONTAINER_ID to ContainerView(ROOT_CONTAINER_ID)
  )

  /**
   * Associates a remote component type with an adapter.
   *
   * @param type Remote component type.
   * @param adapter Remote component adapter.
   */
  fun registerAdapter(
    type: IRemoteComponentType,
    adapter: IRemoteComponentAdapter<*>,
  ): ViewManager = this.apply {
    val typeName = type.toString()
    require(!adapterByTypeName.contains(typeName)) { "$typeName already has an associated IRemoteComponentAdapter" }

    adapterByTypeName[typeName] = adapter
  }

  /**
   * Creates a new view.
   *
   * @param id View ID.
   * @param type Remote component type.
   * @param props View props.
   * @param ctx Message context.
   */
  fun createView(
    id: String,
    type: IRemoteComponentType,
    props: Map<String, Any?>,
    ctx: IMessageContext,
  ) = SwingUtilities.invokeAndWait {
    val view = RemoteComponentView(id, type)
    viewById[id] = view

    val adapter = findAdapter(view)
    view.obj = adapter.create(view, props, RemoteComponentContext(ctx, view))
  }

  /**
   * Updates an existing view.
   *
   * @param id View ID.
   * @param changedProps Props that have changed.
   * @param ctx Message context.
   */
  fun updateView(
    id: String,
    changedProps: Map<String, Any?>,
    ctx: IMessageContext,
  ) = SwingUtilities.invokeAndWait {
    val view = findView<RemoteComponentView>(id)

    val adapter = findAdapter(view)
    adapter.update(view, view.obj!!, changedProps, RemoteComponentContext(ctx, view))
  }

  /**
   * (Re)sets the children for an existing view.
   *
   * @param parentId Parent view ID.
   * @param childrenIds Children view IDs.
   * @param ctx Message context.
   */
  fun setChildren(
    parentId: String,
    childrenIds: List<String>,
    ctx: IMessageContext,
  ) = SwingUtilities.invokeAndWait {
    val parent = findView<View>(parentId)
    val children = childrenIds.map { findView<RemoteComponentView>(it) }

    parent.clear(children)

    when (parent) {
      is ContainerView -> {
        children.forEach {
          val adapter = findAdapter(it)
          adapter.appendToContainer(it, it.obj!!, RemoteComponentContext(ctx, it))
        }
      }
      is RemoteComponentView -> {
        val childrenObjects = children.map { it.obj!! }

        val adapter = findAdapter(parent)
        adapter.setChildren(parent,
          parent.obj!!,
          childrenObjects,
          RemoteComponentContext(ctx, parent))
      }
    }
  }

  /**
   * Appends a child to an existing view.
   *
   * @param parentId Parent view ID.
   * @param childId Child view ID.
   * @param ctx Message context.
   */
  fun appendChild(
    parentId: String,
    childId: String,
    ctx: IMessageContext,
  ) = SwingUtilities.invokeAndWait {
    val parent = findView<View>(parentId)
    val child = findView<RemoteComponentView>(childId)

    parent += child

    when (parent) {
      is ContainerView -> {
        val adapter = findAdapter(child)
        adapter.appendToContainer(child, child.obj!!, RemoteComponentContext(ctx, child))
      }
      is RemoteComponentView -> {
        val adapter = findAdapter(parent)
        adapter.appendChild(parent, parent.obj!!, child.obj!!, RemoteComponentContext(ctx, parent))
      }
    }
  }

  /**
   * Removes a child from an existing view.
   *
   * @param parentId Parent view ID.
   * @param childId Child view ID.
   * @param ctx Message context.
   */
  fun removeChild(
    parentId: String,
    childId: String,
    ctx: IMessageContext,
  ) = SwingUtilities.invokeAndWait {
    val parent = findView<View>(parentId)
    val child = findView<RemoteComponentView>(childId)

    parent -= child

    when (parent) {
      is ContainerView -> {
        val adapter = findAdapter(child)
        adapter.removeFromContainer(child, child.obj!!, RemoteComponentContext(ctx, child))
      }
      is RemoteComponentView -> {
        val adapter = findAdapter(parent)
        adapter.removeChild(parent, parent.obj!!, child.obj!!, RemoteComponentContext(ctx, parent))
      }
    }

    cleanupView(child)
  }

  /**
   * Inserts a child into an existing view, before another child.
   * This is used to both reorder children within the parent and add new children.
   *
   * @param parentId Parent view ID.
   * @param childId Child view ID.
   * @param beforeChildId Before child view ID.
   * @param ctx Message context.
   */
  fun insertChild(
    parentId: String,
    childId: String,
    beforeChildId: String,
    ctx: IMessageContext,
  ) = SwingUtilities.invokeAndWait {
    val parent = findView<View>(parentId)
    val child = findView<RemoteComponentView>(childId)
    val beforeChild = findView<RemoteComponentView>(beforeChildId)

    parent.insertBefore(child, beforeChild)

    when (parent) {
      is ContainerView -> {
        val adapter = findAdapter(child)
        adapter.insertInContainer(child,
          child.obj!!,
          beforeChild.obj!!,
          RemoteComponentContext(ctx, child))
      }
      is RemoteComponentView -> {
        val adapter = findAdapter(parent)
        adapter.insertChild(parent,
          parent.obj!!,
          child.obj!!,
          beforeChild.obj!!,
          RemoteComponentContext(ctx, parent))
      }
    }
  }

  private fun cleanupView(
    view: View,
  ) {
    view.children.forEach(::cleanupView)
    viewById.remove(view.id)
  }

  /**
   * Utility function for getting an adapter based on a view.
   */
  @Suppress("UNCHECKED_CAST")
  private fun findAdapter(
    view: RemoteComponentView,
  ): IRemoteComponentAdapter<Any> {
    val typeName = view.type.toString()
    return requireNotNull(adapterByTypeName[typeName]) { "$typeName has no associated IRemoteComponentAdapter" } as IRemoteComponentAdapter<Any>
  }

  /**
   * Utility function for getting a view based on an ID.
   */
  private inline fun <reified T : View> findView(
    id: String,
  ): T {
    val view = requireNotNull(viewById[id]) { "#$id has no associated View" }
    require(view is T) { "$view is not a valid ${T::class.java}" }

    return view
  }
}
