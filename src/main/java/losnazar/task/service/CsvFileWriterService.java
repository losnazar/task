package losnazar.task.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import losnazar.task.exception.CsvFileWritingException;
import org.springframework.stereotype.Component;

@Component
public class CsvFileWriterService implements CsvFileWriter {
    private static final String FILE_PATH = "src/main/resources/report.csv";

    @Override
    public File writeToFile(String content) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.write(content);
        } catch (IOException e) {
            throw new CsvFileWritingException("Can't write report to file by path: "
                    + FILE_PATH, e);
        }
        return new File(FILE_PATH);
    }
}
