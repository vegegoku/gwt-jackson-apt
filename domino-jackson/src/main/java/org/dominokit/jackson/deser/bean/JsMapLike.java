/*
 * Copyright © 2019 Dominokit
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

import elemental2.core.JsObject;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

/**
 * A wrapper for a map implementation that works in the browser. for the JVM implementation please
 * check {@link DefaultMapLike}
 */
public class JsMapLike<T> implements MapLike<T> {

  private JsPropertyMap<T> map = (JsPropertyMap<T>) Js.asAny(JsObject.create(null)).asPropertyMap();

  /** {@inheritDoc} */
  @Override
  public T get(String key) {
    return map.get(key);
  }

  /** {@inheritDoc} */
  @Override
  public void put(String key, T value) {
    map.set(key, value);
  }
}
