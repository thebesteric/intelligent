package io.github.thebesteric.project.intelligent.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimalSerializer
 * ex: @JsonSerialize(using = BigDecimalSerializer.class)
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-02-05 17:44:07
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(value.setScale(2, RoundingMode.HALF_UP).toString());
        } else {
            gen.writeNull();
        }
    }
}
