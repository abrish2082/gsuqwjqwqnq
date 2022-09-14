/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.leave;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.leave.HrLuLeaveYear;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author insa
 */
@Stateless
public class HrLuLeaveYearFacade extends AbstractFacade<HrLuLeaveYear>  {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLuLeaveYearFacade() {
        super(HrLuLeaveYear.class);
    }

   
        
    //<editor-fold defaultstate="collapsed" desc="Bussiness Immplementation">
    @Override
    public ArrayList<HrLuLeaveYear> findAll() {
        Query query = em.createNamedQuery("HrLuLeaveYear.findAll");
        try {
            ArrayList<HrLuLeaveYear> LvYrs = new ArrayList(query.getResultList());
            return LvYrs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public HrLuLeaveYear findLeaveYr(HrLuLeaveYear hrLuLeaveYear) {
        Query query = em.createNamedQuery("HrLuLeaveYear.findByLyear");
        query.setParameter("year", hrLuLeaveYear.getLyear());
        try {
            HrLuLeaveYear lvyr = (HrLuLeaveYear) query.getResultList().get(0);
            return lvyr;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public HrLuLeaveYear findActiveLeaveYr() {
        Query query = em.createNamedQuery("HrLuLeaveYear.findActiveLyear");
        query.setParameter("stat", "1" );
        try {
            HrLuLeaveYear lvy = (HrLuLeaveYear) query.getResultList().get(0);
            System.err.println("active leave year==================="+lvy.getId());
            return lvy;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
//</editor-fold>
}
