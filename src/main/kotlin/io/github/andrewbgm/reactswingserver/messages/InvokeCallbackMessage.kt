package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class InvokeCallbackMessage(
  @Expose val callbackId: Int,
  @Expose val args: List<Any?>,
) : IMessage
