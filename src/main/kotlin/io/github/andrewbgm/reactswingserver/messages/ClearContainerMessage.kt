package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class ClearContainerMessage(
  @Expose val containerId: Int,
) : Message
