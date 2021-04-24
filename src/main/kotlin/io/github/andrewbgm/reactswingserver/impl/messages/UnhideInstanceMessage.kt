package io.github.andrewbgm.reactswingserver.impl.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class UnhideInstanceMessage(
  @Expose val instanceId: Number,
  @Expose val props: Map<String, Any?>,
) : Message
