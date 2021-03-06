package org.implementable.idl;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
public class Specification implements Node {

    @Getter
    private List<Interface> interfaces = new LinkedList<>();

    @Getter
    private List<Namespace> namespaces = new LinkedList<>();

    public Specification(NodeList nodes) {
        nodes.stream().forEach(node -> {
            if (node instanceof Interface) {
                interfaces.add((Interface) node);
            }
            if (node instanceof Namespace) {
                namespaces.add((Namespace) node);
            }
        });
    }

    @Override
    public void digest(MessageDigest digest) {
        interfaces.stream().forEachOrdered(anInterface -> anInterface.digest(digest));
    }
}
