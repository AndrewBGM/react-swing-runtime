package io.github.andrewbgm.reactswingruntime.impl.view

import io.github.andrewbgm.reactswingruntime.api.*

sealed class View(
  open val id: String,
) {
  private var _parent: View? = null
  val parent: View?
    get() = _parent

  private val _children = mutableListOf<View>()
  val children: List<View>
    get() = _children.toList()

  fun clear(
    children: List<View>? = null,
  ) {
    _children.forEach {
      it._parent = null
      it.clear()
    }

    _children.clear()
    children?.forEach { this += it }
  }

  fun insertBefore(
    child: View,
    beforeChild: View,
  ) {
    require(_children.contains(beforeChild)) { "$beforeChild is not a child of $this" }
    require(beforeChild.parent == this) { "$beforeChild is not a child of $this" }

    _children.remove(child)

    val idx = _children.indexOf(beforeChild)
    child._parent = this
    _children.add(idx, child)
  }

  operator fun plusAssign(
    child: View,
  ) {
    require(!_children.contains(child)) { "$child is already a child of $this" }
    require(child.parent == null) { "$child is a child of another View" }

    child._parent = this
    _children += child
  }

  operator fun minusAssign(
    child: View,
  ) {
    require(_children.contains(child)) { "$child is not a child of $this" }
    require(child.parent == this) { "$child is not a child of $this" }

    child._parent = null
    _children -= child
  }
}

data class ContainerView(
  override val id: String,
) : View(id)

data class RemoteComponentView(
  override val id: String,
  override val type: IRemoteComponentType,
) : View(id), IRemoteComponentView {
  var obj: Any? = null
}
