package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class CommitUpdateMessage(
  @Expose val instanceId: Int,
  @Expose val updatePayload: CommitUpdatePayload,
) : IMessage

data class CommitUpdatePayload(
  val type: String,
  val changedProps: Map<String, Any?>,
)
