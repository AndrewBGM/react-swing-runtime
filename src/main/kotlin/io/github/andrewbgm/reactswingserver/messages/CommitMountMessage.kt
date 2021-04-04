package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class CommitMountMessage(
  @Expose val instanceId: Int,
  @Expose val type: String,
  @Expose val props: Map<String, Any?>,
) : Message
