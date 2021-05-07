package io.github.andrewbgm.reactswingserver.impl.message

import io.github.andrewbgm.reactswingserver.api.*

/**
 * Built in message types.
 */
enum class MessageType : IMessageType {
  CREATE_VIEW,
  CREATE_TEXT_VIEW,
  UPDATE_VIEW,
  UPDATE_TEXT_VIEW,
  SET_CHILDREN,
  APPEND_CHILD,
  REMOVE_CHILD,
  INSERT_CHILD,
  INVOKE_CALLBACK
}
