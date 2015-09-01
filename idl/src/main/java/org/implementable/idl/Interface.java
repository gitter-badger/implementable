package org.implementable.idl;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
public class Interface implements Node, DigestProvider {

    @Getter
    private TypeSpec type;

    @Getter @Setter
    private List<TypeSpec> inheritance = new LinkedList<>();

    @Getter
    private List<Function> functions = new LinkedList<>();

    @Getter
    private List<Struct> structs = new LinkedList<>();

    public Interface(TypeSpec type, NodeList nodes) {
        this.type = type;
        nodes.stream().forEach(node -> {
            if (node instanceof Function) {
                functions.add((Function) node);
            }
            if (node instanceof Struct) {
                structs.add((Struct) node);
            }
        });

    }

    @Override
    public void digest(MessageDigest digest) {
        type.digest(digest);
        inheritance.stream().forEachOrdered(typeSpec -> typeSpec.digest(digest));
        functions.stream().forEachOrdered(function -> function.digest(digest));
        structs.stream().forEachOrdered(struct -> struct.digest(digest));
    }

}
