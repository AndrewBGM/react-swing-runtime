package io.github.andrewbgm.reactswingserver.api

interface MessageHandler<T : Message> {
  fun handleMessage(
    message: T
  )
}
