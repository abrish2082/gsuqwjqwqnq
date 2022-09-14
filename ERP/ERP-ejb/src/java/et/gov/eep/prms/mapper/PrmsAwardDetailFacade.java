/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsAward;
import java.util.ArrayList;
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
public class PrmsAwardDetailFacade extends AbstractFacade<PrmsAwardDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsAwardDetailFacade() {
        super(PrmsAwardDetail.class);
    }

    public List<PrmsAwardDetail> searchAwardDetailByAwardId(PrmsAward papmsAward) {

        Query query = em.createNamedQuery("PrmsAwardDetail.findByAwardId", PrmsAwardDetail.class);

        query.setParameter("awardId", papmsAward);

        try {
            ArrayList<PrmsAwardDetail> awardList = new ArrayList(query.getResultList());

            return awardList;
        } catch (Exception ex) {
            return null;
        }

    }

    public List<PrmsAwardDetail> getAwardDetailList(String AwardNo) {

        List<PrmsAwardDetail> awardDetailList = null;
        try {
            Query query = em.createNamedQuery("PrmsAwardDetail.findByAwardNo", PrmsAwardDetail.class);
            query.setParameter("awardNo", AwardNo);
            awardDetailList = (List<PrmsAwardDetail>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return awardDetailList;
    }

}
