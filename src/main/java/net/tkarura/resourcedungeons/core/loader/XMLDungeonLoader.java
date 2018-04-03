package net.tkarura.resourcedungeons.core.loader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.tkarura.resourcedungeons.core.dungeon.*;
import net.tkarura.resourcedungeons.core.util.DOMUtils;
import org.w3c.dom.*;

import net.tkarura.resourcedungeons.core.exception.DungeonLoadException;

public class XMLDungeonLoader extends FileDungeonLoader {

	@Override
	public IDungeon loadDungeon(InputStream is) throws DungeonLoadException {

		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		Document document;

		DungeonImpl dungeon;

		String id;
		String support;
		String description;
		String name;
		String version;
		List<DungeonUser> users;
		List<IDungeonScript> scripts;
		List<DungeonGenerateOption> options;

		try {

			// XML解析のパーサーを取得
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.parse(is);

            // ルートノードの取得
			Element root = document.getDocumentElement();

			// ダンジョン基本情報の設定
			id = getDungeonId(root);
			support = root.getAttribute("support");

			// ダンジョン情報の生成
			dungeon = new DungeonImpl(id);
			dungeon.setSupport(support);
			dungeon.setDirectory(dir);

			// 視覚的なパラメータを設定
			name = getNodeContent(root, "name");
			description = getNodeContent(root, "description");
			version = getNodeContent(root, "version");

			dungeon.setDescription(description);
			dungeon.setName(name);
			dungeon.setVersion(version);

			// author タグ
			users = getNodeUserList(root, "author");
			for (DungeonUser user : users) {
				dungeon.addAuthor(user);
			}

			// contributor タグ
			users = getNodeUserList(root, "contributor");
			for (DungeonUser user : users) {
				dungeon.addContributor(user);
			}

			// script タグ
			scripts = getNodeScriptList(root, "script");
			for (IDungeonScript script : scripts) {
				dungeon.addScript(script);
			}

			// generate タグ
			options = getNodeGenerateList(root, "generate");
			for (DungeonGenerateOption option : options) {
				dungeon.addGenerateOption(option);
			}

		} catch (Exception e) {
			throw new DungeonLoadException(e.getLocalizedMessage());
		}

		return dungeon;
	}

	private String getDungeonId(Node node) throws DungeonLoadException {

	    Node attribute = node.getAttributes().getNamedItem("id");

	    if (attribute == null) {
            throw new DungeonLoadException("dungeon id の指定がありません");
        }

        String id = attribute.getTextContent();

	    if (id.trim().equals("")) {
	        throw new DungeonLoadException("dungeon id の指定を空白には出来ません。");
        }

        return id;
    }

	private String getNodeContent(Node node, String node_name) {

		String description = "";
		NodeList children =  node.getChildNodes();

		for (int i = 0; i < children.getLength() ; i++) {

			Node child = children.item(i);

			if (!DOMUtils.isMatchNodeName(child, node_name)) {
				continue;
			}

			description = child.getTextContent();
		}

		return description;
	}

	private List<DungeonUser> getNodeUserList(Node node, String node_name) {

		List<DungeonUser> list = new ArrayList<>();

		NodeList children = node.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {

			Node child = children.item(i);

			if (!DOMUtils.isMatchNodeName(child, node_name)) {
				continue;
			}

			try {

                UUID uuid = UUID.fromString(child.getTextContent());
                DungeonUser user = new DungeonUser(uuid);

                NamedNodeMap attribute = child.getAttributes();

                for (int j = 0; j < attribute.getLength(); j++) {
                    Node param = attribute.item(j);
                    user.setParameter(param.getNodeName(), param.getTextContent());
                }

                list.add(user);

            } catch (IllegalArgumentException e) {
			    logs.add(Level.WARNING, "無効なUUIDを指定しています。");
            }

		}

		return list;
	}

	private List<IDungeonScript> getNodeScriptList(Node node, String node_name) {

		List<IDungeonScript> list = new ArrayList<>();

		NodeList children = node.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {

			Node child = children.item(i);

			if (!DOMUtils.isMatchNodeName(child, node_name)) {
				continue;
			}

			NamedNodeMap attribute = child.getAttributes();
			IDungeonScript script = null;

			if (attribute.getNamedItem("src") != null) {

			    File src_dir = new File(dir, attribute.getNamedItem("src").getTextContent());

                if (!src_dir.exists()) {
			        logs.add(Level.WARNING, "存在しないファイルを指定しています。");
                }

                if (src_dir.isDirectory()) {
                    logs.add(Level.WARNING, "ディレクトリを指定しています。");
                }

				script = new DungeonScriptFile(src_dir);

			} else {

			    NodeList list_ = child.getChildNodes();
                StringBuilder script_text = new StringBuilder();

			    for (int j = 0; j < list_.getLength(); j++) {

			        Node child_ = list_.item(j);

                    if (child_.getNodeType() == Node.TEXT_NODE || child_.getNodeType() == Node.COMMENT_NODE) {
                        script_text.append(child_.getTextContent());
                    }

                }

                if (script_text.toString().trim().equals("")) {
			        continue;
                }

                script = new DungeonScriptText(script_text.toString());

			}

			if (script != null) {
				list.add(script);
			}

		}

		return list;
	}

	private List<DungeonGenerateOption> getNodeGenerateList(Node node, String node_name) {

		List<DungeonGenerateOption> list = new ArrayList<>();

		NodeList children = node.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {

			Node child = children.item(i);

			if (!DOMUtils.isMatchNodeName(child, node_name)) {
				continue;
			}

			String function_name = child.getAttributes().getNamedItem("function").getTextContent();
			double percent = DOMUtils.toDoubleAttribute(child, "percent", DungeonGenerateOption.DEFAULT_PERCENT);
			DungeonGenerateOption option = new DungeonGenerateOption(function_name);
			option.setPercent(percent);

			NodeList children_ = child.getChildNodes();

			for (int j = 0; j < children_.getLength(); j++) {

				Node child_ = children_.item(j);

				if (DOMUtils.isMatchNodeName(child_, "biome")) {
					option.includeBiome(child_.getTextContent());
				}

				if (DOMUtils.isMatchNodeName(child_, "block")) {

					int x = DOMUtils.toIntAttribute(child_,"x", 0);
                    int y = DOMUtils.toIntAttribute(child_,"y", 0);
                    int z = DOMUtils.toIntAttribute(child_,"z", 0);

                    option.includeBlock(child_.getTextContent(), x, y, z);
				}

			}

			list.add(option);

		}

		return list;
	}

}
