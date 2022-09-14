/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFaSubstation;
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
public class MmsFaSubstationFacade extends AbstractFacade<MmsFaSubstation> {

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
    public MmsFaSubstationFacade() {
        super(MmsFaSubstation.class);
    }

    public MmsFaSubstation getLastSubId() {

        Query query1 = em.createNamedQuery("MmsFaSubstation.findBysubstationIdMaximum");

        MmsFaSubstation result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsFaSubstation) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsFaSubstation> searchBySubsNo(MmsFaSubstation subStationEntity) {
        Query query = em.createNamedQuery("MmsFaSubstation.findByssNoLike");
        query.setParameter("ssNo", subStationEntity.getSsNo() + '%');
        try {
            ArrayList<MmsFaSubstation> listofSubNo = new ArrayList(query.getResultList());
            return listofSubNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaSubstation> searchBySub(MmsFaSubstation subStationEntity) {
        String subNo1 = subStationEntity.getSsNo();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_SUBSTATION fat   \n"
                + "                INNER JOIN FMS_DPR_SUBSTATION fifat \n"
                + "                ON fat.SUBSTATION_ID=fifat.SS_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.SS_NO Like '" + subNo1 + "' ", MmsFaSubstation.class);

        ArrayList<MmsFaSubstation> listOf = new ArrayList<>(query1.getResultList());

        return listOf;

    }

    public MmsFaSubstation getSelectedRequest(Integer substationId) {
        Query query = em.createNamedQuery("MmsFaSubstation.findBySubstationId");
        query.setParameter("substationId", substationId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFaSubstation selectrequest = (MmsFaSubstation) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaSubstation> findNewItems() {
        Query query = em.createNativeQuery("SELECT * FROM MMS_FA_SUBSTATION WHERE MMS_FA_SUBSTATION.SUBSTATION_ID NOT IN (SELECT FMS_DPR_SUBSTATION.SS_ASSET_ID FROM FMS_DPR_SUBSTATION)", MmsFaSubstation.class);
        ArrayList<MmsFaSubstation> newItems = new ArrayList<>(query.getResultList());
        return newItems;
    }

    public List<MmsFaSubstation> searchBySubLoc(MmsFaSubstation subStationEntity) {
        Query query = em.createNamedQuery("MmsFaSubstation.findBySsLocationLike");
        query.setParameter("ssLocation", subStationEntity.getSsLocation() + '%');
        try {
            ArrayList<MmsFaSubstation> listofSubNo = new ArrayList(query.getResultList());
            return listofSubNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaSubstation> searchBySub2(MmsFaSubstation subStationEntity) {
        String subNo1 = subStationEntity.getSsLocation();
        Integer subPrep = subStationEntity.getSsPrepared();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_SUBSTATION fat   \n"
                + "                INNER JOIN FMS_DPR_SUBSTATION fifat \n"
                + "                ON fat.SUBSTATION_ID=fifat.SS_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.SS_LOCATION Like '" + subNo1 + "' AND fat.SS_PREPARED = '" + subPrep + "'", MmsFaSubstation.class);

        ArrayList<MmsFaSubstation> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public List<MmsFaSubstation> searchBySubsNoAndSubPrep(MmsFaSubstation subStationEntity) {
        Query query = em.createNamedQuery("MmsFaSubstation.findByssNoAndSubPrep");
        query.setParameter("ssNo", subStationEntity.getSsNo());
        query.setParameter("ssPrepared", subStationEntity.getSsPrepared());

        try {
            ArrayList<MmsFaSubstation> listofSubNo = new ArrayList(query.getResultList());
            return listofSubNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaSubstation> searchBySubAndSubPrep(MmsFaSubstation subStationEntity) {
        String subNo1 = subStationEntity.getSsNo();
        Integer subPrep = subStationEntity.getSsPrepared();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_SUBSTATION fat   \n"
                + "                INNER JOIN FMS_DPR_SUBSTATION fifat \n"
                + "                ON fat.SUBSTATION_ID=fifat.SS_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.SS_NO Like '" + subNo1 + "' AND fat.SS_PREPARED = '" + subPrep + "'", MmsFaSubstation.class);

        ArrayList<MmsFaSubstation> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public List<MmsFaSubstation> searchBySubLocAndSubPrep(MmsFaSubstation subStationEntity) {
        Query query = em.createNamedQuery("MmsFaSubstation.findBySsLocationAndSsPrep");
        query.setParameter("ssLocation", subStationEntity.getSsLocation());
        query.setParameter("ssPrepared", subStationEntity.getSsPrepared());
        try {
            ArrayList<MmsFaSubstation> listofSubNo = new ArrayList(query.getResultList());
            return listofSubNo;
        } catch (Exception ex) {
            return null;
        }
    }
      public List<MmsFaSubstation> searchAllSubInfoByPreparerId(Integer ssPrepared) {
        Query query = em.createNamedQuery("MmsFaSubstation.findAllByPreparerId", MmsFaSubstation.class);
       
        query.setParameter("ssPrepared", ssPrepared);
          System.out.println("======@facade===="+ssPrepared);
        try {
            ArrayList<MmsFaSubstation> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
             ex.printStackTrace();
            return null;
        }
    }
}
