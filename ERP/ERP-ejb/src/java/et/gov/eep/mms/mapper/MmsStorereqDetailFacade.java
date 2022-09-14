/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;
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
public class MmsStorereqDetailFacade extends AbstractFacade<MmsStorereqDetail> {

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
    public MmsStorereqDetailFacade() {
        super(MmsStorereqDetail.class);
    }

    /**
     *
     * @param insp
     * @return
     */
    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public List<MmsStorereqDetail> searchBySrId(MmsStorereq insp) {
        Query query = em.createNamedQuery("MmsStorereqDetail.frindBysrId", MmsStorereqDetail.class);
        query.setParameter("storeReqId", insp);

        try {
            ArrayList<MmsStorereqDetail> inspectionList = new ArrayList(query.getResultList());
            return inspectionList;
        } catch (Exception ex) {
            return null;
        }

    }

//get Lists of Store Req Detail By SR Number
    public List<MmsStorereqDetail> getSrNo(String sRNo) {
        Query query = em.createNamedQuery("MmsStorereqDetail.findBySrNo", MmsStorereqDetail.class);
        query.setParameter("srNo", sRNo);
        List<MmsStorereqDetail> srNoLst = new ArrayList<>();
        try {
            if (query.getResultList().size() > 0) {
                srNoLst = query.getResultList();
            }
            return srNoLst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    // </editor-fold>

}
