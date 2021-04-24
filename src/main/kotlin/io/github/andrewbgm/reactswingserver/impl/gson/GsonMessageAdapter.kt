package io.github.andrewbgm.reactswingserver.impl.gson

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.api.*
import java.lang.reflect.*
import kotlin.reflect.*

class GsonMessageAdapter : JsonDeserializer<Message>, JsonSerializer<Message> {
  private val clazzByType: MutableMap<MessageType, KClass<out Message>> =
    mutableMapOf()
  private val typeByClazz: MutableMap<KClass<out Message>, MessageType> =
    mutableMapOf()

  /**
   * Registers a new message type
   *
   * @return current MessageBus for builder style pattern
   */
  inline fun <reified T : Message> registerMessageType(
    type: MessageType,
  ): GsonMessageAdapter = registerMessageType(type, T::class)

  /**
   * Registers a new message type
   *
   * @return current MessageBus for builder style pattern
   */
  fun registerMessageType(
    type: MessageType,
    clazz: KClass<out Message>,
  ): GsonMessageAdapter = this.apply {
    clazzByType[type] = clazz
    typeByClazz[clazz] = type
  }

  override fun deserialize(
    src: JsonElement,
    typeOfSrc: Type,
    ctx: JsonDeserializationContext,
  ): Message? {
    if (src.isJsonNull) {
      return null
    }

    val obj = src.asJsonObject
    val type = obj.get("type").asString
    val payload = obj.get("payload").asJsonObject

    val typeClazz = clazzByType.keys.find { it.toString() == type }
      ?: error("$type is not a registered MessageType")
    val clazz = clazzByType[typeClazz]!!

    return ctx.deserialize(payload, clazz.java)
  }

  override fun serialize(
    src: Message?,
    typeOfSrc: Type,
    ctx: JsonSerializationContext,
  ): JsonElement {
    if (src == null) {
      return JsonNull.INSTANCE
    }

    val type =
      typeByClazz[src::class] ?: error("$src is not a registered Message")

    return JsonObject().apply {
      addProperty("type", type.toString())
      add("payload", ctx.serialize(src))
    }
  }
}
