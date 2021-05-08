package io.github.andrewbgm.reactswingruntime.impl

import com.google.gson.*
import io.github.andrewbgm.reactswingruntime.api.*
import java.lang.reflect.*
import kotlin.reflect.*

private const val TYPE_KEY = "type"
private const val PAYLOAD_KEY = "payload"

class MessageSerializer : JsonDeserializer<IMessage>, JsonSerializer<IMessage> {
  private val clazzByTypeName = mutableMapOf<String, KClass<out IMessage>>()
  private val typeNameByClazz = mutableMapOf<KClass<out IMessage>, String>()

  fun <T : IMessage> registerMessageType(
    type: IMessageType,
    clazz: KClass<T>
  ): MessageSerializer = this.apply {
    val typeName = type.name
    clazzByTypeName[typeName] = clazz
    typeNameByClazz[clazz] = typeName
  }

  override fun deserialize(
    json: JsonElement,
    typeOfT: Type,
    ctx: JsonDeserializationContext
  ): IMessage? {
    if (!json.isJsonObject) {
      return null
    }

    val obj = json.asJsonObject
    val typeName = obj[TYPE_KEY].asString
    val payload = obj[PAYLOAD_KEY].asJsonObject

    val clazz = requireNotNull(clazzByTypeName[typeName]) { "$typeName has no associated IMessage" }
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
    val payload = ctx.serialize(src)

    return JsonObject().apply {
      addProperty(TYPE_KEY, typeName)
      add(PAYLOAD_KEY, payload)
    }
  }
}
