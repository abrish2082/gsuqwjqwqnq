/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.LookUp;
import static et.gov.eep.commonApplications.mapper.AbstractFacade.LOOKUP;
import et.gov.eep.commonApplications.mapper.LookUpFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
//import org.insa.util.exceptionHandler.ErrorLogWriter;

/**
 *
 * @author Tsehayu Tilahun
 */
@Stateless
public class LookUpBean implements LookUpBeanLocal {
    @EJB
    private LookUp lookUp;

    @EJB
    private LookUpFacade lookUpFacade;

    /**
     *
     * @param systemPrefix
     * @return
     */
    @Override
    public ArrayList<String> getLookUpTables(String systemPrefix) {
        return lookUpFacade.getLookUpTables(systemPrefix);
    }

    /**
     *
     * @param lookUp
     * @return
     */
    @Override
    public ArrayList<String> getColumns(LookUp lookUp) {
        return lookUpFacade.getColumns(lookUp);
    }

    /**
     *
     * @param tableInfo
     * @return
     */
    @Override
    public ArrayList<String[]> selectData(HashMap tableInfo) {
        LookUp lookUpEntity = new LookUp();
        lookUpEntity.setTableName(tableInfo.get("name").toString());
        lookUpEntity.setNoColumn((Integer) tableInfo.get("noColumn"));
        return lookUpFacade.selectData(lookUpEntity);
    }

    /**
     *
     * @param tableName
     * @return
     */
    @Override
    public ArrayList<String> checkForeignKey(String tableName) {
        return lookUpFacade.checkForeignKey(tableName);
    }

    /**
     *
     * @param tableName
     * @return
     */
    @Override
    public String checkPrimaryKey(String tableName) {
        return lookUpFacade.selectPkCol(tableName);
    }

    /**
     *
     * @param tableName
     * @param columnName
     * @return
     */
    @Override
    public String checkRequiredOrNot(String tableName, String columnName) {
        return lookUpFacade.checkRequiredOrNot(tableName, columnName);
    }

    /**
     *
     * @param tableName
     * @param columnName
     * @return
     */
    @Override
    public ArrayList<SelectItem> refTableDetail(String tableName, String columnName) {
        try {
            ArrayList<SelectItem> tableColumn = new ArrayList<SelectItem>();
            List<Object[]> result = lookUpFacade.selectAllFromRef(tableName, columnName);
            for (Object[] resultElement : result) {
                tableColumn.add(new SelectItem(resultElement[0], resultElement[1].toString()));
            }
            if (tableColumn != null) {
                return tableColumn;
            } else {
                return null;
            }
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param tableName
     * @param columnNames
     * @param values
     * @return
     */
    @Override
    public int saveLookUp(String tableName, String columnNames, String values) {
        int message = -1;
        try {
            LookUp lookUpEntity = new LookUp();
            lookUpEntity.setTableName(tableName);
            lookUpEntity.setColumnNames(columnNames);
            lookUpEntity.setValues(values);
            message = lookUpFacade.insert(lookUpEntity);
            return message;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return message;
        }
    }

    /**
     *
     * @param tableName
     * @param columnNames
     * @param values
     * @return
     */
    public int updateLookUp(String tableName, String columnNames, String values) {
        int message = -1;
        try {
            LookUp lookUpEntity = new LookUp();
            lookUpEntity.setTableName(tableName);
            lookUpEntity.setColumnNames(columnNames);
            lookUpEntity.setValues(values);
            message = lookUpFacade.update(lookUpEntity);
            return message;
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex, LOOKUP);
            ex.printStackTrace();
            return message;
        }
    }
}
