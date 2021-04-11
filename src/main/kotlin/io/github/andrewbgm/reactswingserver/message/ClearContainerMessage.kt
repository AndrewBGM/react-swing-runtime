package io.github.andrewbgm.reactswingserver.message

import com.google.gson.annotations.*

data class ClearContainerMessage(
  @Expose val containerId: Int,
) : IMessage
