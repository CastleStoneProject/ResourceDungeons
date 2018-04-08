package net.tkarura.resourcedungeons.core.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

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

}
