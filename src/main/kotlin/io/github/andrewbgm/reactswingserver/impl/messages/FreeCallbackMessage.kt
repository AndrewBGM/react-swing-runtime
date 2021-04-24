package io.github.andrewbgm.reactswingserver.impl.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class FreeCallbackMessage(
  @Expose val callbackId: Number,
) : Message
