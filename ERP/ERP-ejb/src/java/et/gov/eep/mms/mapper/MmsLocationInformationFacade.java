/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

//import et.gov.insa.eep.erp.pamps.entity.MmsLocationInformation;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsLocationInformation;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kimmyo
 */
@Stateless
public class MmsLocationInformationFacade extends AbstractFacade<MmsLocationInformation> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsLocationInformationFacade() {
        super(MmsLocationInformation.class);
    }

    /**
     *
     * @param locationInformation
     * @return
     */
    public ArrayList<MmsLocationInformation> searchStoreAndShelfInfo(MmsLocationInformation locationInformation) {
        Query query = em.createNamedQuery("MmsLocationInformation.SearchByStoreNameAndShelfNo",MmsLocationInformation.class);
        query.setParameter("storeName", locationInformation.getStoreId().getStoreName());
        query.setParameter("shelfNo", locationInformation.getShelfNo().toUpperCase() + "%");
        try {
            ArrayList<MmsLocationInformation> locationInformations = new ArrayList(query.getResultList());
        
            return locationInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param locationInformation
     * @return
     */
    public MmsLocationInformation getMmsLocationInformation(MmsLocationInformation locationInformation) {
        Query query = em.createNamedQuery("MmsLocationInformation.findByShelfNo", MmsLocationInformation.class);
        query.setParameter("shelfNo", locationInformation.getShelfNo());
        try {
            MmsLocationInformation importationInfo = (MmsLocationInformation) query.getResultList().get(0);
            return importationInfo;
        } catch (Exception ex) {

            return null;
        }
    }

    /**
     *
     * @param locationInformation
     * @return
     */
    public boolean getMmsLocationInformationDup(MmsLocationInformation locationInformation) {
        boolean duplicaton;
        Query query = em.createNamedQuery("MmsLocationInformation.SearchByStoreNameAndShelfNo", MmsLocationInformation.class);
        query.setParameter("shelfNo", locationInformation.getShelfNo());
        query.setParameter("storeName", locationInformation.getStoreId().getStoreName());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }
    }
}
