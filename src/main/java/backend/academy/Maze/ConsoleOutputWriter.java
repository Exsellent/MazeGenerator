package backend.academy.Maze;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleOutputWriter implements OutputWriter {
    @Override
    public void writeLine(String message) {
        log.info(message);
    }

    @Override
    public void print(String message) {
        log.info(message);
    }

    @Override
    public void println(String message) {
        log.info(message);
    }
}
