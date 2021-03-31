package io.github.andrewbgm.reactswingserver.bridge

import com.google.gson.annotations.Expose

data class PreparePortalMountMessage(
  @Expose val containerInfo: Int,
) : IMessage
