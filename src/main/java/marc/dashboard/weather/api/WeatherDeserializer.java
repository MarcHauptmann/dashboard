package marc.dashboard.weather.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

class WeatherDeserializer extends JsonDeserializer<WeatherCondition> {
    @Override
    public WeatherCondition deserialize(JsonParser jsonParser,
                                        DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        int weatherId = node.get("id").asInt();

        return WeatherCondition.fromInt(weatherId);
    }
}
