/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.ifrs.entity.IfrsOptionValues;

/**
 *
 * @author user
 */
@Stateless
public class IfrsDepreciationSetupFacade extends AbstractFacade<IfrsDepreciationSetup> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IfrsDepreciationSetupFacade() {
        super(IfrsDepreciationSetup.class);
    }
//<editor-fold defaultstate="collapsed" desc="NamedQuery">

    public List<IfrsDepreciationSetup> findAllFixedAssetGroup() {

        try {
            Query query = em.createNamedQuery("IfrsDepreciationSetup.findAllFixedAssets");
            ArrayList<IfrsDepreciationSetup> fixedList = new ArrayList(query.getResultList());
            System.out.println("list size==" + fixedList.size());
            return fixedList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public IfrsDepreciationSetup getListOfAttributesByCategory(int key) {
        Query query = em.createNamedQuery("IfrsDepreciationSetup.findById");
        query.setParameter("id", key);
        try {
            IfrsDepreciationSetup selectGroup = (IfrsDepreciationSetup) query.getResultList().get(0);
            return selectGroup;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public IfrsDepreciationSetup getFixedassetgroup(Integer fixedassetgroupId) {
        em.getEntityManagerFactory().getCache().evictAll();
        IfrsDepreciationSetup fixedassetgroup;
        try {
            Query query = em.createNamedQuery("IfrsDepreciationSetup.findById", IfrsOptionValues.class);
            query.setParameter("id", fixedassetgroupId);
            fixedassetgroup = (IfrsDepreciationSetup) query.getSingleResult();
            return fixedassetgroup;
        } catch (Exception e) {
            return null;
        }
    }

    public List<IfrsDepreciationSetup> depreciationListForStock() {
        Query query = em.createNamedQuery("IfrsDepreciationSetup.findAllStockAssets");
        try {
            ArrayList<IfrsDepreciationSetup> fixedList = new ArrayList(query.getResultList());
            System.out.println("list size==" + fixedList.size());
            return fixedList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public IfrsDepreciationSetup findById(Integer id) {
        Query query = em.createNamedQuery("IfrsDepreciationSetup.findById");
        query.setParameter("id", id);
        try {
            IfrsDepreciationSetup dep = (IfrsDepreciationSetup) query.getResultList().get(0);
            System.out.println("list size==" + dep);
            return dep;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
//</editor-fold>
}
