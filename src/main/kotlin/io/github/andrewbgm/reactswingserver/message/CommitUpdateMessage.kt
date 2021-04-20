package io.github.andrewbgm.reactswingserver.message

import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.bridge.adapter.*

data class CommitUpdateMessage(
  @Expose val instanceId: Int,
  @Expose val oldProps: HostProps?,
  @Expose val newProps: HostProps,
) : IMessage
