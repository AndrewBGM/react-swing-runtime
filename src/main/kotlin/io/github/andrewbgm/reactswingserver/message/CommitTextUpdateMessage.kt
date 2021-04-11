package io.github.andrewbgm.reactswingserver.message

import com.google.gson.annotations.*

data class CommitTextUpdateMessage(
  @Expose val instanceId: Int,
  @Expose val text: String,
) : IMessage
