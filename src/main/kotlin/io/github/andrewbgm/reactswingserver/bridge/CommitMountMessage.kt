package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class CommitMountMessage(
  @Expose val instance: Int,
  @Expose val type: String,
  @Expose val props: Map<String, Any?>,
) : IMessage
