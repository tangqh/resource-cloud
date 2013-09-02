package cn.edu.sdu.drs.util.serarch;

import java.io.*;
import java.util.*;

public class Dictionary {

	public static String a = "/org/mira/lucene/analysis/dict/noise.dic";
	public static String b = "/org/mira/lucene/analysis/dict/number.dic";
	public static String c = "/org/mira/lucene/analysis/dict/wordbase.dic";
	private static Dictionary d = null;
	private HashSet e;
	private HashSet f;
	private HashSet g;
	private HashMap h;
	private HashMap i;

	public static Dictionary a() {
		if (d == null)
			d = new Dictionary();
		return d;
	}

	private Dictionary() {
		e = null;
		f = null;
		g = null;
		h = null;
		i = null;
		b();
		c();
		d();
	}

	private void b() {
		InputStream inputstream;
		inputstream = getClass().getResourceAsStream(a);
		e = new HashSet(64);
		Object obj = null;
		try {
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(inputstream), 512);
			String s;
			do
				if ((s = bufferedreader.readLine()) != null) {
					s = s.trim();
					e.add(s);
				}
			while (s != null);
		} catch (IOException ioexception) {
			System.err.println("噪声词库载入异常.");
			//写到日志
		}

		try {
			if (inputstream != null)
				inputstream.close();
		} catch (Exception e) {
		}
		return;

	}

	public final boolean a(String s) {
		return e.contains(s.trim());
	}

	public final boolean a(char c1) {
		return a(Character.toString(c1));
	}

	private void c() {
		InputStream inputstream;
		inputstream = getClass().getResourceAsStream(b);
		f = new HashSet(64);
		Object obj = null;
		try {
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(inputstream), 512);
			String s;
			do
				if ((s = bufferedreader.readLine()) != null)
					f.add(s.trim());
			while (s != null);
		} catch (IOException ioexception) {
			System.err.println("序数词库载入异常.");
			//写到日志
		}

		try {
			if (inputstream != null)
				inputstream.close();
		} catch (Exception e) {
		}
	}

	public final boolean b(char c1) {
		return f.contains(Character.toString(c1));
	}

	private void d() {
		InputStream inputstream;
		inputstream = getClass().getResourceAsStream(c);
		g = new HashSet(1024);
		h = new HashMap(256);
		i = new HashMap(256);
		Object obj = null;
		try {
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(inputstream), 512);
			String s;
			do
				if ((s = bufferedreader.readLine()) != null) {
					s = s.trim();
					if (g.add(s.trim())) {
						String s1 = s.substring(0, 1);
						ArrayList arraylist;
						if ((arraylist = (ArrayList) h.get(s1)) == null) {
							arraylist = new ArrayList();
							h.put(s1, arraylist);
						}
						arraylist.add(s);
						String s2 = s.substring(s.length() - 1);
						ArrayList arraylist1;
						if ((arraylist1 = (ArrayList) i.get(s2)) == null) {
							arraylist1 = new ArrayList();
							i.put(s2, arraylist1);
						}
						arraylist1.add(s);
					}
				}
			while (s != null);
		} catch (IOException ioexception) {
			System.err.println("分词词库载入异常.");
			//写到日志
		}

		try {
			if (inputstream != null)
				inputstream.close();
		} catch (Exception e) {
		}
	}

	public final int b(String s) {
		if (g.contains(s.trim()))
			return 0;
		String s1 = s.substring(0, 1);
		ArrayList arraylist;
		if ((arraylist = (ArrayList) h.get(s1)) != null) {
			String s2;
			for (Iterator iterator = arraylist.iterator(); iterator.hasNext();)
				if ((s2 = (String) iterator.next()).startsWith(s))
					return 1;

		}
		return -1;
	}

	public final int c(String s) {
		if (g.contains(s.trim()))
			return 0;
		String s1 = s.substring(s.length() - 1);
		ArrayList arraylist;
		if ((arraylist = (ArrayList) i.get(s1)) != null) {
			String s2;
			for (Iterator iterator = arraylist.iterator(); iterator.hasNext();)
				if ((s2 = (String) iterator.next()).endsWith(s))
					return 1;

		}
		return -1;
	}

	public static void main(String args[]) {
		a();
	}

}
