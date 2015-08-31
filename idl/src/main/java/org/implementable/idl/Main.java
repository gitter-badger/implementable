package org.implementable.idl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.implementable.idl.parser.IDLLexer;
import org.implementable.idl.parser.IDLParser;

import java.io.IOException;

public class Main {

    private static IDLParser parser(String filename) throws IOException {
        ANTLRFileStream fileStream = new ANTLRFileStream(filename);
        IDLLexer lexer = new IDLLexer(fileStream);
        return new IDLParser(new CommonTokenStream(lexer));
    }

    private static void testForErrors(String filename) throws IOException {
        IDLParser parser = parser(filename);

        parser.specification(); // test for errors
        if (parser.getNumberOfSyntaxErrors() > 0) {
            System.exit(0);
        }

    }
    public static void main(String[] args) throws IOException {

        testForErrors(args[0]);

        IDLParser parser = parser(args[0]);

        Visitor visitor = new Visitor();
        Node result = visitor.visit(parser.specification());
        assert(result instanceof Specification);
        Specification specification = (Specification) result;

        ObjectMapper mapper = new ObjectMapper().
                                  enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println(mapper.writeValueAsString(specification));
    }


}
