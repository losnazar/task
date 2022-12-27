package losnazar.task.service;

import java.io.File;

public interface CsvFileWriter {
    File writeToFile(String content);
}
