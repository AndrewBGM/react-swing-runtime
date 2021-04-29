package io.github.andrewbgm.reactswingserver.impl.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class CommitUpdateMessage(
  @Expose val instanceId: Number,
  @Expose val oldProps: Map<String, Any?>,
  @Expose val newProps: Map<String, Any?>
) : Message
