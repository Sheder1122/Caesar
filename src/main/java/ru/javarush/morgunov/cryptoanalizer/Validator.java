import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Validator {

    public static boolean isFileExists(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    public static boolean isValidKey(int key) {
        return key >= 0 && key < Constants.ALPHABET.length;
    }
}