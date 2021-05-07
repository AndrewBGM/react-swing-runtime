package io.github.andrewbgm.reactswingserver.api

/**
 * Message handler.
 */
fun interface IMessageHandler<T : IMessage> {
  /**
   * Handler function for the message.
   *
   * @param message Message instance.
   */
  fun handleMessage(
    message: T
  )
}
