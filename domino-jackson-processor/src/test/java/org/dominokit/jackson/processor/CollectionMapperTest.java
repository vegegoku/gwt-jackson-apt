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
package org.dominokit.jackson.processor;

import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dominokit.jackson.ObjectMapper;
import org.dominokit.jackson.ObjectReader;
import org.dominokit.jackson.ObjectWriter;
import org.dominokit.jackson.annotation.JSONMapper;
import org.dominokit.jackson.annotation.JSONReader;
import org.dominokit.jackson.annotation.JSONWriter;
import org.dominokit.jackson.registration.JsonRegistry;
import org.dominokit.jackson.registration.TypeToken;
import org.junit.Test;

public class CollectionMapperTest {

  @JSONMapper
  interface ListOfMapMapper extends ObjectMapper<List<Map<String, SimpleBeanObject>>> {}

  static ListOfMapMapper LISTOFMAPMAPPER = new CollectionMapperTest_ListOfMapMapperImpl();

  @JSONMapper
  interface SetMapper extends ObjectMapper<Set<SimpleBeanObject>> {}

  static SetMapper SETMAPPER = new CollectionMapperTest_SetMapperImpl();

  @JSONReader
  interface SetReader extends ObjectReader<Set<SimpleBeanObject>> {}

  @JSONWriter
  interface SetWriter extends ObjectWriter<Set<SimpleBeanObject>> {}

  @Test
  public void listOfMapMapperDeserializerTest() {
    List<Map<String, SimpleBeanObject>> simpleBeanObjectListOfMaps =
        LISTOFMAPMAPPER.read(
            "[ {\"Dani\":{\"state\":1}; \"Lea\":{\"state\":2}}, {\"Teodor\":{\"state\":3}}]");
    assertThat(simpleBeanObjectListOfMaps.size()).isEqualTo(2);
    assertThat(simpleBeanObjectListOfMaps.get(0).get("Dani").state).isEqualTo(1);
    assertThat(simpleBeanObjectListOfMaps.get(0).get("Lea").state).isEqualTo(2);
    assertThat(simpleBeanObjectListOfMaps.get(1).get("Teodor").state).isEqualTo(3);
    assertThat(simpleBeanObjectListOfMaps.get(0).get("NotExists")).isNull();
  }

  @Test
  public void listOfMapMapperSerializerTest() {
    Map<String, SimpleBeanObject> stringToSimpleBeanObjectMap = new HashMap<>();
    stringToSimpleBeanObjectMap.put("Dani", new SimpleBeanObject(10));
    assertThat(LISTOFMAPMAPPER.write(Arrays.asList(stringToSimpleBeanObjectMap)))
        .isEqualTo("[{\"Dani\":{\"state\":10}}]");

    stringToSimpleBeanObjectMap.put("Lea", new SimpleBeanObject(20));
    assertThat(LISTOFMAPMAPPER.write(Arrays.asList(stringToSimpleBeanObjectMap)))
        .isEqualTo("[{\"Dani\":{\"state\":10},\"Lea\":{\"state\":20}}]");
  }

  @Test
  public void setMapperDeserializerTest() {
    Set<SimpleBeanObject> simpleBeanObjectSet = SETMAPPER.read("[{\"state\":1}]");
    assertThat(simpleBeanObjectSet.size()).isEqualTo(1);
    assertThat(simpleBeanObjectSet.iterator().next().state).isEqualTo(1);

    simpleBeanObjectSet = SETMAPPER.read("[{\"state\":1}, {\"state\":2}]");
    assertThat(simpleBeanObjectSet.size()).isEqualTo(2);

    simpleBeanObjectSet = SETMAPPER.read("[{\"state\":1}, {\"state\":1}, {\"state\":1}]");
    assertThat(simpleBeanObjectSet.size()).isEqualTo(1);
  }

  @Test
  public void setMapperSerializerTest() {
    Set<SimpleBeanObject> simpleBeanObjectSet = new HashSet<>();
    simpleBeanObjectSet.add(new SimpleBeanObject(1));
    simpleBeanObjectSet.add(new SimpleBeanObject(2));
    assertThat(SETMAPPER.write(simpleBeanObjectSet)).isEqualTo("[{\"state\":1},{\"state\":2}]");

    simpleBeanObjectSet.add(new SimpleBeanObject(2));
    simpleBeanObjectSet.add(new SimpleBeanObject(2));
    assertThat(SETMAPPER.write(simpleBeanObjectSet)).isEqualTo("[{\"state\":1},{\"state\":2}]");
  }

  @Test
  public void jsonRegistryTest() {
    JsonRegistry jsonRegistry = TestJsonRegistry.getInstance();

    ObjectMapper<List<Map<String, SimpleBeanObject>>> mapMapper =
        jsonRegistry.getMapper(
            new TypeToken<List<Map<String, SimpleBeanObject>>>(
                List.class,
                new TypeToken<Map<String, SimpleBeanObject>>(
                    Map.class,
                    TypeToken.of(String.class),
                    TypeToken.of(SimpleBeanObject.class)) {}) {});
    assertThat(mapMapper).isNotNull();
    List<Map<String, SimpleBeanObject>> simpleBeanObjectListOfMaps =
        mapMapper.read("[{\"testObj\":{\"state\":10}}]");
    assertThat(simpleBeanObjectListOfMaps.size()).isEqualTo(1);
    assertThat(simpleBeanObjectListOfMaps.get(0).get("testObj")).isNotNull();
    assertThat(simpleBeanObjectListOfMaps.get(0).get("testObj").state).isEqualTo(10);

    ObjectReader<Set<SimpleBeanObject>> setReader =
        jsonRegistry.getReader(
            new TypeToken<Set<SimpleBeanObject>>(
                Set.class, TypeToken.of(SimpleBeanObject.class)) {});
    assertThat(setReader).isNotNull();
    Set<SimpleBeanObject> simpleBeanObjectSet = setReader.read("[{\"state\":20}]");
    assertThat(simpleBeanObjectSet.size()).isEqualTo(1);
    assertThat(simpleBeanObjectSet.iterator().next().state).isEqualTo(20);

    ObjectWriter<Set<SimpleBeanObject>> setWriter =
        jsonRegistry.getWriter(
            new TypeToken<Set<SimpleBeanObject>>(
                Set.class, TypeToken.of(SimpleBeanObject.class)) {});
    assertThat(setWriter).isNotNull();
    assertThat(setWriter.write(new HashSet<>(Arrays.asList(new SimpleBeanObject(30)))))
        .isEqualTo("[{\"state\":30}]");
  }
}
