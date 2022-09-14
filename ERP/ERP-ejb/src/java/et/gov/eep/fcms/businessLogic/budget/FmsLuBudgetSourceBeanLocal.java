/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.entity.budget.FmsLuBudgetSource;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>

/**
 *
 * @author PO
 */
@Local
public interface FmsLuBudgetSourceBeanLocal {
    
    //<editor-fold defaultstate="collapsed" desc="other methods ">
    public List<FmsLuBudgetSource> findAllBudetSource();

    public FmsLuBudgetSource findBudgetSrc(FmsLuBudgetSource id);
    //</editor-fold>

}
