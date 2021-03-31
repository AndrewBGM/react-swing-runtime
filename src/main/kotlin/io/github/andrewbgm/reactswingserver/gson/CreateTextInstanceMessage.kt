package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.annotations.Expose

data class CreateTextInstanceMessage(
  @Expose val instanceId: Int,
  @Expose val text: String,
) : IMessage
