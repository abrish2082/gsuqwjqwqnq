/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.LookUp;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author Tsehayu Tilahun
 */
@Local
public interface LookUpBeanLocal {

    /**
     *
     * @param systemPrefix
     * @return
     */
    ArrayList<String> getLookUpTables(String systemPrefix);

    /**
     *
     * @param lookUp
     * @return
     */
    ArrayList<String> getColumns(LookUp lookUp);

    /**
     *
     * @param tableInfo
     * @return
     */
    ArrayList<String[]> selectData(HashMap tableInfo);

    /**
     *
     * @param tableName
     * @return
     */
    ArrayList<String> checkForeignKey(String tableName);

    /**
     *
     * @param tableName
     * @param columnName
     * @return
     */
    ArrayList<SelectItem> refTableDetail(String tableName, String columnName);

    /**
     *
     * @param tableName
     * @return
     */
    String checkPrimaryKey(String tableName);

    /**
     *
     * @param tableName
     * @param columnNames
     * @param values
     * @return
     */
    int saveLookUp(String tableName, String columnNames, String values);

    /**
     *
     * @param tableName
     * @param columnNames
     * @param values
     * @return
     */
    int updateLookUp(String tableName, String columnNames, String values);

    /**
     *
     * @param tableName
     * @param columnName
     * @return
     */
    String checkRequiredOrNot(String tableName, String columnName);
}
