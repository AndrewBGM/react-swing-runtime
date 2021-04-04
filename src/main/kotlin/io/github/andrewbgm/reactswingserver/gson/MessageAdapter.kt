package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.*
import io.github.andrewbgm.reactswingserver.messages.*
import java.lang.reflect.*
import kotlin.reflect.*

class MessageAdapter(
  vararg types: KClass<out IMessage>,
) : JsonDeserializer<IMessage>, JsonSerializer<IMessage> {
  private val clazzByType: MutableMap<String, KClass<out IMessage>> =
    mutableMapOf()

  private val typeByClazz: MutableMap<KClass<out IMessage>, String> =
    mutableMapOf()

  init {
    types.forEach(::registerMessageClass)
  }

  override fun deserialize(
    src: JsonElement,
    typeOfSrc: Type,
    ctx: JsonDeserializationContext,
  ): IMessage? {
    if (!src.isJsonObject) {
      return null
    }

    val obj = src.asJsonObject
    val type = obj.get("type").asString
    val payload = obj.get("payload").asJsonObject

    val clazz = clazzByType[type] ?: error("Unsupported type $type.")
    return ctx.deserialize(payload, clazz.java)
  }

  override fun serialize(
    src: IMessage?,
    typeOfSrc: Type,
    ctx: JsonSerializationContext,
  ): JsonElement {
    if (src == null) {
      return JsonNull.INSTANCE
    }

    val type =
      typeByClazz[src::class] ?: error("Unsupported type $src.")

    val obj = JsonObject()
    obj.addProperty("type", type)
    obj.add("payload", ctx.serialize(src))
    return obj
  }

  private fun registerMessageClass(
    clazz: KClass<out IMessage>,
  ) {
    val type = getTypenameFromClass(clazz)
    clazzByType[type] = clazz
    typeByClazz[clazz] = type
  }

  private fun getTypenameFromClass(
    clazz: KClass<*>,
  ): String = Regex("[A-Z][a-z0-9_]+")
    .findAll(clazz.simpleName!!)
    .map { it.value }
    .toList()
    .dropLast(1)
    .joinToString("_")
    .toUpperCase()
}
