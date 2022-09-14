/**
 * Utility class used to keep track of the cells in a table.
 */
package et.gov.eep.commonApplications.utility;

import java.io.Serializable;

/**
 *
 * @author tamrat
 */
public class CellKey implements Serializable {

    private final Object row;
    private final Object column;

    /**
     * @param row
     * @param column
     */
    public CellKey(Object row, Object column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof CellKey) {
            CellKey other = (CellKey) obj;
            return other.row.equals(row) && other.column.equals(column);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return row.hashCode() + column.hashCode();
    }

    @Override
    public String toString() {
        return row.toString() + "," + column.toString();
    }
}
