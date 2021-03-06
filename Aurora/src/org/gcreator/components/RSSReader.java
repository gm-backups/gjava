/*
 * Copyright (C) 2007-2008 Luís Reis <luiscubal@gmail.com>
 * Copyright (C) 2007-2008 TGMG <thegamemakerguru@hotmail.com>
 * Copyright (C) 2008 Serge Humphrey <bob@bobtheblueberry.com>
 * 
 * This file is part of G-Creator.
 * G-Creator is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for more details.
 */
package org.gcreator.components;

import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.gcreator.extended.StringInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author  Luís
 */
public class RSSReader extends javax.swing.JList {

    private static final long serialVersionUID = 1;
    private boolean loaded = false;
    private boolean failed = true;
    protected ArrayList<RSSEntry> rssEntries = new ArrayList<RSSEntry>(12);

    /** Creates new form RSSReader
     * @param url The URL to download the RSS entries from.
     */
    public RSSReader(final String url) {
        setModel(new AbstractListModel() {

            private static final long serialVersionUID = 1;

            @Override
            public int getSize() {
                return rssEntries.size();
            }

            @Override
            public Object getElementAt(int index) {
                return rssEntries.get(index);
            }
        });
        initComponents();
        Thread t = new Thread() {

            @Override
            public void run() {

                try {
                    URL _url = new URL(url);
                    InputStream r = _url.openStream();
                    BufferedInputStream br = new BufferedInputStream(r);
                    String s = "";
                    int i = 0;
                    while ((i = br.read()) != -1) {
                        if (i != '\n' && i != '\r') {
                            s += (char) i;
                        }
                    }
                    r.close();

                    DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder;
                    Document doc;

                    builder = fact.newDocumentBuilder();
                    doc = builder.parse(new StringInputStream(s));
                    Node node = doc.getDocumentElement();
                    String root = node.getNodeName();
                    if (!root.equals("rss")) {
                        throw new Exception("Invalid root " + root);
                    }
                    NodeList childs = node.getChildNodes();
                    Node child;
                    if (childs.getLength() != 1) {
                        throw new Exception("Invalid number of channels");
                    }
                    child = childs.item(0);
                    String name = child.getNodeName();
                    if (!name.equals("channel")) {
                        throw new Exception("Invalid");
                    }
                    childs = child.getChildNodes();
                    //  System.out.println(childs.getLength());
                    for (int j = 0; i < childs.getLength(); j++) {
                        child = childs.item(j);
                        if (child != null) {
                            name = child.getNodeName();
                            if (!name.equals("item")) {
                                continue;
                            }
                            parseItem(child);
                        } else {
                            break;
                        }
                    }

                    failed = false;
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
                loaded = true;
                updateUI();
            }
        };
        t.start();
    }

    public void parseItem(Node child) {
        if (child == null) {
            return;
        }
        NodeList childs = child.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            Node c = childs.item(i);
        }
        rssEntries.add(new RSSEntry(childs.item(1).getTextContent(), childs.item(3).getTextContent()));
    }

    @Override
    public void paint(Graphics g) {
        if (!loaded) {
            g.setFont(getFont());
            g.drawString("Loading RSS...", 10, 30);
        } else if (failed) {
            g.setFont(getFont());
            g.drawString("Failed to retrieve RSS", 10, 30);
        } else {
            super.paint(g);
        }
    }

    public class RSSEntry {

        public final String title;
        public final String url;

        public RSSEntry(String title, String url) {
            this.title = title;
            this.url = url;
        }

        @Override
        public String toString() {
            return "<html>" + title + "</html>";
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
