package io.github.andrewbgm.reactswingserver.api

fun interface MessageHandler<T : Message> {
  fun handleMessage(
    message: T
  )
}
