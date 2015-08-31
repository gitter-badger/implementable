package org.implementable.idl;


import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class Interface implements Node {

    @Getter
    private TypeSpec type;

    @Getter @Setter
    private List<TypeSpec> inheritance;

    @Getter
    private List<Function> functions = new LinkedList<>();

    public Interface(TypeSpec type, NodeList nodes) {
        this.type = type;
        nodes.stream().forEach(node -> {
            if (node instanceof Function) {
                functions.add((Function) node);
            }
        });

    }
}
