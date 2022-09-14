/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.promotion;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.promotion.HrPromotionEducCriterias;
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
public class HrPromotionEducCriteriasFacade extends AbstractFacade<HrPromotionEducCriterias> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPromotionEducCriteriasFacade() {
        super(HrPromotionEducCriterias.class);
    }

    public List<HrLuEducLevels> findAlleduc() {
    List<HrLuEducLevels> requestses;
        try {
            Query query = em.createNamedQuery("HrLuEducLevels.findAll", HrLuEducLevels.class);
            requestses = (List<HrLuEducLevels>) query.getResultList();
            return requestses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }}
    
    
}
