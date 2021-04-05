package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class CommitUpdateMessage(
  @Expose val instanceId: Int,
  @Expose val type: String,
  @Expose val changedProps: Map<String, Any?>,
) : IMessage
