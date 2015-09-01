package org.implementable.idl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.security.MessageDigest;

@NoArgsConstructor
@AllArgsConstructor
public class Annotation implements Node {
    @Getter @Setter
    private String identifier;
    @Getter @JsonRawValue
    private String json;

    @Override
    @SneakyThrows
    public void digest(MessageDigest digest) {
        digest.update(identifier.getBytes("UTF-8"));
        digest.update(json.getBytes("UTF-8"));
    }

    @SneakyThrows
    public void setJson(Object json) {
        this.json = new ObjectMapper().writeValueAsString(json);
    }

    @JsonIgnore @SneakyThrows
    public <T> T getContent(Class<T> klass) {
        return new ObjectMapper().readValue(json, klass);
    }
}
