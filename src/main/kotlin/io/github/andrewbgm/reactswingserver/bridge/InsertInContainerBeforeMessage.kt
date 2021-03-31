package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class InsertInContainerBeforeMessage(
  @Expose val containerId: Int,
  @Expose val childId: Int,
  @Expose val beforeChildId: Int,
) : IMessage
