package io.github.andrewbgm.reactswingruntime.impl

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*

class HostContext(
  private val id: String,
  private val ctx: IMessageContext,
) : IHostContext {
  override fun invokeCallback(
    name: String,
    args: List<Any?>?,
  ) {
    ctx.send(InvokeCallbackMessage(id, name, args ?: listOf()))
  }
}
