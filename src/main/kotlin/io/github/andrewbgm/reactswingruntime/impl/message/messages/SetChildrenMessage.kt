package io.github.andrewbgm.reactswingruntime.impl.message.messages

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingruntime.api.*

data class SetChildrenMessage(
  @Expose val parentId: String,
  @Expose val childrenIds: List<String>,
) : IMessage
