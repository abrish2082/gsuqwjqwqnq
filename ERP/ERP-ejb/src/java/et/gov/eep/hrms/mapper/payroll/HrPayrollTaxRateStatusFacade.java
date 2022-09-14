/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRateStatus;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;

/**
 *
 * @author user
 */
@Stateless
public class HrPayrollTaxRateStatusFacade extends AbstractFacade<HrPayrollTaxRateStatus> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPayrollTaxRateStatusFacade() {
        super(HrPayrollTaxRateStatus.class);
    }

}
