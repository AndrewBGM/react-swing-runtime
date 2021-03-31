package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class UnhideTextInstanceMessage(
  @Expose val textInstance: Int,
  @Expose val text: String,
) : IMessage
