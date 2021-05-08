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

  fun setChildren(
    host: T,
    children: List<Any>,
    ctx: IHostContext
  )

  fun appendChild(
    host: T,
    child: Any,
    ctx: IHostContext
  )

  fun appendToContainer(
    host: T,
    ctx: IHostContext
  )

  fun removeChild(
    host: T,
    child: Any,
    ctx: IHostContext
  )

  fun removeFromContainer(
    host: T,
    ctx: IHostContext
  )

  fun insertChild(
    host: T,
    child: Any,
    beforeChild: Any,
    ctx: IHostContext
  )

  fun insertInContainer(
    host: T,
    beforeChild: Any,
    ctx: IHostContext
  )
}
