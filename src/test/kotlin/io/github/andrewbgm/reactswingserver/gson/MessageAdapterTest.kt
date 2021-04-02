package io.github.andrewbgm.reactswingserver.gson

import com.google.gson.*
import com.google.gson.annotations.*
import io.github.andrewbgm.reactswingserver.messages.*
import kotlin.test.*

private data class MessageAdapterTestMessage(
  @Expose val key: String,
) : Message

private const val MESSAGE_ADAPTER_TEST_VALUE = "value"
private const val MESSAGE_ADAPTER_TEST_JSON =
  """{"type":"MESSAGE_ADAPTER_TEST","payload":{"key":"$MESSAGE_ADAPTER_TEST_VALUE"}}"""

private fun configureGson(): Gson = GsonBuilder()
  .excludeFieldsWithoutExposeAnnotation()
  .registerTypeAdapter(Message::class.java, MessageAdapter(
    MessageAdapterTestMessage::class,
  ))
  .create()

class MessageAdapterTest {
  @Test
  fun fromJson() {
    val gson = configureGson()
    val message = gson.fromJson(MESSAGE_ADAPTER_TEST_JSON, Message::class.java)

    expect(message::class) { MessageAdapterTestMessage::class }
    message as MessageAdapterTestMessage
    expect(message.key) { MESSAGE_ADAPTER_TEST_VALUE }
  }

  @Test
  fun toJson() {
    val gson = configureGson()
    val json =
      gson.toJson(MessageAdapterTestMessage(MESSAGE_ADAPTER_TEST_VALUE),
        Message::class.java)

    expect(json) { MESSAGE_ADAPTER_TEST_JSON }
  }
}
