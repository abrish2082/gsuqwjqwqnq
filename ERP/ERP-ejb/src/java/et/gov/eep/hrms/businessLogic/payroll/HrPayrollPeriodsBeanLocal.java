/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollPeriodsBeanLocal {

    public void create(HrPayrollPeriods hrPayrollPeriods);

    public void edit(HrPayrollPeriods hrPayrollPeriods);

    public void remove(HrPayrollPeriods hrPayrollPeriods);

    public HrPayrollPeriods find(Object id);

    public List<HrPayrollPeriods> findAll();

    public List<HrPayrollPeriods> loadPayrollDates();

    public List<HrPayrollPeriods> findRange(int[] range);

    public int count();

    public int updatePayrollPeriod();

    public int finalizePayroll(HrPayrollPeriods hrPayrollPeriods);

    public HrPayrollPeriods findPayrollPeriod(HrPayrollPeriods hrPayrollPeriods);

    public List<HrPayrollPeriods> loadListOfInactivePayrollDates();

    public HrPayrollPeriods checkPayrollDate(HrPayrollPeriods hrPayrollPeriods);

    public HrPayrollPeriods activePayrollDate();

    public List<HrPayrollPeriods> loadListOfActivePayrollDates();

    public HrPayrollPeriods checkDateRepetition(String date);

    public int activateOnlyOneMonth();

    public HrPayrollPeriods lastFinalizedPayroll();

    public HrPayrollPeriods lastPayrollDate();

    public List<HrPayrollPeriods> payrollFrom(String year, String month);

    public List<HrPayrollPeriods> payrollTo(String year, String month);

    public void test(HrPayrollPeriods hrPayrollPeriods);

    public HrPayrollPeriods loadMaxDateForPayroll();

    public HrPayrollPeriods findPayrollPeriodById(int id);
}
