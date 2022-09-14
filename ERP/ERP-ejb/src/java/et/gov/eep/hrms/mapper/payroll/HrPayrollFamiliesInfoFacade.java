/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollFamiliesInfo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollFamiliesInfoFacade extends AbstractFacade<HrPayrollFamiliesInfo> {

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

    /**
     *
     */
    public HrPayrollFamiliesInfoFacade() {
        super(HrPayrollFamiliesInfo.class);
    }

    public int updateFamilyStatus(HrEmployees emp, HrPayrollEarningDeductions ed) {
        Query q = em.createNamedQuery("HrPayrollFamiliesInfo.activeOnlyOne");
        q.setParameter("id", emp.getId());
        q.setParameter("code", ed.getCode());
        return q.executeUpdate();
    }

    public List<HrPayrollFamiliesInfo> findByEmpId(HrEmployees emp) {
        Query q = em.createNamedQuery("HrPayrollFamiliesInfo.findByEmpId");
        q.setParameter("id", emp.getId());
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (List<HrPayrollFamiliesInfo>) q.getResultList();
        }
    }

}
