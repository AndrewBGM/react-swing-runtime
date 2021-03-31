package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class HideInstanceMessage(
  @Expose val instanceId: Int,
) : IMessage
