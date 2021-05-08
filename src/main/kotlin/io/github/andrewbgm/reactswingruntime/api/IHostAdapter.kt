package io.github.andrewbgm.reactswingruntime.api

interface IHostAdapter<T : Any> {
  fun create(
    props: Map<String, Any?>,
    ctx: IHostContext
  ): T

  fun update(
    host: T,
    changedProps: Map<String, Any?>,
    ctx: IHostContext
  )
}
