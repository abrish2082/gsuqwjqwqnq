package et.gov.eep.hrms.controller.training;
//<editor-fold defaultstate="collapsed" desc="Imports">

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.training.HrTdUniversitiesBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrTdUniversities;
import et.gov.eep.hrms.integration.HrAddressesBeanIntegrationLocal;

//</editor-fold>
@Named("hrTdUniversitiesController")
@ViewScoped
public class hrTdUniversitiesController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @Inject
    HrTdUniversities hrTdUniversities;

    @Inject
    HrAddresses hrAddresses;

    @Inject
    HrDepartments hrDepartments;

    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrTdUniversitiesBeanLocal hrTdUniversitiesBeanLocal;

    @EJB
    HrAddressesBeanIntegrationLocal hrAddressesBeanIntegrationLocal;

    boolean btnaddvisibility = true;
    private String headerTitle = "Search Trainer Profile ";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";
    private int updateStatus = 0;
    String SaveOrUpdateButton = "Save";
    private int addressId = 0;
    private String addressType = "Country";
    private String selected;
    private String selectedid;
    private TreeNode root;
    private TreeNode selectedNode;
    private static List<HrAddresses> allAddressData = new ArrayList<>();
    private static List<HrAddresses> addresses;
    private String allAddressUnitAsOne;
    List<HrTdUniversities> universtyList;
    DataModel<HrTdUniversities> hrTdUniversitiesDataModel = new ListDataModel<>();
//</editor-fold>

    @PostConstruct
    public void init() {
        universtyList = hrTdUniversitiesBeanLocal.findAll();
        loadTree();
    }
    //  <editor-fold defaultstate="collapsed" desc="Getters & setter ">

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrTdUniversities getHrTdUniversities() {
        if (hrTdUniversities == null) {
            hrTdUniversities = new HrTdUniversities();
        }
        return hrTdUniversities;
    }

    public void setHrTdUniversities(HrTdUniversities hrTdUniversities) {
        this.hrTdUniversities = hrTdUniversities;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
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

    public DataModel<HrTdUniversities> getHrTdUniversitiesDataModel() {
        return hrTdUniversitiesDataModel;
    }

    public void setHrTdUniversitiesDataModel(DataModel<HrTdUniversities> hrTdUniversitiesDataModel) {
        this.hrTdUniversitiesDataModel = hrTdUniversitiesDataModel;
    }

    public List<HrTdUniversities> getUniverstyList() {
        return universtyList;
    }

    public void setUniverstyList(List<HrTdUniversities> universtyList) {
        this.universtyList = universtyList;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
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
        hrTdUniversitiesController.allAddressData = allAddressData;
    }

    public static List<HrAddresses> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<HrAddresses> addresses) {
        hrTdUniversitiesController.addresses = addresses;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    //  </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void newOrSearchPage() {
        switch (createOrSearchBundle) {
            case "New":
                renderPnlCreateGatePass = true;
                renderPnlManPage = false;
                btnNewRender = false;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderPnlCreateGatePass = false;
                renderPnlManPage = true;
                btnNewRender = false;
                createOrSearchBundle = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        clearUniversities();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        updateStatus = 0;
        SaveOrUpdateButton = "Save";
    }

    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
    }

    public void searchUniverstiryName(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String TdUniversitie = event.getNewValue().toString();
            if (TdUniversitie.equalsIgnoreCase("All University")) {
                hrTdUniversitiesDataModel = new ListDataModel(new ArrayList<>(universtyList));
            } else {
                recreateDataModel2(hrTdUniversitiesBeanLocal.findUniversity(TdUniversitie));
            }
        }
    }

    public void findByUniversityName() {
        if (hrTdUniversities.getUniversityName() != null) {
            hrTdUniversitiesDataModel = new ListDataModel(new ArrayList(hrTdUniversitiesBeanLocal.findByUniversityName(hrTdUniversities)));
        }
    }

    public void selectUniversity(SelectEvent event) {
        hrTdUniversities = (HrTdUniversities) event.getObject();
        setAllAddressUnitAsOne(hrTdUniversities.getAddressId().getAddressName());
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
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
        hrTdUniversities.setAddressId(hrAddresses);
        RequestContext context2 = RequestContext.getCurrentInstance();
        context2.execute("PF('dlgAddress').hide();");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void saveUniversity() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUniversity", dataset)) {
                try {
                    boolean isUniversityNameDuplicated = hrTdUniversitiesBeanLocal.checkDuplicationByName(hrTdUniversities);
                    if (isUniversityNameDuplicated == true) {
                        JsfUtil.addFatalMessage("University name is duplicated");
                    } else {
                        hrTdUniversitiesBeanLocal.saveOrUpdate(hrTdUniversities);
                        if (updateStatus == 0) {
                            JsfUtil.addSuccessMessage("Save Successfully.");
                        } else {
                            JsfUtil.addSuccessMessage("Update Successfully.");
                        }
                        clearUniversities();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JsfUtil.addErrorMessage("Error occure while save update");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveUniversity");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearUniversities() {
        hrTdUniversities = new HrTdUniversities();
        hrAddresses = new HrAddresses();
        allAddressData = null;
        addresses = null;
        allAddressUnitAsOne = null;
        hrTdUniversitiesDataModel = new ListDataModel<>();
    }

    private void recreateDataModel2(List<HrTdUniversities> findUniversity) {
        hrTdUniversitiesDataModel = null;
        hrTdUniversitiesDataModel = new ListDataModel(new ArrayList<>(findUniversity));
    }
    //</editor-fold>
}
