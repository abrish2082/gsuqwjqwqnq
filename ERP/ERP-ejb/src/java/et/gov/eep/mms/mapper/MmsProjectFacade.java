/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;

import et.gov.eep.mms.entity.MmsProject;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class MmsProjectFacade extends AbstractFacade<MmsProject> {

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
    public MmsProjectFacade() {
        super(MmsProject.class);
    }

    /**
     *
     * @param item
     * @return
     */
    public ArrayList<MmsProject> searchByjobNo(MmsProject item) {
        Query query = em.createNamedQuery("MmsProject.findByJobNoLike");
        query.setParameter("jobNo", item.getJobNo()+ '%');
        try {
            ArrayList<MmsProject> ItemList = new ArrayList(query.getResultList());
            return ItemList;
        } catch (Exception ex) {
            return null;
        }
    }
}
