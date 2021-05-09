package io.github.andrewbgm.reactswingruntime.api

fun interface IMessageHandler<T : IMessage> {
  fun handleMessage(
    message: T,
    ctx: IMessageContext,
  )
}
