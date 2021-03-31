package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class ResetTextContentMessage(
  @Expose val instance: Int,
) : IMessage
