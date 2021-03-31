package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class CommitTextUpdateMessage(
  @Expose val textInstance: Int,
  @Expose val oldText: String,
  @Expose val newText: String,
) : IMessage
