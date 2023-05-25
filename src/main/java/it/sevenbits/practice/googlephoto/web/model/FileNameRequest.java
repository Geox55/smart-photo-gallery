package it.sevenbits.practice.googlephoto.web.model;

import java.util.List;

public class FileNameRequest {
    private List<String> files;

    public FileNameRequest() {
    }

    public FileNameRequest(List<String> file) {
        this.files = file;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
