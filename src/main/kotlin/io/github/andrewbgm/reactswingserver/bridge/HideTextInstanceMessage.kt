package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class HideTextInstanceMessage(
  @Expose val textInstance: Int,
) : IMessage
