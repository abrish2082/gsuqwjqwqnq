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
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.mapper.admin.FmsAccountingPeriodFacade;

/**
 *
 * @author AB
 */
@Stateless
public class FmsAccountingPeriodBean implements FmsAccountingPeriodBeanLocal {
    
//<editor-fold defaultstate="collapsed" desc="@EJB">
    @EJB
    private FmsAccountingPeriodFacade accountingPeriodFacadeLocal;
//</editor-fold>
    /**
     *
     * @param accountingPeriod
     */
    @Override
    public void create(FmsAccountingPeriod accountingPeriod) {
        accountingPeriodFacadeLocal.updateAccountingPeriodStatus();
        accountingPeriodFacadeLocal.create(accountingPeriod);

    }

    /**
     *
     * @param fmsAccountingPeriodEnty
     */
    @Override
    public void edit(FmsAccountingPeriod fmsAccountingPeriodEnty) {
        accountingPeriodFacadeLocal.edit(fmsAccountingPeriodEnty);

    }

    /**
     *
     * @param searchAccountingPeriodStatus
     * @return
     */
    @Override
    public List<FmsAccountingPeriod> searchAccountingPeriodStatus(FmsAccountingPeriod searchAccountingPeriodStatus) {
        return accountingPeriodFacadeLocal.searchAccountingPeriodStatus(searchAccountingPeriodStatus);

    }

    /**
     *
     * @return
     */
    @Override
    public FmsAccountingPeriod getCurretActivePeriod() {
        return accountingPeriodFacadeLocal.getCurretActivePeriod();

    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsAccountingPeriod> findAll() {
        return accountingPeriodFacadeLocal.findAll();

    }

    /**
     *
     * @return
     */
    @Override
    public List<String> getNonFinalisedAccountPeriod() {
        return accountingPeriodFacadeLocal.getNonFinalisedAccountPeriod();

    }

    /**
     *
     * @param budgetYear
     * @return
     */
    @Override
    public FmsAccountingPeriod findAccountingPeriodByBudjetYear(FmsLuBudgetYear budgetYear) {
        return accountingPeriodFacadeLocal.findAccountingPeriodByBudjetYear(budgetYear);

    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsLuBudgetYear> Accountperiod() {
        return accountingPeriodFacadeLocal.getActiveYear();
    }
}
