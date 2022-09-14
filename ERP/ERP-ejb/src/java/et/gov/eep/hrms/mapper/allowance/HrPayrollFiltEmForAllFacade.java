/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.allowance;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.allowance.HrPayrollFiltEmForAll;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
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
public class HrPayrollFiltEmForAllFacade extends AbstractFacade<HrPayrollFiltEmForAll> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollFiltEmForAllFacade() {
        super(HrPayrollFiltEmForAll.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrPayrollFiltEmForAll> filterEmpByEd(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        try {
            Query q = em.createNamedQuery("HrPayrollFiltEmForAll.findFiltedEmpByEdCode");
            q.setParameter("code", hrPayrollEarningDeductions.getCode());
            return (List<HrPayrollFiltEmForAll>) q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public HrPayrollFiltEmForAll checkFilteredEmployees(HrEmployees emp, HrPayrollEarningDeductions ed) {
        try {
            Query q = em.createNamedQuery("HrPayrollFiltEmForAll.checkFilteredEmployees");
            q.setParameter("code", ed.getCode());
            q.setParameter("id", emp.getId());

            if (q.getResultList().isEmpty()) {
                return null;
            } else {
                return (HrPayrollFiltEmForAll) q.getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
//</editor-fold>
}
