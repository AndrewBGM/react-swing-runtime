package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class RemoveChildFromContainerMessage(
  @Expose val containerId: Int,
  @Expose val childId: Int,
) : IMessage
