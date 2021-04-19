package io.github.andrewbgm.reactswingserver.bridge

import java.awt.*

interface IHostAdapter<T : Container> {
  fun create(
    bridge: Bridge,
    props: Map<String, Any?>,
  ): T

  fun update(
    bridge: Bridge,
    host: T,
    changedProps: Map<String, Any?>,
  )

  fun appendToContainer(
    bridge: Bridge,
    child: T,
  )

  fun removeFromContainer(
    bridge: Bridge,
    child: T,
  )

  fun appendChild(
    bridge: Bridge,
    parent: T,
    child: Container,
  )

  fun insertBefore(
    bridge: Bridge,
    parent: T,
    child: Container,
    beforeChild: Container,
  )

  fun removeChild(
    bridge: Bridge,
    parent: T,
    child: Container,
  )

  fun updateText(
    bridge: Bridge,
    parent: T,
    text: String,
  )
}
