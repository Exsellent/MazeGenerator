package backend.academy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleOutputWriter implements OutputWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleOutputWriter.class);

    @Override
    public void writeLine(String message) {
        LOGGER.info(message);
    }

    @Override
    public void print(String message) {
        LOGGER.info(message);
    }

    @Override
    public void println(String message) {
        LOGGER.info(message);
    }
}
