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

import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.tree.*;

import org.gcreator.components.impl.*;
import org.gcreator.core.*;
import org.gcreator.fileclass.Folder;
import org.gcreator.fileclass.GFile;
import org.gcreator.fileclass.Group;
import org.gcreator.units.*;

/**
 * @author Luís Reis
 */
public class WorkspaceTree extends JTree {

    public WorkspaceTree(TreeNode root) {
        super(root);
        setScrollsOnExpand(true);
        setDragEnabled(true);
        setDropMode(DropMode.ON_OR_INSERT);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        // Tree drag and drop support

        setDropMode(DropMode.ON_OR_INSERT);

        setTransferHandler(new TransferHandler() {

            protected Transferable createTransferable(JComponent c) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
                if (!(node instanceof ObjectNode)) {
                    return null;
                }
                ObjectNode f = (ObjectNode) node;
                if (f.object instanceof org.gcreator.fileclass.GFile) {
                    //  System.out.println("" + ((org.gcreator.fileclass.GFile) f.object).name);
                    return ((org.gcreator.fileclass.GFile) f.object);
                } else if (f.object instanceof org.gcreator.fileclass.Group) {
                    return ((org.gcreator.fileclass.Group) f.object);
                } else {
                    return null;
                }
            }

            public int getSourceActions(JComponent c) {
                return MOVE;
            }

            public boolean canImport(TransferHandler.TransferSupport support) {
                // System.out.println(""+support);
                TreePath drop = ((JTree.DropLocation) support.getDropLocation()).getPath();
                if (drop == null) {
                    return false;
                }
                if (!(drop.getLastPathComponent() instanceof ObjectNode)) {
                    return false;
                }
                ObjectNode dropNode = (ObjectNode) drop.getLastPathComponent();
                ObjectNode dragNode = (ObjectNode) ((JTree) support.getComponent()).getLastSelectedPathComponent();
                //if (dragNode == dropNode) return false;
                //if (dragNode.isNodeDescendant(dropNode)) return false;

                if(!dragNode.object.allowDrag())
                    return false;
                if (dropNode.object instanceof org.gcreator.fileclass.Group) {
                    if (dragNode.object instanceof GFile) {
                        if (((org.gcreator.fileclass.Group) dropNode.object).allowsFileType(((org.gcreator.fileclass.GFile) dragNode.object).type)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (dragNode.object instanceof Group) {
                        if (((org.gcreator.fileclass.Group) dropNode.object).allowsGroup((org.gcreator.fileclass.Group) dragNode.object)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }

                return false;
            }

            public boolean importData(TransferHandler.TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                JTree.DropLocation drop = (JTree.DropLocation) support.getDropLocation();
                int dropIndex = drop.getChildIndex();
                ObjectNode dropNode = (ObjectNode) drop.getPath().getLastPathComponent();
                ObjectNode dragNode = (ObjectNode) ((JTree) support.getComponent()).getLastSelectedPathComponent();
                if (dropIndex == -1) {
                    dropIndex = dropNode.getChildCount();
                }
                if (dropNode == dragNode.getParent() && dropIndex > dragNode.getParent().getIndex(dragNode)) {
                    dropIndex--;
                }
                if (dragNode.object instanceof GFile) {
                    ((GFile) dragNode.object).root.remove(dragNode.object);
                } else if (dragNode.object instanceof Group) {
                    ((Group) dragNode.object).root.remove(dragNode.object);
                }
                ((Folder) dropNode.object).add(dragNode.object);
                dropNode.insert(dragNode, dropIndex);
                expandPath(new TreePath(dropNode.getPath()));
                updateUI();
                return true;

            }
        });
        setCellRenderer(new WorkspaceCellRenderer());
        expandRow(0);
    }
}
