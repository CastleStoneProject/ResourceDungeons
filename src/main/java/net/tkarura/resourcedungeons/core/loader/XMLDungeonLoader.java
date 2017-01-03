package net.tkarura.resourcedungeons.core.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.dungeon.DungeonUser;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.exception.DungeonLoadException;

public class XMLDungeonLoader extends FileDungeonLoader {

    @Override
    public IDungeon loadDungeon(InputStream is) throws DungeonLoadException {

	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	Document document;

	try {

	    // XML解析のパーサーを取得
	    factory = DocumentBuilderFactory.newInstance();
	    builder = factory.newDocumentBuilder();
	    document = builder.parse(is);
	    Node node_dungeon = getDungeonNode(document.getChildNodes());
	    
	    // dungeonタグのエラーチェック
	    validateDungeonNode(node_dungeon);

	    // ダンジョン情報の生成
	    this.dungeon = new Dungeon(node_dungeon.getAttributes().getNamedItem("id").getNodeValue());
	    this.dungeon.setSupport(node_dungeon.getAttributes().getNamedItem("support").getNodeValue());
	    this.dungeon.setDirectory(dir);

	    loadParameters(node_dungeon);

	} catch (SAXException | IOException | ParserConfigurationException e) {

	    throw new DungeonLoadException(e.getMessage());

	}

	return this.dungeon;
    }
    
    private Node getDungeonNode(NodeList list) throws DungeonLoadException {
	
	Node node;
	
	for (int i = 0; i < list.getLength(); i++) {
	    
	    node = list.item(i);
	    
	    if (node.getNodeName() != null && node.getNodeName().equalsIgnoreCase("dungeon")) {
		
		return node;
		
	    }
	    
	}
	
	return null;
    }

    private void validateDungeonNode(Node node) throws DungeonLoadException {

	if (node != null) {
	    
	    if (!node.getNodeName().equals("dungeon")) {
		this.logs.add(Level.SEVERE, "NotFound dungeon Tag.");
	    }
	    
	    if (node.getAttributes().getNamedItem("id") == null) {
		this.logs.add(Level.SEVERE, "NotFound dungeon id Attribute.");
	    }
	    
	    if (node.getAttributes().getNamedItem("support") == null) {
		this.logs.add(Level.SEVERE, "NotFound dungeon support Attribute.");
	    }
	    
	} else {
	    
	    this.logs.add(Level.SEVERE, "Not Found Dungeon Tag.");
	    
	}

	if (this.logs.isErrorLog()) {
	    throw new DungeonLoadException();
	}

    }

    private void loadParameters(Node node) {

	Node chiled;

	for (int i = 0; i < node.getChildNodes().getLength(); i++) {

	    chiled = node.getChildNodes().item(i);

	    // <name>の解析と読み取り
	    if (chiled.getNodeName().equalsIgnoreCase("name")) {

		if (validateNotUseAttribute(chiled)) {

		    this.dungeon.setName(chiled.getTextContent());

		}

	    }

	    // <version>の解析と読み取り
	    if (chiled.getNodeName().equalsIgnoreCase("version")) {

		if (validateNotUseAttribute(chiled)) {

		    this.dungeon.setVersion(chiled.getTextContent());

		}

	    }

	    // <discription>の解析と読み取り
	    if (chiled.getNodeName().equalsIgnoreCase("discription")) {

		if (validateNotUseAttribute(chiled)) {

		    this.dungeon.setDiscription(chiled.getTextContent());

		}

	    }

	    // <author>の解析と読み取り
	    if (chiled.getNodeName().equalsIgnoreCase("author")) {

		if (validateUserNode(chiled)) {

		    this.dungeon.addAuthor(loadUser(chiled));

		}

	    }

	    // <contributor>の解析と読み取り
	    if (chiled.getNodeName().equalsIgnoreCase("contributor")) {

		if (validateUserNode(chiled)) {

		    this.dungeon.addContributor(loadUser(chiled));

		}

	    }
	    
	}

    }

    private boolean validateNotUseAttribute(Node node) {

	// タグにある属性のチェックを行います。
	// 使用していない属性名があれば注意項目に追加します。
	Node attribute;

	for (int i = 0; i < node.getAttributes().getLength(); i++) {

	    attribute = node.getAttributes().item(i);

	    this.logs.add(Level.WARNING, node.getNodeName() + " value of " + attribute.getNodeName() + " is not used.");

	}

	return true;
    }

    private boolean validateUserNode(Node node) {

	String uuid_string = node.getTextContent();

	Node attribute;

	for (int i = 0; i < node.getAttributes().getLength(); i++) {

	    attribute = node.getAttributes().item(i);

	    if (attribute.getNodeName().equalsIgnoreCase("name")) {
		continue;
	    }

	    if (attribute.getNodeName().equalsIgnoreCase("contribution")) {
		continue;
	    }

	    this.logs.add(Level.WARNING, "<" + node.getNodeName() + "> value of " + attribute.getNodeName() + " is not used.");

	}

	try {

	    // uuidに有効な文字列かを確認します。
	    UUID.fromString(uuid_string);

	} catch (IllegalArgumentException e) {
	    this.logs.add(Level.SEVERE, "<" + node.getNodeName() + "> " + e.getLocalizedMessage());
	    return false;
	}

	return true;
    }
    
    private DungeonUser loadUser(Node node) {
	
	DungeonUser user = new DungeonUser(UUID.fromString(node.getTextContent()));
	
	if (node.getAttributes().getNamedItem("name") != null) {
	    user.setParameter("name", node.getAttributes().getNamedItem("name").getNodeValue());
	}
	
	if (node.getAttributes().getNamedItem("contribution") != null) {
	    user.setParameter("contribution", node.getAttributes().getNamedItem("contribution").getNodeValue());
	}
	
	return user;
    }

}
