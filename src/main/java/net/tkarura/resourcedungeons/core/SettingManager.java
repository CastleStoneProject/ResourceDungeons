package net.tkarura.resourcedungeons.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * ResourceDungeonsの動作に関わる設定情報を管理するクラスです。
 * 
 * @author the_karura
 */
public final class SettingManager {
	
	// 定数
	
	public final static String DEFAULT_ENCODE = "utf-8";
	
	public final static String SETTINGS_DIR = "ResourceDungeons";
	public final static String SETTING_DIR = SETTINGS_DIR + "setting.xml";
	public final static String DUNGEONS_DIR = SETTINGS_DIR + "\\" + "Dungeons";
	public final static String SCRIPTS_DIR = SETTINGS_DIR + "\\" + "scripts";
	
	public final static String NODE_RESOURCEDUNGEONS = "ResourceDungeons";
	public final static String NODE_SETTINGS = "Settings";
	public final static String NODE_SETTINGS_DIRECTORY = "SettingsDirectory";
	public final static String NODE_DUNGEONS_DIRECTORY = "DungeonsDirectory";
	public final static String NODE_SCRIPTS_DIRECTORY = "ScriptsDirectory";
	public final static String NODE_GENERATE_WORLDS = "GenerateWorlds";
	public final static String NODE_GENERATE_WORLD = "World";
	public final static String NODE_IGNORE_BLOCKS = "IgnoreBlocks";
	public final static String NODE_IGNORE_BLOCK = "Block";
	public final static String NODE_IGNORE_ENTITYS = "IgnoreEntitys";
	public final static String NODE_IGNORE_ENTITY = "Entity";
	
	// クラスインスタンス
	private static SettingManager instance = new SettingManager();
	
	// ## ディレクトリ設定 ## // 
	
	private File setting_file = new File(SETTING_DIR);
	
	private File settings_dir = new File(SETTINGS_DIR);
	private File dungeons_dir = new File(DUNGEONS_DIR);
	private File scripts_dir = new File(SCRIPTS_DIR);
	
	// ## 生成設定 ## // 
	
	private boolean enable_auto_generate = false; // 自動生成機能
	private List<String> generate_worlds = new ArrayList<String>(); // 生成するワールド
	private List<String> deny_generate_block = new ArrayList<String>(); // 生成しないブロック
	private List<String> deny_generate_entity = new ArrayList<String>(); // 生成しない実体
	
	private SettingManager() {}
	
	/**
	 * クラスが読み取る設定構成ファイルを設定します。
	 * 他のsetterメソッドと異なり{@link #init()}による初期化はされません
	 * @param setting_file
	 */
	public void setSettingFile(File file) {
		this.setting_file = file;
	}
	
	public void setSettingDirectory(File dir) {
		this.settings_dir = dir;
	}
	
	public void setDungeonDirectory(File dir) {
		this.dungeons_dir = dir;
	}
	
	public void setScriptDirectory(File dir) {
		this.scripts_dir = dir;
	}
	
	public void addGenerateWorld(String world) {
		this.generate_worlds.add(world);
	}
	
	public void removeGenerateWorld(String world) {
		this.generate_worlds.remove(world);
	}
	
	public void addDenyGenerateBlockID(String id) {
		this.deny_generate_block.add(id);
	}
	
	public void removeDenyGenerateBlock(String id) {
		this.deny_generate_block.remove(id);
	}
	
	public void addDenyGenerateEntityID(String id) {
		this.deny_generate_entity.add(id);
	}
	
	public void removeDenyGenerateEntity(String id) {
		this.deny_generate_entity.remove(id);
	}
	
	/**
	 * 設定構成を初期化します。
	 */
	public void init() {
		
		this.enable_auto_generate = false;
		this.deny_generate_block.clear();
		this.deny_generate_entity.clear();
		
	}
	
	public void load() {
		
		try {
			
			// 設定ファイルが存在しない場合ファイルを生成します。
			if (!this.setting_file.exists()) {
				createSettingXMLFile();
			}
			
			// 設定ファイルから構成を読み込み
			load(this.setting_file);
			
		} catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
			
			e.printStackTrace();
			
			// 再初期化
			init();
			
		}
		
	}
	
	private void createSettingXMLFile() throws IOException, SAXException, ParserConfigurationException, TransformerException {
		
		// ディレクトリとファイルを生成
		this.setting_file.getParentFile().mkdirs();
		this.setting_file.createNewFile();
		
		// XML出力の為のファイルを生成
		save();
		
	}
	
	// 指定したファイルから設定を読み取ります。
	public void load(File file) throws SAXException, IOException, ParserConfigurationException {
		
		// XML読み取りクラスの準備
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		
		if (!document.getChildNodes().item(0).getNodeName().equalsIgnoreCase(NODE_RESOURCEDUNGEONS)) {
			return;
		}
		
		NodeList resourceDungeonsNodes = document.getChildNodes().item(0).getChildNodes();
		
		for (int i = 0; i < resourceDungeonsNodes.getLength(); i++) {
			
			// <ResourceDungeons>の子ノードを検索します。
			Node resourceDungeonsNode = resourceDungeonsNodes.item(i);
			
			if (resourceDungeonsNode.getNodeName().equalsIgnoreCase(NODE_SETTINGS)) {
				
				NodeList settingNodes = resourceDungeonsNode.getChildNodes();
				
				for (int j = 0; j < settingNodes.getLength(); j++) {
					
					Node settingNode = settingNodes.item(j);
					
					switch (settingNode.getNodeName()) {
					
					// <SettingDirectory>
					case NODE_SETTINGS_DIRECTORY: {
						this.settings_dir = new File(settingNode.getAttributes().getNamedItem("src").getNodeValue());
					}
					break;
					
					// <DungeonDirectory>
					case NODE_DUNGEONS_DIRECTORY: {
						this.dungeons_dir = new File(settingNode.getAttributes().getNamedItem("src").getNodeValue());
					}
					break;
					
					case NODE_SCRIPTS_DIRECTORY: {
						this.scripts_dir = new File(settingNode.getAttributes().getNamedItem("src").getNodeValue());
					}
					
					// <GenerateWorlds>
					case NODE_GENERATE_WORLDS: {
						
						// <World>
						NodeList generateWorldsNodes = settingNode.getChildNodes();
						
						for (int k = 0; k < generateWorldsNodes.getLength(); k++) {
							
							Node generateWorldsNode = generateWorldsNodes.item(k);
							if (generateWorldsNode.getNodeName().equalsIgnoreCase(NODE_GENERATE_WORLD)) {
								
								// 生成するワールドを追加します。
								this.generate_worlds.add(generateWorldsNode.getTextContent());
								
							}
						}
						
					}
					break;
					
					// <IgnoreBlocks>
					case NODE_IGNORE_BLOCKS: {
						
						// <World>
						NodeList ignoreBlocksNodes = settingNode.getChildNodes();
						
						for (int k = 0; k < ignoreBlocksNodes.getLength(); k++) {
							
							Node ignoreBlocksNode = ignoreBlocksNodes.item(k);
							if (ignoreBlocksNode.getNodeName().equalsIgnoreCase(NODE_IGNORE_BLOCK)) {
								
								// 禁止にするブロックを追加します。
								this.deny_generate_block.add(ignoreBlocksNode.getTextContent());
								
							}
						}
						
					}
					break;
					
					// <IgnoreEntitys>
					case NODE_IGNORE_ENTITYS: {
						
						// <World>
						NodeList ignoreEntitysNodes = settingNode.getChildNodes();
						
						for (int k = 0; k < ignoreEntitysNodes.getLength(); k++) {
							Node ignoreEntitysNode = ignoreEntitysNodes.item(k);
							if (ignoreEntitysNode.getNodeName().equalsIgnoreCase(NODE_IGNORE_ENTITY)) {
								
								// 禁止にする実体を追加します。
								this.deny_generate_block.add(ignoreEntitysNode.getTextContent());
								
							}
						}
						
					}
					break;
					
					}
				}
			}
		}
		
	}
	
	/**
	 * 設定構成をファイルに保管します。
	 * @throws TransformerException ファイル出力の為の変換中に例外が発生した場合
	 * @throws SAXException xml解析中に例外が発生した場合
	 * @throws IOException 出力中に例外が発生した場合
	 * @throws ParserConfigurationException xml解析の為の処理が用意出来ない場合
	 */
	public void save() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		save(this.setting_file);
	}
	
	/**
	 * 設定構成を指定したファイルに保管します。
	 * @param file 保管するファイル
	 * @throws TransformerException ファイル出力の為の変換中に例外が発生した場合
	 * @throws SAXException xml解析中に例外が発生した場合
	 * @throws IOException 出力中に例外が発生した場合
	 * @throws ParserConfigurationException xml解析の為の処理が用意出来ない場合
	 */
	public void save(File file) throws TransformerException, SAXException, IOException, ParserConfigurationException {
		input(file, getDocument());
	}
	
	// 設定構成情報をファイルから取得します。
	private Document getDocument() throws SAXException, IOException, ParserConfigurationException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		DOMImplementation impolementation = builder.getDOMImplementation();
		Document document = impolementation.createDocument("", NODE_RESOURCEDUNGEONS, null);
		
		// <ResourceDungeons>
		Element resourceDungeonsElement = document.getDocumentElement();
		resourceDungeonsElement.setAttribute("version", ResourceDungeons.VERSION);
		
		// <Settings>
		Element settingsElement = document.createElement(NODE_SETTINGS);
		resourceDungeonsElement.appendChild(settingsElement);
		
		// <SettingsDirectory>
		Element settingsDirectoryElement = document.createElement(NODE_SETTINGS_DIRECTORY);
		settingsDirectoryElement.setAttribute("src", this.settings_dir.getPath());
		settingsElement.appendChild(settingsDirectoryElement);
		
		// <DungeonsDirectory>
		Element dungeonsDirectoryElement = document.createElement(NODE_DUNGEONS_DIRECTORY);
		dungeonsDirectoryElement.setAttribute("src", this.dungeons_dir.getPath());
		settingsElement.appendChild(dungeonsDirectoryElement);
		
		// <ScriptsDirectory>
		Element scriptsDirectoryElement = document.createElement(NODE_SCRIPTS_DIRECTORY);
		scriptsDirectoryElement.setAttribute("src", this.scripts_dir.getPath());
		settingsElement.appendChild(scriptsDirectoryElement);
		
		// <GenerateWorlds>
		Element generateWorldsElement = document.createElement(NODE_GENERATE_WORLDS);
		for (String world : this.generate_worlds) {
			Element GenerateWorldElement = document.createElement(NODE_GENERATE_WORLD);
			GenerateWorldElement.setTextContent(world);
			generateWorldsElement.appendChild(GenerateWorldElement);
		}
		settingsElement.appendChild(generateWorldsElement);
		
		// <IgnoreBlocks>
		Element ignoreBlocksElement = document.createElement(NODE_IGNORE_BLOCKS);
		for (String block : this.deny_generate_block) {
			Element ignoreBlockElement = document.createElement(NODE_IGNORE_BLOCK);
			ignoreBlockElement.setTextContent(block);
			ignoreBlocksElement.appendChild(ignoreBlockElement);
		}
		settingsElement.appendChild(ignoreBlocksElement);
		
		// <IgnoreEntitys>
		Element ignoreEntitysElement = document.createElement(NODE_IGNORE_ENTITYS);
		for (String entity : this.deny_generate_entity) {
			Element ignoreEntityElement = document.createElement(NODE_IGNORE_ENTITY);
			ignoreEntityElement.setTextContent(entity);
			ignoreEntitysElement.appendChild(ignoreEntityElement);
		}
		settingsElement.appendChild(ignoreEntitysElement);
		
		return document;
		
	}
	
	// 設定構成情報をファイルに出力します。
	private void input(File file, Document document) throws TransformerException {
		
		// 変数を用意
		String encode = document.getXmlEncoding();
		TransformerFactory transformer_factory = TransformerFactory.newInstance();
		Transformer transformer = transformer_factory.newTransformer();
		
		// 出力設定
		transformer.setOutputProperty("indent", "yes");
		transformer.setOutputProperty("encoding", encode != null ? encode : DEFAULT_ENCODE);
		
		// ファイルへ出力
		transformer.transform(new DOMSource(document), new StreamResult(file));
		
	}
	
	/**
	 * クラスを呼び出します。
	 * @return クラス唯一のインスタンス
	 */
	public static SettingManager getInstance() {
		return instance;
	}
	
	/**
	 * 設定構成を保管するディレクトリを返します。
	 * ファイルを返す場合は{@link #getSettingFile()}を使用してください。
	 * @return 設定構成を保管するディレクトリ
	 */
	public File getSettingDirectory() {
		return this.settings_dir;
	}
	
	/**
	 * ResourceDungeonsの設定構成を管理するファイルを返します。
	 * @return 設定構成ファイル
	 */
	public File getSettingFile() {
		return this.dungeons_dir;
	}
	
	/**
	 * ダンジョン情報を参照するディレクトリを返します。
	 * @return ダンジョン情報を参照するディレクトリ
	 */
	public File getDungeonsDirectory() {
		return this.dungeons_dir;
	}
	
	/**
	 * スクリプト情報を参照するディレクトリを返します。
	 * @return スクリプト情報を参照するディレクトリ
	 */
	public File getScriptsDirectory() {
		return this.scripts_dir;
	}
	
	/**
	 * 自動生成が許可されているかを確認します。
	 * @return 自動生成が許可されている場合 true を返します。
	 */
	public boolean isAutoGenerate() {
		return this.enable_auto_generate;
	}
	
	/**
	 * 指定したワールド名が生成可能なワールド名であるかを確認します。
	 * @param world_name ワールド名
	 * @return 生成可能なワールドであれば true を返します。
	 */
	public boolean isGenerateWorld(String world_name) {
		return this.generate_worlds.contains(world_name);
	}
	
	/**
	 * 指定したブロックIDが使用禁止であるかを確認します。
	 * @param block_id ブロックID
	 * @return 使用禁止の場合 true を返します。
	 */
	public boolean isDenyBlockID(String block_id) {
		return this.deny_generate_block.contains(block_id);
	}
	
	/**
	 * 指定したEntityのIDが使用禁止であるかを確認します。
	 * @param entity_id EntityID
	 * @return 使用禁止の場合 true を返します。
	 */
	public boolean isDenyEntityID(String entity_id) {
		return this.deny_generate_entity.contains(entity_id);
	}
	
}
