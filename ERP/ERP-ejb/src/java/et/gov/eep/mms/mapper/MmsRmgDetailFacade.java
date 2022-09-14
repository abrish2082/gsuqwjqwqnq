/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.entity.MmsRmgDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class MmsRmgDetailFacade extends AbstractFacade<MmsRmgDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsRmgDetailFacade() {
        super(MmsRmgDetail.class);
    }

    /**
     *
     * @param rmgid
     * @return
     */
    public MmsRmgDetail getDetailbyId(MmsRmg rmgid) {
        MmsRmgDetail result = null;
        Query query = em.createNamedQuery("MmsRmgDetail.findbyrmgIds", MmsRmgDetail.class);
        query.setParameter("rmgId", rmgid.getRmgId());
        try {
            MmsRmgDetail importationInfo = (MmsRmgDetail) query.getResultList().get(0);
            return importationInfo;

        } catch (Exception ex) {
            return null;
        }
    }

}
