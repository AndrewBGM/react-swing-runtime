package io.github.andrewbgm.reactswingruntime.impl.component

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*

class RemoteComponentContext(
  private val ctx: IMessageContext,
  private val view: IRemoteComponentView,
) : IRemoteComponentContext {
  override fun invokeCallback(
    name: String,
    args: List<Any?>?,
  ) = ctx.send(InvokeCallbackMessage(view.id, name, args))
}
