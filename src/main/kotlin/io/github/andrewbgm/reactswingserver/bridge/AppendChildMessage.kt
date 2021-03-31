package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class AppendChildMessage(
  @Expose val parentId: Int,
  @Expose val childId: Int,
) : IMessage
