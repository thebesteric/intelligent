package io.github.thebesteric.project.intelligent.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.thebesteric.project.intelligent.core.annotation.BigDecimalFormat;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 可设置精度的 BigDecimal 序列化器
 * ex:  @BigDecimalFormat
 *
 * @author wangweijun
 * @version v1.0
 * @since 2025-02-05 17:55:38
 */
@JsonComponent
public class BigDecimalFormatSerializer extends StdSerializer<BigDecimal> implements ContextualSerializer {
    @Serial
    private static final long serialVersionUID = 4013423705082797551L;

    private final int scale;
    private final RoundingMode roundingMode;

    public BigDecimalFormatSerializer() {
        super(BigDecimal.class);
        this.scale = 2;
        this.roundingMode = RoundingMode.HALF_UP;
    }

    public BigDecimalFormatSerializer(BigDecimalFormat annotation) {
        super(BigDecimal.class);
        this.scale = annotation.scale();
        this.roundingMode = annotation.roundingMode();
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            gen.writeString(value.setScale(scale, roundingMode).toString());
        } else {
            gen.writeNull();
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        AnnotatedMember member = property.getMember();
        BigDecimalFormat annotation = member.getAnnotation(BigDecimalFormat.class);
        if (annotation != null) {
            return new BigDecimalFormatSerializer(annotation);
        }
        return this;
    }
}
