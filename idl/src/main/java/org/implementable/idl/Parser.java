package org.implementable.idl;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.implementable.idl.parser.IDLLexer;
import org.implementable.idl.parser.IDLParser;

import java.io.File;
import java.io.IOException;

public class Parser {

    static class ParsingException extends Exception {

    }

    private static IDLParser parser(File file) throws IOException {
        ANTLRFileStream fileStream = new ANTLRFileStream(file.getAbsolutePath());
        IDLLexer lexer = new IDLLexer(fileStream);
        return new IDLParser(new CommonTokenStream(lexer));
    }

    private static boolean hasErrors(File file) throws IOException {
        IDLParser parser = parser(file);

        parser.specification(); // test for errors
        return parser.getNumberOfSyntaxErrors() > 0;
    }

    public static Specification parse(File file) throws IOException, ParsingException {
        if (hasErrors(file)) {
            throw new ParsingException();
        }
        IDLParser parser = parser(file);
        Visitor visitor = new Visitor();
        Node result = visitor.visit(parser.specification());
        assert (result instanceof Specification);
        return (Specification) result;
    }
}
