package io.github.andrewbgm.reactswingruntime.impl.message.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingruntime.api.*

data class UpdateViewMessage(
  @Expose val id: String,
  @Expose val changedProps: Map<String, Any?>,
) : IMessage
