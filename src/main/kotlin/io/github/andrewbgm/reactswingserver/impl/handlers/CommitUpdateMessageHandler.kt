package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class CommitUpdateMessageHandler : MessageHandler<CommitUpdateMessage> {
  override fun handleMessage(
    message: CommitUpdateMessage
  ) {
    val (instanceId, oldProps, newProps) = message

    println("commitUpdate($instanceId, $oldProps, $newProps)")
  }
}
