package io.github.andrewbgm.reactswingruntime.impl.message

import io.github.andrewbgm.reactswingruntime.api.*

enum class MessageType : IMessageType {
  CREATE_VIEW,
  UPDATE_VIEW,
  SET_CHILDREN,
  APPEND_CHILD,
  REMOVE_CHILD,
  INSERT_CHILD,
  INVOKE_CALLBACK;

  override fun toString(): String = name
}
