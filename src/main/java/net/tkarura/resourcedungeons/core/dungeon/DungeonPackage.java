package net.tkarura.resourcedungeons.core.dungeon;

import net.tkarura.resourcedungeons.core.util.DOMUtils;
import net.tkarura.resourcedungeons.core.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class DungeonPackage {

    private String prefix = "";
    private FileFilter zip_filter = FileFilterUtils.suffixFileFilter(".zip");

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void openPackageDungeons(File package_dir, File temp_dir) throws IOException {

        for (File dir : package_dir.listFiles()) {

            if (dir.isDirectory()) {
                openPackageDungeons(dir, temp_dir);
            }

            if (zip_filter.accept(dir)) {
                openTempFolder(new ZipFile(dir), temp_dir);
            }

        }

    }

    public void openLibraryDungeons(File library_dir, File temp_dir) throws IOException {

        ZipFile file = new ZipFile(library_dir);
        List<String> dungeons_dir = getLibraryDungeonsDirectory(file);

        for (String dungeon_dir : dungeons_dir) {

            try {
                this.openTempFolder(file, dungeon_dir, temp_dir);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private List<String> getLibraryDungeonsDirectory(ZipFile file) throws IOException {

        List<String> list = new ArrayList<>();
        InputStream stream = file.getInputStream(file.getEntry("dungeons.xml"));

        if (stream == null) {
            throw new FileNotFoundException("dungeons.xml");
        }

        try {

            Document document = DOMUtils.readDocument(stream);
            Node dungeons = DOMUtils.getNameToFirstNode(document, "dungeons");
            Node includes = DOMUtils.getNameToFirstNode(dungeons, "includes");
            NodeList child = includes.getChildNodes();
            for (int i = 0; i < child.getLength(); i++) {
                Node item = child.item(i);
                if (DOMUtils.isMatchNodeName(item, "import")) {
                    list.add(DOMUtils.getAttributeTextContent(item,"src"));
                }
            }

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        return list;

    }

    private void openTempFolder(ZipFile file, String dungeon_dir, File temp_dir) throws IOException {
        InputStream stream = file.getInputStream(file.getEntry(dungeon_dir));
        File temp_dungeon_dir = Files.createTempDirectory(temp_dir.toPath(), prefix).toFile();
        FileUtil.unZipFolder(new ZipInputStream(stream), temp_dungeon_dir);
    }

    private void openTempFolder(ZipFile file, File temp_dir) throws IOException {
        File temp_dungeon_dir = Files.createTempDirectory(temp_dir.toPath(), prefix).toFile();
        FileUtil.unZipFolder(file, temp_dungeon_dir);
    }

}
