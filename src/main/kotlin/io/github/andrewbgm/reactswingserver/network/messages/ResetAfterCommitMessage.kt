package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class ResetAfterCommitMessage(
  @Expose val containerInfo: Int,
) : IMessage
