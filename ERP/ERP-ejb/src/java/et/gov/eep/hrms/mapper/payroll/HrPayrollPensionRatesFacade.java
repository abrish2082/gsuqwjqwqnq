/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.payroll.HrPayrollPensionRates;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollPensionRatesFacade extends AbstractFacade<HrPayrollPensionRates> {

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
    public HrPayrollPensionRatesFacade() {
        super(HrPayrollPensionRates.class);
    }

    /**
     *
     * @return
     */
    public int updatePensionRates() {
        try {
            Query q = em.createNamedQuery("HrPayrollPensionRates.pensionUpdate");
            return q.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }

    }

}
