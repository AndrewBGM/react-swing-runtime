package io.github.andrewbgm.reactswingserver.bridge

import java.awt.*

sealed class Instance {
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
  val host: Container,
) : Instance() {
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

object RootInstance : Instance()

data class TextInstance(
  var text: String,
) : Instance() {
  var parent: HostInstance? = null
}
