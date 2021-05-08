package io.github.andrewbgm.reactswingruntime.impl

import java.awt.*

fun Container.insertBefore(
  child: Container,
  beforeChild: Container
) {
  if (components.contains(child)) {
    remove(child)
  }

  val idx = components.indexOf(beforeChild)
  add(child, idx)
}
