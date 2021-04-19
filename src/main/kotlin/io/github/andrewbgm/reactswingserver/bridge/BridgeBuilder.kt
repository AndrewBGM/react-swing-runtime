package io.github.andrewbgm.reactswingserver.bridge

import java.awt.*
import kotlin.reflect.*

class BridgeBuilder {
  private val adapterByType: MutableMap<KClass<*>, IHostAdapter<*>> =
    mutableMapOf()

  fun <T : Container> registerHostAdapter(
    type: KClass<T>,
    adapter: IHostAdapter<T>,
  ): BridgeBuilder {
    adapterByType[type] = adapter
    return this
  }

  inline fun <reified T : Container> registerHostAdapter(
    adapter: IHostAdapter<T>,
  ): BridgeBuilder = registerHostAdapter(T::class, adapter)

  fun create(): Bridge = Bridge(adapterByType.toMap())
}
