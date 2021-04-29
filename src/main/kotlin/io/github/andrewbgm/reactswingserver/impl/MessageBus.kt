package io.github.andrewbgm.reactswingserver.impl

import io.github.andrewbgm.reactswingserver.api.*
import kotlin.reflect.*

class MessageBus {
  private val handlerByClazz: MutableMap<KClass<out Message>, MessageHandler<out Message>> =
    mutableMapOf()

  inline fun <reified T : Message> registerMessageHandler(
    handler: MessageHandler<T>
  ): MessageBus = registerMessageHandler(handler, T::class)

  fun <T : Message> registerMessageHandler(
    handler: MessageHandler<T>,
    clazz: KClass<T>,
  ): MessageBus = this.apply {
    handlerByClazz[clazz] = handler
  }

  fun handleMessage(
    message: Message
  ) {
    val handler = findHandler(message)
    handler.handleMessage(message)
  }

  private fun <T : Message> findHandler(
    message: T
  ): MessageHandler<T> {
    val handler = handlerByClazz[message::class]
      ?: error("$message has no registered handler")

    @Suppress("UNCHECKED_CAST")
    return handler as MessageHandler<T>
  }
}
