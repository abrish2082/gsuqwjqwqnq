/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.mapper;

import et.gov.eep.commonApplications.entity.ComLuDmArchive;
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
public class ComLuDmArchiveFacade extends AbstractFacade<ComLuDmArchive> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComLuDmArchiveFacade() {
        super(ComLuDmArchive.class);
    }

    public List<ComLuDmArchive> getFileLists(ComLuDmArchive comLuDmArchive) {
        System.out.println("Doc id Passed " + comLuDmArchive.getDocumentId());
        Query query = em.createNamedQuery("ComLuDmArchive.findByDocumentId");
        query.setParameter("documentId", comLuDmArchive.getDocumentId());
        List<ComLuDmArchive> fileInfo = new ArrayList<>();
        System.out.println("name " + comLuDmArchive.getFileName());
        try {
            System.out.println("try size " + query.getResultList().size());
            if (query.getResultList().size() > 0) {
                fileInfo = query.getResultList();
            }
            System.out.println("File name " + fileInfo.size());
            System.out.println("File Blob " + fileInfo);
            return fileInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
