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
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Local
public interface FmsCapitalBudgetBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
public Integer RowCount();

    public void create(FmsCapitalBudget1 fmsCapitalBudget1);

    public Integer CostCenterDuplicationChecker(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction, PmsWorkAuthorization authorization);

    public void edit(FmsCapitalBudget1 fmsCapitalBudget1);

    public List<FmsCapitalBudget1> findAllRequest();

    public ArrayList<FmsCapitalBudget1> findByBudgetYearSystem(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction);

    public ArrayList<FmsCapitalBudget1> findByBudgetYearSystemCons(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction);

    public List<FmsCapitalBudget1> findRequestForApproval();

    public FmsCapitalBudget1 findByRequestCodeOnRequest(FmsCapitalBudget1 capitalBudget1);

    public List<FmsCapitalBudget1> findRequestForConsApproval();

    public ArrayList<FmsCapitalBudget1> findByBudgetYear(FmsLuBudgetYear budgetYear);

    public ArrayList<FmsCapitalBudget1> findByBudgetYearAndSystem(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction);

    public ArrayList<FmsCapitalBudget1> findByBudgetYearAndCostCenter(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction fmsCostcSystemJunction);

    public ArrayList<FmsCapitalBudget1> findBySystem(FmsCostcSystemJunction fmsCostcSystemJunction);

    public ArrayList<FmsCapitalBudget1> findBySystemAndCostCenter(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsCapitalBudget1> fetchCBforProcessOnReq(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsCapitalBudget1> fetchCBforProcessPrepared(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsCapitalBudget1> fetchCBsChecked(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsCapitalBudget1> fetchCBsApproved(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsCapitalBudget1> fetchCBsAuthorized(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsCapitalBudget1> findCBSystemAndCostCenterAuthorized(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsCapitalBudget1> findByCCSSJandPMSandBYRAuthorized(FmsLuBudgetYear fmsLuBudgetYear, FmsCostcSystemJunction fmsCostcSystemJunction, PmsWorkAuthorization authorization);

    public ArrayList<FmsCapitalBudget1> findByBudgetYearAndCostCenterAuthorized(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction ccSsJunction);

    //</editor-fold>
    
    
    
    }
