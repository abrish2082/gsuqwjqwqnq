/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Local
public interface FmsCapitalBudgetDetailBeanLocal {
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 public void edit(FmsCapitalBudgetDetail fmsCapitalBudgetDetail);
    public List<FmsCapitalBudgetDetail> fetchSelectedCBRequest(FmsCapitalBudget1 capitalBudget1);
    public List<FmsCapitalBudgetDetail> fetchCBDetail(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);
    public FmsCapitalBudgetDetail fetchCapBudgetDetail(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter,FmsBudgetCode budgetCode);
    public List<FmsCapitalBudgetDetail> getCapBudgetDetailList(FmsLuBudgetYear fmsLuBudgetYear, FmsCapitalBudget1 capitalBudget1, FmsBudgetCode budgetCode);
    public FmsCapitalBudgetDetail getCapBudgetDetail(FmsCapitalBudgetDetail fmsCapitalBudgetDetail);
    
    //</editor-fold>
    
   
}
