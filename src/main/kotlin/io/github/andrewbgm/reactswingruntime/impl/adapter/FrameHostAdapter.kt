package io.github.andrewbgm.reactswingruntime.impl.adapter

import io.github.andrewbgm.reactswingruntime.api.*
import java.awt.event.*
import javax.swing.*

class FrameHostAdapter : IHostAdapter<JFrame> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext
  ): JFrame = JFrame().apply {
    defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
    addWindowListener(object : WindowAdapter() {
      override fun windowClosing(
        e: WindowEvent
      ) {
        ctx.invokeCallback("onClose")
      }
    })

    update(this, props, ctx)
  }

  override fun update(
    host: JFrame,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  ) = with(host) {
    title = changedProps.getOrDefault("title", title) as String?
  }
}
