/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.promotion;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.promotion.HrPromotionCriterias;
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
public class HrPromotionCriteriasFacade extends AbstractFacade<HrPromotionCriterias> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPromotionCriteriasFacade() {
        super(HrPromotionCriterias.class);
    }

    public List<HrPromotionCriterias> findAllCriterias() {
        try {
            Query query = em.createNamedQuery("HrPromotionCriterias.findAll", HrPromotionCriterias.class);
            return (List<HrPromotionCriterias>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean checkDouble(HrPromotionCriterias criterias) {
       
        return true;
    }
}
