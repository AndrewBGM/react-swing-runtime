package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.Expose

data class ResetTextContentMessage(
  @Expose val instanceId: Int,
) : Message
