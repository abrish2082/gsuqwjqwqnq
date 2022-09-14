/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;

/**
 *
 * @author AB
 */
@Local
public interface FmsAccountingPeriodBeanLocal {

    public void create(FmsAccountingPeriod accountingPeriod);

    public void edit(FmsAccountingPeriod fmsAccountingPeriodEnty);

    public List<FmsAccountingPeriod> searchAccountingPeriodStatus(FmsAccountingPeriod searchAccountingPeriodStatus);

    public List<FmsAccountingPeriod> findAll();

    public List<FmsLuBudgetYear> Accountperiod();

    public List<String> getNonFinalisedAccountPeriod();

    public FmsAccountingPeriod findAccountingPeriodByBudjetYear(FmsLuBudgetYear budgetYear);

    public FmsAccountingPeriod getCurretActivePeriod();

}
