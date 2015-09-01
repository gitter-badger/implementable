package org.implementable.idl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.security.MessageDigest;

@NoArgsConstructor
@RequiredArgsConstructor
public class Namespace implements Node {

    @Getter @NonNull
    private String identifier;

    @Getter @NonNull
    private Specification specification;

    @Override
    public void digest(MessageDigest digest) {
        specification.digest(digest);
    }

}
