package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class HideTextInstanceMessage(
  @Expose val textInstance: Int,
) : IMessage
