package io.github.andrewbgm.reactswingruntime.impl.adapter

import io.github.andrewbgm.reactswingruntime.api.*
import javax.swing.*

class PanelHostAdapter : IHostAdapter<JPanel> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext
  ): JPanel = JPanel().apply {
    update(this, props, ctx)
  }

  override fun update(
    host: JPanel,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  ) = with(host) {
    // noop
  }
}
