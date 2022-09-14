/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;
//<editor-fold defaultstate="collapsed" desc="Imports">

import java.io.Serializable;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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
import et.gov.eep.hrms.businessLogic.training.HrInternshipStudentsBeanLocal;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.training.HrTdUniversitiesBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.training.HrTdUniversities;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.integration.HrAddressesBeanIntegrationLocal;

//</editor-fold>
@Named(value = "intetrnshipStudController")
@ViewScoped
public class intetrnshipStudController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & Variables">

    @Inject
    HrTdIspStudents hrTdIspStudents;

    @Inject
    HrTdUniversities hrTdUniversities;

    @Inject
    HrTdIspStudentDetails hrTdIspStudentDetails;

    @Inject
    HrAddresses hrAddresses;

    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrInternshipStudentsBeanLocal hrInternshipStudentsBeanLocal;

    @EJB
    HrAddressesBeanIntegrationLocal hrAddressesBeanIntegrationLocal;

    @EJB
    HrTdUniversitiesBeanLocal hrTdUniversitiesBeanLocal;

    boolean btnaddvisibility = true;
    private String headerTitle = "Search Student";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private String addorUpdate = "Add";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";
    private int updateStatus = 0;
    int selectedRowIndexStudent = -1;
    private int addressId = 0;
    private String addressType = "Country";
    private String selected;
    private String selectedid;
    private TreeNode root;
    private TreeNode selectedNode;
    private static List<HrAddresses> allAddressData;
    private static List<HrAddresses> addresses;
    private String allAddressUnitAsOne;

    DataModel<HrTdIspStudentDetails> hrTdIspStudentDetailsDataModel = new ListDataModel<>();
    List<HrTdUniversities> listUniversities = new ArrayList<>();
    List<HrTdIspStudents> AllStudentsList;
    DataModel<HrTdIspStudents> hrInternshipStudentsListDataModel = new ListDataModel<>();
    List<HrTdUniversities> uniList = new ArrayList<>();
    private List<SelectItem> budgetYears;
    String SaveOrUpdateButton = "Save";

    public intetrnshipStudController() {
    }
    //</editor-fold>

    @PostConstruct
    public void init() {
        loadTree();
        setBudgetYears(readBudgetYears());
        uniList = hrInternshipStudentsBeanLocal.universities();
    }
    // <editor-fold defaultstate="collapsed" desc="Getters & setters">  

    public List<HrTdIspStudents> getAllStudentsList() {
        return AllStudentsList;
    }

    public void setAllStudentsList(List<HrTdIspStudents> AllStudentsList) {
        this.AllStudentsList = AllStudentsList;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public HrTdIspStudents getHrTdIspStudents() {
        if (hrTdIspStudents == null) {
            hrTdIspStudents = new HrTdIspStudents();
        }
        return hrTdIspStudents;
    }

    public void setHrTdIspStudents(HrTdIspStudents hrTdIspStudents) {
        this.hrTdIspStudents = hrTdIspStudents;
    }

    public HrAddresses getHrAddresses() {
        if (hrAddresses != null) {
            hrAddresses = new HrAddresses();
        }
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
    }

    public List<HrTdUniversities> getListUniversities() {
        return listUniversities;
    }

    public void setListUniversities(List<HrTdUniversities> listUniversities) {
        this.listUniversities = listUniversities;
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

    public HrTdIspStudentDetails getHrTdIspStudentDetails() {
        if (hrTdIspStudentDetails == null) {
            hrTdIspStudentDetails = new HrTdIspStudentDetails();
        }
        return hrTdIspStudentDetails;
    }

    public void setHrTdIspStudentDetails(HrTdIspStudentDetails hrTdIspStudentDetails) {
        this.hrTdIspStudentDetails = hrTdIspStudentDetails;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
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

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
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

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public DataModel<HrTdIspStudentDetails> getHrTdIspStudentDetailsDataModel() {
        return hrTdIspStudentDetailsDataModel;
    }

    public void setHrTdIspStudentDetailsDataModel(DataModel<HrTdIspStudentDetails> hrTdIspStudentDetailsDataModel) {
        this.hrTdIspStudentDetailsDataModel = hrTdIspStudentDetailsDataModel;
    }

    public DataModel<HrTdIspStudents> getHrInternshipStudentsListDataModel() {
        return hrInternshipStudentsListDataModel;
    }

    public void setHrInternshipStudentsListDataModel(DataModel<HrTdIspStudents> hrInternshipStudentsListDataModel) {
        this.hrInternshipStudentsListDataModel = hrInternshipStudentsListDataModel;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public List<HrTdUniversities> getUniList() {
        return uniList;
    }

    public void setUniList(List<HrTdUniversities> uniList) {
        this.uniList = uniList;
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
        intetrnshipStudController.allAddressData = allAddressData;
    }

    public static List<HrAddresses> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<HrAddresses> addresses) {
        intetrnshipStudController.addresses = addresses;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    // </editor-fold >
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void newOrSearchPage() {
        switch (createOrSearchBundle) {
            case "New":
                renderPnlCreateGatePass = true;
                renderPnlManPage = false;
                btnNewRender = false;
                setIcone("ui-icon-search");
                createOrSearchBundle = "Search";
                break;
            case "Search":
                renderPnlCreateGatePass = false;
                renderPnlManPage = true;
                btnNewRender = false;
                updateStatus = 0;
                createOrSearchBundle = "New";
                setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        clearInternshipStudentDetails();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        updateStatus = 0;
        SaveOrUpdateButton = "Save";
    }

    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        updateStatus = 0;
    }

    public void selectStudent(SelectEvent event) {
        hrTdIspStudents = (HrTdIspStudents) event.getObject();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        headerTitle = "Student Registration";
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
    }

    public void selectedUniversity(SelectEvent event) {
        hrTdIspStudents = (HrTdIspStudents) event.getObject();
        hrTdUniversities = hrTdIspStudents.getUniversityId();
        recreateStudentDetailDatamodel();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = true;
        SaveOrUpdateButton = "Update";
        updateStatus = 1;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }

    public void selectedStudent(SelectEvent event) {
        hrTdIspStudentDetails = (HrTdIspStudentDetails) event.getObject();
        setAllAddressUnitAsOne(hrTdIspStudentDetails.getAddressId().getAddressName());
        addorUpdate = "Modify";
    }

    public void loadTree() {
        allAddressData = hrAddressesBeanIntegrationLocal.findAllAddressUnit();
        root = new DefaultTreeNode("Root", null);
        populateNodes(allAddressData, 0, root);
    }

    public void populateNodes(List<HrAddresses> addressData, int id, TreeNode node) {
        addresses = new ArrayList<>();
        for (HrAddresses k : getAllAddressData()) {
            if (k.getParentId() == id) {
                TreeNode childNode = new DefaultTreeNode(k.getAddressDescription() + "=>" + k.getAddressId(), node);
                addresses.add(k);
                populateNodes(addresses, k.getAddressId(), childNode);
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        addressId = Integer.parseInt((selectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllAddressUnitAsOne(hrAddresses.getAddressName());
        hrTdIspStudentDetails.setAddressId(hrAddresses);
        RequestContext context2 = RequestContext.getCurrentInstance();
        context2.execute("PF('dlgAddress').hide();");
    }

    public List<SelectItem> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<SelectItem> budgetYears) {
        this.budgetYears = budgetYears;
    }

    public List<SelectItem> readBudgetYears() {
        List<SelectItem> budgetYear = new ArrayList<>();
        String[] toDay = StringDateManipulation.toDayInEc().split("-");
        int year = Integer.valueOf(toDay[0]) + 1;
        while (year > 2000) {
            budgetYear.add(new SelectItem(String.valueOf(year), String.valueOf(year - 1) + "/" + String.valueOf(year)));
            year--;
        }
        return budgetYear;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void SaveInternshipStudentDetails() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "SaveInternshipStudentDetails", dataset)) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
                    Date startDate = format.parse(hrTdIspStudents.getStartDate());
                    Date endDate = format.parse(hrTdIspStudents.getEndDate());
                    if (!(endDate.before(startDate)) && !(startDate.after(endDate))) {
                        JsfUtil.addFatalMessage("End date should be greater than start date");
                    } else if ((!(hrTdIspStudentDetailsDataModel.getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("Data Table Should be filled");
                    } else {
                        if (updateStatus == 0) {
                            hrTdIspStudentDetails.setStatus(0);
                            hrInternshipStudentsBeanLocal.create(hrTdIspStudents);
                            JsfUtil.addSuccessMessage("Save Successfully.");
                            clearInternshipStudentDetails();
                        } else {
                            hrInternshipStudentsBeanLocal.update(hrTdIspStudents);
                            JsfUtil.addSuccessMessage("Update Successfully.");
                            clearInternshipStudentDetails();
                        }
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "SaveInternshipStudentDetails");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearInternshipStudentDetails() {
        hrTdIspStudents = new HrTdIspStudents();
        hrTdIspStudentDetailsDataModel = new ListDataModel<>();
        hrTdUniversities = new HrTdUniversities();
        hrInternshipStudentsListDataModel = new ListDataModel<>();
    }

    public void addInternshipStudentDetails() {
        try {
            hrTdIspStudentDetails.setStatus(0);
            if (selectedRowIndexStudent > -1) {
                hrTdIspStudentDetails.setId(hrTdIspStudents.getHrTdIspStudentDetailsList().get(selectedRowIndexStudent).getId());
                hrTdIspStudents.getHrTdIspStudentDetailsList().remove(selectedRowIndexStudent);
                if (checkDuplicate()) {
                    hrTdIspStudents.getHrTdIspStudentDetailsList().add(selectedRowIndexStudent, hrTdIspStudentDetails);
                    JsfUtil.addSuccessMessage("Successfully Updated!");
                } else {
                    JsfUtil.addFatalMessage("Duplicate Entry!");
                }
                clearStudentDetail();
            } else {
                if (checkDuplicate()) {
                    hrTdIspStudents.addToStudentDetail(hrTdIspStudentDetails);
                    recreateStudentDetailDatamodel();
                } else {
                    JsfUtil.addFatalMessage("Duplicate Entry!");
                }
                clearStudentDetail();
            }
            selectedRowIndexStudent = -1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private boolean checkDuplicate() {
        for (HrTdIspStudentDetails stud : hrTdIspStudents.getHrTdIspStudentDetailsList()) {
            if (stud.getStudentId() != null) {
                if (stud.getStudentId().equalsIgnoreCase(hrTdIspStudentDetails.getStudentId())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void recreateStudentDetailDatamodel() {
        hrTdIspStudentDetailsDataModel = null;
        hrTdIspStudentDetailsDataModel = new ListDataModel<>(new ArrayList(hrTdIspStudents.getHrTdIspStudentDetailsList()));
    }

    public void clearStudentDetail() {
        hrTdIspStudentDetails = null;
        allAddressUnitAsOne = null;
        hrAddresses = new HrAddresses();

    }

    public void btnRowSelector() {
        selectedRowIndexStudent = getHrTdIspStudentDetailsDataModel().getRowIndex();
        displayStudent();
    }

    private void displayStudent() {
        clearStudentDetail();
        if (selectedRowIndexStudent > -1) {
            hrTdIspStudentDetails = getHrTdIspStudentDetailsDataModel().getRowData();
        }
    }

    public void findInternshipStudentDetails() {
        if (hrTdIspStudents.getYear() != null && !hrTdIspStudents.getSemister().toString().isEmpty()) {
            hrInternshipStudentsListDataModel = new ListDataModel<>(new ArrayList(hrInternshipStudentsBeanLocal.findByYearSemister(hrTdIspStudents)));
        }
    }

    public void vcl_Year(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int year = Integer.valueOf(event.getNewValue().toString());
            hrTdIspStudents.setYear(year);
        }
    }

    public void vclUniversity(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrTdIspStudents.setUniversityId(hrTdUniversities);
        }
    }
    //</editor-fold>
}
