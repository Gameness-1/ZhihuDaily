package com.zhihu.net.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.netease.vopen.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 替换HTML字符.
	 */
	public static String htmlDecoder(String src) throws Exception {

		if (src == null || src.equals("")) {
			return "";
		}

		String dst = src;
		dst = replaceAll(dst, "&lt;", "<");
		dst = replaceAll(dst, "&rt;", ">");
		dst = replaceAll(dst, "&quot;", "\"");
		dst = replaceAll(dst, "&039;", "'");
		dst = replaceAll(dst, "&nbsp;", " ");
		dst = replaceAll(dst, "&nbsp", " ");
		dst = replaceAll(dst, "<br>", "\n");
		dst = replaceAll(dst, "\r\n", "\n");
		dst = replaceAll(dst, "&#8826;", "??");
		dst = replaceAll(dst, "&#8226;", "??");
		dst = replaceAll(dst, "&#9642;", "??");
		return dst;
	}

	public static String replaceAll(String src, String fnd, String rep)
			throws Exception {

		if (src == null || src.equals("")) {
			return "";
		}

		String dst = src;

		int idx = dst.indexOf(fnd);

		while (idx >= 0) {
			dst = dst.substring(0, idx) + rep
					+ dst.substring(idx + fnd.length(), dst.length());
			idx = dst.indexOf(fnd, idx + rep.length());
		}

		return dst;
	}

	public static boolean checkStr(String str) {

		boolean _is = false;

		if (null != str && !"".equals(str)) {
			_is = true;
		}

		return _is;

	}

	public static boolean checkObj(Object obj) {
		boolean _is = false;

		if (null != obj) {
			_is = true;
		}

		return _is;
	}

	public static JSONArray StringToJsonArray(String str) {

		if (null != str) {
			try {
				JSONArray jsonArray = new JSONArray(str);
				return jsonArray;
			} catch (JSONException e) {
				return null;
			}
		} else {

			return null;
		}
	}

	public static String getNameFromPath(String path) {
		if (!TextUtils.isEmpty(path)) {
			return path.substring(path.lastIndexOf("/") + 1);
		}
		return null;
	}

	/**
	 * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
	 */
	static final char DBC_CHAR_START = 33; // 半角!

	/**
	 * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
	 */
	static final char DBC_CHAR_END = 126; // 半角~

	/**
	 * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
	 */
	static final char SBC_CHAR_START = 65281; // 全角！

	/**
	 * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
	 */
	static final char SBC_CHAR_END = 65374; // 全角～

	/**
	 * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
	 */
	static final int CONVERT_STEP = 65248; // 全角半角转换间隔

	/**
	 * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
	 */
	static final char SBC_SPACE = 12288; // 全角空格 12288

	/**
	 * 半角空格的值，在ASCII中为32(Decimal)
	 */
	static final char DBC_SPACE = ' '; // 半角空格

	/**
	 * 半角转全角字符
	 *
	 * @param src
	 * @return
	 */
	public static String bj2qj(String src) {
		if (src == null) {
			return src;
		}
		StringBuilder buf = new StringBuilder(src.length());
		char[] ca = src.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			if (ca[i] == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代
				buf.append(SBC_SPACE);
			} else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符
				buf.append((char) (ca[i] + CONVERT_STEP));
			} else { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
				buf.append(ca[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * <PRE>
	 * 全角字符->半角字符转换
	 * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
	 * </PRE>
	 */
	public static String qj2bj(String src) {
		if (src == null) {
			return src;
		}
		StringBuilder buf = new StringBuilder(src.length());
		char[] ca = src.toCharArray();
		for (int i = 0; i < src.length(); i++) {
			if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
				buf.append((char) (ca[i] - CONVERT_STEP));
			} else if (ca[i] == SBC_SPACE) { // 如果是全角空格
				buf.append(DBC_SPACE);
			} else { // 不处理全角空格，全角！到全角～区间外的字符
				buf.append(ca[i]);
			}
		}
		return buf.toString();
	}

	/**
	 *
	 */
	public static String toString(String str)
	{
		return str == null ? "" : str;
	}

	public static String nullStr(String str){

		return str;
	}

	public static boolean startsWithIgnoreCase(String str,int offset,String anObject){

		int length = anObject.length();

		//待比较字串,大于 str字串
		if(offset +  length > str.length() ){
			return false;
		}

		int idx = 0;

		while (idx < length) {
			char c = str.charAt(offset + idx);
			if(c >= 'A' && c <= 'Z'){		//大写字母
				c += 32;
			}
			if(c != anObject.charAt(idx)){
				break;
			}else{
				idx ++;
			}
		}
		if(idx == length && idx > 0){
			return true;
		}
		return false;
	}

	/**
	 * replace old string part with new ones
	 *
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return the replaced String
	 */
	public static String replace(String str, String oldStr, String newStr) {

		int preIndex = 0;
		int index = 0;
		StringBuffer buffer = new StringBuffer();
		index = str.indexOf(oldStr, preIndex);
		while (index >= 0) {
			buffer.append(str.substring(preIndex, index));
			buffer.append(newStr);
			preIndex = index + oldStr.length();
			index = str.indexOf(oldStr, preIndex);
		}
		if (preIndex < str.length()) {
			buffer.append(str.substring(preIndex));
		}
		return buffer.toString();
	}

	/**
	 * 获取HttpLink
	 * @return
	 */
	public static String getHttpLink(String str, int offset) {
        String URLCharTable = "!#$%&'()*+,-./:;=?@[\\]^_`{|}~";
        int len = 0;
		if (startsWithIgnoreCase(str, offset, "http://")) {
			len = "http://".length();
		}
		else if (startsWithIgnoreCase(str, offset, "www.")) {
			len = "www.".length();
		}
		else if (startsWithIgnoreCase(str, offset, "wap.")) {
			len = "wap.".length();
		}
		else if (startsWithIgnoreCase(str, offset, "https://")) {
			len = "https://".length();
		}
		else {
			return null;
		}

		int strLen = str.length();

		while (offset + len < strLen) {
			char c = str.charAt(offset + len);
			if ((c >= 'A' && c <= 'Z') // 'a' - 'z'
					|| (c >= 'a' && c <= 'z') // 'A' - 'Z'
					|| (c >= '0' && c <= '9')) { // '0' - '9'
				len++;
			} else {
				if (URLCharTable.indexOf(c) >= 0) {
					len++;
				} else {
					break;
				}
			}
		}

		return str.substring(offset, offset + len);
	}

	/**
	 * 复制Hashtable
	 *
	 * @param table
	 * @return
	 */
	public static Hashtable cloneHashtable(Hashtable table)
	{
		if (table != null)
		{
			Hashtable dest = new Hashtable();
			Enumeration keys = table.keys();
			while (keys.hasMoreElements())
			{
				Object obj = keys.nextElement();
				dest.put(obj, table.get(obj));
			}
			return dest;
		} else
		{
			return null;
		}
	}

	/**
	 * Returns true if the string is null or 0-length.
	 * @param str the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(CharSequence str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}




	//拼接参数
	public static String appendUrl(String url, Map<String, String> params){
		StringBuilder builder = new StringBuilder(url);
		boolean isFirst = true;
		for(String key : params.keySet()){
			if(key != null && params.get(key) != null){
				if(isFirst){
					isFirst = false;
					builder.append("?");
				}else {
					builder.append("&");
				}
				builder.append(key)
						.append("=")
						.append(params.get(key));
			}
		}

		return builder.toString();
	}

	//数量格式（取整）
	public static String countFormat(int count){
		String countStr;
		if(count >= 100000000){
			countStr = count / 100000000 + "亿";
		}else if(count >= 10000){
			countStr = count / 10000 + "万";
		}else {
			countStr = count + "";
		}

		return countStr;
	}



	/**
	 * 搜索高亮
	 * ##高亮部分##
	 */
	private static final String SPLIT_STR = "##";

	public static SpannableString searchHighlight(Context context, String str){
		SpannableString spanString = new SpannableString(str.replaceAll(SPLIT_STR, ""));

		int start = 0;
		int highlightColor = context.getResources().getColor(
				R.color.search_match_highlight);
		while (start < str.length()){
			int wordStart = str.indexOf(SPLIT_STR, start);
			if(wordStart == -1){
				break;
			}
			int wordEnd = str.indexOf(SPLIT_STR, wordStart + SPLIT_STR.length());
			if(wordEnd == -1){
				break;
			}

			str = str.replaceFirst(SPLIT_STR, "");
			str = str.replaceFirst(SPLIT_STR, "");
			spanString.setSpan(new ForegroundColorSpan(highlightColor),
					wordStart, wordEnd - SPLIT_STR.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			start = wordEnd - SPLIT_STR.length();
		}

		return spanString;
	}

	public static boolean isColorStr(String str){
		Pattern pattern = Pattern.compile("#[0-9a-fA-F]{6,8}$");
		Matcher matcher = pattern.matcher(str);

		return matcher.matches();
	}
}
