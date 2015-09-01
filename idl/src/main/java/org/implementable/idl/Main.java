package org.implementable.idl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {

        try {
            Specification specification = Parser.parse(new File(args[0]));
            ObjectMapper mapper = new ObjectMapper().
                    enable(SerializationFeature.INDENT_OUTPUT);
            System.out.println(mapper.writeValueAsString(specification));

        } catch (Parser.ParsingException e) {
            System.exit(1);
        }

    }


}
