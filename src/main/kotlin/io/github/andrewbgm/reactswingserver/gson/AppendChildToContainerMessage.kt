package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.annotations.Expose

data class AppendChildToContainerMessage(
  @Expose val containerId: Int,
  @Expose val childId: Int,
) : IMessage
