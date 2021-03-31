package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class CommitUpdateMessage(
  @Expose val instanceId: Int,
  @Expose val type: String,
  @Expose val prevProps: Map<String, Any?>,
  @Expose val nextProps: Map<String, Any?>,
) : IMessage
