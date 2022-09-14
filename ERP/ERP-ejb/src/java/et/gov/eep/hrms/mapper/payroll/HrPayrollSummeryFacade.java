/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollSummery;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author memube
 */
@Stateless
public class HrPayrollSummeryFacade extends AbstractFacade<HrPayrollSummery> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollSummeryFacade() {
        super(HrPayrollSummery.class);
    }
     public List<HrPayrollSummery> getPayrollMonthlyTrn() {
        Query query = em.createNamedQuery("HrPayrollSummery.findByStatus");
        query.setParameter("status", 0);
        try {
            return (List<HrPayrollSummery>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
}
