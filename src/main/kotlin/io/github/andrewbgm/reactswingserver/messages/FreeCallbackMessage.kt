package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class FreeCallbackMessage(
  @Expose val callbackId: Int,
) : IMessage
