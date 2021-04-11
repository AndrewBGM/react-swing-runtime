package io.github.andrewbgm.reactswingserver.message

import com.google.gson.annotations.*

data class InsertBeforeMessage(
  @Expose val parentId: Int,
  @Expose val childId: Int,
  @Expose val beforeChildId: Int,
) : IMessage
