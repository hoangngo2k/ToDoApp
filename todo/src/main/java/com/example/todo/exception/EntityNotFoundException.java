package com.example.todo.exception;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(Class clazz, String... params) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, params)));
    }

    private static String generateMessage(String entity, Map<String, String> params) {
        return StringUtils.capitalize(entity) + "was not found for parameters " + params;
    }

    private static <K,V> Map<K,V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
        if(entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(operand -> operand * 2)
                .collect(HashMap::new, (m, i) -> m.put(keyType.cast(entries[i]),
                        valueType.cast(entries[i + 1])), Map::putAll);
    }
}
