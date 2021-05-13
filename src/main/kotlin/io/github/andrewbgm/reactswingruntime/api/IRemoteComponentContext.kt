package io.github.andrewbgm.reactswingruntime.api

/**
 * Contextual utilities for remote components.
 */
interface IRemoteComponentContext {
  /**
   * Invokes a callback on the remote component.
   *
   * @param name Callback name.
   * @param args Callback args.
   */
  fun invokeCallback(
    name: String,
    args: List<Any?>? = null,
  )
}
