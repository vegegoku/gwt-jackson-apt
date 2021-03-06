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

package org.dominokit.jackson.deser.bean;

import java.util.Map;
import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.stream.JsonReader;

/**
 * Delegate the deserialization of a subtype to a corresponding {@link
 * org.dominokit.jackson.deser.bean.AbstractBeanJsonDeserializer}
 */
public abstract class SubtypeDeserializer<T, D extends JsonDeserializer<T>>
    extends HasDeserializer<T, D> implements InternalDeserializer<T, D> {

  /**
   * Delegate the deserialization of a subtype to a corresponding {@link
   * AbstractBeanJsonDeserializer}
   */
  public abstract static class BeanSubtypeDeserializer<T>
      extends SubtypeDeserializer<T, AbstractBeanJsonDeserializer<T>> {
    /**
     * @param reader a {@link org.dominokit.jackson.stream.JsonReader} object.
     * @param ctx a {@link org.dominokit.jackson.JsonDeserializationContext} object.
     * @param params a {@link org.dominokit.jackson.JsonDeserializerParameters} object.
     * @param identityInfo a {@link org.dominokit.jackson.deser.bean.IdentityDeserializationInfo}
     *     object.
     * @param typeInfo a {@link org.dominokit.jackson.deser.bean.TypeDeserializationInfo} object.
     * @param typeInformation a {@link java.lang.String} object.
     * @param bufferedProperties a {@link java.util.Map} object.
     * @return the deserialized object
     */
    @Override
    public T deserializeInline(
        JsonReader reader,
        JsonDeserializationContext ctx,
        JsonDeserializerParameters params,
        IdentityDeserializationInfo identityInfo,
        TypeDeserializationInfo typeInfo,
        String typeInformation,
        Map<String, String> bufferedProperties) {
      return getDeserializer()
          .deserializeInline(
              reader, ctx, params, identityInfo, typeInfo, typeInformation, bufferedProperties);
    }

    /**
     * @param reader a {@link org.dominokit.jackson.stream.JsonReader} object.
     * @param ctx a {@link org.dominokit.jackson.JsonDeserializationContext} object.
     * @param params a {@link org.dominokit.jackson.JsonDeserializerParameters} object.
     * @param identityInfo a {@link org.dominokit.jackson.deser.bean.IdentityDeserializationInfo}
     *     object.
     * @param typeInfo a {@link org.dominokit.jackson.deser.bean.TypeDeserializationInfo} object.
     * @param typeInformation a {@link java.lang.String} object.
     * @return the deserialized object
     */
    @Override
    public T deserializeWrapped(
        JsonReader reader,
        JsonDeserializationContext ctx,
        JsonDeserializerParameters params,
        IdentityDeserializationInfo identityInfo,
        TypeDeserializationInfo typeInfo,
        String typeInformation) {
      return getDeserializer()
          .deserializeWrapped(reader, ctx, params, identityInfo, typeInfo, typeInformation);
    }
  }

  /** Delegate the deserialization of a subtype to a corresponding {@link JsonDeserializer} */
  public abstract static class DefaultSubtypeDeserializer<T>
      extends SubtypeDeserializer<T, JsonDeserializer<T>> {

    /**
     * @param reader a {@link org.dominokit.jackson.stream.JsonReader} object.
     * @param ctx a {@link org.dominokit.jackson.JsonDeserializationContext} object.
     * @param params a {@link org.dominokit.jackson.JsonDeserializerParameters} object.
     * @param identityInfo a {@link org.dominokit.jackson.deser.bean.IdentityDeserializationInfo}
     *     object.
     * @param typeInfo a {@link org.dominokit.jackson.deser.bean.TypeDeserializationInfo} object.
     * @param typeInformation a {@link java.lang.String} object.
     * @param bufferedProperties a {@link java.util.Map} object.
     * @return the deserialized object
     */
    @Override
    public T deserializeInline(
        JsonReader reader,
        JsonDeserializationContext ctx,
        JsonDeserializerParameters params,
        IdentityDeserializationInfo identityInfo,
        TypeDeserializationInfo typeInfo,
        String typeInformation,
        Map<String, String> bufferedProperties) {
      throw ctx.traceError(
          "Cannot deserialize into a bean when not using an AbstractBeanJsonDeserializer");
    }

    /**
     * @param reader a {@link org.dominokit.jackson.stream.JsonReader} object.
     * @param ctx a {@link org.dominokit.jackson.JsonDeserializationContext} object.
     * @param params a {@link org.dominokit.jackson.JsonDeserializerParameters} object.
     * @param identityInfo a {@link org.dominokit.jackson.deser.bean.IdentityDeserializationInfo}
     *     object.
     * @param typeInfo a {@link org.dominokit.jackson.deser.bean.TypeDeserializationInfo} object.
     * @param typeInformation a {@link java.lang.String} object.
     * @return the deserialized object
     */
    @Override
    public T deserializeWrapped(
        JsonReader reader,
        JsonDeserializationContext ctx,
        JsonDeserializerParameters params,
        IdentityDeserializationInfo identityInfo,
        TypeDeserializationInfo typeInfo,
        String typeInformation) {
      return getDeserializer().deserialize(reader, ctx, params);
    }
  }
}
