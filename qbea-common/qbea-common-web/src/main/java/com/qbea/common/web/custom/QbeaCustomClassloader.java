package com.qbea.common.web.custom;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 */
public class QbeaCustomClassloader extends URLClassLoader {
    public QbeaCustomClassloader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public QbeaCustomClassloader(URL[] urls) {
        super(urls);
    }

    public QbeaCustomClassloader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }
}
