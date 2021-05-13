package io.github.andrewbgm.reactswingruntime.impl.component

import io.github.andrewbgm.reactswingruntime.api.*

@Suppress("SpellCheckingInspection")
enum class RemoteComponentType : IRemoteComponentType {
  JBUTTON,
  JFRAME,
  JLABEL,
  JPANEL;

  override fun toString() = name
}
