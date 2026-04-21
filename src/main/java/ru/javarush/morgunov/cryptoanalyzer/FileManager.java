package ru.javarush.morgunov.cryptoanalyzer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Stream;

public class FileManager {

    private static final Path BASE_DIR = Paths.get("src/main/texts").toAbsolutePath().normalize();

    static {
        try {
            Files.createDirectories(BASE_DIR);
            System.out.println("Рабочая директория: " + BASE_DIR);
        } catch (IOException e) {
            System.err.println("Не удалось создать рабочую директорию: " + e.getMessage());
        }
    }

    private static Path validatePath(String userPath) throws IOException {
        Path resolved = BASE_DIR.resolve(userPath).normalize();
        if (!resolved.startsWith(BASE_DIR)) {
            throw new SecurityException("Доступ запрещён. Путь: " + userPath);
        }
        return resolved;
    }

    public static String readFile(String filePath) throws IOException {
        Path safePath = validatePath(filePath);
        StringBuilder sb = new StringBuilder();
        try (Stream<String> lines = Files.lines(safePath, StandardCharsets.UTF_8)) {
            lines.forEach(line -> sb.append(line).append(System.lineSeparator()));
        }
        return sb.toString();
    }

    public static void writeFile(String content, String filePath) throws IOException {
        Path safePath = validatePath(filePath);
        Files.createDirectories(safePath.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(safePath, StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }
}