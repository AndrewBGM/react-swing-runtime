package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class HideInstanceMessage(
  @Expose val instanceId: Int,
) : IMessage
