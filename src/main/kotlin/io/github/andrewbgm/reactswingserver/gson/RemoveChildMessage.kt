package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.annotations.Expose

data class RemoveChildMessage(
  @Expose val parentId: Int,
  @Expose val childId: Int,
) : IMessage
