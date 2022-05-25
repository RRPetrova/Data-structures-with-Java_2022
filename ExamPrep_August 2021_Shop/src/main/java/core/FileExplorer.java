package core;

import model.File;
import shared.FileManager;

import java.util.List;

public class FileExplorer implements FileManager {
    private File root;

    @Override
    public void addInDirectory(int directorNumber, File file) {

    }

    @Override
    public File getRoot() {
        return null;
    }

    @Override
    public File get(int number) {
        return null;
    }

    @Override
    public Boolean deleteFile(File file) {
        return null;
    }

    @Override
    public List<File> getFilesInPath(File path) {
        return null;
    }

    @Override
    public void move(File file, File destination) {

    }

    @Override
    public Boolean contains(File file) {
        return null;
    }

    @Override
    public List<File> getInDepth() {
        return null;
    }

    @Override
    public List<File> getInLevel() {
        return null;
    }

    @Override
    public void cut(int number) {

    }

    @Override
    public void paste(File destination) {

    }

    @Override
    public Boolean isEmpty() {
        return null;
    }

    @Override
    public String getAsString() {
        if (this.root == null) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        print(this.root, buffer, "", "");
        return buffer.toString().trim();
    }

    private void print(File file, StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(file.getNumber());
        buffer.append(System.lineSeparator());
        List<File> children = file.getChildren();
        for (int i = 0; i < children.size(); i++) {
            File next = children.get(i);
            if (i < children.size() - 1) {
                print(next, buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                print(next, buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }
}
