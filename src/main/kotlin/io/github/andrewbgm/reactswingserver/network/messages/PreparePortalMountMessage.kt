package io.github.andrewbgm.reactswingserver.network.messages

import com.google.gson.annotations.Expose

data class PreparePortalMountMessage(
  @Expose val containerInfo: Int,
) : IMessage
