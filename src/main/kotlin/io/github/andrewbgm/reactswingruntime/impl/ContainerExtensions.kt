package io.github.andrewbgm.reactswingruntime.impl

import java.awt.*
import javax.swing.*

fun Container.insertBefore(
  child: Container,
  beforeChild: Container,
) {
  if (this is JMenu) {
    if (menuComponents.contains(child)) {
      remove(child)
    }

    val idx = menuComponents.indexOf(beforeChild)
    add(child, idx)
  } else {
    if (components.contains(child)) {
      remove(child)
    }

    val idx = components.indexOf(beforeChild)
    add(child, idx)
  }
}
