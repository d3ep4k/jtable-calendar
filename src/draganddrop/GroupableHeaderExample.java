/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draganddrop;

/**
 *
 * @author Kainix
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * @version 1.0 11/09/98
 */
public class GroupableHeaderExample extends JFrame {

    GroupableHeaderExample() {
        super("Groupable Header Example");

        DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(new Object[][]{
            {"119", "foo", "bar", "ja", "ko", "zh"},
            {"911", "bar", "foo", "en", "fr", "pt"}},
                new Object[]{"SNo.", "1", "2", "Native", "2", "3"});
        DefaultTableModel dm1 = new DefaultTableModel();
        dm1.setDataVector(new Object[][]{
            {"119", "foo", "bar", "ja", "ko", "zh"},
            {"911", "bar", "foo", "en", "fr", "pt"}},
                new Object[]{"SNo.", "1", "2", "Native", "2", "3"});

        JTable table = new JTable(dm) {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };
        table.setDragEnabled(true);
        table.setDropMode(DropMode.USE_SELECTION);
        table.setTransferHandler(new TransferHelper());
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);
        
        JTable table1 = new JTable(dm1) {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };
        table1.setDragEnabled(true);
        table1.setDropMode(DropMode.USE_SELECTION);
        table1.setTransferHandler(new TransferHelper());
        table1.setRowSelectionAllowed(false);
        table1.setCellSelectionEnabled(true);

        TableColumnModel cm = table.getColumnModel();
        ColumnGroup g_name = new ColumnGroup("Name");
        g_name.add(cm.getColumn(1));
        g_name.add(cm.getColumn(2));
        ColumnGroup g_lang = new ColumnGroup("Language");
        g_lang.add(cm.getColumn(3));
        ColumnGroup g_other = new ColumnGroup("Others");
        g_other.add(cm.getColumn(4));
        g_other.add(cm.getColumn(5));
        g_lang.add(g_other);

        GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
        header.addColumnGroup(g_name);
        header.addColumnGroup(g_lang);
        TableColumnModel cm1 = table1.getColumnModel();
        ColumnGroup g_name1 = new ColumnGroup("Name");
        g_name1.add(cm1.getColumn(1));
        g_name1.add(cm1.getColumn(2));
        ColumnGroup g_lang1 = new ColumnGroup("Language");
        g_lang1.add(cm1.getColumn(3));
        ColumnGroup g_other1 = new ColumnGroup("Others");
        g_other1.add(cm1.getColumn(4));
        g_other1.add(cm1.getColumn(5));
        g_lang1.add(g_other1);

        GroupableTableHeader header1 = (GroupableTableHeader) table1.getTableHeader();
        header1.addColumnGroup(g_name1);
        header1.addColumnGroup(g_lang1);
        Box box = Box.createVerticalBox();
            box.add(new JScrollPane(table));
            box.add(new JScrollPane(table1));
        getContentPane().add(box);
        setSize(400, 120);
    }

    public static void main(String[] args) {
        GroupableHeaderExample frame = new GroupableHeaderExample();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
}
