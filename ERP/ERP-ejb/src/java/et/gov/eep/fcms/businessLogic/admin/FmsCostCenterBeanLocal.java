/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.entity.organization.HrDepartments;

/**
 *
 * @author Binyam
 */
@Local
public interface FmsCostCenterBeanLocal {

    public void create(FmsCostCenter fmsCostCenter);

    public void edit(FmsCostCenter fmsCostCenter);

    public void deleteCC(FmsCostCenter fmsCostCenter);

    public FmsCostCenter getCCDetail(FmsCostCenter fmsCostCenter);

    public FmsCostCenter getCostDetail(FmsCostCenter fmsCostCenter);

    public FmsCostCenter searchbydepidOfcostcent(HrDepartments departmentId);

    public FmsCostCenter findbyDepId(HrDepartments departmentId);

    public FmsCostCenter getCostCenterId(FmsCostCenter costCenter);

    public FmsLuSystem findLuSystem(FmsLuSystem fmsLuSystem);

    public List<FmsCostCenter> searchCostCenter(FmsLuSystem fmsLuSystem);

    public List<FmsCostCenter> findAll();

    public List<FmsCostCenter> findCostCenter(FmsLuSystem fmsLuSystem);

    public List<FmsCostCenter> findCostCenterForRequest(FmsLuSystem fmsLuSystem);

    public List<FmsCostCenter> findCapitalBudgetList(FmsLuSystem fmsLuSystem);

    public List<FmsCostCenter> searcheCostCenterForDisburs(FmsLuSystem fmsLuSystem);

    public List<FmsCostCenter> searchBudgetDisbursment(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear);

    public List<FmsCostCenter> searchBudgetRequest(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear);

    public List<FmsCostCenter> searchBudgetControl(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear);

    public List<FmsCostCenter> searchBudgetCapital(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear);

    public List<FmsCostCenter> searchBudgetCapitalDisbursment(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear);

    public List<FmsCostCenter> searchCost(FmsCostCenter fmsCostCenter);

    public List<FmsCostCenter> findUnmappedCostCenter(FmsLuSystem fmsLuSystem);

    public List<FmsCostCenter> findMappedCostCenter(FmsLuSystem fmsLuSystem);

    public List<FmsCostCenter> findCostcenterlist1();

    public List<FmsCostCenter> searchAllVochNo(FmsCostCenter fmsCostCenter);
}
