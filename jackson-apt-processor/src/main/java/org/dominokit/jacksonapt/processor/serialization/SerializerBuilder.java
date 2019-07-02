/*
 * Copyright 2017 Ahmad Bawaneh
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
package org.dominokit.jacksonapt.processor.serialization;

import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.processor.AbstractJsonMapperGenerator;
import org.dominokit.jacksonapt.processor.AccessorsFilter;
import org.dominokit.jacksonapt.processor.ObjectMapperProcessor;
import org.dominokit.jacksonapt.processor.Type;
import org.dominokit.jacksonapt.ser.bean.BeanPropertySerializer;
import org.dominokit.jacksonapt.stream.impl.DefaultJsonWriter;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

class SerializerBuilder extends AccessorsFilter {

    private final TypeMirror beanType;
    private final Element field;
    private final TypeMirror fieldType;

    SerializerBuilder(Types typeUtils, TypeMirror beanType, Element field) {
        super(typeUtils);
        this.beanType = beanType;
        this.field = field;
        this.fieldType = field.asType();
    }

    SerializerBuilder(Types typeUtils, TypeMirror beanType, Element field, TypeMirror fieldType) {
        super(typeUtils);
        this.beanType = beanType;
        this.field = field;
        this.fieldType = fieldType;
    }
    
    TypeSpec buildSerializer() {
        final String paramBean = "bean";

        TypeSpec.Builder builder = TypeSpec.anonymousClassBuilder("\"$L\"", DefaultJsonWriter.encodeString(getPropertyName(field)))
                .superclass(ParameterizedTypeName
                        .get(ClassName.get(BeanPropertySerializer.class), TypeName.get(beanType), Type.wrapperType(fieldType)));

        builder.addMethod(buildSerializerMethod());

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("getValue")
                .returns(Type.wrapperType(Type.removeOuterWildCards(fieldType)))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(ClassName.get(beanType), paramBean);
        AbstractJsonMapperGenerator.AccessorInfo accessorInfo = getterInfo();

        methodBuilder
                .addParameter(JsonSerializationContext.class, "ctx")
                .addStatement("return $L", paramBean + "." + accessorInfo.accessor + (accessorInfo.present ? "()" : ""));

        builder.addMethod(methodBuilder.build());

        return builder.build();
    }

    private MethodSpec buildSerializerMethod() {
        return MethodSpec.methodBuilder("newSerializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), ObjectMapperProcessor.DEFAULT_WILDCARD))
                .addStatement("return $L", new FieldSerializerChainBuilder(beanType).getInstance(fieldType))
                .build();
    }

    AbstractJsonMapperGenerator.AccessorInfo getterInfo() {
        final String upperCaseFirstLetter = upperCaseFirstLetter(field.getSimpleName().toString());
        String prefix = field.asType().getKind() == TypeKind.BOOLEAN ? "is" : "get";
        if (getAccessors(beanType).contains(prefix + upperCaseFirstLetter)) {
            return new AbstractJsonMapperGenerator.AccessorInfo(true, prefix + upperCaseFirstLetter);
        }
        return new AbstractJsonMapperGenerator.AccessorInfo(false, field.getSimpleName().toString());
    }

    private String upperCaseFirstLetter(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }


}
