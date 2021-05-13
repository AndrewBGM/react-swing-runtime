package io.github.andrewbgm.reactswingruntime.impl.message.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingruntime.api.*

data class InvokeCallbackMessage(
  @Expose val id: String,
  @Expose val name: String,
  @Expose val args: List<Any?>? = null,
) : IMessage
