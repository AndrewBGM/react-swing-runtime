package io.github.andrewbgm.reactswingserver.message

import com.google.gson.annotations.Expose

data class AppendInitialChildMessage(
  @Expose val containerId: Int,
  @Expose val childId: Int,
) : IMessage
