package et.gov.eep.hrms.mapper.documentProvidingService;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentRequests;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Baya
 */
@Stateless
public class HrDocumentRequestsFacade extends AbstractFacade<HrDocumentRequests> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness Implementations">
    public HrDocumentRequestsFacade() {
        super(HrDocumentRequests.class);
    }
    
    public HrDocumentRequests loadDocumentrequestDetails(BigDecimal id) {
        Query query = em.createNamedQuery("HrDocumentRequests.findById");
        query.setParameter("id", id);
        try {
            HrDocumentRequests selectrequest = (HrDocumentRequests) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrDocumentRequests> loadDocumentRequests(int status) {
        Query query = em.createNamedQuery("HrDocumentRequests.findByStatus");
        query.setParameter("status", status);
        try {
            System.out.println("result======" + (List<HrDocumentRequests>) query.getResultList());
            return (List<HrDocumentRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrDocumentRequests> loadDocumentRequestsAndUserID(int status, int UserId) {
        Query query = em.createNativeQuery("select * from HR_DOCUMENT_REQUESTS dr INNER JOIN WF_HR_PROCESSED wf on dr.id=wf.document_id "
                + "where dr.status='" + status + "' " + "and wf.processed_by='" + UserId + "' ", HrDocumentRequests.class);
        List<HrDocumentRequests> qualList = new ArrayList(query.getResultList());
        return qualList;
    }
    
    public List<HrDocumentRequests> findAllAprovedrequests(Integer status) {
        Query query = em.createNamedQuery("HrDocumentRequests.findByStatus");
        query.setParameter("status", 1);
        try {
            return (List<HrDocumentRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public boolean searchByEmpIdAndDocType(HrDocumentRequests hrDocumentRequests) {
        Query query = em.createNamedQuery("HrDocumentRequests.findByEmpIdDocType", HrDocumentRequests.class);
        query.setParameter("requesterId", hrDocumentRequests.getRequesterId().getId());
        query.setParameter("docTypeId", hrDocumentRequests.getDocumentType().getId());
        try {
            return query.getResultList().isEmpty();
            
        } catch (Exception ex) {
            return false;
        }
    }
    
    public String searchreqdatebyEmpId(HrDocumentRequests hrDocumentRequests) {
        Query query = em.createNamedQuery("HrDocumentRequests.findByRequesterId");
        query.setParameter("reqId", hrDocumentRequests.getRequesterId().getId());
        try {
            HrDocumentRequests hrDoc = (HrDocumentRequests) (query.getResultList().get(0));
            return hrDoc.getRequestDate();
            
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrDocumentRequests> findAllRequests() {
        Query query = em.createNamedQuery("HrDocumentRequests.findByStatus");
        query.setParameter("status", 0);
        try {
            return (List<HrDocumentRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrDocumentRequests> findAllApprovedRequests() {
        Query query = em.createNamedQuery("HrDocumentRequests.findByStatus");
        query.setParameter("status", 3);
        try {
            return (List<HrDocumentRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public Integer searchByEmpIdAndDocType1(HrDocumentRequests hrDocumentRequests) {
        Query query = em.createNamedQuery("HrDocumentRequests.findByEmpIdDocType", HrDocumentRequests.class);
        query.setParameter("requesterId", hrDocumentRequests.getRequesterId().getId());
        query.setParameter("docTypeId", hrDocumentRequests.getDocumentType().getId());
        try {
            HrDocumentRequests hrDoc = (HrDocumentRequests) (query.getResultList().get(0));
            return hrDoc.getStatus();
            
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrAddresses> findalladresses() {
        Query query = em.createNamedQuery("HrAddresses.findAll");
        try {
            return query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public HrAddresses findadress(HrAddresses hrAddresses) {
        Query query = em.createNamedQuery("HrAddresses.findByAddressId");
        query.setParameter("addressId", hrAddresses.getAddressId());
        try {
            HrAddresses getAddres = (HrAddresses) query.getResultList().get(0);
            return getAddres;
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>

}
