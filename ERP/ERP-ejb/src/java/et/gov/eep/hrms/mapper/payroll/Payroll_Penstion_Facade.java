
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;


import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.payroll.FmsPayrollPension;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Musie
 */
@Stateless
public class Payroll_Penstion_Facade extends AbstractFacade<FmsPayrollPension> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    EntityManager em;

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
    public Payroll_Penstion_Facade() {
        super(FmsPayrollPension.class);
    }

    /**
     *
     * @return
     */
    public  List<FmsPayrollPension> searchPayroll_Penstion() {
       List<FmsPayrollPension>  hrPayrollPension = null;
        try {
            Query query = em.createNamedQuery("FmsPayrollPension.findByMakeAsCurrentPension", FmsPayrollPension.class);
            query.setParameter("makeAsCurrentPension", 1);
            hrPayrollPension = (List<FmsPayrollPension> ) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hrPayrollPension;
    }

}
