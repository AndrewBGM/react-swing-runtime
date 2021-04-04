package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class CommitTextUpdate(
  @Expose val instanceId: Int,
  @Expose val oldText: String,
  @Expose val newText: String,
) : IMessage
