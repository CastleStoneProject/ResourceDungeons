package net.tkarura.resourcedungeons.core.loader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.tkarura.resourcedungeons.core.dungeon.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
			this.dungeon = new DungeonImpl(node_dungeon.getAttributes().getNamedItem("id").getNodeValue());
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
			String node_name = chiled.getNodeName();

			// <name>の解析と読み取り
			if (node_name.equalsIgnoreCase("name")) {

				if (validateNotUseAttribute(chiled)) {

					this.dungeon.setName(chiled.getTextContent());

				}

			}

			// <version>の解析と読み取り
			if (node_name.equalsIgnoreCase("version")) {

				if (validateNotUseAttribute(chiled)) {

					this.dungeon.setVersion(chiled.getTextContent());

				}

			}

			// <description>の解析と読み取り
			if (node_name.equalsIgnoreCase("description")) {

				if (validateNotUseAttribute(chiled)) {

					this.dungeon.setDiscription(chiled.getTextContent());

				}

			}

			// <author>の解析と読み取り
			if (node_name.equalsIgnoreCase("author")) {

				if (validateUserNode(chiled)) {

					this.dungeon.addAuthor(loadUser(chiled));

				}

			}

			// <contributor>の解析と読み取り
			if (node_name.equalsIgnoreCase("contributor")) {

				if (validateUserNode(chiled)) {

					this.dungeon.addContributor(loadUser(chiled));

				}

			}

			// <generate>の解析と読み取り
			if (node_name.equalsIgnoreCase("generate")) {

				if (validateGenerateNode(chiled)) {

				    this.dungeon.addGenerateOption(loadGenerate(chiled));

				}

			}

			if (node_name.equalsIgnoreCase("script")) {

			    this.dungeon.addScript(loadScript(chiled));

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

	private boolean validateGenerateNode(Node node) {

	    if (node.getAttributes().getNamedItem("function") == null) {
	        logs.add(Level.SEVERE, "Not Found Function Attribute.");
	        return false;
        }

        if (node.getAttributes().getNamedItem("percent") == null) {
	        logs.add(Level.WARNING, "Not Found Percent Attribute. default value setting. "
					+ DungeonGenerateOption.DEFAULT_PERCENT);
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

	private DungeonGenerateOption loadGenerate(Node node) {

	    DungeonGenerateOption option = new DungeonGenerateOption(node.getAttributes().getNamedItem("function").getNodeValue());
	    Node attribute = node.getAttributes().getNamedItem("percent");

	    try {

	        if (attribute != null) {
                option.setPercent(new Float(attribute.getNodeValue()));
            }

        } catch (NumberFormatException e) {
            logs.add(Level.SEVERE, "Format Error <generate> tag attribute percent value.");
        }

		Node child;

		for (int i = 0; i < node.getChildNodes().getLength(); i++) {

			child = node.getChildNodes().item(i);

			if (child.getNodeName().equalsIgnoreCase("block")) {

				Node x_node = child.getAttributes().getNamedItem("x");
				Node y_node = child.getAttributes().getNamedItem("y");
				Node z_node = child.getAttributes().getNamedItem("z");

				int x = x_node != null && x_node.getNodeValue() != null ? formatInteger(x_node.getNodeValue()) : 0;
				int y = y_node != null && y_node.getNodeValue() != null ? formatInteger(y_node.getNodeValue()) : 0;
				int z = z_node != null && z_node.getNodeValue() != null ? formatInteger(z_node.getNodeValue()) : 0;

				String block_id = child.getTextContent().trim();

				if (!block_id.equals("")) {
					option.includeBlock(block_id, x, y, z);
				} else {
					logs.add(Level.SEVERE, "Format Error <block> tag content value.");
				}
			}

			if (child.getNodeName().equalsIgnoreCase("biome")) {

				String biome_id = child.getTextContent().trim();

				if (!biome_id.equals("")) {
					option.includeBiome(biome_id);
				} else {
					logs.add(Level.SEVERE, "Format Error <biome> tag content value");
				}
			}

		}

	    return option;
    }

    private IDungeonScript loadScript(Node node) {

	    Node type_node =  node.getAttributes().getNamedItem("type");
	    Node src_node = node.getAttributes().getNamedItem("src");

	    if (type_node != null) {
	        if (type_node.getNodeValue() != null) {
	            if (!type_node.getNodeValue().equalsIgnoreCase("text/javascript")) {
	                logs.add(Level.WARNING, "text/javascript");
                }
            } else {
	            logs.add(Level.WARNING, "missing value of type attribute.");
            }
        } else {
	        logs.add(Level.WARNING, "missing type attribute.");
        }

	    // src属性がある場合
	    if (src_node != null && src_node.getNodeValue() != null) {

	        // ディレクトリ情報を生成
	        File dir = new File(this.dir, src_node.getNodeValue());

	        // 存在しないディレクトリを指定した時の警告
	        if (!dir.exists()) {
	            logs.add(Level.WARNING, "directory is not exit. " + dir.getPath());
            }

            // ファイルではないディレクトリを指定した時の警告
            if (!dir.isFile()) {
	            logs.add(Level.WARNING, "specified directory.");
            }

            // 読み取りが出来ないファイルを指定した時の警告
            if (!dir.canRead()) {
	            logs.add(Level.WARNING, "file can not read.");
            }

            // ディレクトリ情報を指定して生成
	        return new DungeonScriptFile(dir);

        }

        // テキスト情報の抽出
        String text = node.getTextContent() != null ? node.getTextContent(): "";

	    // 空のテキストを指定した場合の警告
        if (text.isEmpty()) {
	        logs.add(Level.WARNING, "text is empty.");
        }

        // テキスト情報を指定して生成
        return new DungeonScriptText(text);

	}

    private int formatInteger(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
