package io.github.andrewbgm.reactswingruntime.impl.component.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

class JLabelRemoteComponentAdapter : IRemoteComponentAdapter<JLabel> {
  override fun create(
    view: IRemoteComponentView,
    props: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ): JLabel = JLabel().apply {
    update(view, this, props, ctx)
  }

  override fun update(
    view: IRemoteComponentView,
    obj: JLabel,
    changedProps: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ) = with(obj) {
    text = changedProps.getOrDefault("text", text) as String?
  }
}
