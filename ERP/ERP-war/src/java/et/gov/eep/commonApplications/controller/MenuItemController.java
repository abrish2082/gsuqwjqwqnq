/**
 * *****************************************************************************
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 * ****************************************************************************
 */
package et.gov.eep.commonApplications.controller;

import et.gov.eep.commonApplications.entity.MenuItems;
import et.gov.eep.commonApplications.businessLogic.MenuItemBeanLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import et.gov.eep.commonApplications.entity.LuMenuCatagory;
import et.gov.eep.commonApplications.utility.JsfUtil;
import java.util.ArrayList;
import javax.inject.Named;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.event.SelectEvent;

/**
 * *****************************************************************************
 *
 * @author User
 * ***************************************************************************
 */
@Named(value = "MenuItemControllers")
@ViewScoped
public class MenuItemController implements Serializable {

    @Inject
    MenuItems menuItems;
    @Inject
    LuMenuCatagory luMenuCatagory;
    @Inject
    LuMenuCatagory luMenuModule;

    @EJB
    MenuItemBeanLocal menuItemsBeanLocal;
    List<LuMenuCatagory> luMenuCatagoryList;
    List<LuMenuCatagory> luMenuModuleList;
    List<MenuItems> MenuItemResourceList;

    private String SaveOrUpdateButton = "Save";

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public MenuItems getMenuItems() {

        if (menuItems == null) {
            menuItems = new MenuItems();
        }

        return menuItems;
    }

    public void setMenuItems(MenuItems menuItems) {

        this.menuItems = menuItems;
    }

    public MenuItemBeanLocal getMenuItemsBeanLocal() {

        return menuItemsBeanLocal;
    }

    public void setMenuItemsBeanLocal(MenuItemBeanLocal menuItemsBeanLocal) {

        this.menuItemsBeanLocal = menuItemsBeanLocal;
    }

    public LuMenuCatagory getLuMenuCatagory() {
        if (luMenuCatagory == null) {
            luMenuCatagory = new LuMenuCatagory();
        }
        return luMenuCatagory;
    }

    public void setLuMenuCatagory(LuMenuCatagory luMenuCatagory) {
        this.luMenuCatagory = luMenuCatagory;
    }

    public LuMenuCatagory getLuMenuModule() {

        if (luMenuModule == null) {
            luMenuModule = new LuMenuCatagory();
        }

        return luMenuModule;
    }

    public void setLuMenuModule(LuMenuCatagory luMenuModule) {
        this.luMenuModule = luMenuModule;
    }

//    public SelectItem[] catagoryNoFromLuMenu() {
//        return JsfUtil.getSelectItems(menuItemsBeanLocal.CatagoryNoForCheck(), true);
//    }
    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public List<MenuItems> getMenuItemResourceList() {

        MenuItemResourceList = menuItemsBeanLocal.getResourceName();
        System.out.println("sizes===" + MenuItemResourceList.size());

        return MenuItemResourceList;
    }

    /**
     * *************************************************************************
     *
     * @param MenuItemResourceList
     * ************************************************************************
     */
    public void setMenuItemResourceList(
            List<MenuItems> MenuItemResourceList) {

        this.MenuItemResourceList = MenuItemResourceList;
    }

    @PostConstruct
    public void init() {

//        luMenuCatagoryList = menuItemsBeanLocal.getCategoryName();
        luMenuModuleList = menuItemsBeanLocal.getCategoryName();
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public List<LuMenuCatagory> getLuMenuModuleList() {

        if (luMenuModuleList == null) {
            luMenuModuleList = new ArrayList<>();
        }

        luMenuModuleList = menuItemsBeanLocal.getCategoryName();

        return luMenuModuleList;
    }

    public void setLuMenuModuleList(List<LuMenuCatagory> luMenuModuleList) {
        this.luMenuModuleList = luMenuModuleList;
    }

    /**
     ***************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public List<LuMenuCatagory> getLuMenuCatagoryList() {

        if (luMenuCatagoryList == null) {
            luMenuCatagoryList = new ArrayList<>();
        }//        
//        System.out.println("sizes===" + luMenuCatagoryList.size());

        return luMenuCatagoryList;
    }

    /**
     * *************************************************************************
     *
     * @param luMenuCatagoryList
     * ************************************************************************
     */
    public void setLuMenuCatagoryList(List<LuMenuCatagory> luMenuCatagoryList) {

        this.luMenuCatagoryList = luMenuCatagoryList;
    }

    /**
     * *************************************************************************
     *
     *
     ***************************************************************************
     */
//    public void saveMenuItem() {
//
//        try {
//            menuItemsBeanLocal.saveOrUpdate(menuItems);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
    /**
     * *************************************************************************
     *
     *
     ***************************************************************************
     */
    public void searchContractInfo() {

        menuItems = (MenuItems) menuItemsBeanLocal.searchMenuItem(menuItems);
    }

    /**
     * *************************************************************************
     *
     * @param event
     * **************************************************************************
     */
    public void changeEventCatagoryNo(ValueChangeEvent event) {

        if (null != event.getNewValue() && !event.getNewValue().equals("")
                && !event.getNewValue().equals("--- Select One ---")) {

            luMenuCatagory = (LuMenuCatagory) event.getNewValue();
            MenuItemResourceList
                    = menuItemsBeanLocal.getResourceNameByMenu(luMenuCatagory);
        }
    }

    /***************************************************************************
     *
     *
     * @param event
     * *************************************************************************
     */
    public void changeEventModuleName(ValueChangeEvent event) {

        if (null != event.getNewValue() && !event.getNewValue().equals("")
                && !event.getNewValue().equals("--- Select One ---")) {

//            luMenuCatagory = (LuMenuCatagory) event.getNewValue();
//            luMenuCatagoryList = menuItemsBeanLocal.getCatagoryNameByModule(luMenuCatagory);
            try {

                System.out.println("Hello 1");
                luMenuCatagory = (LuMenuCatagory) event.getNewValue();
                System.out.println("Hello 2");
                luMenuCatagoryList
                        = menuItemsBeanLocal.getCatagoryNameByModule(luMenuCatagory);
                System.out.println("Hello 3");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * *************************************************************************
     *
     *
     * @param event
     *
     ***************************************************************************
     */
    public void populate(SelectEvent event) {

        SaveOrUpdateButton = "Update";
    }

    /**
     * *************************************************************************
     *
     *
     ***************************************************************************
     */
    public void saveMenuItem() {
//        try {

//            AAA securityService = new AAA();
//            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//            String systemBundle = "et/gov/eep/commonApplications/securityServer";
//            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
//            
//            if (security.checkAccess(SessionBean.getUserName(), "savePreserviceTraineeResult", dataset)) {
        try {
            menuItems.setMenuCatagoryId(luMenuCatagory);
            System.out.println(" A==> " + luMenuCatagory);
            System.out.println(" B==> " + menuItems.getDescription());
            menuItemsBeanLocal.saveOrUpdate(menuItems);
            // Clear Saved Values
            clearPage();

        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Something occure when data Save");
        }
//            } else {
//                EventEntry eventEntry = new EventEntry();
//                eventEntry.setSessionId(SessionBean.getSessionID());
//                eventEntry.setUserId(SessionBean.getUserId());
//                QName qualifiedName = new QName("", "project");
//                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
//                eventEntry.setUserLogin(test);
////..... add more information by calling fields defined in the object 
//                security.addEventLog(eventEntry, dataset);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    private String clearPage() {

        menuItems = null;
        luMenuCatagory = null;

        return null;
    }

}
