package perobobbot.tools;

import fpc.tools.lang.ThrowableTool;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class MessageSaver {

    private final @Nullable Path root;

    private final String filePrefix;
    private final String fileSuffix;

    public MessageSaver(String filePrefix, String fileSuffix) {
        this("perobobbot_saves",filePrefix,fileSuffix);
    }

    public MessageSaver(String folder, String filePrefix, String fileSuffix) {
        var root = Path.of(System.getProperty("user.home")).resolve(folder);
        try {
            Files.createDirectories(root);
        } catch (Throwable t) {
            t.printStackTrace();
            root = null;
        }
        this.root = root;
        this.filePrefix = filePrefix;
        this.fileSuffix = fileSuffix;

    }

    public void saveMessage(byte [] content) {
        save(path -> {
            try {
                Files.write(path,content);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    public void saveMessage(String content) {
        save(path -> {
            try {
                Files.write(path, List.of(content));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    private void save(Consumer<Path> writer) {
        if (this.root == null) {
            return;
        }
        try {
            final var outputFile = Files.createTempFile(this.root,filePrefix,fileSuffix);
            writer.accept(outputFile);
        } catch (Throwable t) {
            ThrowableTool.interruptIfCausedByInterruption(t);
            LOG.warn("Fail to save message.",t);
        }

    }

}
