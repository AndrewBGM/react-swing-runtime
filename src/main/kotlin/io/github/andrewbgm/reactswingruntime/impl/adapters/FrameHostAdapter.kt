package io.github.andrewbgm.reactswingruntime.impl.adapters

import io.github.andrewbgm.reactswingruntime.api.*
import java.awt.*
import java.awt.event.*
import javax.swing.*

class FrameHostAdapter : IHostAdapter<JFrame> {
  override fun create(
    props: Map<String, Any?>,
    ctx: IHostContext,
  ): JFrame = JFrame().apply {
    defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
    addWindowListener(object : WindowAdapter() {
      override fun windowClosing(
        e: WindowEvent,
      ) {
        ctx.invokeCallback("onClose")
      }
    })

    update(this, props, ctx)
  }

  override fun update(
    host: JFrame,
    changedProps: Map<String, Any?>,
    ctx: IHostContext,
  ) = with(host) {
    title = changedProps.getOrDefault("title", title) as String?
  }

  override fun setChildren(
    host: JFrame,
    children: List<Any>,
    ctx: IHostContext,
  ) = children.forEach { appendChild(host, it, ctx) }

  override fun appendChild(
    host: JFrame,
    child: Any,
    ctx: IHostContext,
  ) = when (child) {
    is JMenuBar -> host.jMenuBar = child
    is Container -> host.contentPane = child
    else -> error("Cannot append $child to $host")
  }

  override fun appendToContainer(
    host: JFrame,
    ctx: IHostContext,
  ) {
    host.pack()
    host.setLocationRelativeTo(null)
    host.isVisible = true
    host.state = JFrame.ICONIFIED
    host.state = JFrame.NORMAL
  }

  override fun removeChild(
    host: JFrame,
    child: Any,
    ctx: IHostContext,
  ) = when (child) {
    is JMenuBar -> host.jMenuBar = null
    is Container -> host.contentPane = null
    else -> error("Cannot append $child to $host")
  }

  override fun removeFromContainer(
    host: JFrame,
    ctx: IHostContext,
  ) {
    host.dispose()
  }

  override fun insertChild(
    host: JFrame,
    child: Any,
    beforeChild: Any,
    ctx: IHostContext,
  ) = when (child) {
    is JMenuBar -> host.jMenuBar = child
    is Container -> host.contentPane = child
    else -> error("Cannot insert $child in $host before $beforeChild")
  }

  override fun insertInContainer(
    host: JFrame,
    beforeChild: Any,
    ctx: IHostContext,
  ) {
    if (!host.isVisible) {
      appendToContainer(host, ctx)
    }
  }
}
