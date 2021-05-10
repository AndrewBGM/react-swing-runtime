package io.github.andrewbgm.reactswingruntime.impl

import io.github.andrewbgm.reactswingruntime.api.*

enum class HostType(
  override val id: String
) : IHostType {
  BUTTON("JButton"),
  CHECK_BOX("JCheckBox"),
  FRAME("JFrame"),
  LABEL("JLabel"),
  MENU("JMenu"),
  MENU_BAR("JMenuBar"),
  MENU_ITEM("JMenuItem"),
  PANEL("JPanel"),
  RADIO_BUTTON("JRadioButton"),
  SCROLL_PANE("JScrollPane"),
  TEXT_FIELD("JTextField");

  companion object {
    fun byId(
      id: String
    ): HostType = values().first { it.id == id }
  }
}
