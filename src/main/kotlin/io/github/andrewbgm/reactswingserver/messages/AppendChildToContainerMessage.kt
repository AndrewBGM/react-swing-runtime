package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class AppendChildToContainerMessage(
  @Expose val containerId: Int,
  @Expose val childId: Int,
) : IMessage
