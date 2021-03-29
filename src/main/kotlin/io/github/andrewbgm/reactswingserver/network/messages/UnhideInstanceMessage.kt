package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class UnhideInstanceMessage(
  @Expose val instanceId: Int,
  @Expose val props: Map<String, Any?>,
) : IMessage
