package cn.edu.sdu.drs.service.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 用于获取远程资源的inputStream的类
 * 
 * @author join
 * 
 */
public class GetRemoteResource {
	private GetRemoteResource() {
	}

	/**
	 * 获取远程资源的inputStream
	 * 
	 * @param path
	 *            远程资源的URL
	 * @return 远程资源的inputStream,不存在则返回null;
	 */
	public static InputStream getInputStream(String path) {

		path = URLProcessor.process(path);
		HttpURLConnection httpUrl = null;
		InputStream in = null;
		URL url;
		try {
			url = new URL(path);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			in = httpUrl.getInputStream();
		} catch (FileNotFoundException e) {
			return null;
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return in;
	}
}
