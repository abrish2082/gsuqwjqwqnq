/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsLcRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Dege
 */
@Stateless
public class LCRequestFacade extends AbstractFacade<PrmsLcRequest> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LCRequestFacade() {
        super(PrmsLcRequest.class);
    }

    public ArrayList<PrmsLcRequest> findAllName() {
        Query query = em.createNamedQuery("Prmslcrequest.findAll", PrmsLcRequest.class);
        try {
            ArrayList<PrmsLcRequest> lcreq = new ArrayList(query.getResultList());
            return lcreq;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsLcRequest> findByRequestId(PrmsLcRequest PrmsLcRequest) {
        
        Query query = em.createNamedQuery("PrmsLcRequest.findByRequestIdLike", PrmsLcRequest.class);
        query.setParameter("requestid", PrmsLcRequest.getRequestid() + '%');
        try {
            ArrayList<PrmsLcRequest> lcList = new ArrayList(query.getResultList());
            
            return lcList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public PrmsLcRequest getlastLCReqNo() {
        Query query = em.createNamedQuery("PrmsLcRequest.findByMaxId", PrmsLcRequest.class);
        PrmsLcRequest result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsLcRequest) query.getResultList().get(0);
            }
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();

            return null;

        }
    }

    public PrmsLcRequest getSelectedId(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsLcRequest.findById", PrmsLcRequest.class);
        query.setParameter("id", id);
        try {
            PrmsLcRequest idlst = (PrmsLcRequest) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
