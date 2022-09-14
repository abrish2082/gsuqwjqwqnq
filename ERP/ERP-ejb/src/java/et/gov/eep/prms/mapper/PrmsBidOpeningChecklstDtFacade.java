/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBidOpeningChecklstDt;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsBidOpeningChecklstDtFacade extends AbstractFacade<PrmsBidOpeningChecklstDt> {

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
    public PrmsBidOpeningChecklstDtFacade() {
        super(PrmsBidOpeningChecklstDt.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named Queries">
    public PrmsBidOpeningChecklstDt LastCheckListDtid() {
        PrmsBidOpeningChecklstDt lastCheckListDtId = new PrmsBidOpeningChecklstDt();
        Query query = em.createNamedQuery("PrmsBidOpeningChecklstDt.findByMaxCheckListNum");

        try {
            if (!query.getResultList().isEmpty()) {
                lastCheckListDtId = (PrmsBidOpeningChecklstDt) query.getResultList().get(0);
            } else {
                lastCheckListDtId = null;
            }

            return lastCheckListDtId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // <editor-fold>

}
