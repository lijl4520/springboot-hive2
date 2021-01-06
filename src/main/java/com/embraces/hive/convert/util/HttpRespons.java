//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.embraces.hive.convert.util;

import java.util.Vector;

public class HttpRespons {
    String urlString;
    int defaultPort;
    String file;
    String host;
    String path;
    int port;
    String protocol;
    String query;
    String ref;
    String userInfo;
    String contentEncoding;
    String content;
    String contentType;
    int code;
    String message;
    String method;
    int connectTimeout;
    int readTimeout;
    Vector<String> contentCollection;

    public HttpRespons() {
    }

    public String getContent() {
        return this.content;
    }

    public String getContentType() {
        return this.contentType;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Vector<String> getContentCollection() {
        return this.contentCollection;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public String getMethod() {
        return this.method;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public String getUrlString() {
        return this.urlString;
    }

    public int getDefaultPort() {
        return this.defaultPort;
    }

    public String getFile() {
        return this.file;
    }

    public String getHost() {
        return this.host;
    }

    public String getPath() {
        return this.path;
    }

    public int getPort() {
        return this.port;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getQuery() {
        return this.query;
    }

    public String getRef() {
        return this.ref;
    }

    public String getUserInfo() {
        return this.userInfo;
    }
}
