/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.training.HrInlandAndForeignTrainingBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.entity.training.HrTdIfEduc;
import et.gov.eep.hrms.entity.training.HrTdIfEducSelectedStaffs;
import et.gov.eep.hrms.entity.training.HrTdIfTrng;
import et.gov.eep.hrms.entity.training.HrTdIfTrngSelectedStaffs;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Behailu
 */
@Named(value = "inlandAnfForeignTrainingControllers")
@ViewScoped
public class InlandAnfForeignTrainingController implements Serializable {

    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrTdIfTrng hrTdIfTrng;
    @Inject
    HrTdIfTrngSelectedStaffs hrTdIfTrngSelectedStaffs;
    @Inject
    HrAddresses hrAddresses;
    @Inject
    HrTdCourses hrTdCourses;
    @Inject
    HrTdTrainerProfiles hrTdTrainerProfiles;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrLuEducTypes hrLuEducTypes;
    @Inject
    HrLuEducLevels hrLuEducLevels;
    @Inject
    HrTdIfEduc hrTdIfEduc;
    @Inject
    HrTdIfEducSelectedStaffs hrTdIfEducSelectedStaffs;
     @Inject
   SessionBean SessionBean;
    
    @EJB
    HrInlandAndForeignTrainingBeanLocal InlandAndForeignTrainingBeanLocal;

    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private String addorUpdate = "Add";
    private String SaveOrUpdate = "Save";
    private Integer status = 0;
    private boolean chooseCreatePage = false;
    private boolean chooseMainPage = true;
    private boolean openDealog = false;
    private boolean empTrainingHistory=false;
    private String renderEmployee = "true";
    private String renderPosition = "false";
    private int addressId = 0;
    private String addressType = "Country";
    private String selected;
    private String selectedid;
    private TreeNode root;
    private TreeNode selectedNode;

    private static List<HrAddresses> allAddressData = new ArrayList<>();
    private static List<HrAddresses> addresses;
    private List<HrTdCourses> TrainingList;
    private List<HrTdTrainerProfiles> InstitutionList;
    private List<HrLuEducLevels> EduLevelList;
    private List<HrLuEducTypes> EduTypeelList;
    List<HrTdIfTrng> TrainingLists = new ArrayList<>();
    List<HrTdIfEduc> EduLists = new ArrayList<>();
    List<HrTdIfTrng> EmpTrainingList = new ArrayList<>();
    List<HrTdIfEduc> EmpEducList = new ArrayList<>();
    DataModel<HrTdIfTrngSelectedStaffs> SelectedStafftDataModel;
    DataModel<HrTdIfTrng> InlandForeignTrainingDataModel;
    DataModel<HrTdIfEducSelectedStaffs> EduSelectedStafftDataModel;
    DataModel<HrTdIfEduc> InlandForeignEduDataModel;
    Set<String> check = new HashSet<>();
    String preparedDate;

    @PostConstruct
    public void init() {
        TrainingList = InlandAndForeignTrainingBeanLocal.findallTrainings();
        InstitutionList = InlandAndForeignTrainingBeanLocal.findallInstitutions();
        TrainingLists = InlandAndForeignTrainingBeanLocal.findallInlandAndForeignTrainings();
        EduLists = InlandAndForeignTrainingBeanLocal.finallInlandAndFoereignEdu();
        EduLevelList = InlandAndForeignTrainingBeanLocal.findallEduLevels();
        EduTypeelList = InlandAndForeignTrainingBeanLocal.findallEduQualififcationList();
        InlandForeignTrainingDataModel = new ListDataModel(TrainingLists);
        InlandForeignEduDataModel = new ListDataModel(EduLists);
        setFullName(SessionBean.getUserName());
        loadTree();
        setPreparedDate(StringDateManipulation.toDayInEc());

    }

    //<editor-fold defaultstate="collapsed" desc="authentication code (it should be removed)">
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter&Setter">
    public List<HrTdIfEduc> getEduLists() {
        return EduLists;
    }

    public void setEduLists(List<HrTdIfEduc> EduLists) {
        this.EduLists = EduLists;
    }

    public DataModel<HrTdIfEducSelectedStaffs> getEduSelectedStafftDataModel() {
        return EduSelectedStafftDataModel;
    }

    public boolean isEmpTrainingHistory() {
        return empTrainingHistory;
    }

    public void setEmpTrainingHistory(boolean empTrainingHistory) {
        this.empTrainingHistory = empTrainingHistory;
    }

    public List<HrTdIfTrng> getEmpTrainingList() {
        return EmpTrainingList;
    }

    public void setEmpTrainingList(List<HrTdIfTrng> EmpTrainingList) {
        this.EmpTrainingList = EmpTrainingList;
    }
    
    public List<HrTdIfEduc> getEmpEducList() {
        return EmpEducList;
    }

    public void setEmpEducList(List<HrTdIfEduc> EmpEducList) {
        this.EmpEducList = EmpEducList;
    }

    
    
    public void setEduSelectedStafftDataModel(DataModel<HrTdIfEducSelectedStaffs> EduSelectedStafftDataModel) {
        this.EduSelectedStafftDataModel = EduSelectedStafftDataModel;
    }

    public DataModel<HrTdIfEduc> getInlandForeignEduDataModel() {
        return InlandForeignEduDataModel;
    }

    public void setInlandForeignEduDataModel(DataModel<HrTdIfEduc> InlandForeignEduDataModel) {
        this.InlandForeignEduDataModel = InlandForeignEduDataModel;
    }

    public HrTdIfEduc getHrTdIfEduc() {
        return hrTdIfEduc;
    }

    public void setHrTdIfEduc(HrTdIfEduc hrTdIfEduc) {
        this.hrTdIfEduc = hrTdIfEduc;
    }

    public HrTdIfEducSelectedStaffs getHrTdIfEducSelectedStaffs() {
        return hrTdIfEducSelectedStaffs;
    }

    public void setHrTdIfEducSelectedStaffs(HrTdIfEducSelectedStaffs hrTdIfEducSelectedStaffs) {
        this.hrTdIfEducSelectedStaffs = hrTdIfEducSelectedStaffs;
    }

    public List<HrLuEducLevels> getEduLevelList() {
        return EduLevelList;
    }

    public void setEduLevelList(List<HrLuEducLevels> EduLevelList) {
        this.EduLevelList = EduLevelList;
    }

    public List<HrLuEducTypes> getEduTypeelList() {
        return EduTypeelList;
    }

    public void setEduTypeelList(List<HrLuEducTypes> EduTypeelList) {
        this.EduTypeelList = EduTypeelList;
    }

    public HrLuEducTypes getHrLuEducTypes() {
        return hrLuEducTypes;
    }

    public void setHrLuEducTypes(HrLuEducTypes hrLuEducTypes) {
        this.hrLuEducTypes = hrLuEducTypes;
    }

    public HrLuEducLevels getHrLuEducLevels() {
        return hrLuEducLevels;
    }

    public void setHrLuEducLevels(HrLuEducLevels hrLuEducLevels) {
        this.hrLuEducLevels = hrLuEducLevels;
    }

    public boolean isOpenDealog() {
        return openDealog;
    }

    public void setOpenDealog(boolean openDealog) {
        this.openDealog = openDealog;
    }

    public int getFilterstatus() {
        return filterstatus;
    }

    public void setFilterstatus(int filterstatus) {
        this.filterstatus = filterstatus;
    }

    public List<HrTdIfTrng> getTrainingLists() {
        return TrainingLists;
    }

    public void setTrainingLists(List<HrTdIfTrng> TrainingLists) {
        this.TrainingLists = TrainingLists;
    }

    public DataModel<HrTdIfTrng> getInlandForeignTrainingDataModel() {
        return InlandForeignTrainingDataModel;
    }

    public void setInlandForeignTrainingDataModel(DataModel<HrTdIfTrng> InlandForeignTrainingDataModel) {
        this.InlandForeignTrainingDataModel = InlandForeignTrainingDataModel;
    }

    public String getSaveOrUpdate() {
        return SaveOrUpdate;
    }

    public void setSaveOrUpdate(String SaveOrUpdate) {
        this.SaveOrUpdate = SaveOrUpdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DataModel<HrTdIfTrngSelectedStaffs> getSelectedStafftDataModel() {
        return SelectedStafftDataModel;
    }

    public void setSelectedStafftDataModel(DataModel<HrTdIfTrngSelectedStaffs> SelectedStafftDataModel) {
        this.SelectedStafftDataModel = SelectedStafftDataModel;
    }

    public Set<String> getCheck() {
        return check;
    }

    public void setCheck(Set<String> check) {
        this.check = check;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
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

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isChooseCreatePage() {
        return chooseCreatePage;
    }

    public void setChooseCreatePage(boolean chooseCreatePage) {
        this.chooseCreatePage = chooseCreatePage;
    }

    public boolean isChooseMainPage() {
        return chooseMainPage;
    }

    public void setChooseMainPage(boolean chooseMainPage) {
        this.chooseMainPage = chooseMainPage;
    }

    public String getRenderEmployee() {
        return renderEmployee;
    }

    public void setRenderEmployee(String renderEmployee) {
        this.renderEmployee = renderEmployee;
    }

    public String getRenderPosition() {
        return renderPosition;
    }

    public void setRenderPosition(String renderPosition) {
        this.renderPosition = renderPosition;
    }

    public HrEmployees getHrEmployees() {
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public HrTdIfTrng getHrTdIfTrng() {
        return hrTdIfTrng;
    }

    public void setHrTdIfTrng(HrTdIfTrng hrTdIfTrng) {
        this.hrTdIfTrng = hrTdIfTrng;
    }

    public HrTdIfTrngSelectedStaffs getHrTdIfTrngSelectedStaffs() {
        return hrTdIfTrngSelectedStaffs;
    }

    public void setHrTdIfTrngSelectedStaffs(HrTdIfTrngSelectedStaffs hrTdIfTrngSelectedStaffs) {
        this.hrTdIfTrngSelectedStaffs = hrTdIfTrngSelectedStaffs;
    }

    public HrAddresses getHrAddresses() {
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
    }

    public HrTdCourses getHrTdCourses() {
        return hrTdCourses;
    }

    public void setHrTdCourses(HrTdCourses hrTdCourses) {
        this.hrTdCourses = hrTdCourses;
    }

    public HrTdTrainerProfiles getHrTdTrainerProfiles() {
        return hrTdTrainerProfiles;
    }

    public void setHrTdTrainerProfiles(HrTdTrainerProfiles hrTdTrainerProfiles) {
        this.hrTdTrainerProfiles = hrTdTrainerProfiles;
    }

    public HrJobTypes getHrJobTypes() {
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrInlandAndForeignTrainingBeanLocal getInlandAndForeignTrainingBeanLocal() {
        return InlandAndForeignTrainingBeanLocal;
    }

    public void setInlandAndForeignTrainingBeanLocal(HrInlandAndForeignTrainingBeanLocal InlandAndForeignTrainingBeanLocal) {
        this.InlandAndForeignTrainingBeanLocal = InlandAndForeignTrainingBeanLocal;

    }

    public List<HrTdCourses> getTrainingList() {
        return TrainingList;
    }

    public void setTrainingList(List<HrTdCourses> TrainingList) {
        this.TrainingList = TrainingList;
    }

    public List<HrTdTrainerProfiles> getInstitutionList() {
        return InstitutionList;
    }

    public void setInstitutionList(List<HrTdTrainerProfiles> InstitutionList) {
        this.InstitutionList = InstitutionList;
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
        InlandAnfForeignTrainingController.allAddressData = allAddressData;
    }

    public static List<HrAddresses> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<HrAddresses> addresses) {
        InlandAnfForeignTrainingController.addresses = addresses;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="main">
    public void InlandForeignTriainingInfo() {
        disableBtnCreate = false;
        switch (createOrSearchBundle) {
            case "New":
                hrTdIfTrng.setLocation(1);
                hrTdIfEduc.setLocation(1);
                chooseCreatePage = true;
                chooseMainPage = false;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                hrTdIfEduc = new HrTdIfEduc();
                hrTdIfTrng = new HrTdIfTrng();

                chooseCreatePage = false;
                chooseMainPage = true;
                createOrSearchBundle = "New";
               setIcone("ui-icon-plus");
                break;
        }
    }

    public ArrayList<HrEmployees> SearchByStafName(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(hrEmployee);
        employees = InlandAndForeignTrainingBeanLocal.SearchByFname(hrEmployees);

        return employees;
    }

    public void getByTrianingName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = InlandAndForeignTrainingBeanLocal.getByFirstName(hrEmployees);
            hrEmployees.setId(hrEmployees.getId());
            hrTdIfTrng.setPreparedBy(hrEmployees);
            hrTdIfTrngSelectedStaffs.setEmpId(hrEmployees);
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
            EmpTrainingList= InlandAndForeignTrainingBeanLocal.findtrainingByEmpId(hrEmployees.getId());
            empTrainingHistory=true;
            System.out.println("History===="+EmpTrainingList);
                System.out.println("Historyonl===="+empTrainingHistory);

        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void getByEduName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = InlandAndForeignTrainingBeanLocal.getByFirstName(hrEmployees);
            hrEmployees.setId(hrEmployees.getId());
            hrTdIfTrng.setPreparedBy(hrEmployees);
            hrTdIfEducSelectedStaffs.setEmpId(hrEmployees);
            hrDepartments = hrEmployees.getDeptId();
            hrJobTypes = hrEmployees.getJobId();
            EmpEducList= InlandAndForeignTrainingBeanLocal.findEducByEmpId(hrEmployees.getId());
            empTrainingHistory=true;
            System.out.println("History===="+EmpEducList);
        } catch (Exception ex) {
            System.err.println("Exception");
        }
    }

    public void loadTree() {
        allAddressData = InlandAndForeignTrainingBeanLocal.findAllAddress();
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
        hrAddresses = InlandAndForeignTrainingBeanLocal.findAllAddressUnitAsOne(hrAddresses);
    }

    public void add() {
        if (hrTdIfTrngSelectedStaffs.getTotalResult() > 100 || hrTdIfTrngSelectedStaffs.getTotalResult() < 0) {
            JsfUtil.addFatalMessage("Result must be b/n 0 & 100");
        } else if (check.contains(hrTdIfTrngSelectedStaffs.getEmpId().getEmpId())) {
            JsfUtil.addFatalMessage("Staff Alrady exist");
        } else {
            hrTdIfTrngSelectedStaffs.setEmpId(hrEmployees);
            hrTdIfTrngSelectedStaffs.setStatus(hrTdIfTrngSelectedStaffs.getStatus());
            hrTdIfTrng.add(hrTdIfTrngSelectedStaffs);
            check.add(hrEmployees.getEmpId());
            recreateDataModel();
            clearselectedStaff();
            EmpTrainingList= new ArrayList<>();
            empTrainingHistory=false;
            JsfUtil.addSuccessMessage("Successfully Added");
        }
    }

    public void addstud() {
        hrTdIfEducSelectedStaffs.setEmpId(hrEmployees);
        if (hrTdIfEducSelectedStaffs.getTotalResult() > 100 || hrTdIfEducSelectedStaffs.getTotalResult() < 0) {
            JsfUtil.addFatalMessage("Result must be b/n 0 & 100");
           
        } else if (check.contains(hrEmployees.getEmpId())) {
            JsfUtil.addFatalMessage("Staff Alrady exist");
        } else {
            hrTdIfEducSelectedStaffs.setEmpId(hrEmployees);
            hrTdIfEducSelectedStaffs.setStatus(hrTdIfEducSelectedStaffs.getStatus());
            hrTdIfEduc.add(hrTdIfEducSelectedStaffs);
            check.add(hrEmployees.getEmpId());
            recreateEduDataModel();
            clearEduselectedStaff();
            EmpEducList= new ArrayList<>();
            JsfUtil.addSuccessMessage("Successfully Added");
        }

    }

    public void clearselectedStaff() {
        hrDepartments = new HrDepartments();
        hrEmployees = new HrEmployees();
        hrJobTypes = new HrJobTypes();
        hrTdIfTrngSelectedStaffs = new HrTdIfTrngSelectedStaffs();

    }

    public void clearEduselectedStaff() {
        hrDepartments = new HrDepartments();
        hrEmployees = new HrEmployees();
        hrJobTypes = new HrJobTypes();
        hrTdIfEducSelectedStaffs = new HrTdIfEducSelectedStaffs();
    }

    public void recreateDataModel() {

        SelectedStafftDataModel = null;
        SelectedStafftDataModel = new ListDataModel(new ArrayList(hrTdIfTrng.getHrTdIfTrngSelectedStaffsList()));
    }

    public void recreateEduDataModel() {

        EduSelectedStafftDataModel = null;
        EduSelectedStafftDataModel = new ListDataModel(new ArrayList(hrTdIfEduc.getHrTdIfEducSelectedStaffsList()));
    }

    public void saveInlandForeignTraining() {
         try {
//            AAA securityService = new AAA();
//            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//            String systemBundle = "et/gov/eep/commonApplications/securityServer";
//            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
//            if (security.checkAccess(SessionBean.getUserName(), "saveInlandForeignTraining", dataset)) {
                
            String SStmonth[] = hrTdIfTrng.getProgramStartDate().split("/");
            int ISstmonth = Integer.parseInt(SStmonth[1]);
            String SSstyear[] = hrTdIfTrng.getProgramStartDate().split("/");
            int ISyear = Integer.parseInt(SSstyear[2]);
            String SSdate[] = hrTdIfTrng.getProgramStartDate().split("/");
            int ISstdate = Integer.parseInt(SSdate[0]);

            String SEday[] = hrTdIfTrng.getProgramEndDate().split("/");
            int IEday = Integer.parseInt(SEday[0]);
            String SEMonth[] = hrTdIfTrng.getProgramEndDate().split("/");
            int IEMonth = Integer.parseInt(SEMonth[1]);
            String SEYear[] = hrTdIfTrng.getProgramEndDate().split("/");
            int IEYear = Integer.parseInt(SEYear[2]);
            int Start_end_DateGap = ((IEday - ISstdate) + ((IEMonth - ISstmonth) * 30) + ((IEYear - ISyear) * 365));
            if (Start_end_DateGap < 0) {
                JsfUtil.addFatalMessage("Ende date can't Exceed Start date");
            } else if (status == 0) {
                hrTdIfTrng.setTrainingId(hrTdCourses);
                hrTdIfTrng.setCountryId(hrAddresses);
                hrTdIfTrng.setInstitutionId(hrTdTrainerProfiles);
                InlandAndForeignTrainingBeanLocal.save(hrTdIfTrng);
                JsfUtil.addSuccessMessage("Data Saved SuccessFully");
                clear();
            } else {
                HrTdTrainerProfiles tp = new HrTdTrainerProfiles();
                tp.setId(hrTdIfTrng.getInstitutionId().getId());
                hrTdIfTrng.setInstitutionId(tp);

                HrTdCourses co = new HrTdCourses();
                co.setId(hrTdIfTrng.getTrainingId().getId());
                hrTdIfTrng.setTrainingId(co);

                HrAddresses add = new HrAddresses();
                add.setAddressId(hrTdIfTrng.getCountryId().getAddressId());
                hrTdIfTrng.setCountryId(add);
                hrTdIfTrng.setProgramSponsor(hrTdIfTrng.getProgramSponsor());

                hrTdIfTrngSelectedStaffs.setEmpId(hrEmployees);
                InlandAndForeignTrainingBeanLocal.update(hrTdIfTrng);
                JsfUtil.addSuccessMessage("Data Updated SuccessFully");
                clear();
         }
//            } else {
//                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
//                EventEntry eventEntry = new EventEntry();
//                eventEntry.setSessionId(SessionBean.getSessionID());
//                eventEntry.setUserId(SessionBean.getUserId());
//                QName qualifiedName = new QName("", "project");
//                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
//                eventEntry.setUserLogin(test);
//                
//                 JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
//                eventEntry.setUserLogin(userName);
//                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
//                eventEntry.setModule(module);
//                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveInlandForeignTraining");
//                eventEntry.setProgram(program);
////..... add more information by calling fields defined in the object 
//                security.addEventLog(eventEntry, dataset);
//
//            }
         }
           catch (Exception ex) {
            JsfUtil.addFatalMessage("Unable to Save data");
        }}
         
    

    public void saveInlandForeignEducation() {
        try {
// AAA securityService = new AAA();
//            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//            String systemBundle = "et/gov/eep/commonApplications/securityServer";
//            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
//            if (security.checkAccess(SessionBean.getUserName(), "saveInlandForeignEducation", dataset)) {
                String SStmonth[] = hrTdIfEduc.getProgramStartDate().split("/");
                int ISstmonth = Integer.parseInt(SStmonth[1]);
                String SSstyear[] = hrTdIfEduc.getProgramStartDate().split("/");
                int ISyear = Integer.parseInt(SSstyear[2]);
                String SSdate[] = hrTdIfEduc.getProgramStartDate().split("/");
                int ISstdate = Integer.parseInt(SSdate[0]);

                String SEday[] = hrTdIfEduc.getProgramEndDate().split("/");
                int IEday = Integer.parseInt(SEday[0]);
                String SEMonth[] = hrTdIfEduc.getProgramEndDate().split("/");
                int IEMonth = Integer.parseInt(SEMonth[1]);
                String SEYear[] = hrTdIfEduc.getProgramEndDate().split("/");
                int IEYear = Integer.parseInt(SEYear[2]);
                int Start_end_DateGap = ((IEday - ISstdate) + ((IEMonth - ISstmonth) * 30) + ((IEYear - ISyear) * 365));
                if (Start_end_DateGap < 0) {
                    JsfUtil.addFatalMessage("Ende date can't Exceed Start date");
                } else if (status == 0) {
                    hrTdIfEduc.setEducLevelId(hrLuEducLevels);
                    hrTdIfEduc.setEducQualId(hrLuEducTypes);
                    hrTdIfEduc.setCountryId(hrAddresses);
                    hrTdIfEducSelectedStaffs.setStatus(0);
                    InlandAndForeignTrainingBeanLocal.saveEdu(hrTdIfEduc);
                    JsfUtil.addSuccessMessage("Data Saved SuccessFully");
                    clear();
                } else {
                    HrLuEducLevels el = new HrLuEducLevels();
                    el.setId(hrTdIfEduc.getEducLevelId().getId());
                    hrTdIfEduc.setEducLevelId(el);
                    HrLuEducTypes et = new HrLuEducTypes();
                    et.setId(hrTdIfEduc.getEducQualId().getId());
                    hrTdIfEduc.setEducQualId(et);
                    HrAddresses ad = new HrAddresses();
                    ad.setAddressId(hrTdIfEduc.getCountryId().getAddressId());
                    hrTdIfEduc.setCountryId(ad);
                    InlandAndForeignTrainingBeanLocal.updateEdu(hrTdIfEduc);
                    JsfUtil.addSuccessMessage("Data Updated SuccessFully");
                    clear();
                }
//            } else {
//                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
//                EventEntry eventEntry = new EventEntry();
//                eventEntry.setSessionId(SessionBean.getSessionID());
//                eventEntry.setUserId(SessionBean.getUserId());
//                QName qualifiedName = new QName("", "project");
//                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
//                eventEntry.setUserLogin(test);
//                
//                 JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
//                eventEntry.setUserLogin(userName);
//                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
//                eventEntry.setModule(module);
//                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveInlandForeignEducation");
//                eventEntry.setProgram(program);
////..... add more information by calling fields defined in the object 
//                security.addEventLog(eventEntry, dataset);
//
//            }

            
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Unable to Save data");
        }

    }

    public void clear() {
        hrTdIfTrng = null;
        hrAddresses = null;
        hrDepartments = null;
        hrEmployees = null;
        hrJobTypes = null;
        hrTdCourses = null;
        hrTdTrainerProfiles = null;
        SelectedStafftDataModel = null;
        hrTdIfEduc = null;
        hrTdIfEducSelectedStaffs = null;
        EduSelectedStafftDataModel = null;
        hrLuEducTypes = new HrLuEducTypes();
        hrLuEducLevels = new HrLuEducLevels();
    }
    int filterstatus = 0;

    private void populateTable() {
        try {
            List<HrTdIfTrng> readFilteredTraining = InlandAndForeignTrainingBeanLocal.loadTrainings(filterstatus);
            InlandForeignTrainingDataModel = new ListDataModel(readFilteredTraining);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void filiterByLocation_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            filterstatus = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }

    public void filiterINladnAndForeignEduByLocation_Vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            filterstatus = Integer.valueOf(event.getNewValue().toString());
            populateTableEdu();
        }
    }

    private void populateTableEdu() {
        try {
            List<HrTdIfEduc> readFilteredEdu = InlandAndForeignTrainingBeanLocal.loadEdus(filterstatus);
            InlandForeignEduDataModel = new ListDataModel(readFilteredEdu);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void InlandAndForeignTraining_processMyEvent(SelectEvent event) {
        hrTdIfTrng = (HrTdIfTrng) event.getObject();
        hrTdIfTrng.setId(hrTdIfTrng.getId());
        hrTdIfTrng = InlandAndForeignTrainingBeanLocal.loadInlandAndForeignTrainingDetails(hrTdIfTrng.getId());
        hrTdCourses = hrTdIfTrng.getTrainingId();
        hrAddresses = hrTdIfTrng.getCountryId();
        hrTdTrainerProfiles = hrTdIfTrng.getInstitutionId();

        recreateDataModel();
        chooseCreatePage = true;
        chooseMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        status = 1;
        SaveOrUpdate = "Update";

    }

    public void EditStaffdata() {
        hrTdIfTrngSelectedStaffs = SelectedStafftDataModel.getRowData();
        hrTdIfTrng = hrTdIfTrngSelectedStaffs.getInlandForeignTrngId();
        hrEmployees = hrTdIfTrngSelectedStaffs.getEmpId();
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        hrTdIfTrngSelectedStaffs.getStatus();
        addorUpdate = "Modify";

    }

    public void EditEduStaffdata() {
        hrTdIfEducSelectedStaffs = EduSelectedStafftDataModel.getRowData();
        hrTdIfEduc = hrTdIfEducSelectedStaffs.getInlandForeignEducId();
        hrEmployees = hrTdIfEducSelectedStaffs.getEmpId();
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        hrTdIfEducSelectedStaffs.getStatus();
        addorUpdate = "Modify";

    }

    public void InlandAndForeignEdu_processMyEvent(SelectEvent event) {
        hrTdIfEduc = (HrTdIfEduc) event.getObject();
        hrTdIfEduc.setId(hrTdIfEduc.getId());
        hrTdIfEduc = InlandAndForeignTrainingBeanLocal.loadInlandAndForeignEduDetails(hrTdIfEduc.getId());
        hrLuEducTypes = hrTdIfEduc.getEducQualId();
        hrAddresses = hrTdIfEduc.getCountryId();
        hrLuEducLevels = hrTdIfEduc.getEducLevelId();
        recreateEduDataModel();
        chooseCreatePage = true;
        chooseMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        status = 1;
        SaveOrUpdate = "Update";

    }

//</editor-fold>
}
