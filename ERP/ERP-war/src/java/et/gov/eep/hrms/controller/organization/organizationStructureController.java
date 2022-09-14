/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.organization;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.address.HrAddressesBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepAddressesBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDeptJobsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrJobTypesBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.lookup.HrLuRegions;
import et.gov.eep.hrms.entity.organization.HrDepAddresses;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.mapper.lookup.HrLuRegionsFacade;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Benin
 */
@Named(value = "organizationStructureController")
@ViewScoped
public class organizationStructureController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="variables">
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrDepartments selectedDepartments;
    @Inject
    HrDepAddresses hrDepAddresses;
    @Inject
    HrDepAddresses selectedDepAddresses;
    @Inject
    HrAddresses hrAddresses;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrDeptJobs hrDeptJobs;
    @Inject
    HrDeptJobs selectedDeptJobs;
    @Inject
    HrLuRegions hrLuRegions;
    @Inject
    SessionBean SessionBean;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    HrAddressesBeanLocal hrAddressesBeanLocal;

//    HrAddressesBeanIntegrationLocal hrAddressesBeanIntegrationLocal;
    @EJB
    HrJobTypesBeanLocal hrJobTypesBeanLocal;
    @EJB
    HrDeptJobsBeanLocal hrDeptJobsBeanLocal;
    @EJB
    HrDepAddressesBeanLocal hrDepAddressesBeanLocal;
    @EJB
    HrLuRegionsFacade hrLuRegionsFacade;
    private String icone = "ui-icon-plus";
    private TreeNode roots;
    private TreeNode selectedNodes;
    private String selected;
    private int departmentId = 0;
    private static List<HrDepartments> allDepartments;
    private static List<HrDepartments> department;

    private TreeNode addressRoot;
    private TreeNode addressSelectedNode;
    private static List<HrAddresses> allAddressData;
    private static List<HrAddresses> addresses;
    private int addressId;
    private String allAddressUnitAsOne;

    private String saveOrUpdateButton = "Save";
    private String saveOrUpdateAddress = "Save";
    private String saveOrUpdateJob = "Save";
    private String disableAddressBtn = "false";
    private String disableDebJobBtn = "false";
    int selectedRowIndexDebAddress = 0;
    int selectedRowIndexDebJob = 0;
    int updateStatus = 0;
    int updateStatusAddress = 0;
    private String preTab = "active";
    private String secTab = "disabled";
    private String adrsJobTab = "disabled";
    private String secTabToggle = "";
    private String adrsJobTabToggle = "";
    private int saveUpdateUnit = 0;
    private BigInteger noEmpNeeded;

    ArrayList<String> checkDubJob = new ArrayList<>();
    ArrayList<String> checkDubAddress = new ArrayList<>();

    private List<HrJobTypes> allJobTypes = new ArrayList();
    private List<HrDeptJobs> listHrDeptJobs = new ArrayList();
    private List<HrLuRegions> HrLuRegionsList = new ArrayList();
    DataModel<HrDeptJobs> hrDeptJobsDataModel = new ListDataModel<>();
    DataModel<HrDepAddresses> hrDepAddressesDataModel = new ListDataModel<>();
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getters and setters">
    public String getDisableAddressBtn() {
        return disableAddressBtn;
    }

    public String getIcone() {
        return icone;
    }

    public List<HrLuRegions> getHrLuRegionsList() {
        return HrLuRegionsList;
    }

    public void setHrLuRegionsList(List<HrLuRegions> HrLuRegionsList) {
        this.HrLuRegionsList = HrLuRegionsList;
    }

    public HrLuRegions getHrLuRegions() {
        if (hrLuRegions == null) {
            hrLuRegions = new HrLuRegions();
        }
        return hrLuRegions;
    }

    public void setHrLuRegions(HrLuRegions hrLuRegions) {
        this.hrLuRegions = hrLuRegions;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public void setDisableAddressBtn(String disableAddressBtn) {
        this.disableAddressBtn = disableAddressBtn;
    }

    public String getDisableDebJobBtn() {
        return disableDebJobBtn;
    }

    public void setDisableDebJobBtn(String disableDebJobBtn) {
        this.disableDebJobBtn = disableDebJobBtn;
    }

    public TreeNode getRoots() {
        return roots;
    }

    public void setRoots(TreeNode roots) {
        this.roots = roots;
    }

    public TreeNode getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
    }

    public HrDepartments getSelectedDepartments() {
        if (selectedDepartments == null) {
            selectedDepartments = new HrDepartments();
        }
        return selectedDepartments;
    }

    public void setSelectedDepartments(HrDepartments selectedDepartments) {
        this.selectedDepartments = selectedDepartments;
    }

    public HrDepAddresses getHrDepAddresses() {
        if (hrDepAddresses == null) {
            hrDepAddresses = new HrDepAddresses();
        }
        return hrDepAddresses;
    }

    public void setHrDepAddresses(HrDepAddresses hrDepAddresses) {
        this.hrDepAddresses = hrDepAddresses;
    }

    public HrDeptJobs getSelectedDeptJobs() {
        if (selectedDeptJobs == null) {
            selectedDeptJobs = new HrDeptJobs();
        }
        return selectedDeptJobs;
    }

    public void setSelectedDeptJobs(HrDeptJobs selectedDeptJobs) {
        this.selectedDeptJobs = selectedDeptJobs;
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

    public static List<HrAddresses> getAllAddressData() {
        return allAddressData;
    }

    public static void setAllAddressData(List<HrAddresses> allAddressData) {
        organizationStructureController.allAddressData = allAddressData;
    }

    public static List<HrAddresses> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<HrAddresses> addresses) {
        organizationStructureController.addresses = addresses;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    public HrDepAddresses getSelectedDepAddresses() {
        if (selectedDepAddresses == null) {
            selectedDepAddresses = new HrDepAddresses();
        }
        return selectedDepAddresses;
    }

    public void setSelectedDepAddresses(HrDepAddresses selectedDepAddresses) {
        this.selectedDepAddresses = selectedDepAddresses;
    }

    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public List<HrJobTypes> getAllJobTypes() {
        return allJobTypes;
    }

    public void setAllJobTypes(List<HrJobTypes> allJobTypes) {
        this.allJobTypes = allJobTypes;
    }

    public HrDeptJobs getHrDeptJobs() {
        if (hrDeptJobs == null) {
            hrDeptJobs = new HrDeptJobs();
        }
        return hrDeptJobs;
    }

    public void setHrDeptJobs(HrDeptJobs hrDeptJobs) {
        this.hrDeptJobs = hrDeptJobs;
    }

    public List<HrDeptJobs> getListHrDeptJobs() {
        return listHrDeptJobs;
    }

    public void setListHrDeptJobs(List<HrDeptJobs> listHrDeptJobs) {
        this.listHrDeptJobs = listHrDeptJobs;
    }

    public int getSelectedRowIndexDebJob() {
        return selectedRowIndexDebJob;
    }

    public void setSelectedRowIndexDebJob(int selectedRowIndexDebJob) {
        this.selectedRowIndexDebJob = selectedRowIndexDebJob;
    }

    public int getSelectedRowIndexDebAddress() {
        return selectedRowIndexDebAddress;
    }

    public void setSelectedRowIndexDebAddress(int selectedRowIndexDebAddress) {
        this.selectedRowIndexDebAddress = selectedRowIndexDebAddress;
    }

    public DataModel<HrDeptJobs> getHrDeptJobsDataModel() {
        return hrDeptJobsDataModel;
    }

    public void setHrDeptJobsDataModel(DataModel<HrDeptJobs> hrDeptJobsDataModel) {
        this.hrDeptJobsDataModel = hrDeptJobsDataModel;
    }

    public DataModel<HrDepAddresses> getHrDepAddressesDataModel() {
        return hrDepAddressesDataModel;
    }

    public void setHrDepAddressesDataModel(DataModel<HrDepAddresses> hrDepAddressesDataModel) {
        this.hrDepAddressesDataModel = hrDepAddressesDataModel;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getUpdateStatusAddress() {
        return updateStatusAddress;
    }

    public void setUpdateStatusAddress(int updateStatusAddress) {
        this.updateStatusAddress = updateStatusAddress;
    }

    public static List<HrDepartments> getAllDepartments() {
        return allDepartments;
    }

    public static void setAllDepartments(List<HrDepartments> allDepartments) {
        organizationStructureController.allDepartments = allDepartments;
    }

    public static List<HrDepartments> getDepartment() {
        return department;
    }

    public static void setDepartment(List<HrDepartments> department) {
        organizationStructureController.department = department;
    }

    public String getSaveOrUpdateAddress() {
        return saveOrUpdateAddress;
    }

    public void setSaveOrUpdateAddress(String saveOrUpdateAddress) {
        this.saveOrUpdateAddress = saveOrUpdateAddress;
    }

    public String getSaveOrUpdateJob() {
        return saveOrUpdateJob;
    }

    public void setSaveOrUpdateJob(String saveOrUpdateJob) {
        this.saveOrUpdateJob = saveOrUpdateJob;
    }

    public String getPreTab() {
        return preTab;
    }

    public void setPreTab(String preTab) {
        this.preTab = preTab;
    }

    public String getSecTab() {
        return secTab;
    }

    public void setSecTab(String secTab) {
        this.secTab = secTab;
    }

    public String getAdrsJobTab() {
        return adrsJobTab;
    }

    public void setAdrsJobTab(String adrsJobTab) {
        this.adrsJobTab = adrsJobTab;
    }

    public String getSecTabToggle() {
        return secTabToggle;
    }

    public void setSecTabToggle(String secTabToggle) {
        this.secTabToggle = secTabToggle;
    }

    public String getAdrsJobTabToggle() {
        return adrsJobTabToggle;
    }

    public void setAdrsJobTabToggle(String adrsJobTabToggle) {
        this.adrsJobTabToggle = adrsJobTabToggle;
    }

    public int getSaveUpdateUnit() {
        return saveUpdateUnit;
    }

    public void setSaveUpdateUnit(int saveUpdateUnit) {
        this.saveUpdateUnit = saveUpdateUnit;
    }

    public ArrayList<String> getCheckDubJob() {
        return checkDubJob;
    }

    public void setCheckDubJob(ArrayList<String> checkDubJob) {
        this.checkDubJob = checkDubJob;
    }

    public ArrayList<String> getCheckDubAddress() {
        return checkDubAddress;
    }

    public void setCheckDubAddress(ArrayList<String> checkDubAddress) {
        this.checkDubAddress = checkDubAddress;
    }

    public BigInteger getNoEmpNeeded() {
        return noEmpNeeded;
    }

    public void setNoEmpNeeded(BigInteger noEmpNeeded) {
        this.noEmpNeeded = noEmpNeeded;
    }
//</editor-fold>

    public organizationStructureController() {
    }

    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void init() {
        loadDepartmentTree();
        loadAddressTree("allAddressUnit");
        allJobTypes = hrJobTypesBeanLocal.readAllJobTypes();
        HrLuRegionsList = hrLuRegionsFacade.findAll();
        hrDepartments.setPreparedBy(SessionBean.getUserName());
        hrDepartments.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
        hrDepAddresses.setPreparedBy(SessionBean.getUserName());
        hrDepAddresses.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
        hrDeptJobs.setPreparedBy(SessionBean.getUserName());
        hrDeptJobs.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
        hrDepartments.setType(0);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main">
    //<editor-fold defaultstate="collapsed" desc="tree">
    public void loadDepartmentTree() {
        allDepartments = hrDepartmentsBeanLocal.findAllDepartment();
        roots = new DefaultTreeNode("Root", null);
        populateDepartmentNode(allDepartments, 0, roots);
    }

    public void populateDepartmentNode(List<HrDepartments> departmentData, int id, TreeNode node) {
        department = new ArrayList<>();
        for (HrDepartments k : getAllDepartments()) {
            if (k.getParentId() == null || "".equals(k.getParentId().toString())) {
                k.setParentId(1);
                populateDepartment(k, id, node);
            } else {
                populateDepartment(k, id, node);
            }
        }
    }

    public void populateDepartment(HrDepartments hrDept, int id, TreeNode node) {
        if (hrDept.getParentId() == id) {
            TreeNode childNode = new DefaultTreeNode(hrDept.getDepName() + "=>" + hrDept.getDepId(), node);
//            TreeNode childNode2 = new DefaultTreeNode(hrDept.getDepName(), node);
            department.add(hrDept);
            populateDepartmentNode(department, hrDept.getDepId(), childNode);
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        preTab = "";
        secTab = "";
        adrsJobTab = "";
        secTabToggle = "tab";
        adrsJobTabToggle = "tab";
        saveOrUpdateButton = "Update";
        disableAddressBtn = "false";
        disableDebJobBtn = "false";
        saveUpdateUnit = 0;
        selectedNodes = event.getTreeNode();
        hrDepartments = new HrDepartments();
        departmentId = Integer.parseInt((selectedNodes.getData().toString()).split("=>")[1]);
        hrDepartments.setDepName((selectedNodes.getData().toString()).split("=>")[0]);
        hrDepartments.setDepId(departmentId);
        hrDepartments = hrDepartmentsBeanLocal.findByDeptId(hrDepartments.getDepId());
        hrLuRegions = hrDepartments.getRegionId();
        recreateDeptJob();
        if (hrDepartments.getParentId() == null || "".equals(hrDepartments.getParentId().toString())) {
            selectedDepartments.setDepId(1);
            selectedDepartments = hrDepartmentsBeanLocal.findByDeptId(addressId);
            selectedDepartments = hrDepartmentsBeanLocal.findByDeptParentId(hrDepartments.getDepId());
        } else {
            selectedDepartments = hrDepartmentsBeanLocal.findByDeptParentId(hrDepartments.getParentId());
        }
        recreateDeptAddress();
        hrDepartments.setPreparedBy(SessionBean.getUserName());
        hrDepartments.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
        hrDepAddresses.setPreparedBy(SessionBean.getUserName());
        hrDepAddresses.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
        hrDeptJobs.setPreparedBy(SessionBean.getUserName());
        hrDeptJobs.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
    }
//</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="departement/process">    
    public String dateFormat(String date) {
        String[] dateFromUI;
        String dateInDB;
        if (date != null && date.contains("-")) {
            dateFromUI = date.split("-");
            dateInDB = dateFromUI[2] + "/" + dateFromUI[1] + "/" + dateFromUI[0];
            return dateInDB;
        }
        return null;
    }

    public void vcl_region(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuRegions = (HrLuRegions) event.getNewValue();
            hrDepartments.setRegionId(hrLuRegions);
        } else {
            hrDepartments.setRegionId(null);
        }
    }

    public void newWorkUnit() {
        try {
            preTab = "";
            secTab = "active";
            secTabToggle = "tab";
            adrsJobTab = "disabled";
            adrsJobTabToggle = "";
            disableAddressBtn = "true";
            disableDebJobBtn = "true";
            saveOrUpdateButton = "Save";
            saveOrUpdateAddress = "Save";
            saveOrUpdateJob = "Save";
            allAddressUnitAsOne = "";
            selectedDepartments = hrDepartments;
            hrDepartments = new HrDepartments();
            hrDepAddresses = new HrDepAddresses();
            clearDeptJob();
            hrAddresses = new HrAddresses();
            hrLuRegions = new HrLuRegions();
            saveUpdateUnit = 1;
            hrDeptJobsDataModel = new ListDataModel<>();
            hrDepAddressesDataModel = new ListDataModel<>();
            hrDepartments.setPreparedBy(SessionBean.getUserName());
            hrDepartments.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
            hrDepAddresses.setPreparedBy(SessionBean.getUserName());
            hrDepAddresses.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
            hrDeptJobs.setPreparedBy(SessionBean.getUserName());
            hrDeptJobs.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
            hrDepartments.setType(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveWorkUnit() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveWorkUnit", dataset)) {
                System.out.println("security.checkAccess");
                try {
                    if (selectedNodes == null) {
//                        addMessage("First Select Department.");
                    } else {
                        if (saveUpdateUnit == 1) {
                            hrDepartments.setParentId(selectedDepartments.getDepId());
                            hrDepartments.setDepLevel(BigInteger.ZERO);
                            hrDepartmentsBeanLocal.create(hrDepartments);
                            loadDepartmentTree();
                            preTab = "active";
                            secTab = "disabled";
                            secTabToggle = "";
                            adrsJobTab = "disabled";
                            adrsJobTabToggle = "";
                            disableAddressBtn = "true";
                            disableDebJobBtn = "true";
                            clear();
                            JsfUtil.addSuccessMessage("Successfully Saved.");
                        } else {
                            hrDepartmentsBeanLocal.edit(hrDepartments);
                            loadDepartmentTree();
                            clear();
                            JsfUtil.addSuccessMessage("Successfully Updated.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="staff plan">
    //<editor-fold defaultstate="collapsed" desc="variables">
    boolean reasonTxtRendered = false;
    boolean reasonLblRendered = false;

    public boolean isReasonTxtRendered() {
        return reasonTxtRendered;
    }

    public void setReasonTxtRendered(boolean reasonTxtRendered) {
        this.reasonTxtRendered = reasonTxtRendered;
    }

    public boolean isReasonLblRendered() {
        return reasonLblRendered;
    }

    public void setReasonLblRendered(boolean reasonLblRendered) {
        this.reasonLblRendered = reasonLblRendered;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="bindings">
    List<HrDeptJobs> deptJobs = new ArrayList();

    public List<HrDeptJobs> getDeptJobs() {
        return deptJobs;
    }

    public void setDeptJobs(List<HrDeptJobs> deptJobs) {
        this.deptJobs = deptJobs;
    }
//</editor-fold>

    public void vlcJobTitle(ValueChangeEvent event) {
        if (event.getNewValue() != null && !event.getNewValue().toString().equalsIgnoreCase("")) {
            int jobId = Integer.parseInt(event.getNewValue().toString());
            hrJobTypes = hrJobTypesBeanLocal.searchJobTypesById(jobId);
            hrDeptJobs.setJobId(hrJobTypes);
        } else {
            JsfUtil.addFatalMessage("Please Select Job Title.");
        }
    }

    private void checkDuplicateDepJob() {
        checkDubJob.clear();
        for (HrDeptJobs hrDepJob : deptJobs) {
            checkDubJob.add(hrDepJob.getJobId().getJobTitle());
            checkDubJob.add(hrDepJob.getNoEmpNeeded().toString());
        }
    }

    public void recreateDeptJob() {
        deptJobs = null;
        deptJobs = hrDeptJobsBeanLocal.findDebJobsByDept(hrDepartments.getDepId());
        int joblistSize = hrDeptJobsBeanLocal.findDebJobsByDept(hrDepartments.getDepId()).size();
        if (joblistSize != 0) {
            hrDeptJobsDataModel = null;
            hrDeptJobsDataModel = new ListDataModel(new ArrayList(deptJobs));
            checkDuplicateDepJob();
        }
    }

    public void clearDeptJob() {
        hrDeptJobs = new HrDeptJobs();
        hrJobTypes = new HrJobTypes();
    }

    public void selectedDeptJob(SelectEvent event) {
        updateStatus = 1;
        saveOrUpdateJob = "Update";
        selectedRowIndexDebJob = hrDeptJobsDataModel.getRowIndex();
        hrDeptJobs = (HrDeptJobs) event.getObject();
        selectedDeptJobs = hrDeptJobs;
        allJobTypes = hrJobTypesBeanLocal.findAll();
        hrJobTypes = hrDeptJobs.getJobId();
        hrDeptJobs.setNoEmpNeeded(hrDeptJobs.getNoEmpNeeded());
        reasonLblRendered = true;
        reasonTxtRendered = true;
    }

    public void saveDepJobs() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveDepJobs", dataset)) {
                BigInteger bi = new BigInteger(hrDeptJobs.getNoEmpNeeded().toString());
                Integer noEmp = Integer.parseInt(bi.toString());
                if (noEmp > 0) {
                    if (!((checkDubJob.contains(hrJobTypes.getJobTitle())) && (checkDubJob.contains(hrDeptJobs.getNoEmpNeeded().toString())))) {
                        if (selectedRowIndexDebJob == 0) {
                            hrDeptJobs.setDeptId(hrDepartments);
                            hrDeptJobsBeanLocal.create(hrDeptJobs);
                            JsfUtil.addSuccessMessage("Successfully Saved.");
                        } else {
                            hrDeptJobsBeanLocal.edit(hrDeptJobs);
                            reasonLblRendered = false;
                            reasonTxtRendered = false;
                            saveOrUpdateJob = "Save";
                            JsfUtil.addSuccessMessage("Successfully Updated.");
                        }
                        clearDeptJob();
                    } else {
                        JsfUtil.addFatalMessage("Duplicate Entry.");
                    }
                } else {
                    JsfUtil.addFatalMessage("No of Employee Should be Greater Than Zero.");
                }
                recreateDeptJob();
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="address">
    private boolean isAllCountry = false;
    List<HrDepAddresses> deptAddress = new ArrayList();

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public boolean isIsAllCountry() {
        return isAllCountry;
    }

    public void setIsAllCountry(boolean isAllCountry) {
        this.isAllCountry = isAllCountry;
    }

    public List<HrDepAddresses> getDeptAddress() {
        return deptAddress;
    }

    public void setDeptAddress(List<HrDepAddresses> deptAddress) {
        this.deptAddress = deptAddress;
    }
//</editor-fold>

    public void loadAddressTree(String type) {
        addressRoot = new DefaultTreeNode("Root", null);
        switch (type) {
            case "allAddressUnit":
                allAddressData = hrAddressesBeanLocal.findAllAddressUnit();
                populateAddressNode(allAddressData, 0, addressRoot);
                break;
            case "allAddressUnitAndCountry":
                allAddressData = hrAddressesBeanLocal.findAllAddressUnitAndCountries();
                populateAddressNode(allAddressData, -1, addressRoot);
                break;
        }
    }

    public void populateAddressNode(List<HrAddresses> addressData, int parentId, TreeNode node) {
        addresses = new ArrayList<>();
        for (HrAddresses addressObj : getAllAddressData()) {
            if (addressObj.getParentId() == parentId) {
                TreeNode childNode = new DefaultTreeNode(addressObj.getAddressDescription() + "=>" + addressObj.getAddressId(), node);
                addresses.add(addressObj);
                populateAddressNode(addresses, addressObj.getAddressId(), childNode);
            }
        }
    }

    public void recreateDeptAddress() {
        deptAddress = null;
        deptAddress = hrDepAddressesBeanLocal.findByDeptId(hrDepartments.getDepId());
        if (deptAddress.size() > 0) {
            hrDepAddresses = deptAddress.get(0);
            selectedDepAddresses = hrDepAddresses;
            setAllAddressUnitAsOne(hrDepAddresses.getLocationId().getAddressName());
            updateStatus = 1;
            saveOrUpdateAddress = "Update";
        } else {
            hrDepAddresses = new HrDepAddresses();
            updateStatus = 0;
            saveOrUpdateAddress = "save";
        }
    }

    public void clearDeptAddress() {
        hrAddresses = new HrAddresses();
        hrDepAddresses = new HrDepAddresses();
        setAllAddressUnitAsOne("");
    }

    public void onAddressNodeSelect(NodeSelectEvent event) {
        System.out.println(" in side method");
        addressSelectedNode = event.getTreeNode();
        addressId = Integer.parseInt((addressSelectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        System.out.println("==============" + hrAddresses.getAddressId());
        hrAddresses = hrAddressesBeanLocal.findAllAddressUnitAsOne(hrAddresses);
        System.out.println("sssssssssss" + hrAddresses);
        setAllAddressUnitAsOne(hrAddresses.getAddressName());
        hrDepAddresses.setLocationId(hrAddresses);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgDepAddress').hide();");
    }

    public void checkSaveDeptAddress() {
        try {
            checkDubAddress.clear();
            for (HrDepAddresses depAdd : deptAddress) {
                checkDubAddress.add(depAdd.getLocationId().getAddressName());
            }
            if (updateStatus == 0) {
                hrDepAddresses.setDepId(hrDepartments);
                hrDepAddressesBeanLocal.create(hrDepAddresses);
                clearDeptAddress();
                JsfUtil.addSuccessMessage("Successfully Saved.");
                saveOrUpdateAddress = "Save";
            } else {
                hrDepAddressesBeanLocal.edit(hrDepAddresses);
                clearDeptAddress();
                JsfUtil.addSuccessMessage("Successfully Updated.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDeptAddress() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveDepAddress", dataset)) {
                if (!checkDubAddress.contains(hrDepAddresses.getLocationId().getAddressName())) {
                    if (selectedRowIndexDebAddress == 0) {
                        hrDepAddresses.setDepId(hrDepartments);
                        hrDepAddressesBeanLocal.create(hrDepAddresses);
                        JsfUtil.addSuccessMessage("Successfully Saved");
                        saveOrUpdateAddress = "Save";
                    } else {
                        hrDepAddressesBeanLocal.edit(hrDepAddresses);
                        JsfUtil.addSuccessMessage("Successfully Updated");
                        saveOrUpdateAddress = "Save";
                    }
                    clearDeptAddress();
                } else {
                    JsfUtil.addFatalMessage(hrDepAddresses.getLocationId().getAddressName() + "Duplicate Entry.");
                }
                recreateDeptAddress();
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void ckbAllCountry(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                isAllCountry = true;
                loadAddressTree("allAddressUnitAndCountry");
            } else {
                isAllCountry = false;
                loadAddressTree("allAddressUnit");
            }
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="delete department">
    public void deleteDepartmentInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "deleteDepartmentInfo", dataset)) {
                int deleteResult = hrDepartmentsBeanLocal.deleteDepartmentInfo(hrDepartments);
                if (deleteResult == 1) {
                    JsfUtil.addSuccessMessage("Department Information Successfully Deleted.");
                    clear();
                    loadDepartmentTree();
                } else if (deleteResult == -1) {
                    JsfUtil.addFatalMessage("You Can't Delete Department. It is Attached to Another Object");
                } else {
                    JsfUtil.addFatalMessage("Error Occurs While Deleting Department.");
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>

    public void clear() {
        hrDepartments = new HrDepartments();
        selectedDepartments = new HrDepartments();
        hrDepAddresses = new HrDepAddresses();
        hrDeptJobs = new HrDeptJobs();
        hrJobTypes = new HrJobTypes();
        hrAddresses = new HrAddresses();
        saveUpdateUnit = 0;
        hrLuRegions = new HrLuRegions();
    }
//</editor-fold>

 public void onDragDrop(TreeDragDropEvent event) {
        preTab = "";
        secTab = "";
        adrsJobTab = "";
        secTabToggle = "tab";
        adrsJobTabToggle = "tab";
        saveOrUpdateButton = "Update";
        disableAddressBtn = "false";
        disableDebJobBtn = "false";
        saveUpdateUnit = 0;
        System.out.println("dragged: " + event.getDragNode());
        System.out.println("dropped: " + event.getDropNode() + " > " + event.getDropIndex());
        selectedNodes = event.getDragNode();
        hrDepartments = new HrDepartments();
        departmentId = Integer.parseInt((selectedNodes.getData().toString()).split("=>")[1]);
        Integer parentId = Integer.parseInt((event.getDropNode().toString()).split("=>")[1]);
        hrDepartments.setDepName((selectedNodes.getData().toString()).split("=>")[0]);
        hrDepartments.setDepId(departmentId);
        hrDepartments.setParentId(parentId);
        hrDepartments = hrDepartmentsBeanLocal.findByDeptId(hrDepartments.getDepId());
        hrLuRegions = hrDepartments.getRegionId();
        recreateDeptJob();
        if (hrDepartments.getParentId() == null || "".equals(hrDepartments.getParentId().toString())) {
            selectedDepartments.setDepId(1);
            selectedDepartments = hrDepartmentsBeanLocal.findByDeptId(addressId);
            selectedDepartments = hrDepartmentsBeanLocal.findByDeptParentId(hrDepartments.getDepId());
        } else {
            selectedDepartments = hrDepartmentsBeanLocal.findByDeptParentId(hrDepartments.getParentId());
        }
        recreateDeptAddress();
        hrDepartments.setParentId(Integer.parseInt((event.getDropNode().toString()).split("=>")[1]));
        hrDepartments.setPreparedBy(SessionBean.getUserName());
        hrDepartments.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
        hrDepAddresses.setPreparedBy(SessionBean.getUserName());
        hrDepAddresses.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
        hrDeptJobs.setPreparedBy(SessionBean.getUserName());
        hrDeptJobs.setPreparedOn(dateFormat(StringDateManipulation.toDayInEc()));
        System.out.println("hrDepartments ID: " + hrDepartments.getDepId());
        System.out.println("hrDepartments Parent: " + hrDepartments.getParentId());
        System.out.println("hrDepartments  Name: " + hrDepartments.getDepName());
        hrDepartmentsBeanLocal.edit(hrDepartments);
        loadDepartmentTree();
        clear();
        JsfUtil.addSuccessMessage("Successfully Moved To." + event.getDropNode());

    }
}
