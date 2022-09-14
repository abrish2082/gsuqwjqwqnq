/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.admin;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;

/**
 *
 * @author AB
 */
@Stateless
public class FmsAccountingPeriodFacade extends AbstractFacade<FmsAccountingPeriod> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsAccountingPeriodFacade() {
        super(FmsAccountingPeriod.class);
    }

    /**
     * get Current Active Period
     *
     * @return accounting Periods List
     */
    public FmsAccountingPeriod getCurretActivePeriod() {
        try {
            int status = 1;
            Query query = em.createNamedQuery("FmsAccountingPeriod.findByStatusActivePeriod", FmsAccountingPeriod.class);
            query.setParameter("status", status);
            if (query.getResultList().size() > 0) {
                FmsAccountingPeriod accountingPeriodsList = (FmsAccountingPeriod) query.getResultList().get(0);
                System.err.println(accountingPeriodsList.getActivePeriod());
                return accountingPeriodsList;
            }
            return null;

        } catch (Exception e) {
            return null;
        }

    }

    //get month in string 
    private String getMonthString(String month) {
        String selectedmonth = "";
        if (month.equals("1")) {
            selectedmonth = "July";
        } else if (month.equals("2")) {
            selectedmonth = "August";
        } else if (month.equals("3")) {
            selectedmonth = "September";
        } else if (month.equals("4")) {
            selectedmonth = "October";
        } else if (month.equals("5")) {
            selectedmonth = "November";
        } else if (month.equals("6")) {
            selectedmonth = "December";
        } else if (month.equals("7")) {
            selectedmonth = "January";
        } else if (month.equals("8")) {
            selectedmonth = "February";
        } else if (month.equals("9")) {
            selectedmonth = "March";
        } else if (month.equals("10")) {
            selectedmonth = "April";
        } else if (month.equals("11")) {
            selectedmonth = "May";
        } else if (month.equals("12")) {
            selectedmonth = "June";
        }
        return selectedmonth;
    }

    /**
     * update Accounting Period Status return updated status
     */
    public void updateAccountingPeriodStatus() {
        Query query = em.createNamedQuery("FmsAccountingPeriod.findByStatusUPDATE", FmsAccountingPeriod.class);
        query.executeUpdate();

    }

    /**
     *
     * @param searchAccountingPeriodStatus
     * @return accountingPeriodsList
     */
    public List<FmsAccountingPeriod> searchAccountingPeriodStatus(FmsAccountingPeriod searchAccountingPeriodStatus) {
        List<FmsAccountingPeriod> accountingPeriodsList = null;
        try {
            Query query = em.createNamedQuery("FmsAccountingPeriod.findByStatus", FmsAccountingPeriod.class);
            query.setParameter("status", searchAccountingPeriodStatus.getStatus() + "%");
            accountingPeriodsList = (List<FmsAccountingPeriod>) query.getResultList();
            return accountingPeriodsList;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * get Non Finalized Account Period
     *
     * @return accounting Periods
     */
    public List<String> getNonFinalisedAccountPeriod() {
        try {

            int status = 1;
            Query query = em.createNamedQuery("FmsAccountingPeriod.findByStatusActivePeriod", FmsAccountingPeriod.class);
            query.setParameter("status", status);
            if (query.getResultList().size() > 0) {
                List<String> accountingPeriods = new ArrayList<>();
                FmsAccountingPeriod accountingPeriodsList = (FmsAccountingPeriod) query.getResultList().get(0);

                accountingPeriods.add(accountingPeriodsList.getStartDate() + " TO " + accountingPeriodsList.getEndDate() + "\n " + getMonthString(accountingPeriodsList.getActivePeriod()));
                String splitedStartYear[] = accountingPeriodsList.getStartDate().split("-");
                String splitedEndYear[] = accountingPeriodsList.getEndDate().split("-");
                String trCode = splitedStartYear[0] + "_" + splitedEndYear[0] + "_" + accountingPeriodsList.getActivePeriod();
                accountingPeriods.add(accountingPeriodsList.getStartDate() + "~" + accountingPeriodsList.getEndDate() + "~" + trCode);

                return accountingPeriods;
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * get Active Year
     *
     * @return Budget Year
     */
    public List<FmsLuBudgetYear> getActiveYear() {
        List<FmsLuBudgetYear> fms = null;
        Query query = em.createNamedQuery("FmsAccountingPeriod.findByBudjetYear");
        try {
            List<FmsLuBudgetYear> BudgetYear = query.getResultList();
            return BudgetYear;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * get Active Period
     *
     *
     * @return accounting Periods List
     */
    public FmsAccountingPeriod getActivePeriod() {
        try {
            int status = 1;
            Query query = em.createNamedQuery("FmsAccountingPeriod.findByStatusActivePeriod", FmsAccountingPeriod.class);
            query.setParameter("status", status);
            if (query.getResultList().size() > 0) {
                FmsAccountingPeriod accountingPeriodsList = (FmsAccountingPeriod) query.getResultList().get(0);
                System.err.println(accountingPeriodsList.getActivePeriod());
                return accountingPeriodsList;
            }
            return null;

        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @param budgetYear
     * @return
     */
    public FmsAccountingPeriod findAccountingPeriodByBudjetYear(FmsLuBudgetYear budgetYear) {
        Query query = em.createNamedQuery("FmsAccountingPeriod.findByBudjetYear");
        query.setParameter("budgetYear", budgetYear.getLuBudgetYearId());
        try {
            if (query.getResultList().size() > 0) {
                FmsAccountingPeriod fap = (FmsAccountingPeriod) query.getResultList().get(0);
                return fap;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }
    /*
     accept period string 
     finding period
     return accounting Periods List
     */

    public FmsAccountingPeriod getPeriod(String period) {
        try {
            int status = 1;
            Query query = em.createNamedQuery("FmsAccountingPeriod.findByPeiod", FmsAccountingPeriod.class);
            query.setParameter("startDate", period);
            query.setParameter("endDate", period);

            if (query.getResultList().size() > 0) {
                FmsAccountingPeriod accountingPeriodsList = (FmsAccountingPeriod) query.getResultList().get(0);
                System.err.println(accountingPeriodsList.getActivePeriod());
                return accountingPeriodsList;
            }
            return null;

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * find all
     *
     * @return all Period
     */
    public ArrayList<FmsAccountingPeriod> findAll() {
        Query query = em.createNamedQuery("FmsAccountingPeriod.findAll");
        try {
            ArrayList<FmsAccountingPeriod> prds = new ArrayList(query.getResultList());
            return prds;
        } catch (Exception ex) {
        }
        return null;
    }

    /*get Active Period
     * return period
     */
    public ArrayList<FmsAccountingPeriod> before() {
        Query query = em.createNamedQuery("FmsAccountingPeriod.findAll_before");
        try {
            ArrayList<FmsAccountingPeriod> prds = new ArrayList(query.getResultList());
            return prds;
        } catch (Exception ex) {
        }
        return null;
    }

    /*
     array list finding period id
     return period
     */
    public ArrayList<FmsAccountingPeriod> After() {
        Query query = em.createNamedQuery("FmsAccountingPeriod.findByAcountigPeriodId_after");
        try {
            ArrayList<FmsAccountingPeriod> prds = new ArrayList(query.getResultList());
            return prds;
        } catch (Exception ex) {
        }
        return null;
    }

}
