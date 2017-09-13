package com.nur1popcorn.basm;

import com.nur1popcorn.basm.classfile.ClassReader;
import com.nur1popcorn.basm.transformers.Transformer;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.*;

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

    public static byte[] encodeUtf8(String s) {
        int strLen = 0;
        final char chars[] = s.toCharArray();
        // obtain size needed for buffer.
        for(char c : chars)
            strLen += c <= '\u007f' ? 1 : c <= '\u07ff' ? 2 : 3;
        final byte buff[] = new byte[strLen];
        // fill buffer.
        int offset = 0;
        for(int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if(c <= '\u007f')
                // only 1 byte needed.
                buff[offset++] = (byte)c;
            else if(c <= '\u07ff' /* 1 1111 11 1111‬ */) {
                // b0 = 110? ????
                buff[offset++] = (byte)(0xc0 | c >> 6 /* 7ff SHR 6 -> 1f */);
                // b1 = 10?? ????
                buff[offset++] = (byte)(0x80 | c & 0x3f);
                /* 11 1111 1111 */
            } else /* if(c <= '\uffff') /* 1111 11 1111 11 1111 */ {
                // b0 = ‭1110 ????
                buff[offset++] = (byte)(0xe0 | c >> 12 /* ffff SHR 12 -> f */);
                // b1 = ‭10?? ????
                buff[offset++] = (byte)(0x80 | c >> 6 & 0x3f);
                // b2 = ‭10?? ????
                buff[offset++] = (byte)(0x80 | c & 0x3f);
            }
        }
        return buff;
    }

    public static void main(String args[]) throws IOException {
        try {
            DataInputStream in = new DataInputStream(BASM.class.getResourceAsStream("BASM.class"));
            ClassReader classReader = new ClassReader(in);
            classReader.read();
            System.out.println();
            for(int i = 0; i < classReader.getConstantPool().getSize(); i++)
                System.out.println(i + ":" + classReader.getConstantPool().getEntry(i));
            System.out.println();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(true)
            return;

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
                    .ofType(String.class)
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
