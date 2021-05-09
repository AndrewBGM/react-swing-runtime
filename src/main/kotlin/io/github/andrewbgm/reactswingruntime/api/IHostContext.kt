package io.github.andrewbgm.reactswingruntime.api

interface IHostContext {
  fun invokeCallback(
    name: String,
    args: List<Any?>? = null,
  )
}
