package net.tkarura.resourcedungeons.core.util;

import java.io.File;
import java.io.FileFilter;

/**
 * ファイルに関する便利な機能を取り扱うクラス
 * @author the_karura
 */
public final class FileHandler {
	
	private FileHandler() {}
	
	/**
	 * ディレクトリフィルターです。
	 * ディレクトリのみを条件に含めます。
	 */
	public final static FileFilter FOLDER_FILTER = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	};
	
	/**
	 * JavaScriptフィルターです。
	 * JavaScript拡張子を持つファイルのみを条件に含めます。
	 */
	public final static FileFilter JAVA_SCRIPT_FILTER = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith("js");
		}
	};

	public static final FileFilter XML_FILTER = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith("xml");
		}
	};
	
}
