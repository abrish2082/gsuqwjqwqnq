/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollCourtCaseInfo;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollCourtCaseInfoFacade extends AbstractFacade<HrPayrollCourtCaseInfo> {

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
    public HrPayrollCourtCaseInfoFacade() {
        super(HrPayrollCourtCaseInfo.class);
    }

    public int updateFamilyStatus(HrEmployees emp, HrPayrollEarningDeductions ed) {
        Query q = em.createNamedQuery("HrPayrollCourtCaseInfo.activeOnlyOne");
        q.setParameter("id", emp.getId());
        q.setParameter("code", ed.getCode());
        return q.executeUpdate();

    }

    public List<HrPayrollCourtCaseInfo> findByEmpId(HrEmployees emp) {
        Query q = em.createNamedQuery("HrPayrollCourtCaseInfo.findByEmpId");
        q.setParameter("id", emp.getId());
        if (q.getResultList().isEmpty()) {
            return null;
        } else {

            return (List<HrPayrollCourtCaseInfo>) q.getResultList();
        }

    }

}
