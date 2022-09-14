/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.training.HrTrainerProfilesBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import et.gov.eep.hrms.integration.HrAddressesBeanIntegrationLocal;

/**
 *
 * @author Mac
 */
@Named(value = "trainerProfilecontroller")
@ViewScoped
public class trainerProfilecontroller implements Serializable {

    @Inject
    HrTdTrainerProfiles hrTdTrainerProfiles;
    @Inject
    HrAddresses hrAddresses;
    @Inject
    HrTdTrainerProfiles srcTrainerProfiles;
    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrTrainerProfilesBeanLocal hrTrainerProfilesBeanLocal;
    @EJB
    HrAddressesBeanIntegrationLocal hrAddressesBeanIntegrationLocal;

    private boolean btnaddvisibility = true;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";
    private int updateStatus = 0;
    private String SaveOrUpdateButton = "Save";
    private int addressId = 0;
    private String selected;
    private String selectedid;
    private TreeNode root;
    private TreeNode selectedNode;
    private String allAddressUnitAsOne;

    DataModel<HrTdTrainerProfiles> trainerProfileDataModel = new ListDataModel<>();
    List<HrTdTrainerProfiles> listInstitutions = new ArrayList<>();
    private static List<HrAddresses> allAddressData = new ArrayList<>();
    private static List<HrAddresses> addresses;
    List<HrTdTrainerProfiles> institutionNameList;

    public trainerProfilecontroller() {
    }

    @PostConstruct
    public void init() {
        loadTree();
    }

    //<editor-fold defaultstate="collapsed" desc="getters & setters">  
    public HrTdTrainerProfiles getHrTdTrainerProfiles() {
        if (hrTdTrainerProfiles == null) {
            hrTdTrainerProfiles = new HrTdTrainerProfiles();
        }
        return hrTdTrainerProfiles;
    }

    public void setHrTdTrainerProfiles(HrTdTrainerProfiles hrTdTrainerProfiles) {
        this.hrTdTrainerProfiles = hrTdTrainerProfiles;
    }

    public HrTdTrainerProfiles getSrcTrainerProfiles() {
        if (srcTrainerProfiles == null) {
            srcTrainerProfiles = new HrTdTrainerProfiles();
        }
        return srcTrainerProfiles;
    }

    public void setSrcTrainerProfiles(HrTdTrainerProfiles srcTrainerProfiles) {
        this.srcTrainerProfiles = srcTrainerProfiles;
    }

    public HrAddresses getHrAddresses() {
        if (hrAddresses == null) {
            hrAddresses = new HrAddresses();
        }
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public DataModel<HrTdTrainerProfiles> getTrainerProfileDataModel() {
        return trainerProfileDataModel;
    }

    public void setTrainerProfileDataModel(DataModel<HrTdTrainerProfiles> trainerProfileDataModel) {
        this.trainerProfileDataModel = trainerProfileDataModel;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getSelectedid() {
        return selectedid;
    }

    public void setSelectedid(String selectedid) {
        this.selectedid = selectedid;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public static List<HrAddresses> getAllAddressData() {
        return allAddressData;
    }

    public static void setAllAddressData(List<HrAddresses> allAddressData) {
        trainerProfilecontroller.allAddressData = allAddressData;
    }

    public static List<HrAddresses> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<HrAddresses> addresses) {
        trainerProfilecontroller.addresses = addresses;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    public List<HrTdTrainerProfiles> getInstitutionNameList() {
        institutionNameList = hrTrainerProfilesBeanLocal.findAll();
        return institutionNameList;
    }

    public void setInstitutionNameList(List<HrTdTrainerProfiles> institutionNameList) {
        this.institutionNameList = institutionNameList;
    }
    // </editor-fold>

    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            btnNewRender = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            btnNewRender = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        clearTrainerProfile();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
    }

    public HrTdTrainerProfiles findByInameObj(ValueChangeEvent event) {
        hrTdTrainerProfiles.setInstitutionName(event.getNewValue().toString());
        return hrTrainerProfilesBeanLocal.findByInameObj(hrTdTrainerProfiles);
    }

    public void loadTree() {
        allAddressData = hrAddressesBeanIntegrationLocal.findAllAddressUnit();
        root = new DefaultTreeNode("Root", null);
        populateNodes(allAddressData, 0, root);
    }

    public void populateNodes(List<HrAddresses> addressData, int id, TreeNode node) {
        addresses = new ArrayList<>();
        if (!allAddressData.isEmpty()) {
            for (HrAddresses k : getAllAddressData()) {
                if (k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getAddressDescription() + "=>" + k.getAddressId(), node);
                    addresses.add(k);
                    populateNodes(addresses, k.getAddressId(), childNode);
                }
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        addressId = Integer.parseInt((selectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllAddressUnitAsOne(hrAddresses.getAddressName());
        hrTdTrainerProfiles.setAddressId(hrAddresses);
        RequestContext context2 = RequestContext.getCurrentInstance();
        context2.execute("PF('dlgAddress').hide();");
    }

    public void saveTrainerProfile() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveTrainerProfile", dataset)) {
                boolean checkDuplication = hrTrainerProfilesBeanLocal.isExist(hrTdTrainerProfiles);
                if (allAddressUnitAsOne == null) {
                    JsfUtil.addFatalMessage("Address can't be empty");
                } else {
                    if (updateStatus == 0 && checkDuplication == false) {
                        hrTrainerProfilesBeanLocal.create(hrTdTrainerProfiles);
                        JsfUtil.addSuccessMessage("Successfully saved");
                        clearTrainerProfile();
                    } else if (updateStatus == 0 && checkDuplication == true) {
                        JsfUtil.addFatalMessage(hrTdTrainerProfiles.getInstitutionName() + " is already registered. Try again!");
                    } else {
                        hrTrainerProfilesBeanLocal.update(hrTdTrainerProfiles);
                        JsfUtil.addSuccessMessage("Successfully updated");
                        clearTrainerProfile();
                    }
                    SaveOrUpdateButton = "Save";
                    updateStatus = 0;
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveTrainerProfile");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void clearTrainerProfile() {
        hrTdTrainerProfiles = null;
        allAddressUnitAsOne = null;
        SaveOrUpdateButton = "Save";
        updateStatus = 0;
    }

    public void findByInstitutionName() {
        if (hrTdTrainerProfiles.getInstitutionName() != null) {
            trainerProfileDataModel = new ListDataModel(new ArrayList(hrTrainerProfilesBeanLocal.findByInstitutionName(hrTdTrainerProfiles)));
        }
    }

    public ArrayList<HrTdTrainerProfiles> loadTrainerProfile(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            String instName = event.getNewValue().toString();
            if (instName.equalsIgnoreCase("Load all")) {
                trainerProfileDataModel = new ListDataModel(new ArrayList(hrTrainerProfilesBeanLocal.findAll()));
            } else {
                trainerProfileDataModel = new ListDataModel(new ArrayList(hrTrainerProfilesBeanLocal.findByInstName(instName)));
            }
        }
        return null;
    }

    public void populateTrainerProfile(SelectEvent event) {
        hrTdTrainerProfiles = (HrTdTrainerProfiles) event.getObject();
        setAllAddressUnitAsOne(hrTdTrainerProfiles.getAddressId().getAddressName());
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
    }

}
