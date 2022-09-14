/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrTdPreserviceTraineesBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import et.gov.eep.hrms.entity.training.HrTdPsvcTrainees;
import et.gov.eep.hrms.integration.HrAddressesBeanIntegrationLocal;

/**
 *
 * @author insa
 */
@Named(value = "hrTdPreserviceTraineesController")
@ViewScoped
public class HrTdPreserviceTraineesController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @Inject
    HrTdPsvcTrainees hrTdPsvcTrainees;

    @Inject
    HrTdPsvcTraineeDetails hrTdPsvcTraineeDetails;

    @Inject
    HrAddresses hrAddresses;

    @Inject
    HrLuEducLevels hrLuEducLevels;

    @Inject
    HrLuEducTypes hrLuEducTypes;

    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrTdPreserviceTraineesBeanLocal hrTdPreserviceTraineesBeanLocal;

    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;

    @EJB
    HrAddressesBeanIntegrationLocal hrAddressesBeanIntegrationLocal;

    private String[] selectedDoumnet;
    private StreamedContent imgfile;
    private TreeNode addressroot;
    private TreeNode selectedAddressNode;
    private String allAddressUnitAsOne;
    private String tabToggle = "";
    int update = 0;
    private int addressId = 0;
    private boolean renderPnlCreateAdditional = true;
    private String addorUpdate = "Add";
    boolean btnaddvisibility = true;
    private String headerTitle = "Search....";
    private String saveorUpdateBundle = "save";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";
    private boolean disabled;
    private byte[] byteData;
    private String filename;
    private int updateStatus = 0;
    int selectedRowIndexStudent = -1;
    private List<SelectItem> budgetYears;
    private List<HrLuEducLevels> educationlevelList = new ArrayList<>();
    private List<HrLuEducTypes> educationQualityList = new ArrayList<>();
    private static List<HrAddresses> allAddressData;
    private static List<HrAddresses> addresses;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    List<HrTdPsvcTrainees> hrTdyearList;
    DataModel<HrTdPsvcTraineeDetails> courseTraineeModel;
    DataModel<HrTdPsvcTrainees> courseMasterDataModel1;
    String preparedDate;
    boolean renderForGraphics = true;
    boolean renderForGraphicsDb = false;
    //</editor-fold>

    @PostConstruct
    public void Init() {
        loadTree();
        setBudgetYears(readBudgetYears());
        educationlevelList = hrTdPreserviceTraineesBeanLocal.findEducationLevel();
        educationQualityList = hrTdPreserviceTraineesBeanLocal.findByEducationQuality();
        hrTdyearList = hrTdPreserviceTraineesBeanLocal.findall();
        String Shday = StringDateManipulation.toDayInEc();
        hrTdPsvcTrainees.setPreparedOn(Shday);
    }
    //<editor-fold defaultstate="collapsed" desc="Getters setters">

    public boolean isRenderForGraphics() {
        return renderForGraphics;
    }

    public void setRenderForGraphics(boolean renderForGraphics) {
        this.renderForGraphics = renderForGraphics;
    }

    public boolean isRenderForGraphicsDb() {
        return renderForGraphicsDb;
    }

    public void setRenderForGraphicsDb(boolean renderForGraphicsDb) {
        this.renderForGraphicsDb = renderForGraphicsDb;
    }

    public String[] getSelectedDoumnet() {
        return selectedDoumnet;
    }

    public void setSelectedDoumnet(String[] selectedDoumnet) {
        this.selectedDoumnet = selectedDoumnet;
    }

    public List<HrLuEducLevels> getEducationlevelList() {
        return educationlevelList;
    }

    public void setEducationlevelList(List<HrLuEducLevels> educationlevelList) {
        this.educationlevelList = educationlevelList;
    }

    public static List<HrAddresses> getAllAddressData() {
        return allAddressData;
    }

    public static void setAllAddressData(List<HrAddresses> allAddressData) {
        HrTdPreserviceTraineesController.allAddressData = allAddressData;
    }

    public List<HrTdPsvcTrainees> getHrTdyearList() {
        return hrTdyearList;
    }

    public void setHrTdyearList(List<HrTdPsvcTrainees> hrTdyearList) {
        this.hrTdyearList = hrTdyearList;
    }

    public static List<HrAddresses> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<HrAddresses> addresses) {
        HrTdPreserviceTraineesController.addresses = addresses;
    }

    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }

    public TreeNode getAddressroot() {
        return addressroot;
    }

    public void setAddressroot(TreeNode Addressroot) {
        this.addressroot = Addressroot;
    }

    public TreeNode getSelectedAddressNode() {
        return selectedAddressNode;
    }

    public void setSelectedAddressNode(TreeNode selectedAddressNode) {
        this.selectedAddressNode = selectedAddressNode;
    }

    public byte[] getByteData() {
        return byteData;
    }

    public void setByteData(byte[] byteData) {
        this.byteData = byteData;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getSelectedRowIndexStudent() {
        return selectedRowIndexStudent;
    }

    public void setSelectedRowIndexStudent(int selectedRowIndexStudent) {
        this.selectedRowIndexStudent = selectedRowIndexStudent;
    }

    public List<HrLuEducLevels> getEduquationlevelList() {
        return educationlevelList;
    }

    public void setEduquationlevelList(List<HrLuEducLevels> eduquationlevelList) {
        this.educationlevelList = eduquationlevelList;
    }

    public List<HrLuEducTypes> getEducationQualityList() {
        return educationQualityList;
    }

    public void setEducationQualityList(List<HrLuEducTypes> educationQualityList) {
        this.educationQualityList = educationQualityList;
    }

    public List<SelectItem> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<SelectItem> budgetYears) {
        this.budgetYears = budgetYears;
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

    public HrLuEducLevels getHrLuEducLevels() {
        if (hrLuEducLevels == null) {
            hrLuEducLevels = new HrLuEducLevels();
        }
        return hrLuEducLevels;
    }

    public void setHrLuEducLevels(HrLuEducLevels hrLuEducLevels) {
        this.hrLuEducLevels = hrLuEducLevels;
    }

    public HrLuEducTypes getHrLuEducTypes() {
        if (hrLuEducLevels == null) {
            hrLuEducLevels = new HrLuEducLevels();
        }
        return hrLuEducTypes;
    }

    public void setHrLuEducTypes(HrLuEducTypes hrLuEducTypes) {
        this.hrLuEducTypes = hrLuEducTypes;
    }

    public HrTdPsvcTrainees getHrTdPsvcTrainees() {
        if (hrTdPsvcTrainees == null) {
            hrTdPsvcTrainees = new HrTdPsvcTrainees();
        }
        return hrTdPsvcTrainees;
    }

    public void setHrTdPsvcTrainees(HrTdPsvcTrainees hrTdPsvcTrainees) {
        this.hrTdPsvcTrainees = hrTdPsvcTrainees;
    }

    public HrTdPsvcTraineeDetails getHrTdPsvcTraineeDetails() {
        if (hrTdPsvcTraineeDetails == null) {
            hrTdPsvcTraineeDetails = new HrTdPsvcTraineeDetails();
        }
        return hrTdPsvcTraineeDetails;
    }

    public void setHrTdPsvcTraineeDetails(HrTdPsvcTraineeDetails hrTdPsvcTraineeDetails) {
        this.hrTdPsvcTraineeDetails = hrTdPsvcTraineeDetails;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getTabToggle() {
        return tabToggle;
    }

    public void setTabToggle(String tabToggle) {
        this.tabToggle = tabToggle;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public DataModel<HrTdPsvcTraineeDetails> getCourseTraineeModel() {
        return courseTraineeModel;
    }

    public void setCourseTraineeModel(DataModel<HrTdPsvcTraineeDetails> courseTraineeModel) {
        this.courseTraineeModel = courseTraineeModel;
    }

    public DataModel<HrTdPsvcTrainees> getCourseMasterDataModel1() {
        if (courseMasterDataModel1 == null) {
            courseMasterDataModel1 = new ArrayDataModel<>();
        }
        return courseMasterDataModel1;
    }

    public void setCourseMasterDataModel1(DataModel<HrTdPsvcTrainees> courseMasterDataModel1) {
        this.courseMasterDataModel1 = courseMasterDataModel1;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public StreamedContent getImgfile() {
        return imgfile;
    }

    public void setImgfile(StreamedContent imgfile) {
        this.imgfile = imgfile;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Search">
    public void newOrSearchPage() {
        switch (createOrSearchBundle) {
            case "New":
                renderPnlCreateGatePass = true;
                renderPnlManPage = false;
                btnNewRender = false;
                headerTitle = "Electro Mecanics Courses";
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
        resetPreserviceTrainees();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
    }

    public void populate(SelectEvent event) {
        hrTdPsvcTrainees = (HrTdPsvcTrainees) event.getObject();
        recreateDataModel();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = true;
        updateStatus = 1;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }

    public ArrayList<HrTdPsvcTrainees> findByYear(ValueChangeEvent event) {
        hrTdPsvcTrainees.setYearOfTraining(Integer.parseInt(event.getNewValue().toString()));
        courseMasterDataModel1 = new ListDataModel(new ArrayList<>(hrTdPreserviceTraineesBeanLocal.findByyear(hrTdPsvcTrainees)));
        return null;
    }

    public void recreateDataModel1() {
        courseMasterDataModel1 = null;
        courseMasterDataModel1 = new ListDataModel(new ArrayList(hrTdyearList));
    }

    public SelectItem[] getHandleDocument() {
        SelectItem[] items = new SelectItem[5];
        items[0] = new SelectItem("Education Qualification Certificate");
        items[1] = new SelectItem("Certificate Of Conformity(COC)");
        items[2] = new SelectItem("Medical Certificate");
        items[3] = new SelectItem("Police Clearance Certificate");
        items[4] = new SelectItem("Contrat Certificate");
        return items;
    }

    public void loadTree() {
        allAddressData = hrAddressesBeanIntegrationLocal.findAllAddressUnit();
        addressroot = new DefaultTreeNode("Addressroot", null);
        populateNodes(allAddressData, 0, addressroot);
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

    public void onNodeSelectA(NodeSelectEvent event) {
        selectedAddressNode = event.getTreeNode();
        addressId = Integer.parseInt((selectedAddressNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllAddressUnitAsOne(hrAddresses.getAddressName());
        hrTdPsvcTraineeDetails.setBirthPlaceAddressId(hrAddresses);
        RequestContext context2 = RequestContext.getCurrentInstance();
        context2.execute("PF('dlgAddress').hide();");
    }

    public void addPreserviceTrainees() {
        try {
            String documnet = "";
            int str = selectedDoumnet.length;
            for (int i = 0; i < str; i++) {
                if (i == 0) {
                    documnet = selectedDoumnet[i];
                } else {
                    documnet = documnet + ", " + selectedDoumnet[i];
                }
            }
            hrTdPsvcTraineeDetails.setAchivement(documnet);
            hrTdPsvcTraineeDetails.setEducLevelId(hrLuEducLevels);
            hrTdPsvcTraineeDetails.setEducQualId(hrLuEducTypes);
            hrTdPsvcTrainees.add(hrTdPsvcTraineeDetails);
            if (updateStatus == 0) {
                if (hrTdPsvcTraineeDetails.getPhoto() != null) {
                    renderForGraphics = false;
                    renderForGraphicsDb = true;
                } else {
                    renderForGraphics = true;
                    renderForGraphicsDb = false;
                }
            } else {
                if (hrTdPsvcTraineeDetails.getPhoto() != null) {
                    renderForGraphics = false;
                    renderForGraphicsDb = true;
                } else {
                    renderForGraphics = true;
                    renderForGraphicsDb = false;
                }
            }
            recreateDataModel();
            clearPreServiceTraineesDetail();
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("error occured");
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void recreateDataModel() {
        courseTraineeModel = null;
        courseTraineeModel = new ListDataModel(new ArrayList(hrTdPsvcTrainees.getHrTdPsvcTraineeDetailsList()));
    }

    public void clearPreServiceTraineesDetail() {
        hrTdPsvcTraineeDetails = null;
        allAddressUnitAsOne = null;
        hrLuEducLevels = null;
        hrLuEducTypes = null;
    }

    public void selectedStudent(SelectEvent event) {
        hrTdPsvcTraineeDetails = (HrTdPsvcTraineeDetails) event.getObject();
        setAllAddressUnitAsOne(hrTdPsvcTraineeDetails.getBirthPlaceAddressId().getAddressName());
    }

    public void savePreserviceTrainee() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "savePreserviceTrainee", dataset)) {
                try {
                    hrTdPsvcTrainees.setPreparedOn(preparedDate);
                    hrTdPsvcTrainees.setPreparedBy(sessionBeanLocal.getUserId());
                    if ((!(courseTraineeModel.getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("Data Table Should be filled");
                    } else {
                        hrTdPreserviceTraineesBeanLocal.saveOrUpdate(hrTdPsvcTrainees);
                        if (updateStatus == 0) {
                            JsfUtil.addSuccessMessage("Saved Sucessfully");
                        } else {
                            JsfUtil.addSuccessMessage("Updated Sucessfully");
                        }
                        resetPreserviceTrainees();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JsfUtil.addFatalMessage("Error occure while save update");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "savePreserviceTrainee");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetPreserviceTrainees() {
        hrTdPsvcTrainees = new HrTdPsvcTrainees();
        hrTdPsvcTraineeDetails = new HrTdPsvcTraineeDetails();
        hrLuEducLevels = new HrLuEducLevels();
        hrLuEducTypes = new HrLuEducTypes();
        courseTraineeModel = new ArrayDataModel<>();
    }

    public void handleFileUpload(FileUploadEvent event) {
        filename = event.getFile().getFileName().split("\\.")[0];
        try {
            byteData = extractByteArray(event.getFile().getInputstream());
            hrTdPsvcTraineeDetails.setPhoto(byteData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] extractByteArray(InputStream inputStream1) {
        try {
            byte[] byteFile = null;
            int len = 0;
            len = inputStream1.available();
            byteFile = new byte[len];
            inputStream1.read(byteFile, 0, len);
            return byteFile;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void selected(SelectEvent event) {
        hrTdPsvcTraineeDetails = (HrTdPsvcTraineeDetails) event.getObject();
        hrTdPsvcTrainees = hrTdPsvcTraineeDetails.getTraineeId();
        hrLuEducLevels = hrTdPsvcTraineeDetails.getEducLevelId();
        hrLuEducTypes = hrTdPsvcTraineeDetails.getEducQualId();
        setAllAddressUnitAsOne(hrTdPsvcTraineeDetails.getBirthPlaceAddressId().getAddressName());
        selectedDoumnet = hrTdPsvcTraineeDetails.getAchivement().split(", ");
        addorUpdate = "Modify";
        if (hrTdPsvcTraineeDetails.getPhoto() != null) {
            renderForGraphics = false;
            renderForGraphicsDb = true;
        } else {
            renderForGraphics = true;
            renderForGraphicsDb = false;
        }
    }

    //</editor-fold>
}
