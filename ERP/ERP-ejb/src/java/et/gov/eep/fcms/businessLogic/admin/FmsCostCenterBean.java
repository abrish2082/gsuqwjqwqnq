/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.mapper.admin.FmsCostCenterFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;

/**
 *
 * @author Binyam
 */
@Stateless
public class FmsCostCenterBean implements FmsCostCenterBeanLocal {

//<editor-fold defaultstate="collapsed" desc="@EJB">
    @EJB
    FmsCostCenterFacade fmsCostCenterFacade;
//</editor-fold>

    /**
     *
     * @return
     */
    @Override
    public List<FmsCostCenter> findAll() {
        return fmsCostCenterFacade.findAll();
    }

    /**
     *
     * @param fmsLuSystem
     * @return
     */
    @Override
    public List<FmsCostCenter> findCostCenter(FmsLuSystem fmsLuSystem) {
        return fmsCostCenterFacade.findCostCenterName(fmsLuSystem);
    }

    /**
     *
     * @param fmsLuSystem
     * @return
     */
    @Override
    public List<FmsCostCenter> findCapitalBudgetList(FmsLuSystem fmsLuSystem) {
        return fmsCostCenterFacade.findCapitalBudgetList(fmsLuSystem);
    }

    /**
     *
     * @param fmsLuSystem
     * @return
     */
    @Override
    public FmsLuSystem findLuSystem(FmsLuSystem fmsLuSystem) {
        return fmsCostCenterFacade.findSystem(fmsLuSystem);
    }

    /**
     *
     * @param fmsCostCenter
     */
    @Override
    public void create(FmsCostCenter fmsCostCenter) {
        fmsCostCenterFacade.create(fmsCostCenter);
    }

    /**
     *
     * @param fmsCostCenter
     */
    @Override
    public void edit(FmsCostCenter fmsCostCenter) {
        fmsCostCenterFacade.edit(fmsCostCenter);
    }

    /**
     *
     * @param fmsCostCenter
     */
    @Override
    public void deleteCC(FmsCostCenter fmsCostCenter) {
        fmsCostCenterFacade.remove(fmsCostCenter);
    }

    /**
     *
     * @param fmsCostCenter
     * @return
     */
    @Override
    public FmsCostCenter getCCDetail(FmsCostCenter fmsCostCenter) {
        return fmsCostCenterFacade.getCostCenter(fmsCostCenter);
    }

    /**
     *
     * @param fmsLuSystem
     * @return
     */
    @Override
    public List<FmsCostCenter> searchCostCenter(FmsLuSystem fmsLuSystem) {
        return fmsCostCenterFacade.getCCbySystem(fmsLuSystem);
    }

    /**
     *
     * @param fmsCostCenter
     * @return
     */
    @Override
    public FmsCostCenter getCostDetail(FmsCostCenter fmsCostCenter) {
        return fmsCostCenterFacade.getCostCenter(fmsCostCenter);
    }

    /**
     *
     * @param fmsLuSystem
     * @return
     */
    @Override
    public List<FmsCostCenter> searcheCostCenterForDisburs(FmsLuSystem fmsLuSystem) {
        return fmsCostCenterFacade.findApprovedCostCenterName(fmsLuSystem);
    }

    /**
     *
     * @param fmsLuSystem
     * @return
     */
    @Override
    public List<FmsCostCenter> findCostCenterForRequest(FmsLuSystem fmsLuSystem) {
        return fmsCostCenterFacade.findCostCenterNameForRequest(fmsLuSystem);
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    @Override
    public List<FmsCostCenter> searchBudgetDisbursment(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        return fmsCostCenterFacade.findCostCenterNameForDisb(fmsLuSystem, budgetYear);
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    @Override
    public List<FmsCostCenter> searchBudgetRequest(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        return fmsCostCenterFacade.findBudgetRequest(fmsLuSystem, budgetYear);
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    @Override
    public List<FmsCostCenter> searchBudgetControl(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        return fmsCostCenterFacade.findBudgetControl(fmsLuSystem, budgetYear);
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    @Override
    public List<FmsCostCenter> searchBudgetCapital(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        return fmsCostCenterFacade.findCapitalRequest(fmsLuSystem, budgetYear);
    }

    /**
     *
     * @param fmsLuSystem
     * @param budgetYear
     * @return
     */
    @Override
    public List<FmsCostCenter> searchBudgetCapitalDisbursment(FmsLuSystem fmsLuSystem, FmsLuBudgetYear budgetYear) {
        return fmsCostCenterFacade.findCapitalDisbursment(fmsLuSystem, budgetYear);
    }

    @Override
    public List<FmsCostCenter> searchCost(FmsCostCenter fmsCostCenter) {
        return fmsCostCenterFacade.findCostCodeLike(fmsCostCenter);
    }

    //mahi
    /**
     *
     * @param departmentId
     * @return
     */
    @Override
    public FmsCostCenter searchbydepidOfcostcent(HrDepartments departmentId) {
        return fmsCostCenterFacade.findbydepidOfcostcent(departmentId);
    }

    @Override
    public FmsCostCenter getCostCenterId(FmsCostCenter costCenter) {
        return fmsCostCenterFacade.getCostCenterId(costCenter);
    }

    @Override
    public FmsCostCenter findbyDepId(HrDepartments departmentId) {
        return fmsCostCenterFacade.findbyDepId(departmentId);
    }

    @Override
    public List<FmsCostCenter> findUnmappedCostCenter(FmsLuSystem fmsLuSystem) {
        return fmsCostCenterFacade.findUnmappedCostCenterName(fmsLuSystem);
    }

    @Override
    public List<FmsCostCenter> findMappedCostCenter(FmsLuSystem fmsLuSystem) {
        return fmsCostCenterFacade.findMappedCostCenterName(fmsLuSystem);
    }

    @Override
    public List<FmsCostCenter> findCostcenterlist1() {
        return fmsCostCenterFacade.getFmsCostCenterSearchingParameterList();
    }
    @Override
    public List<FmsCostCenter> searchAllVochNo(FmsCostCenter fmsCostCenter){
        return fmsCostCenterFacade.getVoch1No(fmsCostCenter);
    }
}
