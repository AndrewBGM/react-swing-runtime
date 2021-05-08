package io.github.andrewbgm.reactswingruntime.impl.adapter

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

class LabelHostAdapter : IHostAdapter<JLabel> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext
  ): JLabel = JLabel().apply {
    update(this, props, ctx)
  }

  override fun update(
    host: JLabel,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  ) = with(host) {
    text = changedProps.getOrDefault("children", text) as String?
  }
}
