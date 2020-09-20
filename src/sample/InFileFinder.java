package sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class InFileFinder {

    private KMP kpm;

    public InFileFinder() {
        kpm = new KMP();
    }

    public void changeFile(String path, byte[] bytes, byte[] replacingBytes) throws IOException {
        byte[] fileToBytes = createBytesFromFile(path);
        int index = 0;
        int fileSize = fileToBytes.length;

        if (bytes.length == replacingBytes.length) {
            while (index < fileSize && index != -1) {
                index = kpm.indexOf(fileToBytes, bytes, index);
                if (index != -1) {
                    for (int i = 0; i < replacingBytes.length; i++) {
                        fileToBytes[index + i] = replacingBytes[i];
                    }
                    index += replacingBytes.length;
                }
            }

            writeToFileFromBytes(path, fileToBytes);
        } else {
            ArrayList indexesArray = new ArrayList<Integer>();
            while (index < fileSize && index != -1) {
                index = kpm.indexOf(fileToBytes, bytes, index);
                if (index != -1) {
                    Integer toAdd = index;
                    indexesArray.add(toAdd);
                    index += bytes.length;
                }
            }
            byte[] bytesToFile = createNewBytes(indexesArray, bytes, replacingBytes, fileToBytes);
            writeToFileFromBytes(path, bytesToFile);

        }

    }

    private byte[] createBytesFromFile(String path) throws IOException {
        File file = new File(path);
        byte[] bytes = Files.readAllBytes(file.toPath());

        return bytes;
    }

    private void writeToFileFromBytes(String path, byte[] bytes) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(bytes);
    }

    private byte[] createNewBytes(ArrayList<Integer> indexes, byte[] bytes, byte[] replacingBytes, byte[] fileToBytes) {
        int byteAmountDifference = replacingBytes.length - bytes.length;
        int newFileSize = fileToBytes.length + (indexes.size()*byteAmountDifference);
        byte[] toReturn = new byte[newFileSize];

        indexes.add(fileToBytes.length);
        for (int i = 0; i < indexes.get(0).intValue(); i++) {
            toReturn[i] = fileToBytes[i];
        }

        int foundPatterns = indexes.size()-1;

        for (int i = 0; i < foundPatterns; i++) {
            int start = (indexes.get(i).intValue() + i*byteAmountDifference);
            int middle = start+replacingBytes.length;
            int end = (indexes.get(i+1).intValue() + (i+1)*byteAmountDifference);
            int k = 0;

            for (int j = start; j < middle; j++) {
                toReturn[j] = replacingBytes[k];
                k++;
            }
            for (int j = middle; j < end; j++) {
                toReturn[j] = fileToBytes[j-((i+1)*byteAmountDifference)];
            }
        }

        return toReturn;
    }
}
