/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.controller;

import et.gov.eep.commonApplications.businessLogic.MenuItemsBeanLocal;
import et.gov.eep.commonApplications.entity.LuMenuCatagory;
import et.gov.eep.commonApplications.entity.MenuItems;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.organization.HrDepAddresses;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author insa
 */
@Named(value = "menuController")
@ViewScoped
public class MenuController implements Serializable {

    @Inject
    LuMenuCatagory luMenuCatagory;

    @Inject
    MenuItems menuItems;

    @EJB
    MenuItemsBeanLocal menuItemsBeanLocal;

    int update = 0;
    private boolean renderPnlCreateAdditional = true;
    private String addorUpdate = "Add";
    boolean btnaddvisibility = true;
    private String createOrSearchBundle = "";
    private String headerTitle = "Search....";
    private String saveorUpdateBundle = "Save";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private String renderpnlContrat = "false";
    List<LuMenuCatagory> luMenuCatagoryList = new ArrayList<>();
    DataModel<MenuItems> menuListModel;
    private String allAddressUnitAsOne;
    private TreeNode addressRoot;
    private TreeNode addressSelectedNode;
    private int addressId;

    @PostConstruct
    public void init() {
        luMenuCatagoryList = menuItemsBeanLocal.findByCatagory();
    }
    //<editor-fold defaultstate="collapsed" desc="getter setter">

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public TreeNode getAddressRoot() {
        return addressRoot;
    }

    public void setAddressRoot(TreeNode addressRoot) {
        this.addressRoot = addressRoot;
    }

    public TreeNode getAddressSelectedNode() {
        return addressSelectedNode;
    }

    public void setAddressSelectedNode(TreeNode addressSelectedNode) {
        this.addressSelectedNode = addressSelectedNode;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    public List<LuMenuCatagory> getLuMenuCatagoryList() {
        return luMenuCatagoryList;
    }

    public void setLuMenuCatagoryList(List<LuMenuCatagory> luMenuCatagoryList) {
        this.luMenuCatagoryList = luMenuCatagoryList;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
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

    public MenuItems getMenuItems() {
        if (menuItems == null) {
            menuItems = new MenuItems();
        }
        return menuItems;
    }

    public void setMenuItems(MenuItems menuItems) {
        this.menuItems = menuItems;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public DataModel<MenuItems> getMenuListModel() {
        if (menuListModel == null) {
            menuListModel = new ArrayDataModel<>();
        }
        return menuListModel;
    }

    public void setMenuListModel(DataModel<MenuItems> menuListModel) {
        this.menuListModel = menuListModel;
    }

    //</editor-fold>
    public void newPage() {
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        headerTitle = "SuccessionPlanning";
        saveorUpdateBundle = "save";
        createOrSearchBundle = "New";
    }

    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        headerTitle = "Search...";
        createOrSearchBundle = "New";
    }

    public void populate(SelectEvent events) {
        menuItems = (MenuItems) events.getObject();
//        recreateDataModel();
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "";
        headerTitle = "Menu Items";
        update = 1;
        saveorUpdateBundle = "Update";
    }

    public void catagoryName(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            luMenuCatagory.setCatacoryName(event.getNewValue().toString());
            luMenuCatagory = menuItemsBeanLocal.searchByCategoryName(luMenuCatagory);
            menuItems.setMenuCatagoryId(luMenuCatagory);
        }
    }
    @Inject
    HrAddresses hrAddresses;
    @Inject
    HrDepAddresses hrDepAddresses;

    public void onAddressNodeSelect(NodeSelectEvent event) {
        addressSelectedNode = event.getTreeNode();
        addressId = Integer.parseInt((addressSelectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = menuItemsBeanLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllAddressUnitAsOne(hrAddresses.getAddressName());
        hrDepAddresses.setLocationId(hrAddresses);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDepAddress').hide();");
    }

    public void saveMenuItems() {
        try {
            if (update == 0) {
//                menuItems.setMenuCatagoryId(luMenuCatagory);
                menuItemsBeanLocal.saveorupdate(menuItems);
                JsfUtil.addSuccessMessage("Successfully Saved");
                resetMenuItems();
            } else {
                JsfUtil.addSuccessMessage("Successfully Updated");
                resetMenuItems();
            }
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Error occured while Saving.");
        }
    }

    public void resetMenuItems() {
        menuItems = new MenuItems();
        luMenuCatagory = new LuMenuCatagory();
    }
}
