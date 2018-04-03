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
            Path p_ = Paths.get(p.getPath()).toRealPath(LinkOption.NOFOLLOW_LINKS);
            Path d_ = Paths.get(d.getPath()).toRealPath(LinkOption.NOFOLLOW_LINKS);
            return d_.toString().startsWith(p_.toString());
        } catch (NullPointerException | IOException e) {
            return false;
        }
    }

}
