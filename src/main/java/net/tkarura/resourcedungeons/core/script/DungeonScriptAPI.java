package net.tkarura.resourcedungeons.core.script;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class DungeonScriptAPI {

    private ClassLoader loader;
    private List<String> script_srcs = new ArrayList<>();

    public DungeonScriptAPI(ClassLoader loader) {
        this.loader = loader;
    }

    public void load() {
        load("scripts.xml");
    }

    public void load(String scripts_file) {

        try {

            InputStream is = loader.getResourceAsStream(scripts_file);

            if (is == null) {
                throw new FileNotFoundException(scripts_file + " is Not Found.");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            Node scripts_node = getNodeByName(doc, "scripts");

            this.loadScriptsNode(getNodeByName(scripts_node, "includes"));

        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

    }

    private Node getNodeByName(Node parent, String node_name) {

        NodeList list = parent.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);

            if (node.getNodeName() != null && node.getNodeName().equalsIgnoreCase(node_name)) {
                return node;
            }

        }

        return null;
    }

    private void loadScriptsNode(Node node) {

        NodeList list = node.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {

            Node item = list.item(i);

            if (item.getNodeName().equalsIgnoreCase("import")) {
                this.script_srcs.add(item.getAttributes().getNamedItem("src").getNodeValue());
            }

        }

    }

    public void loadScripts(ScriptEngine engine) {

        for (String src : script_srcs) {
            loadScript(engine, src);
        }

    }

    private void loadScript(ScriptEngine engine, String src) {

        InputStream is = loader.getSystemResourceAsStream(src);

        if (is == null) {
            return;
        }

        try {
            engine.eval(new InputStreamReader(is));
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

}
