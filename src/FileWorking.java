import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static java.nio.file.Files.exists;

public class FileWorking {
    private Map<Character,Integer> map = new HashMap<>();
    private String data;
    private String filename;
    private String drive;
    private Path path = null;
    FileWorking(String d,String f)
    {
        filename = f;
        drive = d;
        try
        {
            path = Path.of(drive + ":\\" + filename + ".txt");
        }
        catch (InvalidPathException ipe)
        {
            System.out.println("InvalidPathException");

        }
    }

    private boolean isFileExists()
    {
        return exists(path);
    }
    private String readFile() throws IOException {
       return Files.readString(path);
    }

    private boolean writeFile() throws IOException {
        Map<Character, Integer> sorted_map = new TreeMap<Character, Integer>(map);
        StringBuilder result = new StringBuilder();
        for (Character key: sorted_map.keySet())
        {
          result.append(key).append(": ").append(sorted_map.get(key).toString()).append('\n');
        }
        Files.writeString(Path.of(drive + ":\\" + filename + "_symbols"+ ".txt"),
                result);
        return true;
    }

    private void counting()
    {
        for (int i = 0; i < data.length(); ++i)
        {
            char c = data.charAt(i);
            if ((int) c >= (int) 'a' && (int) c <= (int) 'z' || (int) c >= (int) 'A' && (int) c <= (int) 'Z')
            {
                map.merge(c, 1, Integer::sum);
            }
        }
    }


    public void working() throws IOException {
        if (isFileExists())
    {
        data = readFile();
        counting();
        writeFile();
    }

    }

    public void setData(String data) {
        this.data = data;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

