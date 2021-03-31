package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class RemoveChildFromContainerMessage(
  @Expose val containerId: Int,
  @Expose val childId: Int,
) : IMessage
