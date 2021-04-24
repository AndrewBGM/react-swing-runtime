package io.github.andrewbgm.reactswingserver.impl.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class UnhideTextInstanceMessage(
  @Expose val instanceId: Number,
  @Expose val text: String,
) : Message
