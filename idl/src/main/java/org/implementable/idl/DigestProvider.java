package org.implementable.idl;

import lombok.SneakyThrows;

import java.security.MessageDigest;

public interface DigestProvider extends Node {
    @SneakyThrows
    default byte[] getDigest() {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest(digest);
        return digest.digest();
    }

}
