package io.github.andrewbgm.reactswingserver.bridge

import java.awt.*

sealed class Instance {
  private val _children: MutableList<Instance> = mutableListOf()
  val children: List<Instance>
    get() = _children.toList()

  fun insertBefore(
    instance: Instance,
    beforeInstance: Instance,
  ) {
    if (_children.contains(instance)) {
      _children.remove(instance)
    }

    val idx = _children.indexOf(beforeInstance)
    _children.add(idx, instance)
  }

  fun clear() = _children.clear()

  open operator fun plusAssign(
    instance: Instance,
  ) {
    _children += instance
  }

  open operator fun minusAssign(
    instance: Instance,
  ) {
    _children -= instance
  }
}

data class HostInstance<T : Container>(
  val host: T,
) : Instance() {
  val text: String
    get() = children.filterIsInstance<TextInstance>()
      .joinToString("") { it.text }

  override operator fun plusAssign(
    instance: Instance
  ) {
    super.plusAssign(instance)

    if (instance is TextInstance) {
      instance.parent = this
    }
  }

  override operator fun minusAssign(
    instance: Instance
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
  var parent: HostInstance<*>? = null
}
