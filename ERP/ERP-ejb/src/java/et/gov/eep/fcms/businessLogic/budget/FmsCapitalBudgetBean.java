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
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.mapper.budget.FmsCapitalBudget1Facade;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Stateless
public class FmsCapitalBudgetBean implements FmsCapitalBudgetBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
 @EJB
    FmsCapitalBudget1Facade fmsCapitalBudget1Facade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    public Integer RowCount() {
        return fmsCapitalBudget1Facade.RowCount();
    }

    @Override
    public void create(FmsCapitalBudget1 fmsCapitalBudget1) {
        fmsCapitalBudget1Facade.create(fmsCapitalBudget1);
    }

    @Override
    public Integer CostCenterDuplicationChecker(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction, PmsWorkAuthorization authorization) {
        return fmsCapitalBudget1Facade.CostCenterDuplicationChecker(budgetYear, fmsCostcSystemJunction, authorization);
    }

    @Override
    public void edit(FmsCapitalBudget1 fmsCapitalBudget1) {
        fmsCapitalBudget1Facade.edit(fmsCapitalBudget1);
    }

    @Override
    public List<FmsCapitalBudget1> findAllRequest() {
        return fmsCapitalBudget1Facade.findAllRequest();
    }

    @Override
    public ArrayList<FmsCapitalBudget1> findByBudgetYearSystem(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        return fmsCapitalBudget1Facade.findByBudgetYearSystem(budgetYear, fmsCostcSystemJunction);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> findByBudgetYearSystemCons(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        return fmsCapitalBudget1Facade.findByBudgetYearSystemCons(budgetYear, fmsCostcSystemJunction);
    }

    @Override
    public List<FmsCapitalBudget1> findRequestForApproval() {
        return fmsCapitalBudget1Facade.findRequestForApproval();
    }

    @Override
    public List<FmsCapitalBudget1> findRequestForConsApproval() {
        return fmsCapitalBudget1Facade.findRequestForConsApproval();
    }

    @Override
    public ArrayList<FmsCapitalBudget1> findByBudgetYear(FmsLuBudgetYear budgetYear) {
        return fmsCapitalBudget1Facade.findByBudgetYear(budgetYear);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> findByBudgetYearAndSystem(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        return fmsCapitalBudget1Facade.findByBudgetYearAndSystem(budgetYear, fmsCostcSystemJunction);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> findByBudgetYearAndCostCenter(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction) {
        return fmsCapitalBudget1Facade.findByBudgetYearAndCostCenter(budgetYear, fmsCostcSystemJunction);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> findBySystem(FmsCostcSystemJunction fmsCostcSystemJunction) {
        return fmsCapitalBudget1Facade.findBySystem(fmsCostcSystemJunction);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> findBySystemAndCostCenter(FmsCostCenter fmsCostCenter) {
        return fmsCapitalBudget1Facade.findBySystemAndCostCenter(fmsCostCenter);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> fetchCBforProcessOnReq(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsCapitalBudget1Facade.fetchCBforProcessOnReq(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> fetchCBforProcessPrepared(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsCapitalBudget1Facade.fetchCBforProcessPrepared(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> fetchCBsChecked(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsCapitalBudget1Facade.fetchCBsChecked(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> fetchCBsApproved(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsCapitalBudget1Facade.fetchCBsApproved(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> fetchCBsAuthorized(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsCapitalBudget1Facade.fetchCBsAuthorized(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> findCBSystemAndCostCenterAuthorized(FmsCostCenter fmsCostCenter) {
        return fmsCapitalBudget1Facade.findCBSystemAndCostCenterAuthorized(fmsCostCenter);
    }

    @Override
    public FmsCapitalBudget1 findByRequestCodeOnRequest(FmsCapitalBudget1 capitalBudget1) {
        return fmsCapitalBudget1Facade.findByRequestCodeOnRequest(capitalBudget1);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> findByCCSSJandPMSandBYRAuthorized(FmsLuBudgetYear fmsLuBudgetYear, FmsCostcSystemJunction fmsCostcSystemJunction, PmsWorkAuthorization authorization) {
        return fmsCapitalBudget1Facade.findByCCSSJandPMSandBYRAuthorized(fmsLuBudgetYear, fmsCostcSystemJunction, authorization);
    }

    @Override
    public ArrayList<FmsCapitalBudget1> findByBudgetYearAndCostCenterAuthorized(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction ccSsJunction) {
        return fmsCapitalBudget1Facade.findByBudgetYearAndCostCenterAuthorized(budgetYear, ccSsJunction);
    }

    //</editor-fold>
    
   

}
