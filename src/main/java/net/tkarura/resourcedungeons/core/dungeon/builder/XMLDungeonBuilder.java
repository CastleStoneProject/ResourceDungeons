package net.tkarura.resourcedungeons.core.dungeon.builder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.Validate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.dungeon.DungeonScript;
import net.tkarura.resourcedungeons.core.dungeon.GenerateOption;
import net.tkarura.resourcedungeons.core.dungeon.ScriptType;
import net.tkarura.resourcedungeons.core.dungeon.UserData;
import net.tkarura.resourcedungeons.core.exception.DungeonLoadException;
import net.tkarura.resourcedungeons.core.server.DungeonLocation;

/**
 * XML形式で格納された情報からダンジョンを生成するビルダークラスです。
 * @author the_karura
 */
public class XMLDungeonBuilder implements DungeonBuilder {
	
	private String id;
	private Dungeon dungeon;
	
	/**
	 * XML形式のファイルからダンジョン情報を読み取ります。
	 * @param stream ストリーム情報
	 * @param file xml形式のファイル
	 * @throws DungeonLoadException 読込中に不正が発生した場合
	 */
	public XMLDungeonBuilder(InputStream stream, File file) throws DungeonLoadException {
		
		// nullチェック
		Validate.notNull(stream, "stream is null.");
		Validate.notNull(file, "directory is null.");
		
		// 一時的なidからクラスを生成
		// 読み取ったidの情報を反映させたクラスはresultメソッドで生成されます。
		this.dungeon = new Dungeon("system.build.xml");
		
		// 
		this.dungeon.setDirectory(file);
		
		try {
			
			// XML読み取りクラスの準備
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(stream);
			
			// 最上位のタグを取得
			Node dungeon_node = document.getDocumentElement();
			
			// <dungeon> タグであるかを確認
			if (!dungeon_node.getNodeName().equalsIgnoreCase("dungeon")) {
				throw new DungeonLoadException("not dungeon file.");
			}
			
			// <dungeon> タグにidの属性があるかを確認
			if (dungeon_node.getAttributes().getNamedItem("id") == null) {
				throw new DungeonLoadException("not define dungeon tag attribute \"id\".");
			}
			
			// idの情報をdungeonとは別でクラスに保管
			this.id = dungeon_node.getAttributes().getNamedItem("id").getNodeValue();
			
			// 登録処理へ
			registorDungeon(dungeon_node);
			
		} catch (ParserConfigurationException | SAXException | IOException | NumberFormatException e) {
			e.printStackTrace();
			throw new DungeonLoadException(e.getLocalizedMessage());
		}
		
	}
	
	private void registorDungeon(Node mother_node) throws DungeonLoadException {
		
		// <dungeon> の子ノードを取得
		NodeList nodes = mother_node.getChildNodes();
		
		// 子ノード全体をループで回します
		for (int i = 0; i < nodes.getLength(); i++) {
			
			Node node = nodes.item(i);
			
			// <name> タグであれば名前を設定
			if (node.getNodeName().equalsIgnoreCase("name")) {
				this.dungeon.setName(node.getTextContent());
			}
			
			// <version> タグであればバージョンを設定
			if (node.getNodeName().equalsIgnoreCase("version")) {
				this.dungeon.setVersion(node.getTextContent());
			}
			
			// <discription> タグであれば説明を設定
			if (node.getNodeName().equalsIgnoreCase("discription")) {
				this.dungeon.setDiscription(node.getTextContent());
			}
			
			// <author> タグであれば製作者を設定
			if (node.getNodeName().equalsIgnoreCase("author")) {
				this.dungeon.addAuthor(registerUser(node));
			}
			
			// <contributor> タグであれば貢献者を設定
			if (node.getNodeName().equalsIgnoreCase("contributor")) {
				this.dungeon.addContributor(registerUser(node));
			}
			
			// <script> タグであればスクリプト情報を追加
			if (node.getNodeName().equalsIgnoreCase("script")) {
				this.dungeon.addScript(registorScript(node));
			}
			
			// <generate> タグであれば生成設定を追加
			if (node.getNodeName().equalsIgnoreCase("generate")) {
				this.dungeon.addGenerate(registorGenerate(node));
			}
			
		}
		
	}
	
	private UserData registerUser(Node node) {
		
		// UUIDを指定
		UserData user = new UserData(node.getTextContent());
		
		// ユーザ名を設定
		if (node.getAttributes().getNamedItem("name") != null) {
			user.setAttribute("name", node.getAttributes().getNamedItem("name").getNodeValue());
		}
		
		// 貢献内容を設定
		if (node.getAttributes().getNamedItem("contribution") != null) {
			user.setAttribute("contribution",
					node.getAttributes().getNamedItem("contribution").getNodeValue());
		}
		
		return user;
		
	}
	
	private DungeonScript registorScript(Node node) {
		
		DungeonScript script;
		
		// TODO 現在の仕様ではtext/javascriptは暫定であるため処理をコメントアウト
		// if (node.getAttributes().getNamedItem("type") != null) {
		//
		// }
		
		// タグにsrc属性があればスクリプトが格納されたファイル指定で
		// タグの中に記述されていた場合その内容がスクリプトになります。
		if (node.getAttributes().getNamedItem("src") != null) {
			
			String script_dir = node.getAttributes().getNamedItem("src").getNodeValue();
			script = new DungeonScript(script_dir, ScriptType.FILE_LOCATION);
			
		} else {
			
			StringBuilder sb = new StringBuilder();
			
			NodeList script_comments = node.getChildNodes();
			
			for (int j = 0; j < script_comments.getLength(); j++) {
				
				Node comment_node = script_comments.item(j);
				
				if (comment_node.getNodeType() == Node.COMMENT_NODE) {
					
					sb.append(comment_node.getTextContent());
				}
				
			}
			
			script = new DungeonScript(sb.toString(), ScriptType.TEXT_CONTENT);
			
		}
		
		return script;
	}
	
	private GenerateOption registorGenerate(Node node) throws DungeonLoadException {
		
		// タグにfunction属性が含まれているかを確認
		if (node.getAttributes().getNamedItem("function") == null)
			throw new DungeonLoadException("generate tag atributte 'function' is not found.");
		
		// 呼び出し関数名設定
		String function = node.getAttributes().getNamedItem("function").getNodeValue();
		double percent = 0.0d;
		
		// 生成率設定
		try {
			
			// 文字の正規表現を確認
			percent = Double.valueOf(node.getAttributes().getNamedItem("percent").getNodeValue());
			
			// 1.0の範囲内を確認
			if (percent > 1.0) {
				throw new NumberFormatException("generate tag atributte percent num range 1.0 over.");
			}
			
		} catch (NumberFormatException e) {
			percent = 0.0d;
		}
		
		GenerateOption option = new GenerateOption(function, percent);
		
		NodeList generate_nodes = node.getChildNodes();
		
		for (int j = 0; j < generate_nodes.getLength(); j++) {
			
			Node generate_node = generate_nodes.item(j);
			
			// <height> 条件に含める高さを追加します。
			if (generate_node.getNodeName().equalsIgnoreCase("height")) {
				registerHeight(generate_node, option);
			}
			
			// <location> 条件に含める位置情報を追加します。
			if (generate_node.getNodeName().equalsIgnoreCase("location")) {
				option.addLocation(registerLocation(generate_node));
			}
			
			// <include> 条件に含める情報を追加します。
			if (generate_node.getNodeName().equalsIgnoreCase("include")) {
				registerInclude(generate_node, option);
			}
			
			// <ignore> 条件から除外する情報を指定します。
			if (generate_node.getNodeName().equalsIgnoreCase("ignore")) {
				registerIgnore(generate_node, option);
			}
		}
		
		return option;
		
	}
	
	private void registerHeight(Node node, GenerateOption option) {
		
		int min;
		int max;
		
		try {
			min = new Integer(node.getAttributes().getNamedItem("min").getTextContent().trim());
			max = new Integer(node.getAttributes().getNamedItem("max").getTextContent().trim());
		} catch (NumberFormatException e) {
			return;
		}
		
		if (min > max) {
			return;
		}
		
		option.addHeight(min, max);
	}
	
	private DungeonLocation registerLocation(Node node) {
		
		String[] nums = node.getTextContent().split("\\,");
		
		int x;
		int y;
		int z;
		
		// 不正な文字列が含まれた場合値を全て0に設定
		try {
			x = Integer.valueOf(nums[0].trim());
			y = Integer.valueOf(nums[1].trim());
			z = Integer.valueOf(nums[2].trim());
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			x = 0;
			y = 0;
			z = 0;
		}
		
		return new DungeonLocation(x, y, z);
	}
	
	private void registerInclude(Node node, GenerateOption option) {
		
		// 情報の種類を確認
		if (node.getAttributes().getNamedItem("type") != null) {
			
			if (node.getAttributes().getNamedItem("type").getNodeValue().equalsIgnoreCase("block")) {
				
				option.addBlock(node.getTextContent());
				
			}
			
			if (node.getAttributes().getNamedItem("type").getNodeValue().equalsIgnoreCase("biome")) {
				
				option.addBiome(node.getTextContent());
				
			}
		}
	}
	
	private void registerIgnore(Node node, GenerateOption option) {
		
		// 情報の種類を確認
		if (node.getAttributes().getNamedItem("type") != null) {
			
			if (node.getAttributes().getNamedItem("type").getNodeValue().equalsIgnoreCase("block")) {
				
				option.removeBlock(node.getTextContent());
				
			}
			
			if (node.getAttributes().getNamedItem("type").getNodeValue().equalsIgnoreCase("biome")) {
				
				option.removeBiome(node.getTextContent());
				
			}
		}
	}
	
	@Override
	public Dungeon getResult() {
		// クラスに保管していたidの情報からダンジョン情報を再生成して返します。
		return new Dungeon(this.dungeon, id);
	}
	
}
