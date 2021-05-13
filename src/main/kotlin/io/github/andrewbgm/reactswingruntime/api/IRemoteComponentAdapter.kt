package io.github.andrewbgm.reactswingruntime.api

interface IRemoteComponentAdapter<T : Any> {
  fun create(
    view: IRemoteComponentView,
    props: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  ): T

  fun update(
    view: IRemoteComponentView,
    obj: T,
    changedProps: Map<String, Any?>,
    ctx: IRemoteComponentContext,
  )

  fun setChildren(
    view: IRemoteComponentView,
    parent: T,
    children: List<Any>,
    ctx: IRemoteComponentContext,
  ): Unit = error("Cannot set children '$children' on $parent")

  fun appendChild(
    view: IRemoteComponentView,
    parent: T,
    child: Any,
    ctx: IRemoteComponentContext,
  ): Unit = error("Cannot append $child to $parent")

  fun appendToContainer(
    view: IRemoteComponentView,
    obj: T,
    ctx: IRemoteComponentContext,
  ): Unit = error("Cannot append $obj to container")

  fun removeChild(
    view: IRemoteComponentView,
    parent: T,
    child: Any,
    ctx: IRemoteComponentContext,
  ): Unit = error("Cannot remove $child from $parent")

  fun removeFromContainer(
    view: IRemoteComponentView,
    obj: T,
    ctx: IRemoteComponentContext,
  ): Unit = error("Cannot remove $obj from container")

  fun insertChild(
    view: IRemoteComponentView,
    parent: T,
    child: Any,
    beforeChild: Any,
    ctx: IRemoteComponentContext,
  ): Unit = error("Cannot insert $child in $parent before $beforeChild")

  fun insertInContainer(
    view: IRemoteComponentView,
    obj: T,
    beforeObj: Any,
    ctx: IRemoteComponentContext,
  ): Unit = error("Cannot insert $obj in container before $beforeObj")
}
