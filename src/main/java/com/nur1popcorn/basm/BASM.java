package com.nur1popcorn.basm;

import com.nur1popcorn.basm.transformers.Transformer;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

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
            optionParser.accepts("help")
                        .forHelp();
            final OptionSpec<File> inArg = optionParser.accepts("in", "The files which should be converted.")
                    .withRequiredArg()
                    .ofType(File.class)
                    .withValuesSeparatedBy(' ')
                    .required();
            final OptionSpec<File> outArg = optionParser.accepts("out", "The location the output file should be written to.")
                    .withRequiredArg()
                    .ofType(File.class);
            final OptionSpec<String> transformerArg = optionParser.accepts("transformer", "The class responsible for conversion.")
                    .withOptionalArg()
                    .required();

            final OptionSet optionSet = optionParser.parse(args);

            if(optionSet.has("help")) {
                optionParser.printHelpOn(System.out);
                return;
            }

            final Transformer transformer = ((Transformer) Class.forName(transformerArg.value(optionSet)).newInstance());
            final File outFile = outArg.value(optionSet);

            final List<InputStream> inputStreams = inArg.values(optionSet).stream().filter(File::exists).map(file -> {
                try {
                    return new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());

            if(outFile != null) {
                if(!outFile.createNewFile())
                    throw new IOException("Could not create output file, it already exists.");
                final FileOutputStream out = new FileOutputStream(outFile);
                transformer.transform(inputStreams, out);
                out.close();
            } else
                transformer.transform(inputStreams, System.out);

            for(InputStream is : inputStreams)
                is.close();
        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
