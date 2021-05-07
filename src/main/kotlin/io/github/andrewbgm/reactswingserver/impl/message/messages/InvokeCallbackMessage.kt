package io.github.andrewbgm.reactswingserver.impl.message.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class InvokeCallbackMessage(
  @Expose val id: String,
  @Expose val args: List<Any?>
) : IMessage
