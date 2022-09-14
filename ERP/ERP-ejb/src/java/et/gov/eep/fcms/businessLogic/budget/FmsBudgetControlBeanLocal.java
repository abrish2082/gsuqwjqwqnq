/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.budget.FmsBudgetControl;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>

@Local
public interface FmsBudgetControlBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
 public void create(FmsBudgetControl fmsBudgetControl);

    public List<FmsBudgetControl> fetchOBComparison(FmsLuBudgetYear fmsLuBudgetYear, FmsCostcSystemJunction fmsCostcSystemJunction);

    public List<FmsBudgetControl> fetchCBComparison(FmsLuBudgetYear fmsLuBudgetYear, FmsCostcSystemJunction fmsCostcSystemJunction);

    //</editor-fold>
    
    
   }
