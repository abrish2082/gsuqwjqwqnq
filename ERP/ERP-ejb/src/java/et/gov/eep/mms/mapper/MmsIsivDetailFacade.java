/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivDetail;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class MmsIsivDetailFacade extends AbstractFacade<MmsIsivDetail> {

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
    public MmsIsivDetailFacade() {
        super(MmsIsivDetail.class);
    }

    /**
     *
     * @param isivid
     * @return
     */
    public MmsIsivDetail getDetailbyId(MmsIsiv isivid) {
        MmsIsivDetail result = null;
        Query query = em.createNamedQuery("MmsIsivDetail.findbyisivId", MmsIsivDetail.class);
        query.setParameter("transferId", isivid.getTransferId());
        try {
            MmsIsivDetail importationInfo = (MmsIsivDetail) query.getResultList().get(0);
            return importationInfo;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param isivid
     * @return
     */
    public ArrayList<MmsIsivDetail> getMaterialInformation(MmsIsiv isivid) {
        Query query = em.createNamedQuery("MmsIsivDetail.findbyisivId", MmsIsivDetail.class);
        query.setParameter("transferId", isivid.getTransferId());
        try {
            ArrayList<MmsIsivDetail> locationInformations = new ArrayList(query.getResultList());

            return locationInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsIsivDetail getlastIsivDtlId() {
      Query query = em.createNamedQuery("MmsIsivDetail.findByIsivDtlIdMaximum");
        MmsIsivDetail result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsIsivDetail) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            return null;
        }
    }

}
