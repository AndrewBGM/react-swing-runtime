package io.github.andrewbgm.reactswingserver.impl.handlers

import io.github.andrewbgm.reactswingserver.api.*
import io.github.andrewbgm.reactswingserver.impl.messages.*

class CommitTextUpdateMessageHandler : MessageHandler<CommitTextUpdateMessage> {
  override fun handleMessage(
    message: CommitTextUpdateMessage
  ) {
    val (instanceId, oldText, newText) = message

    println("commitTextUpdate($instanceId, $oldText, $newText)")
  }
}
