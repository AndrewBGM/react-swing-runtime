package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class AppendChildToContainerMessage(
  @Expose val containerId: Int,
  @Expose val childId: Int,
) : IMessage
