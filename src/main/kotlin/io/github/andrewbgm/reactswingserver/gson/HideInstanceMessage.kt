package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.annotations.Expose

data class HideInstanceMessage(
  @Expose val instanceId: Int,
) : IMessage
