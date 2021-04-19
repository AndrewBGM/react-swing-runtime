package io.github.andrewbgm.reactswingserver.bridge

import io.github.andrewbgm.reactswingserver.message.*
import io.javalin.websocket.*
import java.awt.*
import kotlin.reflect.*

class Bridge(
  private val adapterByType: Map<KClass<*>, IHostAdapter<*>>,
) {
  private var _ws: WsContext? = null
  val ws: WsContext
    get() = _ws ?: error("No valid WS context available")

  private val adapterByTypeName: Map<String, IHostAdapter<*>> =
    mapOf(*adapterByType.toList()
      .map { Pair(it.first.simpleName!!, it.second) }
      .toTypedArray())

  private val instanceById: MutableMap<Int, Instance> = mutableMapOf(
    0 to RootInstance
  )

  fun appendChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {
    val parentInstance = instanceById[parentId]
      ?: error("#$parentId is not a valid instance ID")
    val childInstance = instanceById[childId]
      ?: error("#$childId is not a valid instance ID")

    parentInstance += childInstance

    if (parentInstance is HostInstance<*>) {
      val host = parentInstance.host
      val hostType = host::class

      val adapter =
        adapterByType[hostType] ?: error("Unsupported host type $hostType")
      @Suppress("UNCHECKED_CAST")
      adapter as IHostAdapter<Container>

      when (childInstance) {
        is HostInstance<*> -> adapter.appendChild(
          this,
          host,
          childInstance.host
        )
        is TextInstance -> adapter.updateText(this, host, parentInstance.text)
        else -> error("Cannot append #$childId to #$parentId")
      }
    } else error("Cannot append #$childId to #$parentId")
  }

  fun appendChildToContainer(
    ws: WsContext,
    containerId: Int,
    childId: Int,
  ) {
    val containerInstance = instanceById[containerId]
      ?: error("#$containerId is not a valid instance ID")
    val childInstance = instanceById[childId]
      ?: error("#$childId is not a valid instance ID")

    containerInstance += childInstance

    if (containerInstance is RootInstance && childInstance is HostInstance<*>) {
      val host = childInstance.host
      val hostType = host::class

      val adapter =
        adapterByType[hostType] ?: error("Unsupported host type $hostType")
      @Suppress("UNCHECKED_CAST")
      adapter as IHostAdapter<Container>

      adapter.appendToContainer(this, host)
    } else error("Cannot append #$childId to #$containerId")
  }

  fun appendInitialChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {
    appendChild(ws, parentId, childId)
  }

  fun clearContainer(
    ws: WsContext,
    containerId: Int,
  ) {
    val containerInstance = instanceById[containerId]
      ?: error("#$containerId is not a valid instance ID")

    if (containerInstance is RootInstance) {
      containerInstance.children.filterIsInstance<HostInstance<*>>().forEach {
        val host = it.host
        val hostType = host::class

        val adapter =
          adapterByType[hostType] ?: error("Unsupported host type $hostType")
        @Suppress("UNCHECKED_CAST")
        adapter as IHostAdapter<Container>

        adapter.removeFromContainer(this, host)
      }

      containerInstance.clear()
    } else error("Cannot clear #$containerId")
  }

  fun commitTextUpdate(
    ws: WsContext,
    instanceId: Int,
    text: String,
  ) {
    val instance = instanceById[instanceId]
      ?: error("#$instanceId is not a valid instance ID")

    if (instance is TextInstance) {
      instance.text = text

      val host = instance.parent?.host!!
      val hostType = host::class

      val adapter =
        adapterByType[hostType] ?: error("Unsupported host type $hostType")
      @Suppress("UNCHECKED_CAST")
      adapter as IHostAdapter<Container>

      adapter.updateText(this, host, instance.parent?.text!!)
    }
  }

  fun commitUpdate(
    ws: WsContext,
    instanceId: Int,
    changedProps: Map<String, Any?>,
  ) {
    val instance = instanceById[instanceId]
      ?: error("#$instanceId is not a valid instance ID")

    if (instance is HostInstance<*>) {
      val host = instance.host
      val hostType = host::class

      val adapter =
        adapterByType[hostType] ?: error("Unsupported host type $hostType")
      @Suppress("UNCHECKED_CAST")
      adapter as IHostAdapter<Container>

      adapter.update(this, host, changedProps)
    }
  }

  fun createInstance(
    ws: WsContext,
    instanceId: Int,
    type: String,
    props: Map<String, Any?>,
  ) {
    val adapter = adapterByTypeName[type]
      ?: error("Unsupported type $type")

    instanceById[instanceId] = HostInstance(adapter.create(this, props))
  }

  fun createTextInstance(
    ws: WsContext,
    instanceId: Int,
    text: String,
  ) {
    instanceById[instanceId] = TextInstance(text)
  }

  fun freeCallback(
    ws: WsContext,
    callbackId: Int,
  ) {
    ws.send(FreeCallbackMessage(callbackId))
  }

  fun insertBefore(
    ws: WsContext,
    parentId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    val parentInstance = instanceById[parentId]
      ?: error("#$parentId is not a valid instance ID")
    val childInstance = instanceById[childId]
      ?: error("#$childId is not a valid instance ID")
    val beforeChildInstance = instanceById[beforeChildId]
      ?: error("#$beforeChildId is not a valid instance ID")

    parentInstance.insertBefore(childInstance, beforeChildInstance)

    if (parentInstance is HostInstance<*>) {
      val host = parentInstance.host
      val hostType = host::class

      val adapter =
        adapterByType[hostType] ?: error("Unsupported host type $hostType")
      @Suppress("UNCHECKED_CAST")
      adapter as IHostAdapter<Container>

      when {
        childInstance is HostInstance<*> && beforeChildInstance is HostInstance<*> -> adapter.insertBefore(
          this,
          host,
          childInstance.host,
          beforeChildInstance.host
        )
        childInstance is TextInstance && beforeChildInstance is TextInstance -> adapter.updateText(
          this,
          host,
          parentInstance.text
        )
        else -> error("Cannot append #$childId to #$parentId")
      }
    } else error("Cannot append #$childId to #$parentId")
  }

  fun insertInContainerBefore(
    ws: WsContext,
    containerId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    val containerInstance = instanceById[containerId]
      ?: error("#$containerId is not a valid instance ID")
    val childInstance = instanceById[childId]
      ?: error("#$childId is not a valid instance ID")
    val beforeChildInstance = instanceById[beforeChildId]
      ?: error("#$beforeChildId is not a valid instance ID")

    containerInstance.insertBefore(childInstance, beforeChildInstance)

    if (containerInstance is RootInstance && childInstance is HostInstance<*> && beforeChildInstance is HostInstance<*>) {
      val host = childInstance.host
      val hostType = host::class

      val adapter =
        adapterByType[hostType] ?: error("Unsupported host type $hostType")
      @Suppress("UNCHECKED_CAST")
      adapter as IHostAdapter<Container>

      if (containerInstance.children.contains(childInstance)) {
        adapter.removeFromContainer(this, host)
      }

      adapter.appendToContainer(this, host)
    } else error("Cannot append #$childId to #$containerId")
  }

  fun invokeCallback(
    ws: WsContext,
    callbackId: Int,
    args: List<Any?>,
  ) {
    ws.send(InvokeCallbackMessage(callbackId, args))
  }

  fun removeChildFromContainer(
    ws: WsContext,
    containerId: Int,
    childId: Int,
  ) {
    val containerInstance = instanceById[containerId]
      ?: error("#$containerId is not a valid instance ID")
    val childInstance = instanceById[childId]
      ?: error("#$childId is not a valid instance ID")

    containerInstance -= childInstance

    if (containerInstance is RootInstance && childInstance is HostInstance<*>) {
      val host = childInstance.host
      val hostType = host::class

      val adapter =
        adapterByType[hostType] ?: error("Unsupported host type $hostType")
      @Suppress("UNCHECKED_CAST")
      adapter as IHostAdapter<Container>

      adapter.removeFromContainer(this, host)
    } else error("Cannot remove #$childId from #$containerId")
  }

  fun removeChild(
    ws: WsContext,
    parentId: Int,
    childId: Int,
  ) {
    val parentInstance = instanceById[parentId]
      ?: error("#$parentId is not a valid instance ID")
    val childInstance = instanceById[childId]
      ?: error("#$childId is not a valid instance ID")

    parentInstance -= childInstance

    if (parentInstance is HostInstance<*>) {
      val host = parentInstance.host
      val hostType = host::class

      val adapter =
        adapterByType[hostType] ?: error("Unsupported host type $hostType")
      @Suppress("UNCHECKED_CAST")
      adapter as IHostAdapter<Container>

      when (childInstance) {
        is HostInstance<*> -> adapter.appendChild(
          this,
          host,
          childInstance.host
        )
        is TextInstance -> adapter.updateText(this, host, parentInstance.text)
        else -> error("Cannot remove #$childId from #$parentId")
      }
    } else error("Cannot remove #$childId from #$parentId")
  }

  fun startApplication(
    ws: WsContext,
  ) {
    _ws = ws
  }
}
