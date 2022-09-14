/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.displine;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffencePenality;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenaltyTypes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrDisciplineOffencePenalityFacade extends AbstractFacade<HrDisciplineOffencePenality> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrDisciplineOffencePenalityFacade() {
        super(HrDisciplineOffencePenality.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrDisciplineOffencePenality> findByOffenceName(HrDisciplineOffenceTypes offenceTypes) {
        List<HrDisciplineOffencePenality> disciplinePenaltyTypeses = null;
        try {
            Query query = em.createNamedQuery("HrDisciplineOffencePenality.findByOffenceNameLike", HrDisciplineOffencePenality.class);
            query.setParameter("offenceName", offenceTypes.getOffenceName().toUpperCase() + '%');
            disciplinePenaltyTypeses = (List<HrDisciplineOffencePenality>) query.getResultList();
            return disciplinePenaltyTypeses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HrDisciplineOffencePenality FindByOffenceTypeAndRepition(HrDisciplineOffencePenality hrDisciplineOffencePenality) {
        HrDisciplineOffencePenality getPenality = new HrDisciplineOffencePenality();
        Query query = em.createNamedQuery("HrDisciplineOffencePenality.findByOffenceTypeAndRepition");
        query.setParameter("offenceTypeId", hrDisciplineOffencePenality.getOffenceTypeId());
        query.setParameter("repetition", hrDisciplineOffencePenality.getRepetition());
        try {
            getPenality = (HrDisciplineOffencePenality) query.getResultList().get(0);
            return getPenality;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrDisciplinePenaltyTypes findByOffenceTypeAndRepition(HrDisciplineOffencePenality hrDisciplineOffencePenality) {
        HrDisciplinePenaltyTypes getPenality = new HrDisciplinePenaltyTypes();
        Query query = em.createNamedQuery("HrDisciplineOffencePenality.findByOffenceTypeAndRepition");
        query.setParameter("offenceTypeId", hrDisciplineOffencePenality.getOffenceTypeId());
        query.setParameter("repetition", hrDisciplineOffencePenality.getRepetition());
        try {
            getPenality = (HrDisciplinePenaltyTypes) query.getResultList().get(0);
            return getPenality;
        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrDisciplineOffencePenality> getOffenceAndPenalityListByName(String offenceName) {
        Query query = em.createNativeQuery("SELECT * FROM HR_DISCIPLINE_OFFENCE_PENALITY dop \n"
                + "inner join HR_DISCIPLINE_OFFENCE_TYPES dot\n"
                + "on dop.OFFENCE_TYPE_ID=dot.ID where dot.OFFENCE_NAME='" + offenceName + "' ", HrDisciplineOffencePenality.class);
        try {
            return (List<HrDisciplineOffencePenality>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineOffenceTypes> findAllOffnces() {
        Query query = em.createNativeQuery("SELECT DISTINCT ot.OFFENCE_NAME\n"
                + "FROM HR_DISCIPLINE_OFFENCE_TYPES ot\n"
                + "INNER JOIN HR_DISCIPLINE_OFFENCE_PENALITY op\n"
                + "ON ot.ID = op.OFFENCE_TYPE_ID");
        try {
            return (List<HrDisciplineOffenceTypes>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
