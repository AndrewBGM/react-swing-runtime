package io.github.andrewbgm.reactswingruntime.impl.message.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingruntime.api.*

data class AppendChildMessage(
  @Expose val parentId: String,
  @Expose val childId: String,
) : IMessage
