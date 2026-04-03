import java.util.Scanner;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Cipher cipher = new Cipher();
    private static final BruteForce bruteForce = new BruteForce();
    private static final StatisticalAnalyzer analyzer = new StatisticalAnalyzer();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    encryptMode();
                    break;
                case "2":
                    decryptWithKeyMode();
                    break;
                case "3":
                    bruteForceMode();
                    break;
                case "4":
                    statisticalAnalysisMode();
                    break;
                case "0":
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Шифр Цезаря ===");
        System.out.println("1. Зашифровать файл");
        System.out.println("2. Расшифровать файл (с известным ключом)");
        System.out.println("3. Расшифровать brute force");
        System.out.println("4. Расшифровать статистическим анализом");
        System.out.println("0. Выйти");
        System.out.print("Выберите действие: ");
    }

    private static void encryptMode() {
        try {
            System.out.print("Введите путь к исходному файлу: ");
            String src = scanner.nextLine();
            if (!Validator.isFileExists(src)) {
                System.out.println("Файл не существует!");
                return;
            }
            System.out.print("Введите путь для сохранения результата: ");
            String dest = scanner.nextLine();
            System.out.print("Введите ключ (сдвиг): ");
            int key = Integer.parseInt(scanner.nextLine());
            if (!Validator.isValidKey(key)) {
                System.out.println("Недопустимый ключ. Допустимы значения от 0 до " + (Constants.ALPHABET.length - 1));
                return;
            }
            String content = FileManager.readFile(src);
            String encrypted = cipher.encrypt(content, key);
            FileManager.writeFile(encrypted, dest);
            System.out.println("Шифрование завершено. Результат в файле: " + dest);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void decryptWithKeyMode() {
        try {
            System.out.print("Введите путь к зашифрованному файлу: ");
            String src = scanner.nextLine();
            if (!Validator.isFileExists(src)) {
                System.out.println("Файл не существует!");
                return;
            }
            System.out.print("Введите путь для сохранения результата: ");
            String dest = scanner.nextLine();
            System.out.print("Введите ключ (сдвиг): ");
            int key = Integer.parseInt(scanner.nextLine());
            if (!Validator.isValidKey(key)) {
                System.out.println("Недопустимый ключ.");
                return;
            }
            String content = FileManager.readFile(src);
            String decrypted = cipher.decrypt(content, key);
            FileManager.writeFile(decrypted, dest);
            System.out.println("Расшифровка завершена. Результат в файле: " + dest);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void bruteForceMode() {
        try {
            System.out.print("Введите путь к зашифрованному файлу: ");
            String src = scanner.nextLine();
            if (!Validator.isFileExists(src)) {
                System.out.println("Файл не существует!");
                return;
            }
            System.out.print("Введите путь для сохранения результата: ");
            String dest = scanner.nextLine();
            System.out.print("Укажите файл-образец (эталонный текст, Enter если нет): ");
            String sample = scanner.nextLine().trim();

            String encrypted = FileManager.readFile(src);
            String decrypted;
            if (!sample.isEmpty() && Validator.isFileExists(sample)) {
                String sampleText = FileManager.readFile(sample);
                decrypted = bruteForce.bruteForceWithSample(encrypted, sampleText);
            } else {
                decrypted = bruteForce.bruteForceDecrypt(encrypted);
            }
            FileManager.writeFile(decrypted, dest);
            System.out.println("Brute force завершён. Результат в файле: " + dest);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void statisticalAnalysisMode() {
        try {
            System.out.print("Введите путь к зашифрованному файлу: ");
            String src = scanner.nextLine();
            if (!Validator.isFileExists(src)) {
                System.out.println("Файл не существует!");
                return;
            }
            System.out.print("Введите путь к файлу с эталонным текстом: ");
            String repFile = scanner.nextLine();
            if (!Validator.isFileExists(repFile)) {
                System.out.println("Эталонный файл не существует!");
                return;
            }
            System.out.print("Введите путь для сохранения результата: ");
            String dest = scanner.nextLine();

            String encrypted = FileManager.readFile(src);
            String representative = FileManager.readFile(repFile);
            int shift = analyzer.findMostLikelyShift(encrypted, representative);
            System.out.println("Наиболее вероятный сдвиг: " + shift);
            String decrypted = cipher.decrypt(encrypted, shift);
            FileManager.writeFile(decrypted, dest);
            System.out.println("Статистическая расшифровка завершена. Результат в файле: " + dest);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}