package io.github.andrewbgm.reactswingserver.bridge.adapter

import io.github.andrewbgm.reactswingserver.bridge.*
import java.awt.*

interface IHostAdapter<T : Container> {
  fun create(
    bridge: Bridge,
    props: HostProps,
  ): T

  fun update(
    bridge: Bridge,
    host: T,
    oldProps: HostProps?,
    newProps: HostProps,
  )

  fun appendToContainer(
    bridge: Bridge,
    host: T,
  )

  fun insertBeforeInContainer(
    bridge: Bridge,
    host: T,
    beforeChild: Container,
  )

  fun removeFromContainer(
    bridge: Bridge,
    host: T,
  )

  fun appendChild(
    bridge: Bridge,
    host: T,
    child: Container,
  )

  fun insertBefore(
    bridge: Bridge,
    host: T,
    child: Container,
    beforeChild: Container,
  )

  fun removeChild(
    bridge: Bridge,
    host: T,
    child: Container,
  )
}
