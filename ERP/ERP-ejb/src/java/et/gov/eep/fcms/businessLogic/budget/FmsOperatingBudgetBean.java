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
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.mapper.budget.FmsOperatingBudget1Facade;
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
public class FmsOperatingBudgetBean implements FmsOperatingBudgetBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB">
 @EJB
    FmsOperatingBudget1Facade fmsOperatingBudgetFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
@Override
    public Integer RowCount() {
        return fmsOperatingBudgetFacade.RowCount();
    }

    @Override
    public Integer CCSSJuncDuplicationChecker(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction costcSystemJunction) {
        return fmsOperatingBudgetFacade.CCSSJuncDuplicationChecker(budgetYear,costcSystemJunction);
    }

    @Override
    public void create(FmsOperatingBudget1 fmsOperatingBudget1) {
        fmsOperatingBudgetFacade.create(fmsOperatingBudget1);
    }

    @Override
    public void edit(FmsOperatingBudget1 fmsOperatingBudget1) {
        fmsOperatingBudgetFacade.edit(fmsOperatingBudget1);
    }
    
    @Override
    public void saveOrUpdate(FmsOperatingBudget1 fmsOperatingBudget1) {
        fmsOperatingBudgetFacade.saveOrUpdate(fmsOperatingBudget1);
    }

    @Override
    public List<FmsOperatingBudget1> findAllRequest(Integer requestedBy) {
        return fmsOperatingBudgetFacade.findAllRequest(requestedBy);
    }
    
    @Override
    public List<FmsOperatingBudget1> findRequestForApproval() {
        return fmsOperatingBudgetFacade.findRequestForApproval();
    }
    
    @Override
    public List<FmsOperatingBudget1> findRequestForConsApproval() {
        return fmsOperatingBudgetFacade.findRequestForConsApproval();
    }
    
    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYear(FmsLuBudgetYear budgetYear) {
        return fmsOperatingBudgetFacade.findByBudgetYear(budgetYear);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystem(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findByBudgetYearAndSystem(budgetYear, fmsCostCenter);
    }
    
    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearSystem(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction costcSystemJunction) {
        return fmsOperatingBudgetFacade.findByBudgetYearSystem(budgetYear, costcSystemJunction);
    }
    
    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearSystemCons(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction ccSsJunction) {
        return fmsOperatingBudgetFacade.findByBudgetYearSystemCons(budgetYear, ccSsJunction);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenter(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findByBudgetYearAndCostCenter(budgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findBySystem(FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findBySystem(fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenter(FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findBySystemAndCostCenter(fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByRequestCode(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetFacade.findByRequestCode(fmsOperatingBudget1);
    }

    @Override
    public List<FmsOperatingBudget1> findAllRequestPrepared() {
        return fmsOperatingBudgetFacade.findAllRequestPrepared();
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearPrepared(FmsLuBudgetYear budgetYear) {
        return fmsOperatingBudgetFacade.findByBudgetYearPrepared(budgetYear);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemPrepared(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findByBudgetYearAndSystemPrepared(budgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenterPrepared(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findByBudgetYearAndCostCenterPrepared(budgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findBySystemPrepared(FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findBySystemPrepared(fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterPrepared(FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findBySystemAndCostCenterPrepared(fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByRequestCodePrepared(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetFacade.findByRequestCodePrepared(fmsOperatingBudget1);
    }

    @Override
    public List<FmsOperatingBudget1> findAllRequestChecked() {
        return fmsOperatingBudgetFacade.findAllRequestChecked();
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearChecked(FmsLuBudgetYear budgetYear) {
        return fmsOperatingBudgetFacade.findByBudgetYearChecked(budgetYear);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemChecked(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findByBudgetYearAndSystemChecked(budgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenterChecked(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findByBudgetYearAndCostCenterChecked(budgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findBySystemChecked(FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findBySystemChecked(fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterChecked(FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findBySystemAndCostCenterChecked(fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByRequestCodeChecked(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetFacade.findByRequestCodeChecked(fmsOperatingBudget1);
    }

    @Override
    public List<FmsOperatingBudget1> findAllRequestApproved() {
        return fmsOperatingBudgetFacade.findAllRequestApproved();
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearApproved(FmsLuBudgetYear budgetYear) {
        return fmsOperatingBudgetFacade.findByBudgetYearApproved(budgetYear);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemApproved(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findByBudgetYearAndSystemApproved(budgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenterApproved(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findByBudgetYearAndCostCenterApproved(budgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findBySystemApproved(FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findBySystemApproved(fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterApproved(FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findBySystemAndCostCenterApproved(fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByRequestCodeApproved(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetFacade.findByRequestCodeApproved(fmsOperatingBudget1);
    }

    @Override
    public List<FmsOperatingBudget1> findAllRequestAuthorized() {
        return fmsOperatingBudgetFacade.findAllRequestAuthorized();
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearAuthorized(FmsLuBudgetYear budgetYear) {
        return fmsOperatingBudgetFacade.findByBudgetYearAuthorized(budgetYear);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemAuthorized(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findByBudgetYearAndSystemAuthorized(budgetYear, fmsCostCenter);
    }

    @Override
    public List<FmsOperatingBudget1> findByBudgetYearAndCostCenterAuthorized(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction ccSsJunction) {
        return fmsOperatingBudgetFacade.findByBudgetYearAndCostCenterAuthorized(budgetYear, ccSsJunction);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findBySystemAuthorized(FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findBySystemAuthorized(fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterAuthorized(FmsCostcSystemJunction ccSsJunction) {
        return fmsOperatingBudgetFacade.findBySystemAndCostCenterAuthorized(ccSsJunction);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> findByRequestCodeAuthorized(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetFacade.findByRequestCodeAuthorized(fmsOperatingBudget1);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> fetchByOBforProcessOnReq(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.fetchByOBforProcessOnReq(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> fetchByOBforProcessPrepared(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.fetchByOBforProcessPrepared(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> fetchByOBsChecked(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.fetchByOBsChecked(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> fetchByOBsApproved(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.fetchByOBsApproved(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public ArrayList<FmsOperatingBudget1> fetchByOBsAuthorized(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.fetchByOBsAuthorized(fmsLuBudgetYear, fmsCostCenter);
    }

    @Override
    public FmsOperatingBudget1 fetchByRequestCode(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetFacade.fetchByRequestCode(fmsOperatingBudget1);
    }
    
    @Override
    public FmsOperatingBudget1 findByRequestCodeOnRequest(FmsOperatingBudget1 fmsOperatingBudget1) {
        return fmsOperatingBudgetFacade.findByRequestCodeOnRequest(fmsOperatingBudget1);
    }
    
    

    @Override
    public ArrayList<FmsOperatingBudget1> findByCostCenterAuthorized(FmsCostCenter fmsCostCenter) {
        return fmsOperatingBudgetFacade.findByCostCenterAuthorized(fmsCostCenter);
    }
    //</editor-fold>
    

    

}
