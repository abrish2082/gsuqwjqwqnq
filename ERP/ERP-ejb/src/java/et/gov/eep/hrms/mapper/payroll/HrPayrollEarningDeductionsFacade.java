/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollEarningDeductionsFacade extends AbstractFacade<HrPayrollEarningDeductions> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollEarningDeductionsFacade() {
        super(HrPayrollEarningDeductions.class);
    }

    public List<HrPayrollEarningDeductions> loadListOfEarnings() {
        try {

            Query query = em.createNamedQuery("HrPayrollEarningDeductions.findByType");
            query.setParameter("type", "Earning");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrPayrollEarningDeductions> loadOnlyEarnings() {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.loadOnlyEarnings");
            query.setParameter("type", "Earning");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    //
    public List<HrPayrollEarningDeductions> loadAllowancesForLocation() {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.loadAllowacesLocation");
            query.setParameter("type", "Earning");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollEarningDeductions> loadEdForLocation() {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.loadEDForAllowanceLocation");
            query.setParameter("type", "Earning");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollEarningDeductions> loadEdForJObTitle() {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.loadEDForAllowanceJobTitle");
            query.setParameter("type", "Earning");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollEarningDeductions> loadAllEar() {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.loadOnlyAllowanceEar");
            query.setParameter("type", "Earning");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollEarningDeductions> listOfEarnngForAllEmp() {

        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.listOfEarningForAllEmp");
            query.setParameter("type", "Earning");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrPayrollEarningDeductions> listOfOtTypes() {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.listOtTypes");
            query.setParameter("type", "Earning");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrPayrollEarningDeductions> listOfDeductionsForAllEmp() {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.listOfEarningForAllEmp");
            query.setParameter("type", "Deduction");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrPayrollEarningDeductions> loadListOfDeductions() {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.findByType");
            query.setParameter("type", "Deduction");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollEarningDeductions> loadOnlyDeductions() {
        try {

            Query query = em.createNamedQuery("HrPayrollEarningDeductions.loadOnlyDeductions");
            query.setParameter("type", "Deduction");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrPayrollEarningDeductions> loadAllEmpEarnings() {
        try {

            Query query = em.createNamedQuery("HrPayrollEarningDeductions.loadAllEmpEarnings");
            query.setParameter("type", "Earning");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrPayrollEarningDeductions> loadAllEmpDeductions() {
        try {

            Query query = em.createNamedQuery("HrPayrollEarningDeductions.loadAllEmpDeductions");
            query.setParameter("type", "Deduction");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrPayrollEarningDeductions> loadEarningAndDeductions() {
        try {

            Query query = em.createNamedQuery("HrPayrollEarningDeductions.loadEarningAndDeductions");

            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<HrPayrollEarningDeductions> loadAllEmployeesEd() {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.findByCriterias");
            query.setParameter("criterias", "All Employees Earning Deduction");
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return query.getResultList();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public HrPayrollEarningDeductions cheakErningDeductionCriterias(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.checkRepationCriterias");
            query.setParameter("criterias", hrPayrollEarningDeductions.getCriterias());

            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrPayrollEarningDeductions) query.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollEarningDeductions cheakItemCode(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.searchByItemCode11");

            query.setParameter("itemCode", hrPayrollEarningDeductions.getItemCode());
            if (query.getResultList().isEmpty()) {
                return null;
            } else {

                return (HrPayrollEarningDeductions) query.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollEarningDeductions cheakItemCodeForUpdate(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.searchByItemCodeForUpdate");
            BigDecimal big = new BigDecimal("422");
            query.setParameter("itemCode", hrPayrollEarningDeductions.getItemCode());
            query.setParameter("code", hrPayrollEarningDeductions.getCode());
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrPayrollEarningDeductions) query.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollEarningDeductions findCriteriaInfo(String criteria) {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.findByCriterias");
            query.setParameter("criterias", criteria);
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrPayrollEarningDeductions) query.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnNumberOfMonths(String sDate, String eDate) {
        String y = (String) em.createNativeQuery("SELECT RETURN_MONTH_DIFF(?1,?2) FROM DUAL").setParameter(1, sDate).setParameter(2, eDate)
                .getSingleResult();
        return y;
    }

    public ArrayList<HrPayrollEarningDeductions> taxTypeList() {
        Query query = em.createNamedQuery("HrPayrollEarningDeductions.taxReportCriterias");
        try {
            ArrayList<HrPayrollEarningDeductions> taxList = new ArrayList(query.getResultList());

            return taxList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPayrollEarningDeductions> taxTypeLists() {
        Query query = em.createNamedQuery("HrPayrollEarningDeductions.taxReportCriterias");
        return query.getResultList();
    }

    public HrPayrollEarningDeductions getTaxType(HrPayrollEarningDeductions taxType) {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.findByCriterias");
            query.setParameter("criterias", taxType.getCriterias());
            return (HrPayrollEarningDeductions) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPayrollEarningDeductions findByCode(HrPayrollEarningDeductions code) {
        try {
            Query query = em.createNamedQuery("HrPayrollEarningDeductions.findByCode");
            query.setParameter("code", code.getCode());
            return (HrPayrollEarningDeductions) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
