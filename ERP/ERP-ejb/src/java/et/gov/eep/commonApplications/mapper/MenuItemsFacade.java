/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.mapper;

import et.gov.eep.commonApplications.entity.LuMenuCatagory;
import et.gov.eep.commonApplications.entity.MenuItems;
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
public class MenuItemsFacade extends AbstractFacade<MenuItems> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuItemsFacade() {
        super(MenuItems.class);
    }

    public List<LuMenuCatagory> findMenuCatagory() {
        Query query = em.createNamedQuery("LuMenuCatagory.findAll");
        try {
            ArrayList<LuMenuCatagory> catagrory = new ArrayList(query.getResultList());
            return catagrory;
        } catch (Exception ex) {
        }
        return null;
    }

    public LuMenuCatagory searchByCategoryName(LuMenuCatagory luMenuCatagory) {
        Query query = em.createNamedQuery("LuMenuCatagory.findByCatacoryName");
        query.setParameter("catacoryName", luMenuCatagory.getCatacoryName());
        try {
            LuMenuCatagory coursesList = (LuMenuCatagory) query.getResultList().get(0);
            return coursesList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
