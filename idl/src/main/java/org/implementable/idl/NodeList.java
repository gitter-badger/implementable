package org.implementable.idl;

import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
public class NodeList extends LinkedList<Node> implements Node {

    public NodeList(List<Node> list) {
        this.addAll(list);
    }
}
