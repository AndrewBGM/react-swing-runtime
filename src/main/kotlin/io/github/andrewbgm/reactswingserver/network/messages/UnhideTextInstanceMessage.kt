package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class UnhideTextInstanceMessage(
  @Expose val textInstance: Int,
  @Expose val text: String,
) : IMessage
