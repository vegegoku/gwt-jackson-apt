package com.progressoft.brix.domino.jacksonapt.deser.map.key;

import java.util.Date;

public interface DateKeyParser<D extends Date> {
    D parse(String keyValue, DateDeserializer<D> deserializer);
}