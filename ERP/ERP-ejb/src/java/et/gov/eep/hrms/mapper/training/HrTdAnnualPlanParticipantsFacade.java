/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdAnnualPlanParticipants;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdAnnualPlanParticipantsFacade extends AbstractFacade<HrTdAnnualPlanParticipants> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdAnnualPlanParticipantsFacade() {
        super(HrTdAnnualPlanParticipants.class);
    }
    
}
