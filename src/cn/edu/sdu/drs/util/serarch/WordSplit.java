package cn.edu.sdu.drs.util.serarch;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import org.apache.lucene.analysis.*;
import org.mira.lucene.analysis.*;

/**
 * 分词器
 * @author join
 *
 */
public class WordSplit extends Analyzer {

	public WordSplit() {
	}

	public final TokenStream tokenStream(String s, Reader reader) {
		return new d(reader);
	}

	/**
	 * 汉字分词器，【最大匹配算法】
	 * @param str要分的一段话
	 * @return 返回分词后的结果
	 */
	public static ArrayList<String> splitCHN(String str) {
		ArrayList<String> word = new ArrayList<String>();
		MIK_CAnalyzer mik_canalyzer;

		mik_canalyzer = new MIK_CAnalyzer();

		StringReader stringreader = new StringReader(str);

		TokenStream tokenstream = mik_canalyzer.tokenStream("TestField",
				stringreader);
		Token token = null;

		try {
			while ((token = tokenstream.next()) != null) {
				String termText = token.termText();
				word.add(termText);
			}
		} catch (IOException e) {
			System.out.println("错误：汉字分词时出现异常");
			//写到日志
		}
		return word;
	}
	
	/**
	 * 拼音分词器
	 * @param str要分的一段话
	 * @return 返回分词后的结果
	 */
	public static ArrayList<String> splitPINYIN(String str){
		return splitCHN(str);
	}
	
	/**
	 * 英语分词器
	 * @param str要分的一段话
	 * @return 返回分词后的结果
	 */
	public static ArrayList<String> splitENG(String str){
		return splitCHN(str);
	}
	
	/**
	 * 任意分词器
	 * @param str要分的一段话
	 * @return 返回分词后的结果
	 */
	public static ArrayList<String> splitNONE(String str){
		return splitCHN(str);
	}
}