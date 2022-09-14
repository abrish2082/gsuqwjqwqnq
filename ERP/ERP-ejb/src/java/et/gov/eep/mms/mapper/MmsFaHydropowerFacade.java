/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFaHydropower;
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
public class MmsFaHydropowerFacade extends AbstractFacade<MmsFaHydropower> {

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
    public MmsFaHydropowerFacade() {
        super(MmsFaHydropower.class);
    }

    public MmsFaHydropower getLastHpId() {

        Query query1 = em.createNamedQuery("MmsFaHydropower.findByhydroPowerIdMaximum");

        MmsFaHydropower result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsFaHydropower) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsFaHydropower> searchByHpNo(MmsFaHydropower hydroPowerEntity) {
        Query query = em.createNamedQuery("MmsFaHydropower.findByhpNoLike");
        query.setParameter("hpNo", hydroPowerEntity.getHpNo() + '%');
        try {
            ArrayList<MmsFaHydropower> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<MmsFaHydropower> searchByHpNoANdProcessedby(MmsFaHydropower hydroPowerEntity) {
        Query query = em.createNamedQuery("MmsFaHydropower.findByhpNoandProcessedby");
        query.setParameter("hpNo", hydroPowerEntity.getHpNo());
        query.setParameter("hpPrepared", hydroPowerEntity.getHpPrepared());
        try {
            ArrayList<MmsFaHydropower> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaHydropower> searchByHp(MmsFaHydropower hydroPowerEntity) {
        String hpNo1 = hydroPowerEntity.getHpNo();
        Integer hpPrep = hydroPowerEntity.getHpPrepared();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_HYDROPOWER fat   \n"
                + "                INNER JOIN FMS_DPR_HYDROPOWER fifat \n"
                + "                ON fat.HYDRO_POWER_ID=fifat.HP_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.HP_NO Like '" + hpNo1 + "' AND fat.HP_PREPARED = '" + hpPrep + "'", MmsFaHydropower.class);

        ArrayList<MmsFaHydropower> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public MmsFaHydropower getSelectedRequest(Integer hydroPowerId) {
        Query query = em.createNamedQuery("MmsFaHydropower.findByHydroPowerId");
        query.setParameter("hydroPowerId", hydroPowerId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFaHydropower selectrequest = (MmsFaHydropower) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaHydropower> findNewItems() {
        Query query = em.createNativeQuery("SELECT * FROM MMS_FA_HYDROPOWER WHERE MMS_FA_HYDROPOWER.HYDRO_POWER_ID \n"
                + "NOT IN (SELECT FMS_DPR_HYDROPOWER.HP_ASSET_ID FROM FMS_DPR_HYDROPOWER)", MmsFaHydropower.class);
        ArrayList<MmsFaHydropower> newItems = new ArrayList<>(query.getResultList());
        return newItems;
    }

    public List<MmsFaHydropower> searchByHpLocation(MmsFaHydropower hydroPowerEntity) {
        Query query = em.createNamedQuery("MmsFaHydropower.findByhpLocLike");
        query.setParameter("hpLocation", hydroPowerEntity.getHpLocation() + '%');
        try {
            ArrayList<MmsFaHydropower> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaHydropower> searchByHp2(MmsFaHydropower hydroPowerEntity) {
        String hpNo1 = hydroPowerEntity.getHpLocation();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_HYDROPOWER fat   \n"
                + "                INNER JOIN FMS_DPR_HYDROPOWER fifat \n"
                + "                ON fat.HYDRO_POWER_ID=fifat.HP_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.HP_LOCATION Like '" + hpNo1 + "' ", MmsFaHydropower.class);

        ArrayList<MmsFaHydropower> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }
     public List<MmsFaHydropower> searchAllTransmissionsInfoByPreparerId(MmsFaHydropower hydroPowerEntity) {
        Query query = em.createNamedQuery("MmsFaHydropower.findAllByPreparerId", MmsFaHydropower.class);
       
        query.setParameter("hpPrepared", hydroPowerEntity.getHpPrepared());
          System.out.println("======@facade===="+hydroPowerEntity.getHpPrepared());
        try {
            ArrayList<MmsFaHydropower> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
             ex.printStackTrace();
            return null;
        }
     }
}
