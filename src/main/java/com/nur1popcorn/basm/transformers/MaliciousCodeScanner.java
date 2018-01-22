package com.nur1popcorn.basm.transformers;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantMethodRef;
import com.nur1popcorn.basm.classfile.constants.ConstantNameAndType;

import java.io.*;
import java.util.List;

import static com.nur1popcorn.basm.utils.Constants.*;

public class MaliciousCodeScanner implements Transformer {

    private static final String MALICIOUS_PATHS[] = {
        "java/io",
        "java/net",
        "java/lang/reflect"
    };

    @Override
    public void transform(List<InputStream> in, OutputStream out) throws IOException {
        final PrintWriter writer = new PrintWriter(out);
        in.forEach(inputStream -> {
            try {
                DataInputStream din = new DataInputStream(inputStream);
                final int magic = din.readInt();
                if(magic != MAGIC)
                    throw new IOException("The class provided has an invalid file header: " + magic);
                din.skipBytes(4);

                {
                    final ConstantPool constantPool = new ConstantPool(din);
                    for(int i = 1 /* the cp's size is 1 less than given */; i < constantPool.getSize(); i++) {
                        final ConstantInfo constantInfo = constantPool.getEntry(i);
                        if(constantInfo != null)
                            if(constantInfo.getTag() == CONSTANT_METHOD_REF) {
                                final ConstantMethodRef methodRef = ((ConstantMethodRef) constantInfo);
                                final String path = methodRef.indexClass(constantPool)
                                                             .indexName(constantPool)
                                                             .bytes;
                                for(String malicious : MALICIOUS_PATHS)
                                    if(path.startsWith(malicious)) {
                                        writer.print(path);
                                        writer.print("#");
                                        final ConstantNameAndType nameAndType = methodRef.indexNameAndType(constantPool);
                                        writer.print(nameAndType.indexName(constantPool).bytes);
                                        writer.println(nameAndType.indexDesc(constantPool).bytes);
                                        //TODO: fetch java docs and display ?
                                        break;
                                    }
                            } else if(constantInfo.getTag() == CONSTANT_CLASS)
                                ;
                    }
                }

                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
