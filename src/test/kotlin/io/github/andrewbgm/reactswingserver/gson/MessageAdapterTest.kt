package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import io.github.andrewbgm.reactswingserver.messages.*
import kotlin.test.Test
import kotlin.test.expect

class MessageAdapterTest {
  private data class MessageAdapterTestMessage(
    @Expose val value: Map<String, Any?>,
  ) : IMessage

  private val gson = GsonBuilder()
    .excludeFieldsWithoutExposeAnnotation()
    .registerTypeAdapter(IMessage::class.java, MessageAdapter(
      MessageAdapterTestMessage::class
    ))
    .create()

  private val json = """{"type":"MESSAGE_ADAPTER_TEST","payload":{"value":{"key":"test"}}}"""

  @Test
  fun fromJson() {
    val result = gson.fromJson<MessageAdapterTestMessage>(json, IMessage::class.java)
    expect(MessageAdapterTestMessage::class) { result::class }
    expect("test") { result.value["key"] }
  }

  @Test
  fun toJson() {
    val result =
      gson.toJson(MessageAdapterTestMessage(mapOf("key" to "test")), IMessage::class.java)
    expect(json) { result }
  }
}
