/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFaGeothermal;
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
public class MmsFaGeothermalFacade extends AbstractFacade<MmsFaGeothermal> {

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
    public MmsFaGeothermalFacade() {
        super(MmsFaGeothermal.class);
    }

    /**
     *
     * @param stationName
     * @return
     */
    public ArrayList<MmsFaGeothermal> searchByStationName(MmsFaGeothermal stationName) {
        Query query = em.createNamedQuery("MmsFaGeothermal.findByStationNameLike");
        query.setParameter("stationName", stationName.getStationName().toUpperCase() + '%');
        try {
            ArrayList<MmsFaGeothermal> geothermalList = new ArrayList(query.getResultList());
            return geothermalList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param stationName
     * @return
     */
    public MmsFaGeothermal getGTInfo(MmsFaGeothermal stationName) {
        Query query = em.createNamedQuery("MmsFaGeothermal.findByStationName");
        query.setParameter("stationName", stationName.getStationName());
        try {
            MmsFaGeothermal geothermalInfo = (MmsFaGeothermal) query.getResultList().get(0);
            return geothermalInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaGeothermal> searchGeoByParameterPrefix(MmsFaGeothermal geothermalEntity) {
        Query query = em.createNamedQuery("MmsFaGeothermal.findByAllParameters");
        query.setParameter("stationName", '%' + geothermalEntity.getStationName() + '%');
        try {
            ArrayList<MmsFaGeothermal> dispList = new ArrayList(query.getResultList());
            return dispList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFaGeothermal getLastGeoId() {

        Query query1 = em.createNamedQuery("MmsFaGeothermal.findBygeothermalIdMaximum");

        MmsFaGeothermal result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsFaGeothermal) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsFaGeothermal> searchByGeoNo(MmsFaGeothermal geothermalEntity) {
        Query query = em.createNamedQuery("MmsFaGeothermal.findBygtNoLike");
        query.setParameter("gtNo", geothermalEntity.getGtNo() + '%');
        try {
            ArrayList<MmsFaGeothermal> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsFaGeothermal> searchByGeo(MmsFaGeothermal geothermalEntity) {
        String gtNo1 = geothermalEntity.getGtNo();
        Integer gtPrep = geothermalEntity.getGtPrepared();
        Query query1 = em.createNativeQuery("SELECT * \n"
                + "                FROM MMS_FA_GEOTHERMAL fat   \n"
                + "                INNER JOIN FMS_DPR_GEOTHERMAL fifat \n"
                + "                ON fat.GEOTHERMAL_ID=fifat.GEO_ASSET_ID\n"
                + "                WHERE fifat.STATUS ='1' AND fat.GT_NO Like '" + gtNo1 + "' AND fat.GT_PREPARED = '" + gtPrep + "' ", MmsFaGeothermal.class);

        ArrayList<MmsFaGeothermal> listOf = new ArrayList<>(query1.getResultList());

        return listOf;
    }

    public MmsFaGeothermal getSelectedRequest(Integer geothermalId) {
        Query query = em.createNamedQuery("MmsFaGeothermal.findByGeothermalId");
        query.setParameter("geothermalId", geothermalId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFaGeothermal selectrequest = (MmsFaGeothermal) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaGeothermal> findNewItems() {
        Query query = em.createNativeQuery("SELECT *  \n"
                + "    FROM MMS_FA_GEOTHERMAL\n"
                + "    WHERE MMS_FA_GEOTHERMAL.GEOTHERMAL_ID NOT IN\n"
                + "        (SELECT FMS_DPR_GEOTHERMAL.GEO_ASSET_ID\n"
                + "            FROM FMS_DPR_GEOTHERMAL)", MmsFaGeothermal.class);
        ArrayList<MmsFaGeothermal> newGeoItems = new ArrayList<>(query.getResultList());
        return newGeoItems;
    }

    public List<MmsFaGeothermal> searchByGeoNoAndGtPrep(MmsFaGeothermal geothermalEntity) {
        Query query = em.createNamedQuery("MmsFaGeothermal.findBygtNoAndGtPrep");
        query.setParameter("gtNo", geothermalEntity.getGtNo());
        query.setParameter("gtPrepared", geothermalEntity.getGtPrepared());
        try {
            ArrayList<MmsFaGeothermal> listofTrNo = new ArrayList(query.getResultList());
            return listofTrNo;
        } catch (Exception ex) {
            return null;
        }
    }
 public List<MmsFaGeothermal> searchAllTransmissionsInfoByPreparerId(Integer buPrepared) {
        Query query = em.createNamedQuery("MmsFaBuilding.findAllByPreparerId", MmsFaGeothermal.class);

        query.setParameter("buPrepared", buPrepared);
        System.out.println("======@facade====" + buPrepared);
        try {
            ArrayList<MmsFaGeothermal> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
