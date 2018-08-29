package net.tkarura.resourcedungeons.core.util;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public final class FileUtil {

    private FileUtil() {}

    public static boolean isDirectoryInPath(File p, File d) {
        try {
            Path p_ = getAbsolutePath(p);
            Path d_ = getAbsolutePath(d);
            return d_.toString().startsWith(p_.toString());
        } catch (NullPointerException | IOException e) {
            return false;
        }
    }

    public static Path getAbsolutePath(File dir) throws IOException {
        return Paths.get(dir.getPath()).toRealPath(LinkOption.NOFOLLOW_LINKS);
    }

    public static String getAbsolutePathName(File dir) {
        try {
            return getAbsolutePath(dir).toString();
        } catch (IOException e) {
            return dir.getAbsolutePath();
        }
    }

    public static void attachDirectory(File dirs, Consumer<File> consumer) {

        Validate.notNull(dirs, "dirs can not be null.");

        for (File dir : dirs.listFiles()) {
            if (dir.isDirectory()) {
                attachDirectory(dir, consumer);
            }
            consumer.accept(dir);
        }

    }

    public static void unZipFolder(ZipInputStream stream, File open_dir) throws IOException {

        ZipEntry entry;
        while ((entry = stream.getNextEntry()) != null) {

            File dir = new File(open_dir, entry.getName());

            if (entry.isDirectory()) {
                dir.mkdirs();
                continue;
            }

            try (FileOutputStream output = new FileOutputStream(dir)) {
                int i;
                while ((i = stream.read()) != -1) {
                    output.write(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            stream.closeEntry();
        }

    }

    public static void unZipFolder(ZipFile file, File open_dir) {

        Enumeration<? extends ZipEntry> enumeration = file.entries();

        while (enumeration.hasMoreElements()) {

            ZipEntry entry = enumeration.nextElement();
            File dir = new File(open_dir, entry.getName());

            // ディレクトリの展開
            if (entry.isDirectory()) {
                dir.mkdirs();
                continue;
            }

            // ファイルの中身を展開先にコピー
            try (
                FileOutputStream out_stream = new FileOutputStream(dir);
                InputStream input_stream = file.getInputStream(entry)
            ) {
                byte[] buf = new byte[1024];
                int size = 0;
                while ((size = input_stream.read(buf)) != -1) {
                    out_stream.write(buf, 0, size);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
