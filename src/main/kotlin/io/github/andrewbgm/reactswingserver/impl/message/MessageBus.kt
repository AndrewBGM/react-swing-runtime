package io.github.andrewbgm.reactswingserver.impl.message

import io.github.andrewbgm.reactswingserver.api.*
import kotlin.reflect.*

/**
 * Provides utilities for publishing and subscribing to message "events"
 */
class MessageBus {
  /**
   * Map of message class to handler instance.
   */
  private val handlerByClazz = mutableMapOf<KClass<*>, IMessageHandler<*>>()

  /**
   * Publishes a message, invoking the registered handler.
   *
   * @param message Message instance.
   */
  fun <T : IMessage> publish(
    message: T
  ) {
    val handler = findHandler(message)
    handler.handleMessage(message)
  }

  /**
   * Subscribes to a message.
   *
   * @param clazz Message class.
   * @param handler Message handler.
   */
  fun <T : IMessage> subscribe(
    clazz: KClass<T>,
    handler: IMessageHandler<T>
  ): MessageBus = this.apply {
    handlerByClazz[clazz] = handler
  }

  /**
   * Helper method for finding the handler for a message.
   */
  @Suppress("UNCHECKED_CAST")
  private fun <T : IMessage> findHandler(
    message: T
  ): IMessageHandler<T> = requireNotNull(handlerByClazz[message::class]) as IMessageHandler<T>
}
