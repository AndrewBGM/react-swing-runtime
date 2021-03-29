package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class RemoveChildMessage(
  @Expose val parentId: Int,
  @Expose val childId: Int,
) : IMessage