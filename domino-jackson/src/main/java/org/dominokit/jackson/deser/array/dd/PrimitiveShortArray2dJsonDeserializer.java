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

package org.dominokit.jackson.deser.array.dd;

import java.util.List;
import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.deser.BaseNumberJsonDeserializer;
import org.dominokit.jackson.stream.JsonReader;

/** Default {@link org.dominokit.jackson.JsonDeserializer} implementation for 2D array of short. */
public class PrimitiveShortArray2dJsonDeserializer
    extends AbstractArray2dJsonDeserializer<short[][]> {

  private static final PrimitiveShortArray2dJsonDeserializer INSTANCE =
      new PrimitiveShortArray2dJsonDeserializer();

  /**
   * getInstance
   *
   * @return an instance of {@link
   *     org.dominokit.jackson.deser.array.dd.PrimitiveShortArray2dJsonDeserializer}
   */
  public static PrimitiveShortArray2dJsonDeserializer getInstance() {
    return INSTANCE;
  }

  private PrimitiveShortArray2dJsonDeserializer() {}

  /** {@inheritDoc} */
  @Override
  public short[][] doDeserialize(
      JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
    List<List<Short>> list =
        deserializeIntoList(
            reader, ctx, BaseNumberJsonDeserializer.ShortJsonDeserializer.getInstance(), params);

    if (list.isEmpty()) {
      return new short[0][0];
    }

    List<Short> firstList = list.get(0);
    if (firstList.isEmpty()) {
      return new short[list.size()][0];
    }

    short[][] array = new short[list.size()][firstList.size()];

    int i = 0;
    int j;
    for (List<Short> innerList : list) {
      j = 0;
      for (Short value : innerList) {
        if (null != value) {
          array[i][j] = value;
        }
        j++;
      }
      i++;
    }
    return array;
  }
}
