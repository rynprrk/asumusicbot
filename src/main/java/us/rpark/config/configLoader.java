package us.rpark.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class configLoader {
    String load(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }
}
