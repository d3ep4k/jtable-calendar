package draganddrop;

import javax.swing.JTable;

public class CellData {

    private final JTable table;

    public CellData(JTable table) {
        this.table = table;
    }

    public int getColumn() {
        return table.getSelectedColumn();
    }

    public Object getValue() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        return  table.getValueAt(row, col);
    }

    public JTable getTable() {
        return table;
    }

}
