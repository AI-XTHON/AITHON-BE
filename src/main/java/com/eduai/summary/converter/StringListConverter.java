package com.eduai.summary.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Converter // 이 클래스를 컨버터로 사용하겠다고 선언
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ","; // 콤마로 URL 구분

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        // List<String> -> String
        // ["url1", "url2"] -> "url1,url2"
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return String.join(SPLIT_CHAR, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        // String -> List<String>
        // "url1,url2" -> ["url1", "url2"]
        if (dbData == null || dbData.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(dbData.split(SPLIT_CHAR));
    }
}
