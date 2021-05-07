package io.github.andrewbgm.reactswingserver.impl.message.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.api.*

data class SetChildrenMessage(
  @Expose val parentId: String,
  @Expose val childrenIds: List<String>
) : IMessage
