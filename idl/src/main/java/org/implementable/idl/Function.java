package org.implementable.idl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    }

    @Getter
    private TypeSpec type;

    @Getter
    private String identifier;

    @Getter
    private List<Argument> arguments;
}
