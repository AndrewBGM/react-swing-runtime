package io.github.andrewbgm.reactswingruntime.impl.message

import io.github.andrewbgm.reactswingruntime.api.*
import kotlin.reflect.*

class MessageBus {
  private val handlerByClazz = mutableMapOf<KClass<out IMessage>, IMessageHandler<out IMessage>>()

  fun publish(
    message: IMessage,
    ctx: IMessageContext,
  ) {
    val handler = findHandler(message)
    handler.handleMessage(message, ctx)
  }

  fun <T : IMessage> subscribe(
    clazz: KClass<T>,
    handler: IMessageHandler<T>,
  ): MessageBus = this.apply {
    require(!handlerByClazz.containsKey(clazz)) { "$clazz already has an associated IMessageHandler" }

    handlerByClazz[clazz] = handler
  }

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
