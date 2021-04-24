package io.github.andrewbgm.reactswingserver.impl

import io.github.andrewbgm.reactswingserver.api.*
import kotlin.reflect.*

class MessageBus {
  private val messageHandlerByClazz: MutableMap<KClass<out Message>, MessageHandler<out Message>> =
    mutableMapOf()

  /**
   * Invokes the handler for a message
   */
  fun handleMessage(
    message: Message,
  ) {
    val handler = findHandler(message)
    handler.handleMessage(message)
  }

  /**
   * Registers a new message handler
   *
   * @return current MessageBus for builder style pattern
   */
  inline fun <reified T : Message> registerMessageHandler(
    handler: MessageHandler<T>,
  ): MessageBus = registerMessageHandler(T::class, handler)

  /**
   * Registers a new message handler
   *
   * @return current MessageBus for builder style pattern
   */
  fun <T : Message> registerMessageHandler(
    type: KClass<T>,
    handler: MessageHandler<T>,
  ): MessageBus = this.apply {
    messageHandlerByClazz[type] = handler
  }

  /**
   * Returns the registered MessageHandler associated with the given Message
   *
   * @param message message instance
   */
  private fun <T : Message> findHandler(
    message: T,
  ): MessageHandler<T> {
    val handler = messageHandlerByClazz[message::class]
      ?: error("$message has no associated MessageHandler")

    @Suppress("UNCHECKED_CAST")
    return handler as MessageHandler<T>
  }
}
