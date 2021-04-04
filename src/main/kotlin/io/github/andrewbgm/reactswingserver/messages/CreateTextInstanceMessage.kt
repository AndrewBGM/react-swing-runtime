package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class CreateTextInstanceMessage(
  @Expose val instanceId: Int,
  @Expose val text: String,
) : IMessage
