package org.implementable.idl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;

@Builder
public class Function implements Node {

    @AllArgsConstructor
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

    @Getter
    private TypeSpec type;

    @Getter
    private String identifier;

    @Getter
    private List<Argument> arguments;

    @Override
    @SneakyThrows
    public void digest(MessageDigest digest) {
        type.digest(digest);
        digest.update(identifier.getBytes("UTF-8"));
        arguments.stream().forEachOrdered(argument -> argument.digest(digest));
    }

}
