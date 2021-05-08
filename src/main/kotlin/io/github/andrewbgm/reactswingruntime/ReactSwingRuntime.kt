package io.github.andrewbgm.reactswingruntime

import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.*
import io.github.andrewbgm.reactswingruntime.impl.adapter.*

/**
 * Note to self:
 * Make text passed as children from JS side.
 * Check type in prepareUpdate, if it's a JButton/JLabel/etc,
 * encode the string there and then.
 */
class ReactSwingRuntime {
  private val adapterByTypeId = mutableMapOf<String, IHostAdapter<*>>()

  init {
    registerHostType(HostType.BUTTON, ButtonHostAdapter())
    registerHostType(HostType.FRAME, FrameHostAdapter())
    registerHostType(HostType.LABEL, LabelHostAdapter())
    registerHostType(HostType.PANEL, PanelHostAdapter())
  }

  fun registerHostType(
    type: IHostType,
    adapter: IHostAdapter<*>
  ): ReactSwingRuntime = this.apply {
    val id = type.id
    adapterByTypeId[id] = adapter
  }

  private fun createHost(
    id: String,
    type: IHostType,
    props: Map<String, Any?>
  ): Any {
    val typeId = type.id
    val adapter = requireNotNull(adapterByTypeId[typeId]) { "$type has no associated IHostAdapter" }
    return adapter.create(props, HostContext(id))
  }
}
