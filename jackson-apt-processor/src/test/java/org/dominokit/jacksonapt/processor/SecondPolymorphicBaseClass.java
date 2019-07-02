package org.dominokit.jacksonapt.processor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, visible = true)
@JsonSubTypes({
		 @Type(value = SecondPolymorphicChildClass.class, name = "childclass")})
public class SecondPolymorphicBaseClass {
	public String str;
}



