package org.implementable.idl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@RequiredArgsConstructor
public class TypeSpec implements Node {

    @AllArgsConstructor
    static class Template implements Node {

        @AllArgsConstructor
        static class Component implements Node {
            @Getter
            private TypeSpec type;

            @Getter
            private List<TypeSpec> inheritance;
        }

        @Getter
        private List<Component> components;
    }

    @Getter @NonNull
    private String identifier;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter @Setter
    private Template template;
}
