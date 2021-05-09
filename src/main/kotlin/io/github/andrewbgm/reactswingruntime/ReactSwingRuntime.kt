package io.github.andrewbgm.reactswingruntime

import com.google.gson.*
import io.github.andrewbgm.reactswingruntime.api.*
import io.github.andrewbgm.reactswingruntime.impl.*
import io.github.andrewbgm.reactswingruntime.impl.adapters.*
import io.github.andrewbgm.reactswingruntime.impl.handlers.*
import io.github.andrewbgm.reactswingruntime.impl.message.*
import io.github.andrewbgm.reactswingruntime.impl.message.messages.*
import io.javalin.*
import io.javalin.plugin.json.*
import io.javalin.websocket.*
import kotlin.reflect.*

class ReactSwingRuntime {
  private val app by lazy(::configureApp)

  private val hostEnvironment = HostEnvironment()
  private val messageBus = MessageBus()
  private val messageSerializer = MessageSerializer()

  init {
    configureGson()

    registerHostType(HostType.BUTTON, ButtonHostAdapter())
    registerHostType(HostType.FRAME, FrameHostAdapter())
    registerHostType(HostType.LABEL, LabelHostAdapter())
    registerHostType(HostType.MENU, MenuHostAdapter())
    registerHostType(HostType.MENU_BAR, MenuBarHostAdapter())
    registerHostType(HostType.MENU_ITEM, MenuItemHostAdapter())
    registerHostType(HostType.PANEL, PanelHostAdapter())
    registerHostType(HostType.TEXT_FIELD, TextFieldHostAdapter())

    registerMessageType(MessageType.CREATE_VIEW, CreateViewMessageHandler(hostEnvironment))
    registerMessageType(MessageType.UPDATE_VIEW, UpdateViewMessageHandler(hostEnvironment))
    registerMessageType(MessageType.SET_CHILDREN, SetChildrenMessageHandler(hostEnvironment))
    registerMessageType(MessageType.APPEND_CHILD, AppendChildMessageHandler(hostEnvironment))
    registerMessageType(MessageType.REMOVE_CHILD, RemoveChildMessageHandler(hostEnvironment))
    registerMessageType(MessageType.INSERT_CHILD, InsertChildMessageHandler(hostEnvironment))

    registerMessageType<InvokeCallbackMessage>(MessageType.INVOKE_CALLBACK)
  }

  fun registerHostType(
    type: IHostType,
    adapter: IHostAdapter<out Any>,
  ): ReactSwingRuntime = this.apply {
    hostEnvironment.registerHostType(type, adapter)
  }

  inline fun <reified T : IMessage> registerMessageType(
    type: IMessageType,
    handler: IMessageHandler<T>? = null,
  ): ReactSwingRuntime = registerMessageType(type, T::class, handler)

  fun <T : IMessage> registerMessageType(
    type: IMessageType,
    clazz: KClass<T>,
    handler: IMessageHandler<T>? = null,
  ): ReactSwingRuntime = this.apply {
    messageSerializer.registerMessageType(type, clazz)
    handler?.let {
      messageBus.subscribe(clazz, handler)
    }
  }

  fun start(
    port: Int,
  ): ReactSwingRuntime = this.apply {
    app.start(port)
  }

  fun stop(): ReactSwingRuntime = this.apply {
    app.stop()
  }

  private fun handleMessage(
    ctx: WsMessageContext,
  ) {
    val message = ctx.message<IMessage>()
    messageBus.publish(message, MessageContext(ctx))
  }

  private fun configureApp(): Javalin = Javalin.create()
    .ws("/") {
      it.onMessage(::handleMessage)
      it.onClose { Thread { stop() }.start() }
    }

  private fun configureGson(): Gson = GsonBuilder()
    .excludeFieldsWithoutExposeAnnotation()
    .registerTypeAdapter(IMessage::class.java, messageSerializer)
    .create().also(::configureJsonMapping)

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
      ): String = if (obj is IMessage) gson.toJson(obj, IMessage::class.java) else gson.toJson(obj)
    }
  }
}
