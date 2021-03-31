package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class InsertBeforeMessage(
  @Expose val parentId: Int,
  @Expose val childId: Int,
  @Expose val beforeChildId: Int,
) : IMessage
