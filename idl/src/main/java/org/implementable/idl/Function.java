package org.implementable.idl;

import lombok.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
public class Function implements Node {

    @AllArgsConstructor
    @NoArgsConstructor
    static class Argument implements Node {
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

    @Getter @Setter
    private TypeSpec type;

    @Getter @Setter
    private String identifier;

    @Getter @Setter
    private List<Argument> arguments;

    @Override
    @SneakyThrows
    public void digest(MessageDigest digest) {
        type.digest(digest);
        digest.update(identifier.getBytes("UTF-8"));
        arguments.stream().forEachOrdered(argument -> argument.digest(digest));
    }

}
