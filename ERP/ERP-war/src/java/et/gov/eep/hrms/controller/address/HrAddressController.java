/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.address;

import//<editor-fold defaultstate="collapsed" desc="">
        et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.address.HrAddressesBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
//</editor-fold>

/**
 *
 * @author munir
 */
@Named("hrAddressController")
@ViewScoped
public class HrAddressController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Initializations">
    @EJB
    private HrAddressesBeanLocal hrAddressBeanLocal;
    
    @Inject
            HrAddresses hrAddresses;
    
    private boolean reloadTree = true;
    private int saveOrUpdate = 0;
    private boolean isAllCountry = false;
    private int addressId = 0;
    private int parentId;
    private String addressType = "All Country";
    private boolean disableAddName = true;
    private boolean disableAddDesc = true;
    private boolean disableNew = true;
    private boolean disableSave = true;
    private boolean disableDelete = true;
    private String selected;
    private TreeNode root;
    private TreeNode selectedNode;
    private static List<HrAddresses> allAddressData;
    private static List<HrAddresses> addresses;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Post Constructor Method">
    @PostConstruct
    public void init() {
        if (reloadTree) {
            reloadTree = false;
            loadTree("allAddressUnit");
        }
    }
//</editor-fold>

    //  <editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrAddresses getHrAddresses() {
        if (hrAddresses == null) {
            hrAddresses = new HrAddresses();
        }
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public boolean isDisableAddName() {
        return disableAddName;
    }

    public void setDisableAddName(boolean disableAddName) {
        this.disableAddName = disableAddName;
    }

    public boolean isDisableAddDesc() {
        return disableAddDesc;
    }

    public void setDisableAddDesc(boolean disableAddDesc) {
        this.disableAddDesc = disableAddDesc;
    }

    public boolean isDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public boolean isDisableNew() {
        return disableNew;
    }

    public void setDisableNew(boolean disableNew) {
        this.disableNew = disableNew;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isIsAllCountry() {
        return isAllCountry;
    }

    public void setIsAllCountry(boolean isAllCountry) {
        this.isAllCountry = isAllCountry;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="main">
    public void loadTree(String type) {
        root = new DefaultTreeNode("Root", null);
        switch (type) {
            case "allAddressUnit":
                allAddressData = hrAddressBeanLocal.findAllAddressUnit();
                populateNodes(allAddressData, 0, root);
                break;
            case "allAddressUnitAndCountry":
                allAddressData = hrAddressBeanLocal.findAllAddressUnitAndCountries();
                populateNodes(allAddressData, -1, root);
                break;
        }
    }

    public void populateNodes(List<HrAddresses> addressData, int parentId, TreeNode node) {
        addresses = new ArrayList<>();
        for (HrAddresses addressObj : getAllAddressData()) {
            if (addressObj.getParentId() == parentId) {
                TreeNode childNode = new DefaultTreeNode(addressObj.getAddressDescription() + "=>" + addressObj.getAddressId(), node);
                addresses.add(addressObj);
                populateNodes(addresses, addressObj.getAddressId(), childNode);
            }
        }
    }

    public static List<HrAddresses> getAllAddressData() {
        return allAddressData;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = (TreeNode) event.getTreeNode();
        addressId = Integer.parseInt((selectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressBeanLocal.findByAddressId(hrAddresses);
        addressType = hrAddresses.getAddressType();
        parentId = hrAddresses.getAddressId();
        setDisableAddName(false);
        setDisableAddDesc(false);
        if (addressId != 0) {
            setDisableSave(false);
            setDisableDelete(false);
        }
        setDisableNew(false);
        saveOrUpdate = 1;
    }

    public void clearComponent() {
        selectedNode = null;
        hrAddresses = null;
    }

    public void btnNew() {
        clearComponent();
        setDisableAddName(false);
        setDisableAddDesc(false);
        setDisableNew(true);
        setDisableSave(false);
        setDisableDelete(true);
        saveOrUpdate = 0;
//        isAllCountry = false;
    }

    private HrAddresses saveAddressDetail() {
        HrAddresses addressObj = new HrAddresses();
        if (addressType.equalsIgnoreCase("All Country")) {
            hrAddresses.setAddressType("Country");
        } else if (addressType.equalsIgnoreCase("Country")) {
            hrAddresses.setAddressType("City/Region");
        } else if (addressType.equalsIgnoreCase("City/Region")) {
            hrAddresses.setAddressType("City/Sub City/Zone");
        } else if (addressType.equalsIgnoreCase("City/Sub City/Zone")) {
            hrAddresses.setAddressType("Woreda/kebele");
        } else if (addressType.equalsIgnoreCase("Woreda/kebele")) {
            hrAddresses.setAddressType("kebele");
        } else {
            hrAddresses.setAddressType("unit");
        }
        addressObj.setAddressId(hrAddresses.getAddressId());
        addressObj.setAddressName(hrAddresses.getAddressName());
        addressObj.setAddressDescription(hrAddresses.getAddressDescription());
        addressObj.setAddressType(hrAddresses.getAddressType());
        addressObj.setParentId(parentId);
        addressObj.setStatus(BigInteger.ONE);
        return addressObj;
    }

    private HrAddresses updateAddressDetail() {
        HrAddresses address = new HrAddresses();
        address.setAddressId(hrAddresses.getAddressId());
        address.setAddressName(hrAddresses.getAddressName());
        address.setAddressDescription(hrAddresses.getAddressDescription());
        address.setAddressType(hrAddresses.getAddressType());
        address.setParentId(hrAddresses.getParentId());
        address.setStatus(BigInteger.ONE);
        return address;
    }

    public void btnSave() {
        try {
            if (saveOrUpdate == 0) {
                hrAddressBeanLocal.save(saveAddressDetail());
                JsfUtil.addSuccessMessage("Address Information is Successfully Saved.");
                clearComponent();
                setDisableAddName(true);
                setDisableAddName(false);
                setDisableNew(false);
                setDisableSave(true);
                setDisableDelete(true);
                if (isAllCountry) {
                    loadTree("allAddressUnitAndCountry");
                } else {
                    loadTree("allAddressUnit");
                }
            } else {
                hrAddressBeanLocal.edit(updateAddressDetail());
                JsfUtil.addSuccessMessage("Address Information is Successfully Updated.");
                clearComponent();
                setDisableNew(false);
                setDisableSave(true);
                setDisableDelete(true);
                if (isAllCountry) {
                    loadTree("allAddressUnitAndCountry");
                } else {
                    loadTree("allAddressUnit");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addErrorMessage("error");
        }
    }

    public void btnDelete() {
        try {
            hrAddresses.setAddressId(addressId);
            hrAddressBeanLocal.remove(hrAddresses);
            JsfUtil.addSuccessMessage("Address Information is Successfully Deleted.");
            clearComponent();
            setDisableSave(true);
            setDisableDelete(true);
            if (isAllCountry) {
                loadTree("allAddressUnitAndCountry");
            } else {
                loadTree("allAddressUnit");
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("error");
        }
    }

    public void ckbAllCountry(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                isAllCountry = true;
                loadTree("allAddressUnitAndCountry");
            } else {
                isAllCountry = false;
                loadTree("allAddressUnit");
            }
        }
    }
    // </editor-fold>
}
