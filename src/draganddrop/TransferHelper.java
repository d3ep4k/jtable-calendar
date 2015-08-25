/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draganddrop;

import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import static javax.swing.TransferHandler.MOVE;
import javax.swing.TransferHandler.TransferSupport;
import javax.swing.table.TableColumn;

/**
 *
 * @author Kainix
 */
public class TransferHelper extends TransferHandler {

    private static final long serialVersionUID = 1L;

    public TransferHelper() {
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent source) {
        // Create the transferable
        // Because I'm hacking a little, I've included the source table...
        JTable table = (JTable) source;
        return new CellDataTransferable(new CellData(table));
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
    }

    @Override
    public boolean canImport(TransferSupport support) {
        // Reject the import by default...
        boolean canImport = false;
        // Can only import into another JTable
        Component comp = support.getComponent();
        if (comp instanceof JTable) {
            JTable table = (JTable) comp;
            // Need the location where the drop might occur
            DropLocation dl = support.getDropLocation();
            Point dp = dl.getDropPoint();
            // Get the column at the drop point
            int dragColumn = table.columnAtPoint(dp);
            try {
                // Get the Transferable, we need to check
                // the constraints
                Transferable t = support.getTransferable();
                CellData cd = (CellData) t.getTransferData(CellDataTransferable.CELL_DATA_FLAVOR);
                // Make sure we're not dropping onto ourselves...
                if (cd.getTable() != table) {
                    // Do the columns match...?
                    canImport = true;
                    if (dragColumn == cd.getColumn()) {
                    }
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        }
        return canImport;
    }

    @Override
    public boolean importData(TransferSupport support) {
        // Import failed for some reason...
        boolean imported = false;
        // Only import into JTables...
        Component comp = support.getComponent();
        if (comp instanceof JTable) {
            JTable target = (JTable) comp;
            // Need to know where we are importing to...
            DropLocation dl = support.getDropLocation();
            Point dp = dl.getDropPoint();
            int dropCol = target.columnAtPoint(dp);
            int dropRow = target.rowAtPoint(dp);
            try {
                // Get the Transferable at the heart of it all
                Transferable t = support.getTransferable();
                CellData cd = (CellData) t.getTransferData(CellDataTransferable.CELL_DATA_FLAVOR);
                if (cd.getTable() != target) {
                    // Make sure the columns match
//                    if (dropCol == cd.getColumn()) {
                    // Get the data from the "dropped" table
                    Object exportValue = target.getValueAt(dropRow, dropCol);
                    // Get the data from the "dragged" table
                    int table = cd.getColumn();
                    TableColumn column = cd.getTable().getColumnModel().getColumn(table);
//                    table.getS
                    Object importValue = cd.getValue();
                    String stringValue = importValue.toString();
                    int length = Integer.valueOf(importValue.toString().substring(stringValue.length() - 1, stringValue.length()));
                    // This is where we swap the values...
                    // Set the target/dropped tables value
                    if (dropCol != 0) {
                        String valueAt = (String) target.getValueAt(dropRow, dropCol);
                        System.out.println(valueAt);
                        if ((dropCol + length - 1) < target.getColumnCount()) {

                            System.out.println("passed length constrain");
                            System.out.println("Value at:" + valueAt + "?");
                            if (valueAt == "") {
//                                isOutOfBounds(length, dropRow, dropCol, target);
                                for (int i = 0; i < length; i++) {
                                    if (target.getValueAt(0, dropCol + i) == "") {
                                        target.setValueAt(new ImageIcon(), 0, dropCol + i);
                                    }
                                }
                                if (collides(length, dropRow, dropCol, target)) {
                                    JOptionPane.showMessageDialog(null, "This slot clahes with one of the alloted slot");
                                    return false;
                                } else {
                                    if (length == 0) {
                                        for (int i = 1; i < target.getColumnCount(); i++) {
                                            if (target.getValueAt(0, dropCol + i) == "") {
                                                target.setValueAt(new ImageIcon(), 0, dropCol + i);
                                            }
                                                target.setValueAt(new ImageIcon(), dropRow, i);
                                        }
                                    } else {
                                        for (int i = 0; i < length; i++) {
                                        System.out.println(target.getBounds());
                                            target.setValueAt(new ImageIcon(getClass().getResource("/resources/" + length)), dropRow, dropCol + i);
                                        }
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "This slot has been already alloted");
                                return false;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Time slot exceeds working hours");
                            return false;
                        }

                        // Set the source/dragged tables values
//                    JTable source = cd.getTable();
//                    int row = source.getSelectedRow();
//                    int col = source.getSelectedColumn();
////                        source.setValueAt(exportValue, row, col);
                        imported = true;
                    }
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }

        }
        return imported;
    }

    private boolean collides(int length, int dropRow, int dropCol, JTable target) {
        for (int i = 0; i < length; i++) {
            System.out.println("collision:" + target.getValueAt(dropRow, dropCol + i));
            if (target.getValueAt(dropRow, dropCol + i) != "") {
                System.out.println("collision at " + dropRow + " " + (dropCol + i));
                return true;
            }
        }
        return false;
    }
}
