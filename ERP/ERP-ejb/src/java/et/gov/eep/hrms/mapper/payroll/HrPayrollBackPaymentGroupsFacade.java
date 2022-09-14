/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.payroll.HrPayrollBackPaymentGroups;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless//extends AbstractFacade<HrPayrollCourtCaseInfo> implements HrPayrollCourtCaseInfoFacadeLocal {
public class HrPayrollBackPaymentGroupsFacade extends AbstractFacade<HrPayrollBackPaymentGroups> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollBackPaymentGroupsFacade() {
        super(HrPayrollBackPaymentGroups.class);
    }

    public HrPayrollBackPaymentGroups findById(HrPayrollBackPaymentGroups pg) {

        try {
            Query q = em.createNamedQuery("HrPayrollBackPaymentGroups.findById");
            q.setParameter("id", pg.getId());
            if (q.getResultList().isEmpty()) {
                return null;

            } else {
                return (HrPayrollBackPaymentGroups) q.getSingleResult();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
