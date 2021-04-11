package io.github.andrewbgm.reactswingserver.message

data class InvokeCallbackMessage(
  val callbackId: Int,
  val args: List<Any?>,
) : IMessage
