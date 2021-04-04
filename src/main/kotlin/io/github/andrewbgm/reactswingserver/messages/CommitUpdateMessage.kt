package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class CommitUpdateMessage(
  @Expose val instanceId: Int,
  @Expose val updatePayload: Map<String, Any?>,
  @Expose val type: String,
) : Message
