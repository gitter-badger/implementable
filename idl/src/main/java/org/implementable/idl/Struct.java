package org.implementable.idl;

import lombok.*;

import java.security.MessageDigest;
import java.util.List;

@RequiredArgsConstructor
public class Struct implements Node, DigestProvider {

    @AllArgsConstructor
    static class Member implements Node {
        @Getter
        private TypeSpec type;
        @Getter
        private String identifier;

        @Override
        @SneakyThrows
        public void digest(MessageDigest digest) {
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
        type.digest(digest);
        members.stream().forEachOrdered(member -> member.digest(digest));
    }

}
