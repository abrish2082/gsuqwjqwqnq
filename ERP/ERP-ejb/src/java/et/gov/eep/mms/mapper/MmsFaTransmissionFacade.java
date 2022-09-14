/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFaTransmission;
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
public class MmsFaTransmissionFacade extends AbstractFacade<MmsFaTransmission> {

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
    public MmsFaTransmissionFacade() {
        super(MmsFaTransmission.class);
    }

    /**
     *
     * @param fromName
     * @return
     */
    public ArrayList<MmsFaTransmission> searchByFromName(MmsFaTransmission fromName) {
        Query query = em.createNamedQuery("MmsFaTransmission.findByFromNameLike");
        query.setParameter("fromName", fromName.getFromName().toUpperCase() + '%');
        try {
            ArrayList<MmsFaTransmission> MmsFaTransmissionlist = new ArrayList(query.getResultList());
            return MmsFaTransmissionlist;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param trFromName
     * @return
     */
    public MmsFaTransmission getTRInfo(MmsFaTransmission trFromName) {
        Query query = em.createNamedQuery("MmsFaTransmission.findByFromName");
        query.setParameter("fromName", trFromName.getFromName());
        try {
            MmsFaTransmission TrAssetInfo = (MmsFaTransmission) query.getResultList().get(0);
            return TrAssetInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFaTransmission getLastTraId() {

        Query query1 = em.createNamedQuery("MmsFaTransmission.findBytransmissionIdMaximum");

        MmsFaTransmission result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsFaTransmission) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsFaTransmission> searchByTransNo(MmsFaTransmission FaTransmissionEntity) {
        Query query = em.createNamedQuery("MmsFaTransmission.findBytrNoLike");
        query.setParameter("trNo", FaTransmissionEntity.getTrNo() + '%');
        try {
            ArrayList<MmsFaTransmission> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaTransmission> searchByTrans(MmsFaTransmission FaTransmissionEntity) {
        String trNo = FaTransmissionEntity.getTrNo();
        Integer trPrep = FaTransmissionEntity.getTrPrepared();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_TRANSMISSION fat   \n"
                + "                INNER JOIN FMS_DPR_TRANSMISSON fifat \n"
                + "                ON fat.TRANSMISSION_ID=fifat.TR_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.TR_NO Like '" + trNo + "' AND fat.TR_PREPARED = '" + trPrep + "'", MmsFaTransmission.class);

        ArrayList<MmsFaTransmission> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public MmsFaTransmission getSelectedRequest(Integer transmissionId) {
        Query query = em.createNamedQuery("MmsFaTransmission.findByTransmissionId");
        query.setParameter("transmissionId", transmissionId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFaTransmission selectrequest = (MmsFaTransmission) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaTransmission> findNewItems() {
        Query query = em.createNativeQuery("SELECT * FROM MMS_FA_TRANSMISSION WHERE MMS_FA_TRANSMISSION.TRANSMISSION_ID "
                + "NOT IN (SELECT FMS_DPR_TRANSMISSON.TR_ASSET_ID FROM FMS_DPR_TRANSMISSON)", MmsFaTransmission.class);
        ArrayList<MmsFaTransmission> newItems = new ArrayList<>(query.getResultList());
        return newItems;
    }

    public List<MmsFaTransmission> searchByLocName(MmsFaTransmission FaTransmissionEntity) {
        Query query = em.createNamedQuery("MmsFaTransmission.findByFromName");
        query.setParameter("fromName", FaTransmissionEntity.getFromName());
        try {
            ArrayList<MmsFaTransmission> LocList = new ArrayList(query.getResultList());
            System.out.println("------- @Facade -------- " + LocList.size());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaTransmission> searchByTrans2(MmsFaTransmission FaTransmissionEntity) {
        String trNo = FaTransmissionEntity.getFromName();
        Integer trPrep = FaTransmissionEntity.getTrPrepared();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_TRANSMISSION fat   \n"
                + "                INNER JOIN FMS_DPR_TRANSMISSON fifat \n"
                + "                ON fat.TRANSMISSION_ID=fifat.TR_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.FROM_NAME Like '" + trNo + "' AND fat.TR_PREPARED = '" + trPrep + "' ", MmsFaTransmission.class);

        ArrayList<MmsFaTransmission> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public List<MmsFaTransmission> searchByTransNoAndTrPrep(MmsFaTransmission FaTransmissionEntity) {
        Query query = em.createNamedQuery("MmsFaTransmission.findBytrNoAndTrPrep");
        query.setParameter("trNo", FaTransmissionEntity.getTrNo());
        query.setParameter("trPrepared", FaTransmissionEntity.getTrPrepared());
        try {
            ArrayList<MmsFaTransmission> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaTransmission> searchByLocNameAndTrPrep(MmsFaTransmission FaTransmissionEntity) {
        Query query = em.createNamedQuery("MmsFaTransmission.findByFromNameAndTrPrep");
        query.setParameter("fromName", FaTransmissionEntity.getFromName());
        query.setParameter("trPrepared", FaTransmissionEntity.getTrPrepared());
        try {
            ArrayList<MmsFaTransmission> LocList = new ArrayList(query.getResultList());
            System.out.println("------- @Facade -------- " + LocList.size());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
      public List<MmsFaTransmission> searchAllTransmissionsInfoByPreparerId(MmsFaTransmission FaTransmissionEntity) {
        Query query = em.createNamedQuery("MmsFaTransmission.findAllByPreparerId", MmsFaTransmission.class);
       
        query.setParameter("trPrepared", FaTransmissionEntity.getTrPrepared());
          System.out.println("======@facade===="+FaTransmissionEntity.getTrPrepared());
        try {
            ArrayList<MmsFaTransmission> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
             ex.printStackTrace();
            return null;
        }
    }
}
