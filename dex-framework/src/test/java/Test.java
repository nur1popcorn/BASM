import com.nur1popcorn.basm.tree.DexFile;
import junit.framework.TestCase;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test extends TestCase {
    @org.junit.Test
    public void test() throws IOException {
        final Path path = Paths.get("classes.dex");
        new DexFile(ByteBuffer.wrap(Files.readAllBytes(path)));
    }
}
