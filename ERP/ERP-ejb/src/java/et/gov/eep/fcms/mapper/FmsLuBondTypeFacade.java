/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Bond.FmsLuBondType;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mora
 */
@Stateless
public class FmsLuBondTypeFacade extends AbstractFacade<FmsLuBondType> {

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
    public FmsLuBondTypeFacade() {
        super(FmsLuBondType.class);
    }

    /**
     *
     * @param luBondType
     * @return
     */
    public ArrayList<FmsLuBondType> searchFmsLuBondType(FmsLuBondType luBondType) {
        Query query = em.createNamedQuery("FmsLuBondType.findByNameLike");
        query.setParameter("name", luBondType.getName() + '%');
        try {
            ArrayList<FmsLuBondType> LubondTypeList = new ArrayList(query.getResultList());
            return LubondTypeList;
        } catch (Exception ex) {
            // ex.printStackTrace();
            System.out.println("not fund");
            return null;
        }
    }

    /**
     *
     * @param luBondType
     * @return
     */
    public FmsLuBondType getFmsLuLubondTypeInfo(FmsLuBondType luBondType) {
        Query query = em.createNamedQuery("FmsLuBondType.findByName");
        query.setParameter("name", luBondType.getName());
        try {
            FmsLuBondType bondTypeInfo = (FmsLuBondType) query.getResultList().get(0);
            return bondTypeInfo;

        } catch (Exception ex) {
            //    ex.printStackTrace();
            return null;
        }
    }

    public List<FmsLuBondType> searchLuBondType() {
        Query query = em.createNamedQuery("FmsLuBondType.findAll");
//        query.setParameter("name", luBondType.getName());
        try {
           List<FmsLuBondType> LubondTypeList = new ArrayList(query.getResultList());
            return LubondTypeList;
        } catch (Exception ex) {
            // ex.printStackTrace();
            System.out.println("not fund");
            return null;
        }
    }
}
