package io.github.andrewbgm.reactswingserver.impl.message.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class AppendChildMessage(
  @Expose val parentId: String,
  @Expose val childId: String
) : IMessage
