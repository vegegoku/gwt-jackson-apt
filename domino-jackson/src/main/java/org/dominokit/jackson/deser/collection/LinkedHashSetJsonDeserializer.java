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

package org.dominokit.jackson.deser.collection;

import java.util.LinkedHashSet;
import org.dominokit.jackson.JsonDeserializer;

/**
 * Default {@link org.dominokit.jackson.JsonDeserializer} implementation for {@link
 * java.util.LinkedHashSet}.
 *
 * @param <T> Type of the elements inside the {@link java.util.LinkedHashSet}
 */
public class LinkedHashSetJsonDeserializer<T> extends BaseSetJsonDeserializer<LinkedHashSet<T>, T> {

  /**
   * newInstance
   *
   * @param deserializer {@link org.dominokit.jackson.JsonDeserializer} used to deserialize the
   *     objects inside the {@link java.util.LinkedHashSet}.
   * @param <T> Type of the elements inside the {@link java.util.LinkedHashSet}
   * @return a new instance of {@link
   *     org.dominokit.jackson.deser.collection.LinkedHashSetJsonDeserializer}
   */
  public static <T> LinkedHashSetJsonDeserializer<T> newInstance(JsonDeserializer<T> deserializer) {
    return new LinkedHashSetJsonDeserializer<T>(deserializer);
  }

  /**
   * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link
   *     LinkedHashSet}.
   */
  private LinkedHashSetJsonDeserializer(JsonDeserializer<T> deserializer) {
    super(deserializer);
  }

  /** {@inheritDoc} */
  @Override
  protected LinkedHashSet<T> newCollection() {
    return new LinkedHashSet<T>();
  }
}
