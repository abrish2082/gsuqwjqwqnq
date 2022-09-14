/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFaBuilding;
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
public class MmsFaBuildingFacade extends AbstractFacade<MmsFaBuilding> {

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
    public MmsFaBuildingFacade() {
        super(MmsFaBuilding.class);
    }

    /**
     *
     * @param buildingName
     * @return
     */
    public ArrayList<MmsFaBuilding> searchByBuildingName(MmsFaBuilding buildingName) {
        Query query = em.createNamedQuery("MmsFaBuilding.findByBuildingNameLike");
        query.setParameter("buildingName", buildingName.getBuildingName().toUpperCase() + '%');
        try {
            ArrayList<MmsFaBuilding> buildingNamelist = new ArrayList(query.getResultList());
            return buildingNamelist;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param buildingName
     * @return
     */
    public MmsFaBuilding getBUInfo(MmsFaBuilding buildingName) {
        Query query = em.createNamedQuery("MmsFaBuilding.findByBuildingName");
        query.setParameter("buildingName", buildingName.getBuildingName());
        try {
            MmsFaBuilding buildingInfo = (MmsFaBuilding) query.getResultList().get(0);
            return buildingInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaBuilding> searchBuildingByParameterPrefix(MmsFaBuilding buildingEntity) {
        Query query = em.createNamedQuery("MmsFaBuilding.findByAllParameters");
        query.setParameter("buildingName", '%' + buildingEntity.getBuildingName() + '%');
        try {
            ArrayList<MmsFaBuilding> dispList = new ArrayList(query.getResultList());
            return dispList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFaBuilding getLastBuId() {

        Query query1 = em.createNamedQuery("MmsFaBuilding.findBybuildingIdMaximum");

        MmsFaBuilding result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsFaBuilding) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsFaBuilding> searchByBuNo(MmsFaBuilding buildingEntity) {
        Query query = em.createNamedQuery("MmsFaBuilding.findBybuNoLike");
        query.setParameter("buNo", buildingEntity.getBuNo() + '%');
        try {
            ArrayList<MmsFaBuilding> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaBuilding> searchByBldg(MmsFaBuilding buildingEntity) {
        String BuNo1 = buildingEntity.getBuNo();
        Integer BuPrep = buildingEntity.getBuPrepared();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_BUILDING fat   \n"
                + "                INNER JOIN FMS_DPR_BUILDING fifat \n"
                + "                ON fat.BUILDING_ID=fifat.BU_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.BU_NO Like '" + BuNo1 + "' AND fat.BU_PREPARED = '" + BuPrep + "'", MmsFaBuilding.class);

        ArrayList<MmsFaBuilding> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public MmsFaBuilding getSelectedRequest(Integer buildingId) {
        Query query = em.createNamedQuery("MmsFaBuilding.findByBuildingId");
        query.setParameter("buildingId", buildingId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFaBuilding selectrequest = (MmsFaBuilding) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaBuilding> findNewItems() {
        Query query = em.createNativeQuery("SELECT *  \n"
                + "    FROM MMS_FA_BUILDING\n"
                + "    WHERE MMS_FA_BUILDING.BUILDING_ID NOT IN\n"
                + "        (SELECT FMS_DPR_BUILDING.BU_ASSET_ID\n"
                + "            FROM FMS_DPR_BUILDING)", MmsFaBuilding.class);
        ArrayList<MmsFaBuilding> newBldgItems = new ArrayList<>(query.getResultList());
        return newBldgItems;
    }

    public List<MmsFaBuilding> searchByBldg2(MmsFaBuilding buildingEntity) {
        String BuName = buildingEntity.getBuildingName();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_BUILDING fat   \n"
                + "                INNER JOIN FMS_DPR_BUILDING fifat \n"
                + "                ON fat.BUILDING_ID=fifat.BU_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.BUILDING_NAME Like '" + BuName + "' ", MmsFaBuilding.class);

        ArrayList<MmsFaBuilding> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public List<MmsFaBuilding> searchByBuName(MmsFaBuilding buildingEntity) {
        Query query = em.createNamedQuery("MmsFaBuilding.findByBuildingNameLike");
        query.setParameter("buildingName", buildingEntity.getBuildingName() + '%');
        try {
            ArrayList<MmsFaBuilding> listofBuName = new ArrayList(query.getResultList());
            return listofBuName;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaBuilding> searchByBuNoAndProcessedBy(MmsFaBuilding buildingEntity) {
        Query query = em.createNamedQuery("MmsFaBuilding.findBybuNoAndProcessedBy");
        query.setParameter("buNo", buildingEntity.getBuNo());
        query.setParameter("buPrepared", buildingEntity.getBuPrepared());
        try {
            ArrayList<MmsFaBuilding> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaBuilding> searchByBuNameAndBuPrep(MmsFaBuilding buildingEntity) {
        Query query = em.createNamedQuery("MmsFaBuilding.findByBuildingNameAndBuPrep");
        query.setParameter("buildingName", buildingEntity.getBuildingName());
        query.setParameter("buPrepared", buildingEntity.getBuPrepared());
        try {
            ArrayList<MmsFaBuilding> listofBuName = new ArrayList(query.getResultList());
            return listofBuName;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaBuilding> searchByBldg2AndBuPrep(MmsFaBuilding buildingEntity) {
        String BuName = buildingEntity.getBuildingName();
        Integer BuPrep = buildingEntity.getBuPrepared();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_BUILDING fat   \n"
                + "                INNER JOIN FMS_DPR_BUILDING fifat \n"
                + "                ON fat.BUILDING_ID=fifat.BU_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.BUILDING_NAME Like '" + BuName + "' AND fat.BU_PREPARED = '" + BuPrep + "'", MmsFaBuilding.class);

        ArrayList<MmsFaBuilding> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public List<MmsFaBuilding> searchAllTransmissionsInfoByPreparerId(Integer buPrepared) {
        Query query = em.createNamedQuery("MmsFaBuilding.findAllByPreparerId", MmsFaBuilding.class);

        query.setParameter("buPrepared", buPrepared);
        System.out.println("======@facade====" + buPrepared);
        try {
            ArrayList<MmsFaBuilding> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
