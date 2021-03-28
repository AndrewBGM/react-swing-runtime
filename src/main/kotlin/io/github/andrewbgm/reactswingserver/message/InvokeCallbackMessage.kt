package io.github.andrewbgm.reactswingserver.message

import com.google.gson.annotations.Expose

data class InvokeCallbackMessage(
  @Expose val callbackId: Int,
  @Expose val args: List<Any?>,
) : IMessage
