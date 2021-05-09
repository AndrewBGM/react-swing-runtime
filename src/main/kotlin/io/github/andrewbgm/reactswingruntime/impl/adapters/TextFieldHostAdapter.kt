package io.github.andrewbgm.reactswingruntime.impl.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*
import javax.swing.event.*

class TextFieldHostAdapter : IHostAdapter<JTextField> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext,
  ): JTextField = JTextField().apply {
    text = props.getOrDefault("initialValue", text) as String?

    document.addDocumentListener(object : DocumentListener {
      override fun insertUpdate(
        e: DocumentEvent,
      ) = handleChange()

      override fun removeUpdate(
        e: DocumentEvent,
      ) = handleChange()

      override fun changedUpdate(
        e: DocumentEvent,
      ) = handleChange()

      private fun handleChange() {
        ctx.invokeCallback("onChange", listOf(text))
      }
    })

    update(this, props, ctx)
  }

  override fun update(
    host: JTextField,
    changedProps: Map<String, Any?>,
    ctx: IHostContext,
  ) = with(host) {
    // noop
  }

  override fun setChildren(
    host: JTextField,
    children: List<Any>,
    ctx: IHostContext,
  ) = error("Cannot set children for $host")

  override fun appendChild(
    host: JTextField,
    child: Any,
    ctx: IHostContext,
  ) = error("Cannot append $child to $host")

  override fun appendToContainer(
    host: JTextField,
    ctx: IHostContext,
  ) = error("Cannot append $host to container")

  override fun removeChild(
    host: JTextField,
    child: Any,
    ctx: IHostContext,
  ) = error("Cannot remove $child from $host")

  override fun removeFromContainer(
    host: JTextField,
    ctx: IHostContext,
  ) = error("Cannot remove $host from container")

  override fun insertChild(
    host: JTextField,
    child: Any,
    beforeChild: Any,
    ctx: IHostContext,
  ) = error("Cannot insert $child in $host before $beforeChild")

  override fun insertInContainer(
    host: JTextField,
    beforeChild: Any,
    ctx: IHostContext,
  ) = error("Cannot insert $host in container before $beforeChild")
}
