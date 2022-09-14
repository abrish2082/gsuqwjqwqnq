/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFaTransport;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Binyam
 */
@Stateless
public class MmsFaTransportFacade extends AbstractFacade<MmsFaTransport> {

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
    public MmsFaTransportFacade() {
        super(MmsFaTransport.class);
    }

    public List<MmsFaTransport> searchTransportByParameterPrefix(MmsFaTransport transportEntity) {

        Query query = em.createNamedQuery("MmsFaTransport.findByAllParameters");
        query.setParameter("tpPlateNo", '%' + transportEntity.getTpPlateNo() + '%');
        try {
            ArrayList<MmsFaTransport> dispList = new ArrayList(query.getResultList());
            return dispList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFaTransport getLastTrId() {

        Query query1 = em.createNamedQuery("MmsFaTransport.findBytransportIdMaximum");

        MmsFaTransport result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsFaTransport) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsFaTransport> searchByTrNo(MmsFaTransport transportEntity) {
        Query query = em.createNamedQuery("MmsFaTransport.findBytpNoLike");
        query.setParameter("tpNo", transportEntity.getTpNo() + '%');
        try {
            ArrayList<MmsFaTransport> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaTransport> searchBytr(MmsFaTransport transportEntity) {
        String tpNo1 = transportEntity.getTpNo();
        Integer tpPrep = transportEntity.getTpPrepared();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_TRANSPORT fat   \n"
                + "                INNER JOIN FMS_DPR_TRANSPORT fifat \n"
                + "                ON fat.TRANSPORT_ID=fifat.TP_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.TP_NO Like '" + tpNo1 + "' AND fat.TP_PREPARED = '" + tpPrep + "' ", MmsFaTransport.class);

        ArrayList<MmsFaTransport> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public MmsFaTransport getSelectedRequest(Integer transportId) {
        Query query = em.createNamedQuery("MmsFaTransport.findByTransportId");
        query.setParameter("transportId", transportId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFaTransport selectrequest = (MmsFaTransport) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaTransport> findNewItems() {
        Query query = em.createNativeQuery("SELECT *  \n"
                + "    FROM MMS_FA_TRANSPORT\n"
                + "    WHERE MMS_FA_TRANSPORT.TRANSPORT_ID NOT IN\n"
                + "        (SELECT FMS_DPR_TRANSPORT.TP_ASSET_ID\n"
                + "            FROM FMS_DPR_TRANSPORT)", MmsFaTransport.class);
        ArrayList<MmsFaTransport> newItems = new ArrayList<>(query.getResultList());
        return newItems;
    }

    public List<MmsFaTransport> searchByTrNoAndTrPrep(MmsFaTransport transportEntity) {
        Query query = em.createNamedQuery("MmsFaTransport.findBytpNoAndTpPrep");
        query.setParameter("tpNo", transportEntity.getTpNo());
        query.setParameter("tpPrepared", transportEntity.getTpPrepared());
        try {
            ArrayList<MmsFaTransport> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }
     public List<MmsFaTransport> searchAllTransmissionsInfoByPreparerId(Integer tpPrepared) {
        Query query = em.createNamedQuery("MmsFaTransport.findAllByPreparerId", MmsFaTransport.class);

        query.setParameter("tpPrepared", tpPrepared);
        System.out.println("======@facade====" + tpPrepared);
        try {
            ArrayList<MmsFaTransport> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
