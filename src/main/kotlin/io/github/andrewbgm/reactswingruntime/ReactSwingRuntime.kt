package io.github.andrewbgm.reactswingruntime

import com.google.gson.*
import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.component.*
import io.github.andrewbgm.reactswingruntime.impl.component.adapters.*
import io.github.andrewbgm.reactswingruntime.impl.message.*
import io.github.andrewbgm.reactswingruntime.impl.message.handlers.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*
import io.github.andrewbgm.reactswingruntime.impl.view.*
import io.javalin.*
import io.javalin.plugin.json.*
import io.javalin.websocket.*
import kotlin.reflect.*

/**
 * Utilities for managing a React Swing compatible runtime.
 */
class ReactSwingRuntime {
  /**
   * Javalin server.
   */
  private val server by lazy(::configureServer)

  /**
   * Message bus.
   */
  private val messageBus = MessageBus()

  /**
   * Message JSON serializer.
   */
  private val messageSerializer = MessageSerializer()

  /**
   * View manager.
   */
  private val viewManager = ViewManager()

  /**
   * Remote component type JSON serializer.
   */
  private val remoteComponentTypeSerializer = RemoteComponentTypeSerializer()

  init {
    configureGson()

    registerMessage(MessageType.CREATE_VIEW, CreateViewMessageHandler(viewManager))
    registerMessage(MessageType.UPDATE_VIEW, UpdateViewMessageHandler(viewManager))
    registerMessage(MessageType.SET_CHILDREN, SetChildrenMessageHandler(viewManager))
    registerMessage(MessageType.APPEND_CHILD, AppendChildMessageHandler(viewManager))
    registerMessage(MessageType.REMOVE_CHILD, RemoveChildMessageHandler(viewManager))
    registerMessage(MessageType.INSERT_CHILD, InsertChildMessageHandler(viewManager))

    registerRemoteComponent(RemoteComponentType.JBUTTON, JButtonRemoteComponentAdapter())
    registerRemoteComponent(RemoteComponentType.JFRAME, JFrameRemoteComponentAdapter())
    registerRemoteComponent(RemoteComponentType.JLABEL, JLabelRemoteComponentAdapter())
    registerRemoteComponent(RemoteComponentType.JMENU, JMenuRemoteComponentAdapter())
    registerRemoteComponent(RemoteComponentType.JMENU_BAR, JMenuBarRemoteComponentAdapter())
    registerRemoteComponent(RemoteComponentType.JMENU_ITEM, JMenuItemRemoteComponentAdapter())
    registerRemoteComponent(RemoteComponentType.JPANEL, JPanelRemoteComponentAdapter())
    registerRemoteComponent(RemoteComponentType.JSCROLL_PANE, JScrollPaneRemoteComponentAdapter())
    registerRemoteComponent(RemoteComponentType.JSPLIT_PANE, JSplitPaneRemoteComponentAdapter())

    registerMessage<InvokeCallbackMessage>(MessageType.INVOKE_CALLBACK)
  }

  /**
   * Registers a message and associates it with a given type and optional handler.
   *
   * @param T Message class.
   * @param type Message type.
   * @param handler Message handler.
   */
  inline fun <reified T : IMessage> registerMessage(
    type: IMessageType,
    handler: IMessageHandler<T>? = null,
  ): ReactSwingRuntime = registerMessage(type, T::class, handler)

  /**
   * Registers a message and associates it with a given type and optional handler.
   *
   * @param type Message type.
   * @param clazz Message class.
   * @param handler Message handler.
   */
  fun <T : IMessage> registerMessage(
    type: IMessageType,
    clazz: KClass<T>,
    handler: IMessageHandler<T>? = null,
  ): ReactSwingRuntime = this.apply {
    messageSerializer.registerMessageType(type, clazz)
    handler?.let { messageBus.subscribe(clazz, handler) }
  }

  /**
   * Registers a remote component and associates it with a given type and adapter.
   *
   * @param type Remote component type.
   * @param type Remote component adapter.
   */
  fun registerRemoteComponent(
    type: IRemoteComponentType,
    adapter: IRemoteComponentAdapter<*>,
  ): ReactSwingRuntime = this.apply {
    remoteComponentTypeSerializer.registerComponentType(type)
    viewManager.registerAdapter(type, adapter)
  }

  /**
   * Synchronously starts the runtime server on the specified port.
   *
   * @param port Port number.
   */
  fun start(
    port: Int,
  ): ReactSwingRuntime = this.apply {
    server.start(port)
  }

  /**
   * Synchronously stops the runtime server.
   */
  fun stop(): ReactSwingRuntime = this.apply {
    server.stop()
  }

  /**
   * Called when a message is received.
   *
   * Dispatches messages to the message bus.
   */
  private fun handleMessage(
    ctx: WsMessageContext,
  ) {
    val message = ctx.message<IMessage>()
    messageBus.publish(message, MessageContext(ctx))
  }

  /**
   * Called when the connection is close.
   *
   * Shuts down the runtime server.
   */
  private fun handleClose() {
    Thread { stop() }.start()
  }

  /**
   * Configures the Javalin server used by the runtime.
   */
  private fun configureServer(): Javalin = Javalin.create()
    .ws("/") {
      it.onMessage(::handleMessage)
      it.onClose { handleClose() }
    }

  /**
   * Configures the Gson instance.
   */
  private fun configureGson(): Gson = GsonBuilder()
    .excludeFieldsWithoutExposeAnnotation()
    .registerTypeAdapter(IMessage::class.java, messageSerializer)
    .registerTypeAdapter(IRemoteComponentType::class.java, remoteComponentTypeSerializer)
    .create().also(::configureJsonMapping)

  /**
   * Configures Javalin JSON mapping.
   */
  private fun configureJsonMapping(
    gson: Gson,
  ) {
    JavalinJson.fromJsonMapper = object : FromJsonMapper {
      override fun <T> map(
        json: String,
        targetClass: Class<T>,
      ): T = gson.fromJson(json, targetClass)
    }

    JavalinJson.toJsonMapper = object : ToJsonMapper {
      override fun map(
        obj: Any,
      ): String = when (obj) {
        is IMessage -> gson.toJson(obj, IMessage::class.java)
        is IRemoteComponentType -> gson.toJson(obj, IRemoteComponentType::class.java)
        else -> gson.toJson(obj)
      }
    }
  }
}
