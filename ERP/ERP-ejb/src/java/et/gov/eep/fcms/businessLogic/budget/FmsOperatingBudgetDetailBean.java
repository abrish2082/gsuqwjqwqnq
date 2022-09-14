/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.mapper.budget.FmsOperatingBudgetDetailFacade;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Stateless
public class FmsOperatingBudgetDetailBean implements FmsOperatingBudgetDetailBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
    @EJB
    FmsOperatingBudgetDetailFacade fmsOperatingBudgetDetailFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 @Override
    public void edit(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        fmsOperatingBudgetDetailFacade.edit(fmsOperatingBudgetDetail);
    }

    @Override
    public List<FmsOperatingBudgetDetail> fetchSelectedOBRequest(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetDetailFacade.fetchSelectedOBRequest(fmsOperatingBudget1);
    }

    @Override
    public List<FmsOperatingBudgetDetail> fetchOBDetail(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetDetailFacade.fetchOBDetail(fmsOperatingBudget1);
    }
    
    @Override
    public List<FmsOperatingBudgetDetail> fetchOBDetailByGL(FmsOperatingBudget1 fmsOperatingBudget1,FmsGeneralLedger fmsGeneralLedger) {
        return fmsOperatingBudgetDetailFacade.fetchOBDetailByGL(fmsOperatingBudget1,fmsGeneralLedger);
    }
    
    @Override
    public FmsOperatingBudgetDetail fetchOBDetailByGLOB1(FmsOperatingBudget1 fmsOperatingBudget1,FmsGeneralLedger fmsGeneralLedger) {
        return fmsOperatingBudgetDetailFacade.fetchOBDetailByGLOB1(fmsOperatingBudget1,fmsGeneralLedger);
    }

    @Override
    public List<FmsGeneralLedger> fetchGLfromOBDetail(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetDetailFacade.fetchGLfromOBDetail(fmsOperatingBudget1);
    }

    @Override
    public FmsOperatingBudgetDetail fetchByGLfromOBDetail(FmsGeneralLedger fmsGeneralLedger, FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetDetailFacade.fetchByGLfromOBDetail(fmsGeneralLedger, fmsOperatingBudget1);
    }

    @Override
    public FmsOperatingBudgetDetail budgetcomparison(HashMap hashMap) {
        return fmsOperatingBudgetDetailFacade.budgetcomparison(hashMap);
    }

    @Override
    public FmsOperatingBudgetDetail getOBDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        return fmsOperatingBudgetDetailFacade.getOBDetail(fmsOperatingBudgetDetail);
    }
    
    @Override
    public FmsOperatingBudgetDetail fetchOB(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetDetailFacade.fetchOB(fmsOperatingBudget1);
    }
    //</editor-fold>
    


   
}
