/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.payroll.FmsPayrollTaxRates;


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
public class Payroll_TaxRate_Facade extends AbstractFacade<FmsPayrollTaxRates> {

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
    public Payroll_TaxRate_Facade() {
        super(FmsPayrollTaxRates.class);
    }

    /**
     *
     * @return
     */
    public List<FmsPayrollTaxRates> getListofTaxRate() {
        List<FmsPayrollTaxRates> hrPayrollTaxRateses = null;
        Query query = em.createNamedQuery("FmsPayrollTaxRates.findAll", FmsPayrollTaxRates.class);
        hrPayrollTaxRateses = query.getResultList();
        return hrPayrollTaxRateses;
    }

}
