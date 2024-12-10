package poeticrainbow.rainbowchristmas.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class OpenFile {
    private final File file;

    public OpenFile(String path) {
        file = new File(path);
    }

    public OpenFile(Path path) {
        file = new File(path.toString());
    }

    public OpenFile(File f) {
        file = f;
    }

    public String readFile() {
        try {
            FileReader fr = new FileReader(file);
            char[] charArray = new char[(int) file.length()];
            fr.read(charArray);
            fr.close();

            return String.valueOf(charArray);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to read the file.");
            e.printStackTrace();

            return "";
        }
    }

    public void saveFile(String s) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(s);
            fw.close();
        } catch (Exception e) {
            System.out.println("An error occurred while trying to save the file.");
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return file.getName();
    }

    public String getFilePath() {
        return file.getPath();
    }
}
