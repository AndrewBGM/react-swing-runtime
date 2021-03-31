package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class CreateTextInstanceMessage(
  @Expose val instanceId: Int,
  @Expose val text: String,
) : IMessage
