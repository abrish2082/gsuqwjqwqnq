/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.io.Serializable;

/**
 *
 * @author hp
 */
 public class ColumnNames implements Serializable {
        String Table_Col_Name;
        String Parsed_Col_name;

    public String getTable_Col_Name() {
        return Table_Col_Name;
    }

    public void setTable_Col_Name(String Table_Col_Name) {
        this.Table_Col_Name = Table_Col_Name;
    }

    public String getParsed_Col_name() {
        return Parsed_Col_name;
    }

    public void setParsed_Col_name(String Parsed_Col_name) {
        this.Parsed_Col_name = Parsed_Col_name;
    }
    
    
}
