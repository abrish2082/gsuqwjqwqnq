/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.IfrsLease;

/**
 *
 * @author Minab
 */
@Stateless
public class IfrsLeaseFacade extends AbstractFacade<IfrsLease> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IfrsLeaseFacade() {
        super(IfrsLease.class);
    }
//<editor-fold defaultstate="collapsed" desc="NamedQuery">

    public IfrsLease getSelectedRequest2(BigDecimal leaseId) {
        Query query = em.createNamedQuery("IfrsLease.findByLeaseId");
        query.setParameter("leaseId", leaseId);
        System.err.println("===" + query.getResultList().size());
        try {
            IfrsLease selectrequest = (IfrsLease) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public IfrsLease findAll(IfrsLease lease) {
        Query query = em.createNamedQuery("IfrsLease.findAll");
        System.err.println("===" + query.getResultList().size());
        try {
            IfrsLease selectrequest = (IfrsLease) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<IfrsLease> searchLeaseInformationByAssetName(IfrsLease lease) {
        Query query = em.createNamedQuery("IfrsLease.findByAssetName", IfrsLeaseFacade.class);
        query.setParameter("storeName", lease.getAssetName());
        try {
            ArrayList<IfrsLease> information = new ArrayList(query.getResultList());
            return information;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<IfrsLease> searchLeaseInformationByLeaseCode(IfrsLease lease) {
        Query query = em.createNamedQuery("IfrsLease.findByLeaseCode", IfrsLeaseFacade.class);
        query.setParameter("leaseCode", lease.getLeaseCode());
        try {
            ArrayList<IfrsLease> information = new ArrayList(query.getResultList());
            return information;
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold> 
}
