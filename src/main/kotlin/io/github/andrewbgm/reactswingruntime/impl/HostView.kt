package io.github.andrewbgm.reactswingruntime.impl

import io.github.andrewbgm.reactswingruntime.api.*

data class HostView(
  val id: String,
  val type: IHostType,
  val host: Any,
) {
  private val _children = mutableListOf<HostView>()
  val children: List<HostView>
    get() = _children.toList()

  fun setChildren(
    children: List<HostView>,
  ) {
    _children.clear()
    _children += children
  }

  operator fun plusAssign(
    child: HostView,
  ) = _children.plusAssign(child)

  operator fun minusAssign(
    child: HostView,
  ) = _children.minusAssign(child)

  fun insertChild(
    child: HostView,
    beforeChild: HostView,
  ) {
    if (_children.contains(child)) {
      _children.remove(child)
    }

    val idx = _children.indexOf(beforeChild)
    _children.add(idx, child)
  }
}
