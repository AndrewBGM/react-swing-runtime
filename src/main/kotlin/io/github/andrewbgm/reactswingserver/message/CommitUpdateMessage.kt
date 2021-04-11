package io.github.andrewbgm.reactswingserver.message

import com.google.gson.annotations.*

data class CommitUpdateMessage(
  @Expose val instanceId: Int,
  @Expose val changedProps: Map<String, Any?>,
) : IMessage
