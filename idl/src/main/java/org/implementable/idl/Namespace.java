package org.implementable.idl;

import lombok.*;

import java.security.MessageDigest;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
public class Namespace implements Node, Annotated {

    @Getter @Setter private List<Annotation> annotations;

    @Getter @NonNull
    private String identifier;

    @Getter @NonNull
    private Specification specification;

    @Override
    public void digest(MessageDigest digest) {
        annotations.stream().forEachOrdered(annotation -> annotation.digest(digest));
        specification.digest(digest);
    }

}
