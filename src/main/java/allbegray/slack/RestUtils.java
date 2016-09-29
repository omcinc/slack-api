package allbegray.slack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import allbegray.slack.exception.SlackException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

public abstract class RestUtils {

	private static Log logger = LogFactory.getLog(RestUtils.class);

	public static HttpEntity createUrlEncodedFormEntity(Map<String, String> parameters) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>(parameters.size());
		for (Entry<String, String> entry : parameters.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8"));
	}

	public static HttpEntity createMultipartFormEntity(Map<String, String> parameters, InputStream is) {
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));

		multipartEntityBuilder.addBinaryBody("file", is, ContentType.create("application/octet-stream"), "file");
		for (Entry<String, String> entry : parameters.entrySet()) {
			multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue());
		}
		return multipartEntityBuilder.build();
	}

	public static CloseableHttpClient createHttpClient(int timeout) {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();
		return httpClient;
	}

	public static String execute(CloseableHttpClient httpClient, String url, HttpEntity httpEntity) {
		logger.info("url : " + url);

		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(httpEntity);
			String retStr = httpClient.execute(httpPost, new StringResponseHandler());
			
			logger.debug("return : " + retStr);
			
			return retStr;
		} catch (IOException e) {
			throw new SlackException(e);
		}
	}

}
