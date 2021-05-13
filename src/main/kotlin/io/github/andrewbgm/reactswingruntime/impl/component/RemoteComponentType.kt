package io.github.andrewbgm.reactswingruntime.impl.component

import io.github.andrewbgm.reactswingruntime.api.*

@Suppress("SpellCheckingInspection")
enum class RemoteComponentType : IRemoteComponentType {
  JBUTTON,
  JFRAME,
  JLABEL,
  JMENU,
  JMENU_BAR,
  JMENU_ITEM,
  JPANEL,
  JSCROLL_PANE,
  JSPLIT_PANE;

  override fun toString() = name
}
