/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dominokit.jackson.server.ser;

import static org.assertj.core.api.Assertions.assertThat;

import org.dominokit.jackson.DefaultJsonSerializationContext;
import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.server.ServerJacksonTestCase;
import org.dominokit.jackson.stream.JsonWriter;
import org.junit.Test;

/** @author Nicolas Morel */
public abstract class AbstractJsonSerializerTest<T> extends ServerJacksonTestCase {

  protected abstract JsonSerializer<T> createSerializer();

  @Test
  public void testSerializeNullValue() {
    assertSerialization("null", null);
  }

  protected String serialize(T value) {
    JsonSerializationContext ctx = DefaultJsonSerializationContext.builder().build();
    JsonWriter writer = ctx.newJsonWriter();
    createSerializer().serialize(writer, value, ctx);
    return writer.getOutput();
  }

  protected void assertSerialization(String expected, T value) {
    assertThat(expected).isEqualTo(serialize(value));
  }

  public abstract void testSerializeValue();
}
