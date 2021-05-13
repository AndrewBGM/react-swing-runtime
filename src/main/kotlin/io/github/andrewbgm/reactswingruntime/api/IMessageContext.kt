package io.github.andrewbgm.reactswingruntime.api

/**
 * Contextual utilities for message handlers.
 */
interface IMessageContext {
  /**
   * Sends a message to the client.
   */
  fun send(
    message: IMessage,
  )
}
