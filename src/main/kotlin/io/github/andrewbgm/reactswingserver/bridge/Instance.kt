package io.github.andrewbgm.reactswingserver.bridge

import java.awt.*

sealed class Instance

data class HostInstance(
  val component: Container,
) : Instance() {
  private val _textInstances: MutableList<TextInstance> = mutableListOf()
  val textInstances: List<TextInstance>
    get() = _textInstances

  operator fun plusAssign(
    textInstance: TextInstance,
  ) {
    _textInstances += textInstance
  }

  operator fun minusAssign(
    textInstance: TextInstance,
  ) {
    _textInstances -= textInstance
  }
}

data class TextInstance(
  var text: String,
  var host: HostInstance?,
) : Instance()
