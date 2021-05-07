package io.github.andrewbgm.reactswingserver.impl.message.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class CreateViewMessage(
  @Expose val id: String,
  @Expose val type: String,
  @Expose val props: Map<String, Any?>
) : IMessage
