package io.github.andrewbgm.reactswingruntime.impl.component

import com.google.gson.*
import io.github.andrewbgm.reactswingruntime.api.*
import java.lang.reflect.*

/**
 * Message (de)serializer for Gson
 */
class RemoteComponentTypeSerializer : JsonDeserializer<IRemoteComponentType>,
  JsonSerializer<IRemoteComponentType> {
  private val typeByTypeName = mutableMapOf<String, IRemoteComponentType>()

  /**
   * Registers a component type so that it can be (de)serialized.
   */
  fun registerComponentType(
    type: IRemoteComponentType,
  ): RemoteComponentTypeSerializer = this.apply {
    val typeName = type.toString()
    require(!typeByTypeName.contains(typeName)) { "$typeName already has an associated IRemoteComponentType" }

    typeByTypeName[typeName] = type
  }

  override fun deserialize(
    json: JsonElement,
    typeOfT: Type,
    ctx: JsonDeserializationContext,
  ): IRemoteComponentType? {
    if (!json.isJsonPrimitive) {
      return null
    }

    val typeName = json.asString
    return requireNotNull(typeByTypeName[typeName]) { "$typeName has no associated IRemoteComponentType" }
  }

  override fun serialize(
    src: IRemoteComponentType?,
    typeOfSrc: Type,
    ctx: JsonSerializationContext,
  ): JsonElement {
    if (src == null) {
      return JsonNull.INSTANCE
    }

    return JsonPrimitive(src.toString())
  }
}
