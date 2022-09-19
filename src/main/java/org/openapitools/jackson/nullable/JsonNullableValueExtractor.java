package org.openapitools.jackson.nullable;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.UnwrapByDefault;
import jakarta.validation.valueextraction.ValueExtractor;

/**
 * Extractor for JsonNullable
 */
@UnwrapByDefault
public class JsonNullableValueExtractor implements ValueExtractor<JsonNullable<@ExtractedValue ?>> {
    @Override
    public void extractValues(JsonNullable<?> originalValue, ValueReceiver receiver) {
        if (originalValue.isPresent()) {
            receiver.value(null, originalValue.get());
        }
    }
}
