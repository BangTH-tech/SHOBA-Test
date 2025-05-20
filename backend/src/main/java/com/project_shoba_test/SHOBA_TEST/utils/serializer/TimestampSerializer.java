package com.project_shoba_test.SHOBA_TEST.utils.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TimestampSerializer extends JsonSerializer<Long> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");

    @Override
    public void serialize(Long timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (timestamp != null) {
            String formattedDate = sdf.format(new Date(timestamp));
            jsonGenerator.writeString(formattedDate);
        } else {
            jsonGenerator.writeNull();
        }
    }



}