/*
 * Copyright (C) 2007-2008 Luís Reis <luiscubal@gmail.com>
 * Copyright (C) 2007-2008 TGMG <thegamemakerguru@hotmail.com>
 * Copyright (C) 2008 Serge Humphrey <bob@bobtheblueberry.com>
 * 
 * This file is part of G-Creator.
 * G-Creator is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for more details.
 */
package org.gcreator.fileclass;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.gcreator.fileclass.res.Resource;

/**
 *
 * @author Luís
 */
public class GFile extends GObject implements Transferable {

    private static final long serialVersionUID = 1;
    public static final DataFlavor NODE_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "Node");
    private static DataFlavor[] flavors = {NODE_FLAVOR};
    public transient Folder root;
    /**
     * ONLY TO BE USED BY IOManager!
     */
    public transient String _savetype = "";
    public transient boolean _read = false;
    public String type; //If file is "a.txt", leave only "txt" here
    public transient org.gcreator.components.TabPanel tabPanel;//Used to kill any tabpanel when this is deleted. 
    public java.lang.Object value = null;
    //public ImageIcon treeimage;
    public String xml = ""; // the data xml used to load
    private static final ObjectStreamField[] serialPersistentFields = {/*new ObjectStreamField("root", Folder.class),*/new ObjectStreamField("type", String.class),
        new ObjectStreamField("value", java.lang.Object.class)
    };

    public GFile(Folder root, String name, String type, GObject value) {
        this(root, name, type, value, false);
    }

    public GFile(Folder root, String name, String type, GObject value, boolean artificial) {
        super(name);
        this.root = root;
        this.type = type;
        this.value = value;
        root.add(this);
        if (!artificial) {
            getProject().addFile(this);
        }
    }

    private GFile(String name, String type) {
        this(null, name, type, null);
    }

    @Override
    public String getObjectType() {
        return "File";
    }

    public int getID() {
        return getProject().getIdFor(this);
    }

    /*private class MyOutputStream extends ImageOutputStreamImpl {
    public ZipOutputStream out;
    public MyOutputStream(ZipOutputStream out) {
    this.out = out;
    }
    public void write(byte[] barray, int a, int b) throws IOException {
    for (int i = a; i < b; i++) {
    }
    }
    public void write(byte[] barray) throws IOException {
    for (int i = 0; i < barray.length; i++) {
    out.write(barray[i]);
    }
    }
    public void write(int a) throws IOException {
    out.write((byte) a);
    }
    public int read(byte[] barray, int a, int b) {
    return 0;
    }
    public int read(int a) {
    return 0;
    }
    public int read() {
    return 0;
    }
    }*/
    public void writeToBuffer(ZipOutputStream out) throws IOException {




        if (value != null) {
            if (value instanceof String) {
                out.write(value.toString().getBytes());
            } //else if (value instanceof AudioClip) {
            //            System.out.println("audio clip");
            //            AudioClip ac = (AudioClip)value;
            //            
            //        }
            else if (value instanceof ImageIcon) {
                ImageIcon img = ((ImageIcon) value);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                BufferedImage ii = null;
                Image imgg = null;
                try {
                    imgg = img.getImage();
                    if (imgg.getClass().getName().equals("sun.awt.image.ToolkitImage")) {
                        System.out.println("Toolkit");
                        ii = (BufferedImage) imgg.getClass().getMethod("getBufferedImage").invoke(imgg);
                    } else {
                        ii = (BufferedImage) (img.getImage());
                    }
                } catch (Exception e) { //CLass not found?
                    System.out.println("Exception " + e.getMessage());
                    ii = (BufferedImage) (imgg);
                }

                ImageIO.write(ii, type, baos);
                out.write(baos.toByteArray());
            } else {//if (value instanceof org.gcreator.fileclass.res.Resource) {
                ObjectOutput s = new ObjectOutputStream(out);
                s.writeObject(value);
                s.flush();
            //out.write(((org.gcreator.fileclass.res.Resource) value).writeXml().getBytes());
            }
        }
    }

    public static ImageIcon getScaledIcon(ImageIcon ii) {
        ImageIcon iii = new ImageIcon();
        if (ii != null) {
            Image i = ii.getImage().getScaledInstance(16, 16, BufferedImage.SCALE_DEFAULT);
            iii.setImage(i);
            return iii;
        }
        return null;
    }

    @Override
    public GObject clone() {
        GFile o = new GFile(name, type);
        if (value instanceof Resource) {
            o.value = ((Resource) value).clone();
        } else if (value instanceof ImageIcon) {
            o.value = value;
        } else {
            o.value = value;
        }
        return null;//(Object)o;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor == NODE_FLAVOR;
    }

    @Override
    public GObject getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor != NODE_FLAVOR) {
            throw new UnsupportedFlavorException(flavor);
        }
        return this;
    }

    @Override
    public String getPath() {
        return root.getPath() + "/" + super.getPath();
    }

    @Override
    public Project getProject() {

        return root.getProject();
    }

    @Override
    public String toString() {
        return "" + this.getClass() + "@" + this.hashCode() + "\t Folder: " + this.root + "\tType: " + this.type + "\t Value: " + this.value;
    }
}
