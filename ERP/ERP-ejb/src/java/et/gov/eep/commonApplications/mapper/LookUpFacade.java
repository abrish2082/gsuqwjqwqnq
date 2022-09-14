/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.mapper;

import et.gov.eep.commonApplications.entity.LookUp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//import org.insa.util.exceptionHandler.ErrorLogWriter;

/**
 *
 * @author Tsehayu Tilahun
 */
@Stateless
public class LookUpFacade {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * This method gets list of tables whose names start with systemPrefix
     *
     * @param systemPrefix
     * @return array list of table names
     */
    public ArrayList<String> getLookUpTables(String systemPrefix) {
        systemPrefix = systemPrefix + "_LU_%";
        Query query = em.createNativeQuery("SELECT DISTINCT TABLE_NAME FROM ALL_TAB_COLUMNS "
                + "WHERE OWNER ='ERP' AND TABLE_NAME LIKE '" + systemPrefix + "' ORDER BY TABLE_NAME");
        try {
            System.out.println("entity " + query.getResultList().size());
            ArrayList<String> dutyFreeList = new ArrayList(query.getResultList());
            return dutyFreeList;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns list of columns for a table
     *
     * @param lookUp
     * @return returns list of columns for a table
     */
    public ArrayList<String> getColumns(LookUp lookUp) {
        Query query = em.createNativeQuery("SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS "
                + "WHERE TABLE_NAME = '" + lookUp.getTableName() + "' AND OWNER = 'ERP' ORDER BY COLUMN_ID");
        try {
            ArrayList<String> dutyFreeList = new ArrayList(query.getResultList());
            return dutyFreeList;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * This method selects data from a table
     *
     * @param lookUp table name
     * @return Array List of String Array data
     */
    public ArrayList<String[]> selectData(LookUp lookUp) {
        ArrayList<String[]> lookUpData = new ArrayList<String[]>();
        String pk = selectPkCol(lookUp.getTableName());
        Query query = em.createNativeQuery("SELECT DISTINCT * FROM " + lookUp.getTableName() + " ORDER BY CAST(" + pk + " AS int)");
        try {
            List<Object[]> result = query.getResultList();
            for (Object[] resultElement : result) {
                String[] rowData = new String[lookUp.getNoColumn()];
                for (int i = 0; i < rowData.length; i++) {
                    if (resultElement[i] != null && resultElement[i] != "") {
                        String col = getColumns(lookUp).get(i);
                        if (checkForeignKey(lookUp.getTableName(), col) == true) {
                            String ref = selectReferenceTable(lookUp.getTableName(), col);
                            rowData[i] = selectFromRef(ref, resultElement[i].toString()) + "@" + resultElement[i] + "@" + "ForeignKey";
                        } else {
                            rowData[i] = resultElement[i].toString();
                        }
                    }
                }
                lookUpData.add(rowData);
            }
            return lookUpData;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns list of foreign keys which exist in table
     *
     * @param tableName
     * @return Array list of String foreign keys
     */
    public ArrayList<String> checkForeignKey(String tableName) {
        Query query = em.createNativeQuery("select user_cons_columns.column_name from user_constraints "
                + "inner join user_cons_columns on user_constraints.constraint_name = "
                + "user_cons_columns.constraint_name "
                + "AND user_constraints.table_name = '" + tableName + "' AND "
                + "user_constraints.constraint_type='R'");
        try {
            ArrayList<String> dutyFreeList = new ArrayList(query.getResultList());
            return dutyFreeList;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * this method checks if a column is foreign keys
     *
     * @param tableName
     * @param columnName
     * @return true if the column is foreign otherwise returns false
     */
    public boolean checkForeignKey(String tableName, String columnName) {
        boolean foreignKeyCheck = false;
        ArrayList<String> cfk = checkForeignKey(tableName);
        for (int i = 0; i < cfk.size(); i++) {
            if (cfk.get(i).compareToIgnoreCase(columnName) == 0) {
                foreignKeyCheck = true;
                break;
            }
        }
        return foreignKeyCheck;
    }

    /**
     * This method returns name of a table that a column is referring
     *
     * @param tableName
     * @param columnName
     * @return String of table name that a column references otherwise returns
     * null
     */
    public String selectReferenceTable(String tableName, String columnName) {
        String refTable = "";
        Query query = em.createNativeQuery("SELECT c_pk.table_name r_table_name FROM all_cons_columns a "
                + "JOIN all_constraints c ON a.owner = c.owner "
                + "AND a.constraint_name = c.constraint_name "
                + "JOIN all_constraints c_pk ON c.r_owner = c_pk.owner "
                + "AND c.r_constraint_name = c_pk.constraint_name "
                + "WHERE c.constraint_type = 'R' AND a.table_name = '" + tableName + "' AND a.column_name = '" + columnName + "' ");
        try {
            refTable = (String) query.getResultList().get(0);
            return refTable;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * This method selects data from table tableName where id is pkValue
     *
     * @param tableName
     * @param pkValue
     * @return String of value
     */
    public String selectFromRef(String tableName, String pkValue) {
        String label = "";
        String pk = selectPkCol(tableName);
//        Query query = em.createNativeQuery("SELECT * FROM " + tableName + " WHERE " + pk + " = '" + pkValue + "' ");
        Query query = em.createNativeQuery("SELECT NAME FROM " + tableName + " WHERE " + pk + " = '" + pkValue + "' ");
        try {
            label = (String) query.getResultList().get(0);
            return label;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * This method selects primary key of a table tableName
     *
     * @param tableName
     * @return name of a column which is primary key
     */
    public String selectPkCol(String tableName) {
        String pkColumn = "";
        Query query = em.createNativeQuery("select user_cons_columns.column_name from user_constraints "
                + "inner join user_cons_columns on user_constraints.constraint_name = "
                + "user_cons_columns.constraint_name "
                + "AND user_constraints.table_name = '" + tableName + "' AND "
                + "user_constraints.constraint_type='P'");
        try {
            pkColumn = (String) query.getResultList().get(0);
            return pkColumn;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * This method selects data from reference table
     *
     * @param tableName
     * @param columnName
     * @return List of Object Array of data
     */
    public List<Object[]> selectAllFromRef(String tableName, String columnName) {
        String fromRef = selectReferenceTable(tableName, columnName);
//        Query query = em.createNativeQuery("SELECT * FROM " + fromRef + "");
        String pk = selectPkCol(fromRef);
        Query query = em.createNativeQuery("SELECT " + pk + ", NAME FROM " + fromRef + "");
        try {
            List<Object[]> result = query.getResultList();
            return result;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Insert a row into tables
     *
     * @param lookUp
     * @return values on successful completion of the method, otherwise -1
     */
    public int insert(LookUp lookUp) {
        String[] _data = lookUp.getValues().replace(" ", "").split(",");
        System.out.println("---facade----" + _data);
        String columnsValue = "";
        String[] _col = lookUp.getColumnNames().split(",");
        String pkInfo[] = selectMaxId(lookUp);
        for (int i = 0; i < _data.length; i++) {
            if ((i + 1) != _data.length) {
                if (_col[i].compareToIgnoreCase(pkInfo[0]) == 0) {
                    _data[i] = pkInfo[1];
                    columnsValue += _data[i] + "','";
                } else {
                    columnsValue += _data[i] + "','";
                }
            } else {
                if (_col[i].compareToIgnoreCase(pkInfo[0]) == 0) {
                    _data[i] = pkInfo[1];
                    columnsValue += _data[i];
                } else {
                    columnsValue += _data[i];
                }
            }
        }
        Query query = em.createNativeQuery("INSERT INTO " + lookUp.getTableName() + " (" + lookUp.getColumnNames() + " ) VALUES( '" + columnsValue + "')");
        try {
            return query.executeUpdate();
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * This method selects maximum value of primary key
     *
     * @param lookUp
     * @return maximum primary key value
     */
    public String[] selectMaxId(LookUp lookUp) {
        int maxPkId = 1;
        String pkColName = selectPkCol(lookUp.getTableName());
        Query query = em.createNativeQuery("SELECT MAX(CAST(" + pkColName + " AS int)) AS id FROM " + lookUp.getTableName() + "");
        try {
            String result = null;
            if (query.getResultList() != null && query.getResultList().get(0) != null) {
                result = String.valueOf(query.getResultList().get(0));
            } else {
                result = String.valueOf(0);
            }
            maxPkId = Integer.parseInt(result) + 1;
            System.out.println("maxPKID "+maxPkId);
            String pkInfo[] = {pkColName, String.valueOf(maxPkId)};
            return pkInfo;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Update a row into tables
     *
     * @param lookUp
     * @return values on successful completion of the method, otherwise -1
     */
    public int update(LookUp lookUp) {
        String pkColNameValue = "";
        String pkColName = selectPkCol(lookUp.getTableName());
        String[] _data = lookUp.getValues().replace(" ", "").split(",");
        String columnsValue = "";
        String[] _col = lookUp.getColumnNames().split(",");
        for (int i = 0; i < _data.length; i++) {
            if ((i + 1) != _data.length) {
                if (_col[i].compareToIgnoreCase(pkColName) == 0) {
                    pkColNameValue = _col[i] + "='" + _data[i] + "'";
                } else {
                    columnsValue += _col[i] + "='" + _data[i] + "',";
                }
            } else {
                if (_col[i].compareToIgnoreCase(pkColName) == 0) {
                    pkColNameValue = _col[i] + "='" + _data[i] + "'";
                } else {
                    columnsValue += _col[i] + "='" + _data[i] + "'";
                }
            }
        }
        Query query = em.createNativeQuery("UPDATE " + lookUp.getTableName() + " SET " + columnsValue + " where " + pkColNameValue + "");
        try {
            return query.executeUpdate();
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * This method checks table column name required or not
     *
     * @param tableName
     * @param columnName
     * @return Y or N
     */
    public String checkRequiredOrNot(String tableName, String columnName) {
        String nullable = "";
        Query query = em.createNativeQuery("SELECT nullable FROM user_tab_columns "
                + "WHERE table_name = '" + tableName + "' AND column_name='" + columnName + "'");
        try {
            nullable = (String) query.getResultList().get(0);
            return nullable;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

}
