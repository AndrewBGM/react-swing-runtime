package io.github.andrewbgm.reactswingruntime.impl

import io.github.andrewbgm.reactswingruntime.api.*

enum class HostType(
  override val id: String
) : IHostType {
  BUTTON("BUTTON"),
  FRAME("FRAME"),
  LABEL("LABEL"),
  PANEL("PANEL"),
}
