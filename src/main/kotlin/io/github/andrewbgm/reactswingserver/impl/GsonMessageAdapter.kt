package io.github.andrewbgm.reactswingserver.impl

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.api.*
import java.lang.reflect.*
import kotlin.reflect.*

class GsonMessageAdapter : JsonDeserializer<Message>, JsonSerializer<Message> {
  private val clazzByType: MutableMap<String, KClass<out Message>> =
    mutableMapOf()
  private val typeByClazz: MutableMap<KClass<out Message>, String> =
    mutableMapOf()

  inline fun <reified T : Message> registerMessageType(
    type: String,
  ): GsonMessageAdapter = registerMessageType(type, T::class)

  fun registerMessageType(
    type: String,
    clazz: KClass<out Message>,
  ): GsonMessageAdapter = this.apply {
    clazzByType[type] = clazz
    typeByClazz[clazz] = type
  }

  override fun deserialize(
    src: JsonElement,
    typeOfSrc: Type,
    ctx: JsonDeserializationContext
  ): Message? {
    if (!src.isJsonObject) {
      return null
    }

    val obj = src.asJsonObject
    val type = obj.get("type").asString
    val payload = obj.get("payload").asJsonObject
    val clazz =
      clazzByType[type] ?: error("$type is not a registered message type")

    return ctx.deserialize(payload, clazz.java)
  }

  override fun serialize(
    src: Message?,
    typeOfSrc: Type,
    ctx: JsonSerializationContext
  ): JsonElement {
    if (src == null) {
      return JsonNull.INSTANCE
    }

    val type =
      typeByClazz[src::class] ?: error("$src is not a registered message type")

    return JsonObject().apply {
      addProperty("type", type)
      add("payload", ctx.serialize(src))
    }
  }
}
