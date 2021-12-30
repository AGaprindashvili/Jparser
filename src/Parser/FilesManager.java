package Parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class FilesManager {

    public boolean createFolder() {
        File folder = new File("Jparser");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder.exists();
    }

    public boolean createFile(String content, String fileNm, String fileExt) {
        try {
            String fileName = "Jparser/" + fileNm + "." + fileExt;
            File newFile = new File(fileName);
            if (newFile.createNewFile()) {
                BufferedWriter fw = Files.newBufferedWriter(Paths.get(fileName), StandardCharsets.UTF_8);
                fw.write(content);
                fw.close();
                return true;
            }
            else {
                return false;
            }
        }
        catch (IOException e) {
            return false;
        }
    }

    public boolean getFromUrl(Set<String> links) {
        try {
            for (String link : links) {
                InputStream st = new URL(link).openStream();
                Files.copy(st, Paths.get("Jparser/" + java.util.UUID.randomUUID().toString() + ".png"));
            }
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

}