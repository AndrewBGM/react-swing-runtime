package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.annotations.Expose

data class HideTextInstanceMessage(
  @Expose val textInstance: Int,
) : IMessage
