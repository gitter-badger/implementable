package org.implementable.idl;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class Specification implements Node {

    @Getter
    private List<Interface> interfaces = new LinkedList<>();

    public Specification(NodeList nodes) {
        nodes.stream().forEach(node -> {
            if (node instanceof Interface) {
                interfaces.add((Interface) node);
            }
        });
    }
}
