package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.annotations.Expose

data class ResetAfterCommitMessage(
  @Expose val containerInfo: Int,
) : IMessage
