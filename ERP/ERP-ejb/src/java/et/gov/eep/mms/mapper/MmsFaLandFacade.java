/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFaLand;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsFaLandFacade extends AbstractFacade<MmsFaLand> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsFaLandFacade() {
        super(MmsFaLand.class);
    }

    public MmsFaLand getLastLandId() {

        Query query1 = em.createNamedQuery("MmsFaLand.findBylandIdMaximum");

        MmsFaLand result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsFaLand) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaLand> searchLandByParameterPrefix(MmsFaLand landEntity) {
        Query query = em.createNamedQuery("MmsFaLand.findByAllParameters");
        query.setParameter("landNo", '%' + landEntity.getLandNo() + '%');

        try {
            ArrayList<MmsFaLand> lostItemList = new ArrayList(query.getResultList());
            return lostItemList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFaLand getSelectedRequest(BigDecimal landId) {
        Query query = em.createNamedQuery("MmsFaLand.findByLandId");
        query.setParameter("landId", landId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFaLand selectrequest = (MmsFaLand) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaLand> searchLandByName(MmsFaLand landEntity) {
        Query query = em.createNamedQuery("MmsFaLand.findByName");
        query.setParameter("name", landEntity.getName());
        try {
            ArrayList<MmsFaLand> LandList = new ArrayList(query.getResultList());
            System.out.println("------- @Facade -------- " + LandList.size());
            return LandList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaLand> searchLandByParameterPrefixAndLandPrep(MmsFaLand landEntity) {

        Query query = em.createNamedQuery("MmsFaLand.findByAllParametersAndLandPrep");
        query.setParameter("landNo", '%' + landEntity.getLandNo());
        query.setParameter("preparedBy", '%' + landEntity.getPreparedBy());

        try {
            ArrayList<MmsFaLand> lostItemList = new ArrayList(query.getResultList());
            return lostItemList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaLand> searchLandByNameAndLandPrep(MmsFaLand landEntity) {

        Query query = em.createNamedQuery("MmsFaLand.findByNameAndLandPrep");
        query.setParameter("name", landEntity.getName());
        query.setParameter("preparedBy", landEntity.getPreparedBy());
        try {
            ArrayList<MmsFaLand> LandList = new ArrayList(query.getResultList());
            System.out.println("------- @Facade -------- " + LandList.size());
            return LandList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsFaLand> searchAllLandInfoByPreparerId(Integer preparedBy) {
        Query query = em.createNamedQuery("MmsFaLand.findAllByPreparerId", MmsFaLand.class);
        query.setParameter("preparedBy", preparedBy);
        System.out.println("======@facade====" + preparedBy);
        try {
            ArrayList<MmsFaLand> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
