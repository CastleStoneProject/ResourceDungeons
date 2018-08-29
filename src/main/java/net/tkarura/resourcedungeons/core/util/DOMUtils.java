package net.tkarura.resourcedungeons.core.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class DOMUtils {

    private DOMUtils() {}

    public static Document readDocument(InputSource is) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
        return document;
    }

    public static Document readDocument(InputStream is) throws ParserConfigurationException, IOException, SAXException {
        return readDocument(new InputSource(is));
    }

    public static Document readDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        return readDocument(new FileInputStream(file));
    }

    public static Document readDocument(String file_name) throws ParserConfigurationException, IOException, SAXException {
        return readDocument(new File(file_name));
    }

    public static Node[] getNameToNodeList(Node parent, String node_name) {
        List<Node> nodes = new ArrayList<>();
        if (parent == null || node_name == null) return nodes.toArray(new Node[0]);
        NodeList list = parent.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeName().equals(node_name)) {
                nodes.add(node);
            }
        }
        return nodes.toArray(new Node[0]);
    }

    public static Node getNameToFirstNode(Node parent, String node_name) {
        if (parent == null || node_name == null) return null;
        Node[] nodes = getNameToNodeList(parent, node_name);
        return nodes.length > 0 ? nodes[0] : null;
    }

    public static String getNodeTextContent(Node node, String node_name) {
        return getNodeTextContent(node, node_name, "");
    }

    public static String getNodeTextContent(Node node, String node_name, String def) {
        if (node == null || node_name == null) return def;
        String text = node.getTextContent();
        return text != null ? text : def;
    }

    public static boolean isMatchNodeName(Node node, String node_name) {
       if (node == null || node_name == null) return false;
       return node.getNodeName().equals(node_name);
    }

    public static String getAttributeTextContent(Node node, String attiribute_name) {
        Node attr = node.getAttributes().getNamedItem(attiribute_name);
        if (attr != null) {
            return attr.getTextContent();
        }
        return "";
    }

    // TextContent

    public static byte toByteTextContent(Node node, String node_name) {
        return toByteTextContent(node, node_name, (byte) 0);
    }

    public static byte toByteTextContent(Node node, String node_name, byte def) {
        return NumberUtils.toByte(getNodeTextContent(node, node_name).trim(), def);
    }

    public static short toShortTextContent(Node node, String node_name) {
        return toShortTextContent(node, node_name, (short) 0);
    }

    public static short toShortTextContent(Node node, String node_name, short def) {
        return NumberUtils.toShort(getNodeTextContent(node, node_name).trim(), def);
    }

    public static int toIntTextContent(Node node, String node_name) {
        return toIntTextContent(node, node_name, 0);
    }

    public static int toIntTextContent(Node node, String node_name, int def) {
        return NumberUtils.toInt(getNodeTextContent(node, node_name).trim(), def);
    }

    public static long toLongTextContent(Node node, String node_name) {
        return toLongTextContent(node, node_name, 0);
    }

    public static long toLongTextContent(Node node, String node_name, long def) {
        return NumberUtils.toLong(getNodeTextContent(node, node_name).trim(),  def);
    }

    public static float toFloatTextContent(Node node, String node_name) {
        return toFloatTextContent(node, node_name, 0.0f);
    }

    public static float toFloatTextContent(Node node, String node_name, float def) {
        return NumberUtils.toFloat(getNodeTextContent(node, node_name).trim(), def);
    }

    public static double toDoubleTextContent(Node node, String node_name) {
        return toDoubleTextContent(node, node_name, 0.0);
    }

    public static double toDoubleTextContent(Node node, String node_name, double def) {
        return NumberUtils.toDouble(getNodeTextContent(node, node_name).trim(), def);
    }

    // Attribute

    public static byte toByteAttribute(Node node, String node_name) {
        return toByteAttribute(node, node_name, (byte) 0);
    }

    public static byte toByteAttribute(Node node, String node_name, byte def) {
        return NumberUtils.toByte(getAttributeTextContent(node, node_name).trim(), def);
    }

    public static short toShortAttribute(Node node, String node_name) {
        return toShortAttribute(node, node_name, (short) 0);
    }

    public static short toShortAttribute(Node node, String node_name, short def) {
        return NumberUtils.toShort(getAttributeTextContent(node, node_name).trim(), def);
    }

    public static int toIntAttribute(Node node, String node_name) {
        return toIntAttribute(node, node_name, 0);
    }

    public static int toIntAttribute(Node node, String node_name, int def) {
        return NumberUtils.toInt(getAttributeTextContent(node, node_name).trim(), def);
    }

    public static long toLongAttribute(Node node, String node_name) {
        return toLongAttribute(node, node_name, 0);
    }

    public static long toLongAttribute(Node node, String node_name, long def) {
        return NumberUtils.toLong(getAttributeTextContent(node, node_name).trim(),  def);
    }

    public static float toFloatAttribute(Node node, String node_name) {
        return toFloatAttribute(node, node_name, 0.0f);
    }

    public static float toFloatAttribute(Node node, String node_name, float def) {
        return NumberUtils.toFloat(getAttributeTextContent(node, node_name).trim(), def);
    }

    public static double toDoubleAttribute(Node node, String node_name) {
        return toDoubleAttribute(node, node_name, 0.0);
    }

    public static double toDoubleAttribute(Node node, String node_name, double def) {
        return NumberUtils.toDouble(getAttributeTextContent(node, node_name).trim(), def);
    }


}
