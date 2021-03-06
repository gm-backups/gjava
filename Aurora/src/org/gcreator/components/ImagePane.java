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

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;
import org.gcreator.editors.*;

/**
 *
 * @author  luis
 */
public class ImagePane extends JPanel {

    public ImageEditor editor;
    private Cursor c = null;
    private Cursor c2 = null;
    private int px,  py;
    private int cx,  cy;
    private int sx,  sy,  sw,  sh;
    private boolean drawing = false;
    private boolean kstate = false;
    private boolean resizing = false;
    private boolean dragging = false;
    private Image i = null;

    /** Creates new form ImagePane */
    public ImagePane(ImageEditor _editor) {
        editor = _editor;
        initComponents();
        addMouseListener(new MouseListener() {

            public void mouseExited(MouseEvent evt) {
                if (c != null) {
                    setCursor(c);
                }
            }

            public void mouseEntered(MouseEvent evt) {
                c = getCursor();

                if (editor.jToggleButton1.isSelected() ||
                        editor.jToggleButton2.isSelected() ||
                        editor.jToggleButton3.isSelected() ||
                        editor.jToggleButton4.isSelected()) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                }
            }

            public void mouseReleased(MouseEvent evt) {
                dragging = false;
                if (resizing) {
                    int x = (int) (evt.getX() / getZoom());
                    int y = (int) (evt.getY() / getZoom());

                    if (x < 1 || y < 1) {
                        resizing = false;
                        return;
                    }

                    BufferedImage b = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
                    Graphics g = b.getGraphics();
                    g.drawImage(editor.i.getImage().getImage(), 0, 0, editor.i.image.getImageObserver());
                    editor.i.image = new ImageIcon(b);
            //  ???      editor.g.image = editor.i;
                    repaint();
                    updateUI();
                    resizing = false;
                }
                if (editor.jToggleButton2.isSelected() && drawing) {
                    Graphics g = editor.i.getImage().getImage().getGraphics();
                    Color c = editor.colorSelection2.getBackground();
                    c = new Color(c.getRed(), c.getGreen(), c.getBlue(), (Integer) editor.jSpinner2.getValue());
                    g.setColor(c);
                    //g.drawLine((int) (px * getZoom()), (int) (py*getZoom()), (int) (evt.getX() * getZoom()), (int) (evt.getY() * getZoom()));
                    //g.drawLine(px, py, evt.getX(), evt.getY());
                    g.drawLine((int) (px / getZoom()), (int) (py / getZoom()), (int) (evt.getX() / getZoom()), (int) (evt.getY() / getZoom()));
                    drawing = false;
                    repaint();
                    editor.changed = true;
                }
                if (editor.jToggleButton3.isSelected() && drawing) {
                    Graphics g = editor.i.getImage().getImage().getGraphics();
                    Color c = editor.colorSelection2.getBackground();
                    c = new Color(c.getRed(), c.getGreen(), c.getBlue(), (Integer) editor.jSpinner2.getValue());
                    g.setColor(c);
                    //g.drawLine((int) (px * getZoom()), (int) (py*getZoom()), (int) (evt.getX() * getZoom()), (int) (evt.getY() * getZoom()));
                    //g.drawLine(px, py, evt.getX(), evt.getY());
                    int x1 = px > evt.getX() ? evt.getX() : px;
                    int x2 = px < evt.getX() ? evt.getX() : px;
                    int y1 = py > evt.getY() ? evt.getY() : py;
                    int y2 = py < evt.getY() ? evt.getY() : py;
                    g.drawRect(
                            (int) (x1 / getZoom()), (int) (y1 / getZoom()), (int) ((x2 - x1) / getZoom()), (int) ((y2 - y1) / getZoom()));
                    drawing = false;
                    repaint();
                    editor.changed = true;
                }
                if (editor.jToggleButton4.isSelected() && drawing) {
                    Graphics g = editor.i.getImage().getImage().getGraphics();
                    Color c = editor.colorSelection2.getBackground();
                    c = new Color(c.getRed(), c.getGreen(), c.getBlue(), (Integer) editor.jSpinner2.getValue());
                    g.setColor(c);
                    //g.drawLine((int) (px * getZoom()), (int) (py*getZoom()), (int) (evt.getX() * getZoom()), (int) (evt.getY() * getZoom()));
                    //g.drawLine(px, py, evt.getX(), evt.getY());
                    int x1 = px > evt.getX() ? evt.getX() : px;
                    int x2 = px < evt.getX() ? evt.getX() : px;
                    int y1 = py > evt.getY() ? evt.getY() : py;
                    int y2 = py < evt.getY() ? evt.getY() : py;
                    g.fillRect(
                            (int) (x1 / getZoom()), (int) (y1 / getZoom()),
                            (int) ((x2 - x1) / getZoom()), (int) ((y2 - y1) / getZoom()));
                    drawing = false;
                    repaint();
                    editor.changed = true;
                }
                if (editor.jToggleButton5.isSelected()) {
                    int p1 = sw > 0 ? sx : sw + sx;
                    int p2 = sh > 0 ? sy : sh + sy;
                    int pw = sw > 0 ? sw : -sw;
                    int ph = sh > 0 ? sh : -sh;

                    sx = p1;
                    sy = p2;
                    sw = pw;
                    sh = ph;

                    repaint();
                }
            }

            public void mousePressed(MouseEvent evt) {
                if (kstate) {
                    px = (int) (evt.getX() / getZoom());
                    py = (int) (evt.getY() / getZoom());
                    resizing = true;
                    editor.changed = true;
                } else if (editor.jToggleButton1.isSelected()) {
                    drawPixelIn(evt.getX(), evt.getY(), false);
                    editor.changed = true;
                } else if (editor.jToggleButton2.isSelected()) {
                    px = evt.getX();
                    py = evt.getY();
                    cx = px;
                    cy = py;
                    drawing = true;
                } else if (editor.jToggleButton3.isSelected()) {
                    px = evt.getX();
                    py = evt.getY();
                    cx = px;
                    cy = py;
                    drawing = true;
                } else if (editor.jToggleButton4.isSelected()) {
                    px = evt.getX();
                    py = evt.getY();
                    cx = px;
                    cy = py;
                    drawing = true;
                } else if (editor.jToggleButton5.isSelected()) {
                    int mx = (int) (evt.getX() * getZoom());
                    int my = (int) (evt.getY() * getZoom());
                    if (mx >= sx && my >= sy && mx <= sx + sw && my <= sy + sh) {
                        if (i == null) {
                            i = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
                            Graphics g = i.getGraphics();
                            g.drawImage(
                                    editor.i.getImage().getImage(),
                                    -sx, -sy, editor.i.getImage().getImageObserver());
                            Graphics2D g2 = (Graphics2D) editor.i.getImage().getImage().getGraphics();
                            Composite c = g2.getComposite();
                            g2.setComposite(AlphaComposite.Src);
                            g2.setColor(new Color(255, 255, 255, 0));
                            g2.fillRect(sx, sy, sw, sh);
                            g2.setComposite(c);
                        }
                        px = (int) (evt.getX() / getZoom() - sx);
                        py = (int) (evt.getX() / getZoom() - sy);
                        dragging = true;
                    } else {
                        mergeSelection();
                        sx = (int) (evt.getX() / getZoom());
                        sy = (int) (evt.getY() / getZoom());
                        sw = 0;
                        sh = 0;
                        repaint();
                        requestFocus();
                    }
                }
            }

            public void mouseClicked(MouseEvent evt) {
                requestFocus();
            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            public void mouseMoved(MouseEvent evt) {
                if (editor.jToggleButton5.isSelected()) {
                    int mx = (int) (evt.getX() * getZoom());
                    int my = (int) (evt.getY() * getZoom());
                    if (mx >= sx && my >= sy && mx <= sx + sw && my <= sy + sh) {
                        if (!kstate) {
                            c2 = getCursor();
                            setCursor(new Cursor(Cursor.MOVE_CURSOR));
                            kstate = true;
                        }
                    } else {
                        if (kstate) {
                            setCursor(c2);
                            kstate = false;
                        }
                    }
                }
                if (evt.getX() > getPreferredWidth() - 10 &&
                        evt.getY() > getPreferredHeight() - 10 && evt.getX() < getPreferredWidth() && evt.getY() < getPreferredHeight()) {
                    if (!kstate) {
                        c2 = getCursor();
                        setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                        kstate = true;
                    }
                } else {
                    if (kstate) {
                        setCursor(c2);
                        kstate = false;
                    }
                }
            }

            public void mouseDragged(MouseEvent evt) {
                if (editor.jToggleButton1.isSelected()) {
                    drawPixelIn(evt.getX(), evt.getY(), true);
                    editor.changed = true;
                }
                if (editor.jToggleButton2.isSelected() || editor.jToggleButton3.isSelected() || editor.jToggleButton4.isSelected()) {
                    cx = evt.getX();
                    cy = evt.getY();
                    repaint();
                }
                if (editor.jToggleButton5.isSelected()) {
                    if (!dragging) {
                        sw = (int) (evt.getX() / getZoom());
                        sh = (int) (evt.getY() / getZoom());
                        sw -= sx;
                        sh -= sy;
                        repaint();
                    } else {
                        sx = (int) (evt.getX() / getZoom()) - px;
                        sy = (int) (evt.getY() / getZoom()) - py;
                        repaint();
                    }
                }
            }
        });

        addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent evt) {

                if (evt.isControlDown()) {

                    if (evt.getKeyChar() == 22) {

                        mergeSelection();
                        editor.jToggleButton5.setSelected(true);
                        editor.sel5 = true;
                        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

                        try {
                            if (t != null && t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                                Image img = (Image) t.getTransferData(DataFlavor.imageFlavor);
                                i = img;
                                sx = 0;
                                sy = 0;
                                sw = i.getWidth(null);
                                sh = i.getHeight(null);
                                repaint();
                            }
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                    if (evt.getKeyChar() == 3&&editor.jToggleButton5.isSelected()) {

                        if(i==null){
                            i = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
                            Graphics g = i.getGraphics();
                            g.drawImage(
                                    editor.i.getImage().getImage(),
                                    -sx, -sy, editor.i.getImage().getImageObserver());
                            Graphics2D g2 = (Graphics2D) editor.i.getImage().getImage().getGraphics();
                            Composite c = g2.getComposite();
                            g2.setComposite(AlphaComposite.Src);
                            g2.setColor(new Color(255, 255, 255, 0));
                            g2.fillRect(sx, sy, sw, sh);
                            g2.setComposite(c);
                        }
                        
                        ImageSelection imgSel = new ImageSelection(i);
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel, null);

                    }
                    
                    if (evt.getKeyChar() == 24&&editor.jToggleButton5.isSelected()) {

                        if(i==null){
                            i = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
                            Graphics g = i.getGraphics();
                            g.drawImage(
                                    editor.i.getImage().getImage(),
                                    -sx, -sy, editor.i.getImage().getImageObserver());
                            Graphics2D g2 = (Graphics2D) editor.i.getImage().getImage().getGraphics();
                            Composite c = g2.getComposite();
                            g2.setComposite(AlphaComposite.Src);
                            g2.setColor(new Color(255, 255, 255, 0));
                            g2.fillRect(sx, sy, sw, sh);
                            g2.setComposite(c);
                        }
                        
                        ImageSelection imgSel = new ImageSelection(i);
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel, null);

                        i = null;
                        repaint();
                        
                    }
                }
            }

            public void keyReleased(KeyEvent evt) {
            }

            public void keyTyped(KeyEvent evt) {
            }
        });
    }

    public void drawPixelIn(int x, int y, boolean continuous) {
        int rx = (int) (x / getZoom());
        int ry = (int) (y / getZoom());
        //int rx = x;
        //int ry = y;

        Graphics g = editor.i.getImage().getImage().getGraphics();
        Color c = editor.colorSelection2.getBackground();
        c = new Color(c.getRed(), c.getGreen(), c.getBlue(), (Integer) editor.jSpinner2.getValue());
        g.setColor(c);
        if (!continuous) {
            g.drawRect(rx, ry, 1, 1);
        } else {
            g.drawLine(px, py, rx, ry);
        }
        repaint();

        px = rx;
        py = ry;
    }

    public int getPreferredWidth() {
        return (int) (editor.i.getImage().getIconWidth() * getZoom()) + 10;
    }

    public int getPreferredHeight() {
        return (int) (editor.i.getImage().getIconHeight() * getZoom()) + 10;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getPreferredWidth(),
                getPreferredHeight());
    }

    public double getZoom() {
        switch (editor.i.zoom) {
            case 5:
                return 6;
            case 4:
                return 5;
            case 3:
                return 4;
            case 2:
                return 3;
            case 1:
                return 2;
            case 0:
                return 1;
            case -1:
                return 0.5;
            case -2:
                return 0.33;
            case -3:
                return 0.25;
            case -4:
                return 0.2;
            default:
                return 0.16;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        ImageIcon img = editor.i.getImage();

        Rectangle2D.Float r2d = new Rectangle2D.Float(0, 0, getWidth(), getHeight());
        Graphics2D g2 = (Graphics2D) g;
        Paint oldPaint = g2.getPaint();
        g2.setPaint(editor.bgColor);
        g2.fill(r2d);
        g2.setPaint(oldPaint);
        
        g.drawImage(img.getImage(), 0, 0, (int) (img.getIconWidth() * getZoom()),
                (int) (img.getIconHeight() * getZoom()), img.getImageObserver());

        if (editor.jToggleButton2.isSelected() && drawing) {
            Color c = editor.colorSelection2.getBackground();
            c = new Color(c.getRed(), c.getGreen(), c.getBlue(), (Integer) editor.jSpinner2.getValue());
            g.setColor(c);
            g.drawLine(px, py, cx, cy);
        }
        if (editor.jToggleButton3.isSelected() && drawing) {
            Color c = editor.colorSelection2.getBackground();
            c = new Color(c.getRed(), c.getGreen(), c.getBlue(), (Integer) editor.jSpinner2.getValue());
            g.setColor(c);
            g.drawRect(px < cx ? px : cx, py < cy ? py : cy, px < cx ? cx - px : px - cx, py < cy ? cy - py : py - cy);
        }
        if (editor.jToggleButton4.isSelected() && drawing) {
            Color c = editor.colorSelection2.getBackground();
            c = new Color(c.getRed(), c.getGreen(), c.getBlue(), (Integer) editor.jSpinner2.getValue());
            g.setColor(c);
            g.fillRect(px < cx ? px : cx, py < cy ? py : cy, px < cx ? cx - px : px - cx, py < cy ? cy - py : py - cy);
        }

        Stroke s = ((Graphics2D) g).getStroke();

        int p1 = sw > 0 ? sx : sw + sx;
        int p2 = sh > 0 ? sy : sh + sy;
        int pw = sw > 0 ? sw : -sw;
        int ph = sh > 0 ? sh : -sh;

        if (i != null) {
            g.drawImage(i, p1, p2, null);
        }
        g.setColor(Color.darkGray);
        ((Graphics2D) g).setStroke(
                new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{2f},
                0f));

        if (editor.jToggleButton5.isSelected()) {
            g.drawRect(p1, p2, pw, ph);
        }

        ((Graphics2D) g).setStroke(s);

        g.setColor(Color.black);
        g.fillRect(getPreferredWidth() - 10, getPreferredHeight() - 10, 10, 10);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public static class ImageSelection implements Transferable {

        private Image image;

        public ImageSelection(Image image) {
            this.image = image;
        }

        // Returns supported flavors
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }

        // Returns true if flavor is supported
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        // Returns image
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!DataFlavor.imageFlavor.equals(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return image;
        }
    }

    public void mergeSelection() {
        if (i != null) {
            Graphics g = editor.i.getImage().getImage().getGraphics();
            g.drawImage(i, sx, sy, sw, sh, null);
            i = null;
            repaint();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
