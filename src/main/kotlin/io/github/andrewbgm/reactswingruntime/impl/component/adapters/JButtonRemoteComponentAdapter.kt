package io.github.andrewbgm.reactswingruntime.impl.component.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

class JButtonRemoteComponentAdapter : IRemoteComponentAdapter<JButton> {
  override fun create(
    view: IRemoteComponentView,
    props: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ): JButton = JButton().apply {
    addActionListener {
      ctx.invokeCallback("onAction")
    }

    update(view, this, props, ctx)
  }

  override fun update(
    view: IRemoteComponentView,
    obj: JButton,
    changedProps: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ) = with(obj) {
    text = changedProps.getOrDefault("text", text) as String?
  }
}
