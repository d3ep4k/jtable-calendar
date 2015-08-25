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
public interface CellSpan {

    public final int ROW = 0;
    public final int COLUMN = 1;

    public int[] getSpan(int row, int column);

    public void setSpan(int[] span, int row, int column);

    public boolean isVisible(int row, int column);

    public void combine(int[] rows, int[] columns);

    public void split(int row, int column);

}
