package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class ResetTextContentMessage(
  @Expose val instance: Int,
) : IMessage
