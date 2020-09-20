package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesFinder {
    private ArrayList foundFiles;

    public List getFoundFiles() {
        return foundFiles;
    }

    public FilesFinder() {
        foundFiles = new ArrayList();
    }

    public ArrayList findFiles(String path, String extension){
        File dir = new File(path);
        fetchFiles(dir, extension);
        return foundFiles;
    }

    public void resetFoundFiles(){
        foundFiles.clear();
    }

    private void fetchFiles(File dir, String extension) {

        if (dir.isDirectory()) {
            for (File file1 : dir.listFiles()) {
                fetchFiles(file1, extension);
            }
        } else {
            if (dir.getName().toLowerCase().endsWith("." + extension)) {
                foundFiles.add(dir.getAbsolutePath());
            }
        }
    }
}
