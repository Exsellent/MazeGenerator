package backend.academy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputReader implements InputReader {
    private final BufferedReader reader;

    public ConsoleInputReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public char readChar() throws IOException {
        return (char) reader.read();
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
