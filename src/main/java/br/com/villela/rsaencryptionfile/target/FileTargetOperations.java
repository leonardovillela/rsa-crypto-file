package br.com.villela.rsaencryptionfile.target;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class FileTargetOperations {

    public static String read(Path fileTarget) {
        try {
            return Files.lines(fileTarget, Charset.forName("UTF-8")).reduce(String::concat).orElse("");
        } catch (IOException e) {
            throw new FileTargetReadException();
        }
    }

    public static void write(Path fileTarget, String text) {
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(fileTarget, CREATE, TRUNCATE_EXISTING))) {
            out.write(text.getBytes(), 0, text.getBytes().length);
        } catch (IOException x) {
            throw new FileTargetWriteException();
        }
    }
}
