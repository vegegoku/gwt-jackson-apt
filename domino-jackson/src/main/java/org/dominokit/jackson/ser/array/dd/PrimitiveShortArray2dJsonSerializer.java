/*
 * Copyright 2014 Nicolas Morel
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

package org.dominokit.jackson.ser.array.dd;

import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.stream.JsonWriter;

/** Default {@link org.dominokit.jackson.JsonSerializer} implementation for 2D array of short. */
public class PrimitiveShortArray2dJsonSerializer extends JsonSerializer<short[][]> {

  private static final PrimitiveShortArray2dJsonSerializer INSTANCE =
      new PrimitiveShortArray2dJsonSerializer();

  /**
   * getInstance
   *
   * @return an instance of {@link
   *     org.dominokit.jackson.ser.array.dd.PrimitiveShortArray2dJsonSerializer}
   */
  public static PrimitiveShortArray2dJsonSerializer getInstance() {
    return INSTANCE;
  }

  private PrimitiveShortArray2dJsonSerializer() {}

  /** {@inheritDoc} */
  @Override
  protected boolean isEmpty(short[][] value) {
    return null == value || value.length == 0;
  }

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer,
      short[][] values,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    if (!ctx.isWriteEmptyJsonArrays() && values.length == 0) {
      writer.cancelName();
      return;
    }

    writer.beginArray();
    for (short[] array : values) {
      writer.beginArray();
      for (short value : array) {
        writer.value(value);
      }
      writer.endArray();
    }
    writer.endArray();
  }
}
