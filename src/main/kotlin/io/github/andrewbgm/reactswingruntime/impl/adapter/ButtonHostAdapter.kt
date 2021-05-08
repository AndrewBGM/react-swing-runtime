package io.github.andrewbgm.reactswingruntime.impl.adapter

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

class ButtonHostAdapter : IHostAdapter<JButton> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext
  ): JButton = JButton().apply {
    addActionListener {
      ctx.invokeCallback("onAction")
    }

    update(this, props, ctx)
  }

  override fun update(
    host: JButton,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  ) = with(host) {
    text = changedProps.getOrDefault("children", text) as String?
  }
}
