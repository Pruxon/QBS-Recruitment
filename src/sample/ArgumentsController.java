package sample;

public class ArgumentsController {

    private String path;
    private String fileExtension;
    private byte[] bytes;
    private byte[] replacingBytes;

    public byte[] getBytes() {
        return bytes;
    }

    public byte[] getReplacingBytes() {
        return replacingBytes;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getPath() {
        return path;
    }

    public ArgumentsController(String path, String extension, byte[] bytes1, byte[] bytes2) {
        this.bytes = bytes1;
        this.replacingBytes = bytes2;
        this.path = path;
        this.fileExtension = extension;
    }
}
