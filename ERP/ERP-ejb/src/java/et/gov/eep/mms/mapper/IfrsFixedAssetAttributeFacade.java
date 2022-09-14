/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.mms.entity.IfrsFixedAssetAttribute;

/**
 *
 * @author user
 */
@Stateless
public class IfrsFixedAssetAttributeFacade extends AbstractFacade<IfrsFixedAssetAttribute> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IfrsFixedAssetAttributeFacade() {
        super(IfrsFixedAssetAttribute.class);
    }
//<editor-fold defaultstate="collapsed" desc="NamedQuery">
    public List<IfrsFixedAssetAttribute> getListOfAttributesByCategory(IfrsFixedAssetAttribute assetGroup) {
        Query query = em.createNamedQuery("IfrsFixedAssetAttribute.fagId");
        query.setParameter("fagId", assetGroup.getId());
        ArrayList<IfrsFixedAssetAttribute> attributeList = new ArrayList(query.getResultList());
        return attributeList;
    }

    public IfrsFixedAssetAttribute getSelectedRequest(Integer id) {
        Query query = em.createNamedQuery("IfrsFixedAssetAttribute.findById");
        query.setParameter("id", id);
        System.err.println("===" + query.getResultList().size());
        try {
            IfrsFixedAssetAttribute selectrequest = (IfrsFixedAssetAttribute) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<IfrsFixedAssetAttribute> searchStoreByAttribute(IfrsFixedAssetAttribute fixedassetattribute) {
        Query query = em.createNamedQuery("IfrsFixedAssetAttribute.searchByFaaname");
        query.setParameter("faaname", '%' + fixedassetattribute.getFaaname() + '%');
        try {
            ArrayList<IfrsFixedAssetAttribute> attributInformation = new ArrayList(query.getResultList());
            return attributInformation;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<IfrsFixedAssetAttribute> getListOfAttributesByAssetGroupId(Integer assetGroupId) {
        Query query = em.createNamedQuery("IfrsFixedAssetAttribute.findByFixedAssetGroup");
        query.setParameter("fagId", assetGroupId);
        ArrayList<IfrsFixedAssetAttribute> attributeList = new ArrayList(query.getResultList());
        return attributeList;
    }

    public IfrsFixedAssetAttribute getIfrsFixedAssetAttributeObject(Integer fixedAttributeId) {

        IfrsFixedAssetAttribute fixedassetattribute;
//
        Query query = em.createNamedQuery("IfrsFixedAssetAttribute.findById", IfrsFixedAssetAttribute.class);
        query.setParameter("id", fixedAttributeId);
        fixedassetattribute = (IfrsFixedAssetAttribute) query.getSingleResult();
        return fixedassetattribute;
    }

    public List<IfrsFixedAssetAttribute> getListOfAttributesByCategory(IfrsDepreciationSetup ifrsDepreciationSetup) {
        em.getEntityManagerFactory().getCache().evictAll();
        Query query = em.createNamedQuery("IfrsFixedAssetAttribute.findByFixedAssetByCategory");
        query.setParameter("fagId", ifrsDepreciationSetup);
        ArrayList<IfrsFixedAssetAttribute> attributeList = new ArrayList(query.getResultList());
        return attributeList;

    }

    public List<IfrsFixedAssetAttribute> getListOfAttributesBySlCode(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        em.getEntityManagerFactory().getCache().evictAll();
        Query query = em.createNamedQuery("IfrsFixedAssetAttribute.findBySlCode");
        query.setParameter("subsidiaryId", fmsSubsidiaryLedger);
        ArrayList<IfrsFixedAssetAttribute> attributeList = new ArrayList(query.getResultList());
        System.out.println("==========subid====" + attributeList);
        return attributeList;
    }

    public boolean getAttributeDup(IfrsFixedAssetAttribute fixedassetattribute) {

        try {
            boolean deblicate = false;
            Query query = em.createNamedQuery("IfrsFixedAssetAttribute.findByFaaname", IfrsFixedAssetAttribute.class);
            query.setParameter("faaname", fixedassetattribute.getFaaname());
            System.out.println("dup size---" + query.getResultList().size());
            if (query.getResultList().size() > 0) {
                deblicate = true;
            } else {
                deblicate = false;
            }
            System.out.println("deblicate---" + deblicate);
            return deblicate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 //</editor-fold> 
}
