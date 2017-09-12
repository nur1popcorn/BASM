package com.nur1popcorn.basm.transformers;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Transformer {
    public void transform(List<File> in, File out) throws IOException;
}
