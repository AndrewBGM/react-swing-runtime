package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class ResetAfterCommitMessage(
  @Expose val containerInfo: Int,
) : IMessage
