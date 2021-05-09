package io.github.andrewbgm.reactswingruntime.impl.message.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingruntime.api.*

data class CreateViewMessage(
  @Expose val id: String,
  @Expose val type: String,
  @Expose val props: Map<String, Any?>,
) : IMessage
