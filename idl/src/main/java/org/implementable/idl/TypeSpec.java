package org.implementable.idl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.security.MessageDigest;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
public class TypeSpec implements Node {

    @NoArgsConstructor
    @AllArgsConstructor
    static class Template implements Node {

        @NoArgsConstructor
        @AllArgsConstructor
        static class Component implements Node {
            @Getter
            private TypeSpec type;

            @Getter
            private List<TypeSpec> inheritance;

            @Override
            public void digest(MessageDigest digest) {
                type.digest(digest);
                inheritance.stream().forEachOrdered(inheritance -> inheritance.digest(digest));
            }
        }

        @Getter
        private List<Component> components;

        @Override
        public void digest(MessageDigest digest) {
            components.stream().forEachOrdered(component -> component.digest(digest));
        }

    }

    @Getter @NonNull
    private String identifier;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter @Setter
    private Template template;

    @Override
    @SneakyThrows
    public void digest(MessageDigest digest) {
        digest.update(identifier.getBytes("UTF-8"));
        if (template != null) {
            template.digest(digest);
        }
    }

}
