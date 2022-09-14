/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.LuMenuCatagory;
import et.gov.eep.commonApplications.entity.MenuItems;
import et.gov.eep.hrms.entity.address.HrAddresses;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface MenuItemsBeanLocal {

    public void saveorupdate(MenuItems menuItems);

    public List<LuMenuCatagory> findByCatagory();
    
    public LuMenuCatagory searchByCategoryName(LuMenuCatagory luMenuCatagory);

    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses);
}
