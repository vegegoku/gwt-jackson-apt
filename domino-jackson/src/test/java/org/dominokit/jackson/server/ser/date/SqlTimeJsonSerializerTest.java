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

package org.dominokit.jackson.server.ser.date;

import java.sql.Time;
import org.dominokit.jackson.ser.BaseDateJsonSerializer;
import org.dominokit.jackson.server.ser.AbstractJsonSerializerTest;
import org.junit.Test;

/** @author Nicolas Morel */
public class SqlTimeJsonSerializerTest extends AbstractJsonSerializerTest<Time> {

  @Override
  protected BaseDateJsonSerializer.SqlTimeJsonSerializer createSerializer() {
    return BaseDateJsonSerializer.SqlTimeJsonSerializer.getInstance();
  }

  @Test
  public void testSerializeValue() {
    Time time = new Time(getUTCTime(2012, 8, 18, 12, 45, 56, 543));
    assertSerialization("\"" + time.toString() + "\"", time);
  }
}