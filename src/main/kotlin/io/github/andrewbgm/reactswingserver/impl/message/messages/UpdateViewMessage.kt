package io.github.andrewbgm.reactswingserver.impl.message.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class UpdateViewMessage(
  @Expose val id: String,
  @Expose val changedProps: Map<String, Any?>
) : IMessage
