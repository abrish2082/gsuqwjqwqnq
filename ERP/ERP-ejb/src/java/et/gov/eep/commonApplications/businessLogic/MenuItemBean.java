/**
 * *****************************************************************************
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 * ****************************************************************************
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.LookUp;
import et.gov.eep.commonApplications.entity.LuMenuCatagory;
import et.gov.eep.commonApplications.entity.MenuItems;
import et.gov.eep.commonApplications.mapper.MenuItemFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * *****************************************************************************
 *
 * @author insa
 * ****************************************************************************
 */
@Stateless
public class MenuItemBean implements MenuItemBeanLocal {

    @EJB
    MenuItemFacade menuItemFacade;

    @Override
    public void saveOrUpdate(MenuItems menuItems) {
        menuItemFacade.saveOrUpdate(menuItems);
    }

    @Override
    public List<MenuItems> searchMenuItem(MenuItems menuItems) {
        return menuItemFacade.searchMenuItem(menuItems);
    }

    @Override
    public ArrayList<LookUp> CatagoryNoForCheck() {
        return menuItemFacade.CatagoryNoForCheck();
    }

    @Override
    public List<LuMenuCatagory> getCategoryName() {
        return menuItemFacade.getCategoryName();
    }

    @Override
    public List<MenuItems> getResourceName() {
        return menuItemFacade.getResourceName();
    }

    @Override
    public List<MenuItems> getResourceNameByMenu(LuMenuCatagory luMenuCatagory) {
        return menuItemFacade.getResourceNameByMenu(luMenuCatagory);
    }//getCatagoryNameByModule

    @Override
    public List<LuMenuCatagory> getCatagoryNameByModule(LuMenuCatagory luMenuCatagory) {
        return menuItemFacade.getCatagoryNameByModule(luMenuCatagory);//getCatagoryNameByModule
    }
}