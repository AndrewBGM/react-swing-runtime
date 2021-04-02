package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import io.github.andrewbgm.reactswingserver.messages.*
import java.lang.reflect.Type
import kotlin.reflect.KClass

class MessageAdapter(
  vararg messageClazzes: KClass<out Message>,
) : JsonDeserializer<Message>, JsonSerializer<Message> {
  private val messageClazzByType: MutableMap<String, KClass<out Message>> =
    mutableMapOf()
  private val messageTypeByClazz: MutableMap<KClass<out Message>, String> =
    mutableMapOf()

  init {
    messageClazzes.forEach(::registerMessageClass)
  }

  override fun deserialize(
    src: JsonElement,
    typeOfSrc: Type,
    ctx: JsonDeserializationContext,
  ): Message? {
    if (!src.isJsonObject) {
      return null
    }

    val obj = src.asJsonObject
    val type = obj.get("type").asString
    val payload = obj.get("payload").asJsonObject

    val clazz = messageClazzByType[type] ?: error("Unsupported message $type.")
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
      messageTypeByClazz[src::class] ?: error("Unsupported message $src.")
    val obj = JsonObject()
    obj.addProperty("type", type)
    obj.add("payload", ctx.serialize(src))
    return obj
  }

  private fun registerMessageClass(
    clazz: KClass<out Message>,
  ) {
    val type = getTypenameFromClass(clazz)
    messageClazzByType[type] = clazz
    messageTypeByClazz[clazz] = type
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
