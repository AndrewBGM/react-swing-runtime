package io.github.andrewbgm.reactswingserver.bridge

import java.awt.*

sealed class Instance(
  open val id: Int,
) {
  private val _children: MutableList<Instance> = mutableListOf()
  val children: List<Instance>
    get() = _children.toList()

  open operator fun plusAssign(
    instance: Instance,
  ) = _children.plusAssign(instance)

  open operator fun minusAssign(
    instance: Instance,
  ) = _children.minusAssign(instance)

  fun insertBefore(
    child: Instance,
    beforeChild: Instance,
  ) {
    if (children.contains(child)) {
      _children.remove(child)
    }

    val beforeIdx = children.indexOf(beforeChild)
    _children.add(beforeIdx, child)
  }
}

data class HostInstance(
  override val id: Int,
  val host: Container,
) : Instance(id) {
  val text: String
    get() = children.filterIsInstance<TextInstance>()
      .joinToString("") { it.text }

  override operator fun plusAssign(
    instance: Instance,
  ) {
    super.plusAssign(instance)

    if (instance is TextInstance) {
      instance.parent = this
    }
  }

  override operator fun minusAssign(
    instance: Instance,
  ) {
    super.minusAssign(instance)

    if (instance is TextInstance) {
      instance.parent = null
    }
  }
}

class RootInstance(
  override val id: Int,
) : Instance(id)

data class TextInstance(
  override val id: Int,
  var text: String,
) : Instance(id) {
  var parent: HostInstance? = null
}
