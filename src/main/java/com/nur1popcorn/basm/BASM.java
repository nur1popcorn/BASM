package com.nur1popcorn.basm;

import com.nur1popcorn.basm.transformers.Transformer;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.File;
import java.io.IOException;

public final class BASM {
    /**
     * The program's name.
     */
    public static final String NAME = "BASM",

    /**
     * The program's version.
     */
                               VERSION = "1.0.0-alpha";

    /**
     * An array of authors.
     */
    public static final String AUTHORS[] = {
        "nur1popcorn"
    };

    public static void main(String args[]) throws IOException {
        try {
            final OptionParser optionParser = new OptionParser();
            optionParser.printHelpOn(System.out);
            optionParser.accepts("help")
                        .forHelp();
            final OptionSpec<File> in = optionParser.accepts("in", "The files which should be converted.")
                    .withRequiredArg()
                    .ofType(File.class)
                    .withValuesSeparatedBy(' ')
                    .required();
            final OptionSpec<File> out = optionParser.accepts("out", "The location the output file should be written to.")
                    .withRequiredArg()
                    .ofType(File.class)
                    .required();
            final OptionSpec<String> transformer = optionParser.accepts("transformer", "The class responsible for conversion.")
                    .withOptionalArg()
                    .required();

            final OptionSet optionSet = optionParser.parse(args);

            if(optionSet.has("help"))
                return;

            ((Transformer) Class.forName(transformer.value(optionSet)).newInstance())
                    .transform(in.values(optionSet),
                               out.value(optionSet));

        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
