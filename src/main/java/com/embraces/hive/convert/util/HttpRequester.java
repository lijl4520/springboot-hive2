//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.embraces.hive.convert.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class HttpRequester {
    private String defaultContentEncoding = Charset.defaultCharset().name();

    public HttpRequester() {
    }

    public com.embraces.hive.convert.util.HttpRespons sendGet(String urlString) throws IOException {
        return this.send(urlString, "GET", (Map)null, (Map)null);
    }

    public com.embraces.hive.convert.util.HttpRespons sendGet(String urlString, Map<String, String> params) throws IOException {
        return this.send(urlString, "GET", params, (Map)null);
    }

    public com.embraces.hive.convert.util.HttpRespons sendGet(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
        return this.send(urlString, "GET", params, propertys);
    }

    public com.embraces.hive.convert.util.HttpRespons sendPost(String urlString) throws IOException {
        return this.send(urlString, "POST", (Map)null, (Map)null);
    }

    public com.embraces.hive.convert.util.HttpRespons sendPost(String urlString, Map<String, String> params) throws IOException {
        return this.send(urlString, "POST", params, (Map)null);
    }

    public com.embraces.hive.convert.util.HttpRespons sendPost(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
        return this.send(urlString, "POST", params, propertys);
    }

    private com.embraces.hive.convert.util.HttpRespons send(String urlString, String method, Map<String, String> parameters, Map<String, String> propertys) throws IOException {
        HttpURLConnection urlConnection = null;
        String key;
        Iterator var9;
        if (method.equalsIgnoreCase("GET") && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;

            for(var9 = parameters.keySet().iterator(); var9.hasNext(); ++i) {
                key = (String)var9.next();
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }

                param.append(key).append("=").append((String)parameters.get(key));
            }

            urlString = urlString + param;
        }

        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        if (propertys != null) {
            Iterator var13 = propertys.keySet().iterator();

            while(var13.hasNext()) {
                 key = (String)var13.next();
                urlConnection.addRequestProperty(key, (String)propertys.get(key));
            }
        }

        if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            var9 = parameters.keySet().iterator();

            while(var9.hasNext()) {
                key = (String)var9.next();
                param.append("&");
                param.append(key).append("=").append((String)parameters.get(key));
            }

            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    private com.embraces.hive.convert.util.HttpRespons makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
        com.embraces.hive.convert.util.HttpRespons httpResponser = new com.embraces.hive.convert.util.HttpRespons();

        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            httpResponser.contentCollection = new Vector();
            StringBuffer temp = new StringBuffer();

            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                httpResponser.contentCollection.add(line);
                temp.append(line).append("\r\n");
            }

            bufferedReader.close();
            String ecod = urlConnection.getContentEncoding();
            if (ecod == null) {
                ecod = this.defaultContentEncoding;
            }

            httpResponser.urlString = urlString;
            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
            httpResponser.file = urlConnection.getURL().getFile();
            httpResponser.host = urlConnection.getURL().getHost();
            httpResponser.path = urlConnection.getURL().getPath();
            httpResponser.port = urlConnection.getURL().getPort();
            httpResponser.protocol = urlConnection.getURL().getProtocol();
            httpResponser.query = urlConnection.getURL().getQuery();
            httpResponser.ref = urlConnection.getURL().getRef();
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();
            httpResponser.content = new String(temp.toString().getBytes(), ecod);
            httpResponser.contentEncoding = ecod;
            httpResponser.code = urlConnection.getResponseCode();
            httpResponser.message = urlConnection.getResponseMessage();
            httpResponser.contentType = urlConnection.getContentType();
            httpResponser.method = urlConnection.getRequestMethod();
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
            httpResponser.readTimeout = urlConnection.getReadTimeout();
            com.embraces.hive.convert.util.HttpRespons var10 = httpResponser;
            return var10;
        } catch (IOException var13) {
            throw var13;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

        }
    }

    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }
}
