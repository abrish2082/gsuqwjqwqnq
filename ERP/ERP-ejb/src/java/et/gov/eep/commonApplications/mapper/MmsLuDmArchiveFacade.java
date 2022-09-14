/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.mapper;

import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author insa
 */
@Stateless
public class MmsLuDmArchiveFacade extends AbstractFacade<MmsLuDmArchive> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsLuDmArchiveFacade() {
        super(MmsLuDmArchive.class);
    }

    public List<MmsLuDmArchive> getFileList(MmsLuDmArchive mmsLuDmArchive) {
        System.out.println("getting File List By Document Id " + mmsLuDmArchive);
        Query query = em.createNamedQuery("MmsLuDmArchive.findByDocumentId");
        query.setParameter("documentId", mmsLuDmArchive.getDocumentId());
        List<MmsLuDmArchive> fileLists = new ArrayList<>();
        try {
            if (query.getResultList().size() > 0) {
                fileLists = query.getResultList();
            }
            return fileLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
