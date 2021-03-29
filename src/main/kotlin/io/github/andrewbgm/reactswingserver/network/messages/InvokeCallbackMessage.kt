package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class InvokeCallbackMessage(
  @Expose val callbackId: Int,
  @Expose val args: List<Any?>,
) : IMessage
