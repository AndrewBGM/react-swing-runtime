package io.github.andrewbgm.reactswingserver.impl.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class CommitTextUpdateMessage(
  @Expose val instanceId: Number,
  @Expose val oldText: String,
  @Expose val newText: String
) : Message
