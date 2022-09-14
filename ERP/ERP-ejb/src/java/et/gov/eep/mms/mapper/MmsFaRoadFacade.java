/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFaRoad;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsFaRoadFacade extends AbstractFacade<MmsFaRoad> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsFaRoadFacade() {
        super(MmsFaRoad.class);
    }

    public MmsFaRoad getLastRoadId() {
        
        Query query1 = em.createNamedQuery("MmsFaRoad.findByroadIdMaximum");
       
         MmsFaRoad result=null;
          
          
        try {
            if(query1.getResultList().size()>0)
            {
             result = (MmsFaRoad) query1.getResultList().get(0);
            }
            else
            {
                return result;
            }
            
            return result;
        } catch (Exception ex) {
            
            ex.printStackTrace();
            return null;
        } 
    }

    public List<MmsFaRoad> searchRoadByParameterPrefix(MmsFaRoad roadEntity) {
           Query query = em.createNamedQuery("MmsFaRoad.findByAllParameters");
        query.setParameter("roadNo", '%' + roadEntity.getRoadNo() + '%');
        
         try {
            ArrayList<MmsFaRoad> lostItemList = new ArrayList(query.getResultList());           
            return lostItemList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsFaRoad getSelectedRequest(BigDecimal roadId) {
         Query query = em.createNamedQuery("MmsFaRoad.findByRoadId");
        query.setParameter("roadId", roadId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsFaRoad selectrequest = (MmsFaRoad) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } 
    }
    
}
