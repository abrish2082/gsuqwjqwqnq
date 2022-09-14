/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsServiceProviderDetailFacade extends AbstractFacade<PrmsServiceProviderDetail> {

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
    public PrmsServiceProviderDetailFacade() {
        super(PrmsServiceProviderDetail.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named Queries">
    public ArrayList<PrmsServiceProviderDetail> toBranch() {
        Query query = em.createNamedQuery("PrmsServiceProviderDetail.findAll");
        try {
            ArrayList<PrmsServiceProviderDetail> branch = new ArrayList(query.getResultList());
            return branch;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<PrmsServiceProviderDetail> getBranchByServiceProId(PrmsServiceProvider prmsServiceProviderTo) {
        Query query = em.createNamedQuery("PrmsServiceProviderDetail.searchByServiceId");
        query.setParameter("serviceProId", prmsServiceProviderTo);
        try {
            ArrayList<PrmsServiceProviderDetail> bankList = new ArrayList(query.getResultList());

            return bankList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsServiceProviderDetail findByServiceDtId(PrmsServiceProviderDetail prmsServiceProviderBranch) {
        Query query = em.createNamedQuery("PrmsServiceProviderDetail.findByServiceDtId");
        query.setParameter("serviceDtId", prmsServiceProviderBranch.getServiceDtId());
        try {
            PrmsServiceProviderDetail serviceDtInfo = null;
            if (query.getResultList().size() > 0) {
                serviceDtInfo = (PrmsServiceProviderDetail) query.getResultList().get(0);
            }
            return serviceDtInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>
}
