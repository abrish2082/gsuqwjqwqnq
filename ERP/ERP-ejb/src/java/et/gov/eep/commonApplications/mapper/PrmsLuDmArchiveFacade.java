/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.mapper;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mora1
 */
@Stateless
public class PrmsLuDmArchiveFacade extends AbstractFacade<PrmsLuDmArchive> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsLuDmArchiveFacade() {
        super(PrmsLuDmArchive.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named Queries">
    public List<PrmsLuDmArchive> getFileLists(PrmsLuDmArchive prmsLuDmArchive) {
        Query query = em.createNamedQuery("PrmsLuDmArchive.findByDocumentId");
        query.setParameter("documentId", prmsLuDmArchive.getDocumentId());
        List<PrmsLuDmArchive> fileInfo = new ArrayList<>();
        try {
            if (query.getResultList().size() > 0) {
                fileInfo = query.getResultList();
            }
            return fileInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
    // </editor-fold>

}
