package hackerrank.util;

import hackerrank.CrackingTheCodingInterview.easy.Arrays_LeftRotation;

import java.io.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Created by grigory@clearscale.net on 6/25/2018.
 */
public class TestUtils {

    public static void withStdStreams(BiConsumer<Writer, BufferedReader> f) {
        InputStream sIn = System.in;
        PrintStream sOut = System.out;
        try {
            PipedOutputStream pipedOutputStreamToStdIn_ = new PipedOutputStream();
            PipedInputStream stdInput_;
            try { stdInput_ = new PipedInputStream(pipedOutputStreamToStdIn_); } catch (IOException ioe) { throw new RuntimeException(ioe); }
            OutputStreamWriter writer = new OutputStreamWriter(pipedOutputStreamToStdIn_);

            PipedOutputStream stdOut_ = new PipedOutputStream();
            PipedInputStream pipeOutReaderStream = new PipedInputStream();
            try { stdOut_.connect(pipeOutReaderStream); } catch (IOException ioe) { throw new RuntimeException(ioe); }
            BufferedReader reader = new BufferedReader(new InputStreamReader(pipeOutReaderStream));

            System.setIn(stdInput_);
            System.setOut(new PrintStream(stdOut_));

            try {
                f.accept(writer, reader);
            } finally {
                try {                 stdInput_.close(); } catch (IOException ignored) {  }
                try {                   stdOut_.close(); } catch (IOException ignored) {  }
                try {                    writer.close(); } catch (IOException ignored) {  }
                try {                    reader.close(); } catch (IOException ignored) {  }
                try { pipedOutputStreamToStdIn_.close(); } catch (IOException ignored) {  }
                try {       pipeOutReaderStream.close(); } catch (IOException ignored) {  }
            }

        } finally {
            System.setIn(sIn); System.setOut(sOut);
        }

    }

}
