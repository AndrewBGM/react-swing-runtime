package io.github.andrewbgm.reactswingruntime.impl.message

import io.github.andrewbgm.reactswingruntime.api.*
import kotlin.reflect.*

/**
 * Message bus.
 */
class MessageBus {
  /**
   * Message handler by message class.
   */
  private val handlerByClazz = mutableMapOf<KClass<out IMessage>, IMessageHandler<out IMessage>>()

  /**
   * Dispatches a message to it's associated handler.
   */
  fun <T : IMessage> publish(
    message: T,
    ctx: IMessageContext,
  ) {
    val handler = findHandler(message)
    handler.handleMessage(message, ctx)
  }

  /**
   * Subscribes a handler to a specific message class.
   */
  fun <T : IMessage> subscribe(
    clazz: KClass<T>,
    handler: IMessageHandler<T>,
  ): MessageBus = this.apply {
    require(!handlerByClazz.contains(clazz)) { "$clazz already has an associated IMessageHandler" }

    handlerByClazz[clazz] = handler
  }

  /**
   * Utility function for finding the handler associated with a message.
   */
  @Suppress("UNCHECKED_CAST")
  private fun <T : IMessage> findHandler(
    message: T,
  ): IMessageHandler<T> {
    val clazz = message::class
    val handler =
      requireNotNull(handlerByClazz[clazz]) { "$clazz has no associated IMessageHandler" }

    return handler as IMessageHandler<T>
  }
}
