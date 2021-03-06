/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
 * This file is part of {BASM}.
 *
 * Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 * {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
 *
 */

package com.nur1popcorn.basm;
/*
import com.nur1popcorn.basm.transformers.ITransformer;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.*;*/

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

    /*public static void main(String args[]) throws IOException {
        try {
            final OptionParser optionParser = new OptionParser();
            optionParser.accepts("help")
                        .forHelp();
            final OptionSpec<File> inArg = optionParser.accepts("in", "The file which should be transformed.")
                    .withRequiredArg()
                    .ofType(File.class)
                    .required();
            final OptionSpec<File> outArg = optionParser.accepts("out", "The file the output file should be written to.")
                    .withRequiredArg()
                    .ofType(File.class);
            final OptionSpec<String> transformerArg = optionParser.accepts("transformer", "The class of the transformer responsible for transformation of the input.")
                    .withRequiredArg()
                    .required();

            final OptionSet optionSet = optionParser.parse(args);

            if(optionSet.has("help")) {
                optionParser.printHelpOn(System.out);
                return;
            }

            final ITransformer transformer = ((ITransformer) Class.forName(transformerArg.value(optionSet)).newInstance());
            final File outFile = outArg.value(optionSet);

            final InputStream in = new FileInputStream(inArg.value(optionSet));

            if(outFile != null) {
                if(!outFile.createNewFile())
                    throw new IOException("Could not create output file, it already exists.");
                final FileOutputStream out = new FileOutputStream(outFile);
                transformer.transform(in, out);
                out.close();
            } else
                transformer.transform(in, System.out);
            in.close();
        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
