package settings;

import okhttp3.internal.http2.Settings;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class iFileReader {
    private final Logger log = Logger.getLogger(Settings.class.getName());

    /**
     * Метод для преобразования списка строк в текст.
     *
     * @param list список строк в формате List<String>
     * @return текст в формате String
     */
    private String getText(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String line : list) {
            builder.append(line);
        }
        return builder.toString();
    }

    /**
     * Вспомогательный метод для возврата списка считываемых строк из тестовых ресурсов.
     *
     * @param filePath имя файла
     * @return список строк из тестовых ресурсов в формате List<String>
     * @throws IOException исключение ввода/вывода
     */
    private List<String> getDataTest(String filePath) throws IOException {
        return Files.readAllLines(
                Paths.get("src/test/java/" + filePath), StandardCharsets.UTF_8);
    }

    /**
     * Метод для чтения текста из тестовых ресурсов src/test/java/.
     *
     * @param file имя файла
     * @return текст из файла в формате String
     * @throws IOException исключение ввода/вывода
     */
    public String readTest(String file) throws IOException {
        return getText(getDataTest(file));
    }

    /**
     * Метод для построчного чтения текста из тестовых ресурсов src/test/java/.
     *
     * @param file имя файла
     * @param n    номер строки, которую необходимо вернуть в ответе метода
     * @return возвращаемая по номеру строка в формате String
     * @throws IOException исключение ввода/вывода
     */
    public String readTest(String file, int n) throws IOException {
        return getDataTest(file).get(n);
    }

    /**
     * Метод для записи текста в файл.
     *
     * @param filePath путь, по которому находится файл для записи
     * @param append   параметр, указывающий на предварительную очистку файла перед записью (true/false)
     * @param text     текст, который будет записан в файл filePath
     */
    public void writeToFile(String filePath, boolean append, String text) {
        try (FileWriter writer = new FileWriter(filePath, append)) {
            writer.write(text);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
    }
}
