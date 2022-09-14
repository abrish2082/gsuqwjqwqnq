/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.budget.FmsLuBudgetSource;
import et.gov.eep.fcms.mapper.budget.FmsLuBudgetSourceFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>


/**
 *
 * @author PO
 */
@Stateless
public class FmsLuBudgetSourceBean implements FmsLuBudgetSourceBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
 @EJB
    FmsLuBudgetSourceFacade fmsLuBudgetSourceFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 @Override
    public List<FmsLuBudgetSource> findAllBudetSource() {
        return fmsLuBudgetSourceFacade.findAll();
    }

    @Override
    public FmsLuBudgetSource findBudgetSrc(FmsLuBudgetSource id) {
        return fmsLuBudgetSourceFacade.findByBudgetSourceId(id);
    }
    //</editor-fold>
    
   

   
}
