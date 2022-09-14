/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsPettyCheqPaymControl;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class FmsPettyCheqPaymControlFacade extends AbstractFacade<FmsPettyCheqPaymControl> {
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
    public FmsPettyCheqPaymControlFacade() {
        super(FmsPettyCheqPaymControl.class);
    }
    
    /**
     *
     * @param bgtContol
     * @return
     */
    public FmsPettyCheqPaymControl getBgtControll(FmsPettyCheqPaymControl bgtContol) {
        Query query = em.createNamedQuery("FmsPettyCheqPaymControl.findByControlId");
        query.setParameter("controlId", bgtContol.getControlId());  
        System.out.println("bgt control is:"+query.getResultList());
        try {
            FmsPettyCheqPaymControl bgtControl = (FmsPettyCheqPaymControl)query.getResultList().get(0);           
            return bgtControl;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
     
    /**
     *
     * @return
     */
    public ArrayList<FmsPettyCheqPaymControl> getcapitalBgtControllList() {
        Query query = em.createNamedQuery("FmsPettyCheqPaymControl.findByCapitalBgtControlList");  
        try {
            ArrayList<FmsPettyCheqPaymControl> budgutControlList = new ArrayList(query.getResultList());

            return budgutControlList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<FmsPettyCheqPaymControl> getoperatingBgtControllList() {
        Query query = em.createNamedQuery("FmsPettyCheqPaymControl.findByOperatingBgtControlList");  
        try {
            ArrayList<FmsPettyCheqPaymControl> budgutControlList = new ArrayList(query.getResultList());

            return budgutControlList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
