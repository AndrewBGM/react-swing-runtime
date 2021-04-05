package io.github.andrewbgm.reactswingserver.messages

import com.google.gson.annotations.*

data class StartApplicationMessage(
  @Expose val containerId: Int,
) : IMessage
