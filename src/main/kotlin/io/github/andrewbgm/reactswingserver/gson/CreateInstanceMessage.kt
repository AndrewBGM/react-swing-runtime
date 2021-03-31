package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.annotations.Expose

data class CreateInstanceMessage(
  @Expose val instanceId: Int,
  @Expose val type: String,
  @Expose val props: Map<String, Any?>,
) : IMessage
