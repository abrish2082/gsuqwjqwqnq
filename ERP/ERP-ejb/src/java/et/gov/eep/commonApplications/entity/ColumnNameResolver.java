/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class ColumnNameResolver implements Serializable {
    String Col_Name_FromTable;
    String Parsed_Col_Name;

    //<editor-fold defaultstate="collapsed" desc="getterAndSetter">
    public String getCol_Name_FromTable() {
        return Col_Name_FromTable;
    }
    
    public void setCol_Name_FromTable(String Col_Name_FromTable) {
        this.Col_Name_FromTable = Col_Name_FromTable;
    }
    
    public String getParsed_Col_Name() {
        return Parsed_Col_Name;
    }
    
    public void setParsed_Col_Name(String Parsed_Col_Name) {
        this.Parsed_Col_Name = Parsed_Col_Name;
    }
//</editor-fold>
    
}
