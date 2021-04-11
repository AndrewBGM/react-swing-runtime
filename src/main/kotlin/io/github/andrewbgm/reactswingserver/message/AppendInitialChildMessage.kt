package io.github.andrewbgm.reactswingserver.message

import com.google.gson.annotations.*

data class AppendInitialChildMessage(
  @Expose val parentId: Int,
  @Expose val childId: Int,
) : IMessage
