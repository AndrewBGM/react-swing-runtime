package io.github.andrewbgm.reactswingserver.impl.message

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.api.*
import java.lang.reflect.*
import kotlin.reflect.*

private const val TYPE_KEY = "type"

private const val PAYLOAD_KEY = "payload"

/**
 * Gson (De)Serializer for IMessage
 */
class MessageAdapter : JsonDeserializer<IMessage>, JsonSerializer<IMessage> {
  /**
   * Map of type name to message class.
   */
  private val clazzByTypeName = mutableMapOf<String, KClass<out IMessage>>()

  /**
   * Map of message class to type name.
   */
  private val typeNameByClazz = mutableMapOf<KClass<out IMessage>, String>()

  /**
   * Registers an association between a message type and class.
   */
  fun registerType(
    type: IMessageType,
    clazz: KClass<out IMessage>
  ): MessageAdapter = this.apply {
    val typeName = type.name
    clazzByTypeName[typeName] = clazz
    typeNameByClazz[clazz] = typeName
  }

  override fun deserialize(
    src: JsonElement,
    typeOfSrc: Type,
    ctx: JsonDeserializationContext
  ): IMessage? {
    if (!src.isJsonObject) {
      return null
    }

    val obj = src.asJsonObject
    val type = obj[TYPE_KEY].asString
    val payload = obj[PAYLOAD_KEY].asJsonObject

    val clazz = requireNotNull(clazzByTypeName[type]) { "$type has no associated IMessage" }
    return ctx.deserialize(payload, clazz.java)
  }

  override fun serialize(
    src: IMessage?,
    typeOfSrc: Type,
    ctx: JsonSerializationContext
  ): JsonElement {
    if (src == null) {
      return JsonNull.INSTANCE
    }

    val clazz = src::class
    val typeName =
      requireNotNull(typeNameByClazz[clazz]) { "$clazz has no associated IMessageType" }
    return JsonObject().apply {
      addProperty(TYPE_KEY, typeName)
      add(PAYLOAD_KEY, ctx.serialize(src))
    }
  }
}
