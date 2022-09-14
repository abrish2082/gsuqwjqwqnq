/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import javax.ejb.Stateless;

/**
 *
 * @author leul
 */
@Stateless
public class LookUp {

    private String tableName;
    private int noColumn;
    private String columnNames;
    private String values;
    private String owner;
    private String pkValue;
    private String refTableName;

    /**
     *
     * @return
     */
    public String getTableName() {
        return tableName;
    }

    /**
     *
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     *
     * @return
     */
    public int getNoColumn() {
        return noColumn;
    }

    /**
     *
     * @param noColumn
     */
    public void setNoColumn(int noColumn) {
        this.noColumn = noColumn;
    }

    /**
     *
     * @return
     */
    public String getColumnNames() {
        return columnNames;
    }

    /**
     *
     * @return
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *
     * @return
     */
    public String getValues() {
        return values;
    }

    /**
     *
     * @return
     */
    public String getPkValue() {
        return pkValue;
    }

    /**
     *
     * @param pkValue
     */
    public void setPkValue(String pkValue) {
        this.pkValue = pkValue;
    }

    /**
     *
     * @return
     */
    public String getRefTableName() {
        return refTableName;
    }

    /**
     *
     * @param refTableName
     */
    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    /**
     *
     * @param columnNames
     */
    public void setColumnNames(String columnNames) {
        StringBuilder sb = new StringBuilder();
        sb.append(columnNames);
        sb.deleteCharAt(0);
        sb.deleteCharAt(columnNames.length() - 2);
        this.columnNames = sb.toString();
    }

    /**
     *
     * @param values
     */
    public void setValues(String values) {
        StringBuilder sb = new StringBuilder();
        sb.append(values);
        sb.deleteCharAt(0);
        sb.deleteCharAt(values.length() - 2);
        this.values = sb.toString();
    }

}
