/*******************************************************************************
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 ******************************************************************************/
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.LookUp;
import et.gov.eep.commonApplications.entity.LuMenuCatagory;
import et.gov.eep.commonApplications.entity.MenuItems;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/*******************************************************************************
 *
 * @author insa
 ******************************************************************************/
@Local
public interface MenuItemBeanLocal {

    public void saveOrUpdate(MenuItems menuItems);

    public List<MenuItems> searchMenuItem(MenuItems menuItems);
    
    public ArrayList<LookUp> CatagoryNoForCheck();

    public List<LuMenuCatagory> getCategoryName();
    
    public List<MenuItems> getResourceName();
    
    public List<MenuItems> getResourceNameByMenu(LuMenuCatagory luMenuCatagory);
    
    public List<LuMenuCatagory> getCatagoryNameByModule(LuMenuCatagory luMenuCatagory);
}
