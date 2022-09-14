/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.massSalaryIncrement;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.massSalaryIncrement.HrSalaryIncrementDetails;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Behailu
 */
@Stateless
public class HrSalaryIncrementDetailsFacade extends AbstractFacade<HrSalaryIncrementDetails> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSalaryIncrementDetailsFacade() {
        super(HrSalaryIncrementDetails.class);
    }
    
}
