package org.implementable.idl;

import java.util.List;

public interface Annotated extends Node {
    List<Annotation> getAnnotations();
    void setAnnotations(List<Annotation> annotation);
}
