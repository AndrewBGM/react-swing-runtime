package io.github.andrewbgm.reactswingserver.bridge

import java.awt.*

sealed class Instance

data class HostInstance(
  val component: Container,
) : Instance() {
  private val textInstances: MutableList<TextInstance> = mutableListOf()
  val text: String
    get() = textInstances.joinToString("") { it.text }

  operator fun plusAssign(
    textInstance: TextInstance,
  ) {
    textInstances += textInstance
  }

  operator fun minusAssign(
    textInstance: TextInstance,
  ) {
    textInstances -= textInstance
  }
}

data class TextInstance(
  var text: String,
  var host: HostInstance?,
) : Instance()
