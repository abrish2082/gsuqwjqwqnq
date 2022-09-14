/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.LuMenuCatagory;
import et.gov.eep.commonApplications.entity.MenuItems;
import et.gov.eep.commonApplications.mapper.MenuItemsFacade;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.mapper.address.HrAddressesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class MenuItemsBean implements MenuItemsBeanLocal {

    @EJB
    MenuItemsFacade menuItemsFacade;
    
    @EJB
    HrAddressesFacade hrAddressesFacade;

    @Override
    public void saveorupdate(MenuItems menuItems) {
        menuItemsFacade.saveOrUpdate(menuItems);
    }

    @Override
    public List<LuMenuCatagory> findByCatagory() {
        return menuItemsFacade.findMenuCatagory();
    }

    @Override
    public LuMenuCatagory searchByCategoryName(LuMenuCatagory luMenuCatagory) {
        return menuItemsFacade.searchByCategoryName(luMenuCatagory);
    }

    @Override
    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses) {
        return hrAddressesFacade.findAllAddressUnitAsOne(hrAddresses);
    }
}
