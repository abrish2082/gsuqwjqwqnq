/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import et.gov.eep.hrms.mapper.payroll.HrPayrollPeriodsFacade;

/**
 *
 * @author GLORY
 */
@Stateless
@LocalBean
public class HrPayrollPeriodsBean implements HrPayrollPeriodsBeanLocal {

    @EJB
    HrPayrollPeriodsFacade hrPayrollPeriodsFacade;

    @Override
    public void create(HrPayrollPeriods hrPayrollPeriods) {
        hrPayrollPeriodsFacade.create(hrPayrollPeriods);
    }

    @Override
    public void edit(HrPayrollPeriods hrPayrollPeriods) {
        hrPayrollPeriodsFacade.edit(hrPayrollPeriods);
    }

    @Override
    public void remove(HrPayrollPeriods hrPayrollPeriods) {
        hrPayrollPeriodsFacade.remove(hrPayrollPeriods);
    }

    @Override
    public HrPayrollPeriods find(Object id) {
        return hrPayrollPeriodsFacade.find(id);
    }

    @Override
    public List<HrPayrollPeriods> findAll() {
        return hrPayrollPeriodsFacade.findAll();
    }

    @Override
    public List<HrPayrollPeriods> loadPayrollDates() {
        return hrPayrollPeriodsFacade.loadPayrollDates();
    }

    @Override
    public List<HrPayrollPeriods> findRange(int[] range) {
        return hrPayrollPeriodsFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrPayrollPeriodsFacade.count();
    }

    @Override
    public int updatePayrollPeriod() {
        return hrPayrollPeriodsFacade.updatePayrollPeriod();
    }

    @Override
    public int finalizePayroll(HrPayrollPeriods hrPayrollPeriods) {
        return hrPayrollPeriodsFacade.finalizePayroll(hrPayrollPeriods);
    }

    @Override
    public HrPayrollPeriods findPayrollPeriod(HrPayrollPeriods hrPayrollPeriods) {
        return hrPayrollPeriodsFacade.findPayrollPeriod(hrPayrollPeriods);
    }

    @Override
    public List<HrPayrollPeriods> loadListOfInactivePayrollDates() {
        return hrPayrollPeriodsFacade.loadListOfInactivePayrollDates();
    }

    @Override
    public HrPayrollPeriods checkPayrollDate(HrPayrollPeriods hrPayrollPeriods) {
        return hrPayrollPeriodsFacade.checkPayrollDate(hrPayrollPeriods);
    }

    @Override
    public HrPayrollPeriods activePayrollDate() {
        return hrPayrollPeriodsFacade.activePayrollDate();
    }

    @Override
    public List<HrPayrollPeriods> loadListOfActivePayrollDates() {
        return hrPayrollPeriodsFacade.loadListOfActivePayrollDates();
    }

    @Override
    public HrPayrollPeriods checkDateRepetition(String date) {
        return hrPayrollPeriodsFacade.checkDateRepetition(date);
    }

    @Override
    public int activateOnlyOneMonth() {
        return hrPayrollPeriodsFacade.activateOnlyOneMonth();
    }

    @Override
    public HrPayrollPeriods lastFinalizedPayroll() {
        return hrPayrollPeriodsFacade.lastFinalizedPayroll();
    }

    @Override
    public HrPayrollPeriods lastPayrollDate() {
        return hrPayrollPeriodsFacade.lastPayrollDate();
    }

    @Override
    public List<HrPayrollPeriods> payrollFrom(String year, String month) {
        return hrPayrollPeriodsFacade.payrollFrom(year, month);
    }

    @Override
    public List<HrPayrollPeriods> payrollTo(String year, String month) {
        return hrPayrollPeriodsFacade.payrollTo(year, month);
    }

    @Override
    public void test(HrPayrollPeriods hrPayrollPeriods) {
        hrPayrollPeriodsFacade.test(hrPayrollPeriods);
    }

    @Override
    public HrPayrollPeriods loadMaxDateForPayroll() {
        return hrPayrollPeriodsFacade.loadMaxDateForPayroll();
    }

    @Override
    public HrPayrollPeriods findPayrollPeriodById(int id) {
        return hrPayrollPeriodsFacade.findPayrollPeriodById(id);
    }

}
