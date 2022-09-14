/**
 * ****************************************************************************
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 *
 *****************************************************************************
 */
package et.gov.eep.commonApplications.mapper;

import et.gov.eep.commonApplications.entity.LookUp;
import et.gov.eep.commonApplications.entity.LuMenuCatagory;
import et.gov.eep.commonApplications.entity.MenuItems;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * *****************************************************************************
 *
 * @author insa
 * *****************************************************************************
 */
@Stateless
public class MenuItemFacade extends AbstractFacade<MenuItems> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuItemFacade() {
        super(MenuItems.class);
    }

    /**
     ***************************************************************************
     *
     * @param menuItems
     * @return
     * **************************************************************************
     */
    public List<MenuItems> searchMenuItem(MenuItems menuItems) {

        Query query = em.createNamedQuery("MenuItems.findAll");// Query query = em.createNamedQuery("MenuItems.findById");
        try {
            ArrayList<MenuItems> menuNo = new ArrayList(query.getResultList());
            System.out.println("-------------2========" + menuNo.size());

            return menuNo;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public ArrayList<LookUp> CatagoryNoForCheck() {

        Query query = em.createNativeQuery("SELECT distinct LU_MENU_CATAGORY.*\n"
                + " FROM LU_MENU_CATAGORY INNER JOIN MENU_ITEMS\n"
                + " ON LU_MENU_CATAGORY.ID = MENU_ITEMS.MENU_CATAGORY_ID "
                + " ORDER BY LU_MENU_CATAGORY.CATACORY_NAME", LookUp.class);
        try {
            ArrayList<LookUp> catagory_ = new ArrayList<>();
            catagory_ = new ArrayList<>(query.getResultList());
            System.err.println("Kokoko" + catagory_.size());

            return catagory_;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<LuMenuCatagory> getCategoryName() {
        System.out.println("here facade");
        Query query = em.createNamedQuery("LuMenuCatagory.findAll");
        try {
            ArrayList<LuMenuCatagory> catagoryLists = null;
            if (query.getResultList().size() > 0) {
                catagoryLists = new ArrayList<>(query.getResultList());
            }
            return catagoryLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public List<MenuItems> getResourceName() {

        System.out.println("here facade");
        Query query = em.createNamedQuery("MenuItems.findAll");

        try {
            ArrayList<MenuItems> catagoryLists = null;

            if (query.getResultList().size() > 0) {
                catagoryLists = new ArrayList<>(query.getResultList());
            }

            return catagoryLists;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @param luMenuCatagory
     * @return
     * **************************************************************************
     */
    public List<MenuItems> getResourceNameByMenu(LuMenuCatagory luMenuCatagory) {

//        luMenuCatagory
        Query query = em.createNamedQuery("MenuItems.findByMenuName");
        query.setParameter("menuName", luMenuCatagory.getCatacoryName() + '%');

        try {
            ArrayList<MenuItems> catagoryLists = null;

            if (query.getResultList().size() > 0) {
                catagoryLists = new ArrayList<>(query.getResultList());
            }

            return catagoryLists;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***************************************************************************
     * 
     * @param luMenuCatagory
     * @return 
     **************************************************************************/
    public List<LuMenuCatagory> getCatagoryNameByModule(
            LuMenuCatagory luMenuCatagory) {

        System.out.println("here facade 2 FF");
        Query query = em.createNamedQuery("LuMenuCatagory.findBymoduleName");
//        query.setParameter("moduleName", luMenuCatagory.getModuleName()+ '%');

        try {
            ArrayList<LuMenuCatagory> catagoryLists = null;
            
            if (query.getResultList().size() > 0) {
                catagoryLists = new ArrayList<>(query.getResultList());
            }
            
            return catagoryLists;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
