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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>
   


/**
 *
 * @author Me
 */
@Local
public interface FmsOperatingBudgetBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
public Integer RowCount();
    
    public Integer CCSSJuncDuplicationChecker(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction costcSystemJunction);
    
    public void create(FmsOperatingBudget1 fmsOperatingBudget1);

    public void edit(FmsOperatingBudget1 fmsOperatingBudget1);
    
    public void saveOrUpdate(FmsOperatingBudget1 fmsOperatingBudget1);

    public List<FmsOperatingBudget1> findAllRequest(Integer requestedBy);
    
    public List<FmsOperatingBudget1> findRequestForApproval();
    
    public List<FmsOperatingBudget1> findRequestForConsApproval();   
    
    public ArrayList<FmsOperatingBudget1> findByBudgetYear(FmsLuBudgetYear budgetYear);

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystem(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter);
    
    public ArrayList<FmsOperatingBudget1> findByBudgetYearSystem(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction costcSystemJunction);
    
    public ArrayList<FmsOperatingBudget1> findByBudgetYearSystemCons(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction ccSsJunction);

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenter(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findBySystem(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenter(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findByRequestCode(FmsOperatingBudget1 fmsOperatingBudget1);

    public List<FmsOperatingBudget1> findAllRequestPrepared();

    public ArrayList<FmsOperatingBudget1> findByBudgetYearPrepared(FmsLuBudgetYear budgetYear);

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemPrepared(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenterPrepared(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findBySystemPrepared(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterPrepared(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findByRequestCodePrepared(FmsOperatingBudget1 fmsOperatingBudget1);

    public List<FmsOperatingBudget1> findAllRequestChecked();

    public ArrayList<FmsOperatingBudget1> findByBudgetYearChecked(FmsLuBudgetYear budgetYear);

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemChecked(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenterChecked(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findBySystemChecked(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterChecked(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findByRequestCodeChecked(FmsOperatingBudget1 fmsOperatingBudget1);

    public List<FmsOperatingBudget1> findAllRequestApproved();

    public ArrayList<FmsOperatingBudget1> findByBudgetYearApproved(FmsLuBudgetYear budgetYear);

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemApproved(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndCostCenterApproved(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findBySystemApproved(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterApproved(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findByRequestCodeApproved(FmsOperatingBudget1 fmsOperatingBudget1);

    public List<FmsOperatingBudget1> findAllRequestAuthorized();

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAuthorized(FmsLuBudgetYear budgetYear);

    public ArrayList<FmsOperatingBudget1> findByBudgetYearAndSystemAuthorized(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter);

    public List<FmsOperatingBudget1> findByBudgetYearAndCostCenterAuthorized(FmsLuBudgetYear budgetYear, FmsCostcSystemJunction ccSsJunction);

    public ArrayList<FmsOperatingBudget1> findBySystemAuthorized(FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> findBySystemAndCostCenterAuthorized(FmsCostcSystemJunction ccSsJunction);

    public ArrayList<FmsOperatingBudget1> findByRequestCodeAuthorized(FmsOperatingBudget1 fmsOperatingBudget1);

    public ArrayList<FmsOperatingBudget1> fetchByOBforProcessOnReq(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> fetchByOBforProcessPrepared(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> fetchByOBsChecked(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> fetchByOBsApproved(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);

    public ArrayList<FmsOperatingBudget1> fetchByOBsAuthorized(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter);
    
    public FmsOperatingBudget1 fetchByRequestCode(FmsOperatingBudget1 fmsOperatingBudget1);
    
    public FmsOperatingBudget1 findByRequestCodeOnRequest(FmsOperatingBudget1 fmsOperatingBudget1);

    public ArrayList<FmsOperatingBudget1> findByCostCenterAuthorized(FmsCostCenter fmsCostCenter);
    //</editor-fold>
   
    
   
    
   
    
}
