/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draganddrop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.Box;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kainix
 */
public final class TableSwap {

    public static void main(String[] args) {
        new TableSwap();
    }

    public TableSwap() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            }

            JTable timeTable = createGanttTable(1);
            JTable unassigned = createTable(20);
            Box box = Box.createVerticalBox();
            box.add(new JScrollPane(timeTable));
            box.add(new JSeparator(SwingConstants.HORIZONTAL));
            box.add(new JScrollPane(unassigned));

            JFrame frame = new JFrame("Gantt Chart");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setLocationByPlatform(true);
            frame.add(box);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    protected JTable createTable(int startAt) {
        String[] columns = {"Time interval", "Color"};
        Object[][] data = {
            {"2 hr", new ImageIcon(getClass().getResource("/resources/4"))},
            {"1.5 hr", new ImageIcon(getClass().getResource("/resources/3"))},
            {"1 hr", new ImageIcon(getClass().getResource("/resources/2"))},
            {"0.5 hr", new ImageIcon(getClass().getResource("/resources/1"))},
            {"Clear Row", new ImageIcon(getClass().getResource("/resources/0"))},};

        DefaultTableModel model = new DefaultTableModel(data, columns);

        JTable table = new JTable(model) {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        table.setDragEnabled(true);
        table.setDropMode(DropMode.USE_SELECTION);
        table.setTransferHandler(new TransferHelper());
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setPreferredScrollableViewportSize(new Dimension(400, 90));
        return table;
    }

    private JTable createGanttTable(int startAt) {
        String[] columns = {"<html>Employee<br><br></html>", "<html>8am<br>to<br>8:30am</html>", "<html>8:30am<br>to<br>9am</html>", "<html>9am<br>to<br>9:30", "<html>9:30am<br>to<br>10am</html>", "<html>10am<br>to<br>10:30am</html>", "<html>10:30am<br>to<br>11am</html>", "<html>11am<br>to<br>11:30am</html>", "<html>11:30am<br>to<br>12noon", "<html>12noon<br>to<br>12:30pm</html>", "<html>12:30pm<br>to<br>1pm</html>", "<html>1pm<br>to<br>1:30pm</html>", "<html>1:30pm<br>to<br>2pm</html>", "<html>2pm<br>to<br>2:30pm</html>", "<html>2:30pm<br>to<br>3pm</html>", "<html>3pm<br>to<br>3:30pm</html>", "<html>3:30pm<br>to<br>4pm</html>", "<html>4pm<br>to<br>4:30pm</html>", "<html>4:30pm<br>to<br>5pm</html>", "<html>5pm<br>to<br>5:30pm</html>", "<html>5:30pm<br>to<br>6pm</html>", "<html>6pm<br>to<br>6:30pm</html>", "<html>6:30pm<br>to<br>7pm</html>"};
        Object[][] data
                = {
                    {"Alice", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
                    {"Bob", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
                    {"Foo", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
                    {"Bar", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""}
                };
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model) {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        table.setDragEnabled(true);
        table.setDropMode(DropMode.USE_SELECTION);
        table.setTransferHandler(new TransferHelper());
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);
        table.setPreferredScrollableViewportSize(new Dimension(1150, 70));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int columnCount = table.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(50);
            table.getColumnModel().getColumn(i).setMinWidth(50);

        }
        //Allign table header to center
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(String.class, renderer);

        return table;
    }

}
