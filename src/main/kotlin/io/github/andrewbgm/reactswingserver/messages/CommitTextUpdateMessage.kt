package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.Expose

data class CommitTextUpdateMessage(
  @Expose val instanceId: Int,
  @Expose val oldText: String,
  @Expose val newText: String,
) : Message
