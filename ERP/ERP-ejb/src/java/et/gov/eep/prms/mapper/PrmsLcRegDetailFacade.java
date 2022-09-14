/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsLcRegDetail;
import et.gov.eep.prms.entity.PrmsLcRegDetailAmendment;
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
public class PrmsLcRegDetailFacade extends AbstractFacade<PrmsLcRegDetail> {

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
    public PrmsLcRegDetailFacade() {
        super(PrmsLcRegDetail.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named Queries">
    public List<PrmsLcRegDetailAmendment> getListOfLCAmendNo() {
        Query query = em.createNamedQuery("PrmsLcRegDetailAmendment.findLCAmendNo");
        List<PrmsLcRegDetailAmendment> directPurcObj = null;
        try {
            directPurcObj = (List<PrmsLcRegDetailAmendment>) query.getResultList();
            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

}
