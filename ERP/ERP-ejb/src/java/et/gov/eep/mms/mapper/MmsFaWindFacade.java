/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFaWind;
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
public class MmsFaWindFacade extends AbstractFacade<MmsFaWind> {

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
    public MmsFaWindFacade() {
        super(MmsFaWind.class);
    }

    public List<MmsFaWind> searchWindByParameterPrefix(MmsFaWind windENtity) {
        Query query = em.createNamedQuery("MmsFaWind.findByAllParameters");
        query.setParameter("wdPlantName", '%' + windENtity.getWdPlantName() + '%');
        try {
            ArrayList<MmsFaWind> dispList = new ArrayList(query.getResultList());
            return dispList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFaWind getLastWindId() {

        Query query1 = em.createNamedQuery("MmsFaWind.findBywindIdMaximum");

        MmsFaWind result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsFaWind) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsFaWind> searchByWindNo(MmsFaWind windENtity) {
        Query query = em.createNamedQuery("MmsFaWind.findBywdWindNoLike");
        query.setParameter("wdWindNo", windENtity.getWdWindNo() + '%');
        try {
            ArrayList<MmsFaWind> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaWind> searchByWn(MmsFaWind windENtity) {
        String wnNo1 = windENtity.getWdWindNo();
        Integer wdPrep = windENtity.getWdPreparedBy();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_WIND fat   \n"
                + "                INNER JOIN FMS_DPR_WIND fifat \n"
                + "                ON fat.WIND_ID=fifat.WD_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.WD_WIND_NO Like '" + wnNo1 + "' AND fat.WD_PREPARED_BY = '" + wdPrep + "' ", MmsFaWind.class);

        ArrayList<MmsFaWind> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public MmsFaWind getSelectedRequest(Integer windId) {
        Query query = em.createNamedQuery("MmsFaWind.findByWindId");
        query.setParameter("windId", windId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFaWind selectrequest = (MmsFaWind) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaWind> findNewItems() {
        Query query = em.createNativeQuery("SELECT * FROM MMS_FA_WIND WHERE MMS_FA_WIND.WIND_ID NOT IN (SELECT FMS_DPR_WIND.WD_ASSET_ID FROM FMS_DPR_WIND)", MmsFaWind.class);
        ArrayList<MmsFaWind> newItems = new ArrayList<>(query.getResultList());
        return newItems;
    }

    public List<MmsFaWind> searchByWn2(MmsFaWind windENtity) {
        String wnPlant = windENtity.getWdPlantName();
        Integer wdPrep = windENtity.getWdPreparedBy();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_WIND fat   \n"
                + "                INNER JOIN FMS_DPR_WIND fifat \n"
                + "                ON fat.WIND_ID=fifat.WD_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.WD_PLANT_NAME Like '" + wnPlant + "' AND fat.WD_PREPARED_BY = '" + wdPrep + "' ", MmsFaWind.class);

        ArrayList<MmsFaWind> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public List<MmsFaWind> searchByWindPlantName(MmsFaWind windENtity) {
        Query query = em.createNamedQuery("MmsFaWind.findByWdPlantNameLike");
        query.setParameter("wdPlantName", windENtity.getWdPlantName() + '%');
        try {
            ArrayList<MmsFaWind> listofPlant = new ArrayList(query.getResultList());
            return listofPlant;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaWind> searchByWindNoAndWdPrep(MmsFaWind windENtity) {
        Query query = em.createNamedQuery("MmsFaWind.findBywdWindNoAndWdPrep");
        query.setParameter("wdWindNo", windENtity.getWdWindNo());
        query.setParameter("wdPreparedBy", windENtity.getWdPreparedBy());
        try {
            ArrayList<MmsFaWind> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaWind> searchByWindPlantNameAndWdPrep(MmsFaWind windENtity) {

        Query query = em.createNamedQuery("MmsFaWind.findByWdPlantNameAndWdPrep");
        query.setParameter("wdPlantName", windENtity.getWdPlantName());
        query.setParameter("wdPreparedBy", windENtity.getWdPreparedBy());
        try {
            ArrayList<MmsFaWind> listofPlant = new ArrayList(query.getResultList());
            return listofPlant;
        } catch (Exception ex) {
            return null;
        }
    }
     public List<MmsFaWind> searchAllTransmissionsInfoByPreparerId(MmsFaWind windENtity) {
        Query query = em.createNamedQuery("MmsFaWind.findAllByPreparerId", MmsFaWind.class);

        query.setParameter("wdPreparedBy", windENtity.getWdPreparedBy());
        System.out.println("======@facade====" + windENtity.getWdPreparedBy());
        try {
            ArrayList<MmsFaWind> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
