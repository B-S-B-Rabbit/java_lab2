import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import static java.nio.file.Files.exists;

/**
 * Класс, представляющий возможность работы с файлом для подсчета количества латинских букв с учетом регистра
 * и имеющий свойства <b>map</b>, <b>filename</b>, <b>drive</b> и <b>path</b>
 * @author Андрей Помошников
 * @version 1.0
 */
public class FileWorking {
    /** Поле словаря символ: частота */
    private final Map<Character,Integer> map = new HashMap<>();
    /** Поле имени файла */
    private final String filename;
    /** Поле локального диска */
    private final String drive;
    /** Поле пути к файлу */
    private Path path = null;

    /**
     * Конструктор - создание нового объекта для работы с файлом
     * @param d - имя локального диска
     * @param f - имя файла
     * @exception InvalidPathException если путь невалиден
     */
    FileWorking(String d,String f) throws InvalidPathException
    {

        filename = f;
        drive = d;
        path = Path.of(drive + ":\\" + filename + ".txt");


    }

    /**
     * Функция для проверки существования файла
     * @return возвращает булево значение true/false
     */
    private boolean isFileExists()
    {
        return exists(path);
    }

    /**
     * Процедура записи в новый файл результирующих значений
     * @exception IOException если была неудачная попытка ввода-вывода
     */
    private void writeFile() throws IOException {
        Map<Character, Integer> sorted_map = new TreeMap<Character, Integer>(map);
        StringBuilder result = new StringBuilder();
        for (Character key: sorted_map.keySet())
        {
          result.append(key).append(": ").append(sorted_map.get(key).toString()).append('\n');
        }
        Files.writeString(Path.of(drive + ":\\" + filename + "_symbols"+ ".txt"),
                result);
    }

    /**
     * Процедура подсчета частотности латинских символов
     * @exception IOException если была неудачная попытка ввода-вывода
     */
    private void counting_reading() throws IOException {

        FileReader fr = new FileReader(new File(String.valueOf(path)));
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        while (line != null)
        {
            for (int i = 0; i < line.length(); ++i) {
                int ch = line.charAt(i);
                if (ch >= (int) 'a' && ch <= (int) 'z' || ch >= (int) 'A' && ch <= (int) 'Z') {
                    map.merge((char) ch, 1, Integer::sum);
                }
            }
            line = reader.readLine();
        }
        fr.close();
    }

    /**
     * Функция полной обработки файла
     * @exception IOException если была неудачная попытка ввода-вывода
     * @return возвращает 0 в случае успеха, 1 в случае если файл не существует
     */
    public int working() throws IOException {
        if (isFileExists())
    {
        counting_reading();
        writeFile();

    }
        else
        {
            return 1;
        }
    return 0;
    }

}

