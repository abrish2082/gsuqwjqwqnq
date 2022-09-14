/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.recruitment;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrExamPercentages;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Benin
 */
@Stateless
public class HrExamPercentagesFacade extends AbstractFacade<HrExamPercentages> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrExamPercentagesFacade() {
        super(HrExamPercentages.class);
    }

    public List<HrAdvertisements> batchCodes(String type) {
        Query query = em.createNativeQuery("SELECT ID, "
                + " BATCH_CODE "
                + " FROM HR_ADVERTISEMENTS "
                + " WHERE UPPER(ADVERT_TYPE) = ? "
                + " ORDER BY ID DESC", HrAdvertisements.class);
        query.setParameter(1, type.toUpperCase());
        try {
            return (List<HrAdvertisements>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public boolean checkDuplicate(HrExamPercentages hrExamPercentages) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrExamPercentages.findDuplicateEntry", HrExamPercentages.class);
        query.setParameter("batchCode", hrExamPercentages.getBatchCode());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }
    }

    public HrExamPercentages selectExamPrecentage(HrAdvertisements hrAdvertisements) {
        Query query = em.createNativeQuery("SELECT ID, "
                + "  nvl(WRITTEN_PERCENTAGE,'0') as WRITTEN_PERCENTAGE,"
                + "  nvl(PRACTICAL_PERCENTAGE,'0') as PRACTICAL_PERCENTAGE,"
                + "  nvl(INTERVIEW_PERCENTAGE,'0') as INTERVIEW_PERCENTAGE,"
                + "  nvl(CGPA_PERCENTAGE,'0') as CGPA_PERCENTAGE,"
                + "  nvl(EXPERIENCE_PERCENTAGE,'0') as EXPERIENCE_PERCENTAGE,"
                + "  nvl(OTHER_PERCENTAGE,'0') as OTHER_PERCENTAGE,"
                + "  BATCH_CODE "
                + "  FROM HR_EXAM_PERCENTAGES "
                + "  WHERE BATCH_CODE=?", HrExamPercentages.class);
        query.setParameter(1, hrAdvertisements.getId());
        try {
            if (query.getResultList().size() > 0) {
                HrExamPercentages assWeight = (HrExamPercentages) query.getResultList().get(0);
                return assWeight;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public HrExamPercentages selectExamPrecentageDetail(HrExamPercentages hrExamPercentages) {
        Query query = em.createNamedQuery("HrExamPercentages.findById");
        query.setParameter("id", hrExamPercentages.getId());
        try {
            HrExamPercentages examPercentage = (HrExamPercentages) query.getResultList().get(0);
            return examPercentage;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
