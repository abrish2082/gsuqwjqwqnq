/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdIfEduc;
import et.gov.eep.hrms.entity.training.HrTdIfTrng;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Behailu
 */
@Stateless
public class HrTdIfTrngFacade extends AbstractFacade<HrTdIfTrng> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdIfTrngFacade() {
        super(HrTdIfTrng.class);
    }

    public List<HrTdIfTrng> filterTrainingByLocation(int filterstatus) {
        Query query = em.createNamedQuery("HrTdIfTrng.findByLocation");
        query.setParameter("location", filterstatus);
            return (List<HrTdIfTrng>) query.getResultList();
    }

    public HrTdIfTrng loadInlandAndForeignTraining(BigDecimal id) {
        Query query = em.createNamedQuery("HrTdIfTrng.findById");
        query.setParameter("id", id);
            HrTdIfTrng selectTraining = (HrTdIfTrng) query.getResultList().get(0);
            return selectTraining;
    }

    public List<HrTdIfTrng> findtrainingByEmpId(Integer id) {
        Query query = em.createNativeQuery("SELECT * FROM HR_TD_IF_TRNG TR JOIN HR_TD_IF_TRNG_SELECTED_STAFFS TS ON TR.ID= TS.INLAND_FOREIGN_TRNG_ID WHERE TS.EMP_ID='"+ id+"'",HrTdIfTrng.class);
           List<HrTdIfTrng> selectTraining = new ArrayList(query.getResultList());
            return selectTraining;
    }

    public List<HrTdIfEduc> findEducByEmpId(Integer id) {
        Query query = em.createNativeQuery("SELECT * FROM HR_TD_IF_EDUC TR JOIN HR_TD_IF_EDUC_SELECTED_STAFFS TS ON TR.ID= TS.INLAND_FOREIGN_EDUC_ID WHERE TS.EMP_ID='"+ id+"'",HrTdIfEduc.class);
           List<HrTdIfEduc> selectTraining = new ArrayList(query.getResultList());
            return selectTraining;
    }
    
}
