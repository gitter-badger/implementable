package org.implementable.idl;

import lombok.*;

import java.security.MessageDigest;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
public class Struct implements Node, DigestProvider, Annotated {

    @Getter @Setter private List<Annotation> annotations;

    @NoArgsConstructor
    @RequiredArgsConstructor
    static class Member implements Node, Annotated {
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

    @Getter @NonNull
    private TypeSpec type;

    @Getter @Setter
    private List<Member> members;

    @Override
    public void digest(MessageDigest digest) {
        annotations.stream().forEachOrdered(annotation -> annotation.digest(digest));
        type.digest(digest);
        members.stream().forEachOrdered(member -> member.digest(digest));
    }

}
