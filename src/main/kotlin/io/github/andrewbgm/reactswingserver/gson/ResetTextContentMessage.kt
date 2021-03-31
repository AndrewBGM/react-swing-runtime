package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.annotations.Expose

data class ResetTextContentMessage(
  @Expose val instance: Int,
) : IMessage
