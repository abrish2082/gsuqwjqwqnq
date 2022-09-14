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
import et.gov.eep.fcms.mapper.budget.FmsCapitalBudgetDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Stateless
public class FmsCapitalBudgetDetailBean implements FmsCapitalBudgetDetailBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
@EJB
    FmsCapitalBudgetDetailFacade fmsCapitalBudgetDetailFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 @Override
    public List<FmsCapitalBudgetDetail> fetchSelectedCBRequest(FmsCapitalBudget1 capitalBudget1) {
        return fmsCapitalBudgetDetailFacade.fetchSelectedCBRequest(capitalBudget1);
    }

    @Override
    public List<FmsCapitalBudgetDetail> fetchCBDetail(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsCapitalBudgetDetailFacade.fetchCBDetail(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public FmsCapitalBudgetDetail fetchCapBudgetDetail(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter, FmsBudgetCode budgetCode) {
        return fmsCapitalBudgetDetailFacade.fetchCapBudgetDetail(fmsLuBudgetYear, fmsCostCenter, budgetCode);
    }

    @Override
    public void edit(FmsCapitalBudgetDetail fmsCapitalBudgetDetail) {
        fmsCapitalBudgetDetailFacade.edit(fmsCapitalBudgetDetail);
    }

    @Override
    public FmsCapitalBudgetDetail getCapBudgetDetail(FmsCapitalBudgetDetail fmsCapitalBudgetDetail) {
        return fmsCapitalBudgetDetailFacade.getCapBudgetDetail(fmsCapitalBudgetDetail);
    }

    @Override
    public List<FmsCapitalBudgetDetail> getCapBudgetDetailList(FmsLuBudgetYear fmsLuBudgetYear, FmsCapitalBudget1 capitalBudget1, FmsBudgetCode budgetCode) {
        return fmsCapitalBudgetDetailFacade.getCapBudgetDetailList(fmsLuBudgetYear, capitalBudget1, budgetCode);
    }
    //</editor-fold>
    
    

   
}
