/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuPayrollAePGroup;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollMaintainEdsFacade extends AbstractFacade<HrPayrollMaintainEds> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {

        return em;
    }

    public HrPayrollMaintainEdsFacade() {
        super(HrPayrollMaintainEds.class);
    }

    public HrPayrollMaintainEds cheackRepeatedEarningOrDed(String code, HrEmployees emp) {
        System.out.print("The code is on entity" + code);
        Query q = em.createNamedQuery("HrPayrollMaintainEds.checkED", HrPayrollMaintainEds.class);
        BigDecimal big = new BigDecimal(code);
        q.setParameter("code", big);
        q.setParameter("empId", emp);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (HrPayrollMaintainEds) q.getSingleResult();
        }
    }

    public HrPayrollMaintainEds cheackRepeatedAllEmpEarningOrDed(HrPayrollMaintainEds ed) {
        Query q = em.createNamedQuery("HrPayrollMaintainEds.cheackRepeatedAllEmpEd", HrPayrollMaintainEds.class);
        q.setParameter("code", ed.getEarningDeductionCode().getCode());
        q.setParameter("groupId", ed.getGroupId());
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            q.setFirstResult(0);
            q.setMaxResults(1);
            return (HrPayrollMaintainEds) q.getSingleResult();
        }
    }

    public HrPayrollMaintainEds returnSavedInfo(HrLuPayrollAePGroup ed) {
        Query q = em.createNamedQuery("HrPayrollMaintainEds.returnSavedEd", HrPayrollMaintainEds.class);
        q.setParameter("groupId", String.valueOf(ed.getId()));
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            q.setFirstResult(0);
            q.setMaxResults(1);
            return (HrPayrollMaintainEds) q.getSingleResult();
        }
    }

    public List<HrPayrollMaintainEds> loadEarningDeductions(String type, int id) {
        Query q = em.createNamedQuery("HrPayrollMaintainEds.findEarnngDeduction").setHint(QueryHints.CACHE_RETRIEVE_MODE, CacheRetrieveMode.BYPASS);
        q.setParameter("type", type);
        q.setParameter("id", id);
        return q.getResultList();
    }

    public List<HrPayrollMaintainEds> removeGroupedEarningDeductions(HrPayrollMaintainEds ed) {
        Query q = em.createNamedQuery("HrPayrollMaintainEds.removeEarningDeductions").setHint(QueryHints.CACHE_RETRIEVE_MODE, CacheRetrieveMode.BYPASS);
        q.setParameter("groupId", ed.getGroupId());
        q.setParameter("id", ed.getEmpId().getId());
        return q.getResultList();
    }
}
