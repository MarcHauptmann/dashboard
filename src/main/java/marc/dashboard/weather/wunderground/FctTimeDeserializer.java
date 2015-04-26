package marc.dashboard.weather.wunderground;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.*;
import java.util.Date;

public class FctTimeDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException,
            JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Long epoch = node.get("epoch").asLong();

        ZoneOffset offset = OffsetTime.now().getOffset();
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(epoch.longValue(), 0, offset);

        return java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
