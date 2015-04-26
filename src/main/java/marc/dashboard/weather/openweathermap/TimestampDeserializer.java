package marc.dashboard.weather.openweathermap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

public class TimestampDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return new Date(Long.parseLong(jp.getText().trim()) * 1000);
    }
}
