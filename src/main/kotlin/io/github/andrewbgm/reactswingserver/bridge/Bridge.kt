package io.github.andrewbgm.reactswingserver.bridge

import io.github.andrewbgm.reactswingserver.bridge.adapter.*
import io.github.andrewbgm.reactswingserver.message.*
import io.javalin.websocket.*
import java.awt.*
import kotlin.reflect.*

class Bridge(
  vararg pairs: Pair<KClass<out Container>, IHostAdapter<out Container>>,
) {
  companion object {
    fun setContext(
      bridge: Bridge,
      ctx: WsContext,
    ) {
      bridge._ctx = ctx
    }
  }

  private var _ctx: WsContext? = null
  private val ctx: WsContext
    get() = _ctx ?: error("No web-socket context configured for $this")
  private val hostAdapterByType: Map<KClass<out Container>, IHostAdapter<out Container>> =
    pairs.toMap()
  private val hostAdapterByTypeName: Map<String, IHostAdapter<out Container>> =
    hostAdapterByType.map { Pair(it.key.simpleName!!, it.value) }.toMap()
  private val instanceById: MutableMap<Int, Instance> = mutableMapOf(
    0 to RootInstance
  )

  fun appendChild(
    parentId: Int,
    childId: Int,
  ) {
    val parentInstance = findInstance<HostInstance>(parentId)
    val childInstance = findInstance<Instance>(childId)
    val parentAdapter = findAdapter(parentInstance)

    parentInstance += childInstance

    when (childInstance) {
      is HostInstance -> parentAdapter.appendChild(
        this,
        parentInstance.host,
        childInstance.host
      )
      is TextInstance -> parentAdapter.applyText(
        this,
        parentInstance.host,
        parentInstance.text
      )
      else -> error("Cannot append #$childId to $parentId")
    }
  }

  fun appendChildToContainer(
    containerId: Int,
    childId: Int,
  ) {
    val containerInstance = findInstance<RootInstance>(containerId)
    val childInstance = findInstance<HostInstance>(childId)
    val childAdapter = findAdapter(childInstance)

    containerInstance += childInstance

    childAdapter.appendToContainer(this, childInstance.host)
  }

  fun appendInitialChild(
    parentId: Int,
    childId: Int,
  ) = appendChild(parentId, childId)

  fun clearContainer(
    containerId: Int,
  ) {
    val containerInstance = findInstance<RootInstance>(containerId)
    containerInstance.children.filterIsInstance<HostInstance>().forEach {
      val adapter = findAdapter(it)
      adapter.removeFromContainer(this, it.host)
    }
  }

  fun commitTextUpdate(
    instanceId: Int,
    text: String,
  ) {
    val instance = findInstance<TextInstance>(instanceId)
    instance.text = text

    instance.parent?.let {
      val adapter = findAdapter(it)
      adapter.applyText(this, it.host, it.text)
    }
  }

  fun commitUpdate(
    instanceId: Int,
    oldProps: Map<String, Any?>?,
    newProps: Map<String, Any?>,
  ) {
    val instance = findInstance<HostInstance>(instanceId)
    val adapter = findAdapter(instance)

    adapter.update(this, instance.host, oldProps, newProps)
  }

  fun createInstance(
    instanceId: Int,
    type: String,
    props: Map<String, Any?>,
  ) {
    val adapter = findAdapter(type)
    val host = adapter.create(this, props)

    instanceById[instanceId] = HostInstance(host)
  }

  fun createTextInstance(
    instanceId: Int,
    text: String,
  ) {
    instanceById[instanceId] = TextInstance(text)
  }

  fun freeCallback(
    callbackId: Number,
  ) {
    ctx.send(FreeCallbackMessage(callbackId.toInt()))
  }

  fun insertBefore(
    parentId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    val parentInstance = findInstance<HostInstance>(parentId)
    val childInstance = findInstance<Instance>(childId)
    val beforeChildInstance = findInstance<Instance>(beforeChildId)
    val parentAdapter = findAdapter(parentInstance)

    parentInstance.insertBefore(childInstance, beforeChildInstance)

    when {
      childInstance is HostInstance && beforeChildInstance is HostInstance ->
        parentAdapter.insertBefore(
          this,
          parentInstance.host,
          childInstance.host,
          beforeChildInstance.host
        )
      childInstance is TextInstance && beforeChildInstance is TextInstance -> parentAdapter.applyText(
        this,
        parentInstance.host,
        parentInstance.text
      )
      else -> error("Cannot append #$childId to $parentId")
    }
  }

  fun insertInContainerBefore(
    containerId: Int,
    childId: Int,
    beforeChildId: Int,
  ) {
    val containerInstance = findInstance<RootInstance>(containerId)
    val childInstance = findInstance<HostInstance>(childId)
    val beforeChildInstance = findInstance<HostInstance>(beforeChildId)
    val childAdapter = findAdapter(childInstance)

    containerInstance.insertBefore(childInstance, beforeChildInstance)

    childAdapter.insertInContainerBefore(
      this,
      childInstance.host,
      beforeChildInstance.host
    )
  }

  fun invokeCallback(
    callbackId: Number,
    args: List<Any?>? = null,
  ) {
    ctx.send(InvokeCallbackMessage(callbackId.toInt(), args))
  }

  fun removeChildFromContainer(
    containerId: Int,
    childId: Int,
  ) {
    val containerInstance = findInstance<RootInstance>(containerId)
    val childInstance = findInstance<HostInstance>(childId)
    val childAdapter = findAdapter(childInstance)

    containerInstance -= childInstance

    childAdapter.removeFromContainer(this, childInstance.host)
  }

  fun removeChild(
    parentId: Int,
    childId: Int,
  ) {
    val parentInstance = findInstance<HostInstance>(parentId)
    val childInstance = findInstance<Instance>(childId)
    val parentAdapter = findAdapter(parentInstance)

    parentInstance -= childInstance

    when (childInstance) {
      is HostInstance -> parentAdapter.removeChild(
        this,
        parentInstance.host,
        childInstance.host
      )
      is TextInstance -> parentAdapter.applyText(
        this,
        parentInstance.host,
        parentInstance.text
      )
      else -> error("Cannot append #$childId to $parentId")
    }
  }

  fun startApplication() {
    // noop
  }

  private inline fun <reified T : Instance> findInstance(
    instanceId: Int,
  ): T {
    val instance = instanceById[instanceId]
      ?: error("#$instanceId is not a valid instance ID")

    if (instance !is T) {
      error("#$instance is not a valid ${T::class}")
    }

    return instance
  }

  @Suppress("UNCHECKED_CAST")
  private fun findAdapter(
    instance: HostInstance,
  ): IHostAdapter<Container> =
    hostAdapterByType[instance.host::class] as IHostAdapter<Container>?
      ?: error("Invalid host type ${instance.host::class}")

  @Suppress("UNCHECKED_CAST")
  private fun findAdapter(
    type: String,
  ): IHostAdapter<Container> =
    hostAdapterByTypeName[type] as IHostAdapter<Container>?
      ?: error("Invalid host type $type")
}
