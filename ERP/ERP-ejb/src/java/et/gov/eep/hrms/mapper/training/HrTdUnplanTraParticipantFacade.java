/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdUnplanTraParticipant;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Abdi_Pc
 */
@Stateless
public class HrTdUnplanTraParticipantFacade extends AbstractFacade<HrTdUnplanTraParticipant> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdUnplanTraParticipantFacade() {
        super(HrTdUnplanTraParticipant.class);
    }

    public HrTdUnplanTraParticipant getSelectedRequest(BigDecimal id) {
          Query query = em.createNamedQuery("HrTdUnplanTraParticipant.findById");
        query.setParameter("id", id);
        try {
            HrTdUnplanTraParticipant selectedTraining = (HrTdUnplanTraParticipant) query.getResultList().get(0);
            return selectedTraining;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
