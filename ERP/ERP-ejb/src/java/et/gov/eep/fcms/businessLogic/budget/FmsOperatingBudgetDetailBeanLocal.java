/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Local
public interface FmsOperatingBudgetDetailBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
public void edit(FmsOperatingBudgetDetail fmsOperatingBudgetDetail);

    public List<FmsOperatingBudgetDetail> fetchSelectedOBRequest(FmsOperatingBudget1 fmsOperatingBudget1);

    public List<FmsOperatingBudgetDetail> fetchOBDetail(FmsOperatingBudget1 fmsOperatingBudget1);

    public List<FmsOperatingBudgetDetail> fetchOBDetailByGL(FmsOperatingBudget1 fmsOperatingBudget1, FmsGeneralLedger fmsGeneralLedger);

    public FmsOperatingBudgetDetail fetchOBDetailByGLOB1(FmsOperatingBudget1 fmsOperatingBudget1, FmsGeneralLedger fmsGeneralLedger);

    public List<FmsGeneralLedger> fetchGLfromOBDetail(FmsOperatingBudget1 fmsOperatingBudget1);

    public FmsOperatingBudgetDetail getOBDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail);

    public FmsOperatingBudgetDetail fetchByGLfromOBDetail(FmsGeneralLedger fmsGeneralLedger, FmsOperatingBudget1 fmsOperatingBudget1);

    public FmsOperatingBudgetDetail budgetcomparison(HashMap hashMap);

    public FmsOperatingBudgetDetail fetchOB(FmsOperatingBudget1 fmsOperatingBudget1);
    //</editor-fold>
    
    
}
