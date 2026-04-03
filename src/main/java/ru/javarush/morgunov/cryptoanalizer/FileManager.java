import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Stream;

public class FileManager {

    public static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        StringBuilder sb = new StringBuilder();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.forEach(line -> sb.append(line).append(System.lineSeparator()));
        }
        return sb.toString();
    }

    public static void writeFile(String content, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }
}