package io.github.andrewbgm.reactswingserver.impl.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class InvokeCallbackMessage(
  @Expose val callbackId: Number,
  @Expose val args: List<Any?>?,
) : Message
