/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.medical;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.medical.HrLocalMedTreatmentType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ob
 */
@Stateless
public class HrLocalMedTreatmentTypeFacade extends AbstractFacade<HrLocalMedTreatmentType> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLocalMedTreatmentTypeFacade() {
        super(HrLocalMedTreatmentType.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">

    public boolean isTreatmentExist(HrLocalMedTreatmentType hrLocalMedTreatmentType) {
        boolean isExist;
        Query query = em.createNamedQuery("HrLocalMedTreatmentType.findByTreatmentType");
        query.setParameter("treatmentType", hrLocalMedTreatmentType.getTreatmentType());
        try {
            if (query.getResultList().size() > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
            return isExist;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<HrLocalMedTreatmentType> findByTreatmentType(String treatment) {
        Query query = em.createNamedQuery("HrLocalMedTreatmentType.findByTreatmentType");
        query.setParameter("treatmentType", treatment);
        try {
            return (List<HrLocalMedTreatmentType>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrLocalMedTreatmentType findById(HrLocalMedTreatmentType hrLocalMedTreatmentType) {
        Query query = em.createNamedQuery("HrLocalMedTreatmentType.findById");
        query.setParameter("id", hrLocalMedTreatmentType.getId());
        try {
            return (HrLocalMedTreatmentType) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

}
