package io.github.andrewbgm.reactswingruntime.api

/**
 * Message handler.
 */
fun interface IMessageHandler<T : IMessage> {
  /**
   * Called when a message is received that has been associated with this handler.
   */
  fun handleMessage(
    message: T,
    ctx: IMessageContext,
  )
}
