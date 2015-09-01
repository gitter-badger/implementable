package org.implementable.idl;

import lombok.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
public class Function implements Node, Annotated {

    @Getter @Setter private List<Annotation> annotations;

    @RequiredArgsConstructor
    @NoArgsConstructor
    static class Argument implements Node, Annotated {
        @Getter @Setter private List<Annotation> annotations;
        @Getter @NonNull
        private TypeSpec type;
        @Getter @NonNull
        private String identifier;

        @Override
        @SneakyThrows
        public void digest(MessageDigest digest) {
            annotations.stream().forEachOrdered(annotation -> annotation.digest(digest));
            type.digest(digest);
            digest.update(identifier.getBytes("UTF-8"));
        }
    }

    @Getter @Setter private TypeSpec.Template template;

    @Getter @Setter
    private TypeSpec type;

    @Getter @Setter
    private String identifier;

    @Getter @Setter
    private List<Argument> arguments;

    @Override
    @SneakyThrows
    public void digest(MessageDigest digest) {
        annotations.stream().forEachOrdered(annotation -> annotation.digest(digest));
        template.digest(digest);
        type.digest(digest);
        digest.update(identifier.getBytes("UTF-8"));
        arguments.stream().forEachOrdered(argument -> argument.digest(digest));
    }

}
