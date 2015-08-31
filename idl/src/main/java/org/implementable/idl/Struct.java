package org.implementable.idl;

import lombok.*;

import java.util.List;

@RequiredArgsConstructor
public class Struct implements Node {
    @AllArgsConstructor
    static class Member implements Node {
        @Getter
        private TypeSpec type;
        @Getter
        private String identifier;
    }

    @Getter @NonNull
    private TypeSpec type;

    @Getter @Setter
    private List<Member> members;
}
