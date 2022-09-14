/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.succession;

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
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.succession.successionBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmKmp;

/**
 *
 * @author user
 */
@Named(value = "KMPControllers")
@ViewScoped
public class KMPController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @EJB
    successionBeanLocal successionBean;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;

    @Inject
    private HrSmKmp hrSmKmp;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrLuGrades hrLuGrades;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrDeptJobs hrDeptJobs;
    @Inject
    SessionBean sessionBeanLocal;

    List<HrDeptJobs> deptJobsesList = new ArrayList<>();
    List<HrDeptJobs> alldeptjobslist = new ArrayList<>();
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    ArrayList<HrLuGrades> payGradeList = null;
    List<HrJobTypes> allJobTypes;
    List<HrSmKmp> allkmps;
    List<HrJobTypes> hrJobTypesList = new ArrayList<>();

    DataModel<HrSmKmp> kmpListDataModel = new ListDataModel<>();

    private String createOrSearchBundle = "New";
    private String headerTitle = "Search...";
    private String smallheaderTitle = "";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String saveorUpdateBundle = "Save";
    int updateStatus = 0;
    boolean btnaddvisibility = true;
    private TreeNode root;
    private TreeNode selectedNode;
    private List<HrDepartments> allDepartments;
    private static List<HrDepartments> departments;

    public KMPController() {
        this.hrSmKmp = new HrSmKmp();
    }
// </editor-fold> 

    @PostConstruct
    public void init() {
        loadTree();
        allDepartmentsList = successionBean.findalldept();
        allkmps = successionBean.findAll();
    }

    //<editor-fold defaultstate="collapsed" desc="Geters and setter">
    public successionBeanLocal getSuccessionBean() {
        return successionBean;
    }

    public void setSuccessionBean(successionBeanLocal successionBean) {
        this.successionBean = successionBean;
    }

    public HrDepartmentsBeanLocal getHrDepartmentsBeanLocal() {
        return hrDepartmentsBeanLocal;
    }

    public void setHrDepartmentsBeanLocal(HrDepartmentsBeanLocal hrDepartmentsBeanLocal) {
        this.hrDepartmentsBeanLocal = hrDepartmentsBeanLocal;
    }

    public HrDeptJobs getHrDeptJobs() {
        return hrDeptJobs;
    }

    public void setHrDeptJobs(HrDeptJobs hrDeptJobs) {
        this.hrDeptJobs = hrDeptJobs;
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

    public List<HrDepartments> getAllDepartments() {
        return allDepartments;
    }

    public void setAllDepartments(List<HrDepartments> allDepartments) {
        this.allDepartments = allDepartments;
    }

    public static List<HrDepartments> getDepartments() {
        return departments;
    }

    public static void setDepartments(List<HrDepartments> departments) {
        KMPController.departments = departments;
    }

    public DataModel<HrSmKmp> getKmpListDataModel() {

        if (kmpListDataModel == null) {
            kmpListDataModel = new ListDataModel<>();
        }

        return kmpListDataModel;
    }

    public void setKmpListDataModel(DataModel<HrSmKmp> kmpListDataModel) {
        this.kmpListDataModel = kmpListDataModel;
    }

    public List<HrDeptJobs> getAlldeptjobslist() {
        return alldeptjobslist;
    }

    public void setAlldeptjobslist(List<HrDeptJobs> alldeptjobslist) {
        this.alldeptjobslist = alldeptjobslist;
    }

    public ArrayList<HrLuGrades> getPayGradeList() {
        return payGradeList;
    }

    public void setPayGradeList(ArrayList<HrLuGrades> payGradeList) {
        this.payGradeList = payGradeList;
    }

    public List<HrJobTypes> getAllJobTypes() {
        return allJobTypes;
    }

    public void setAllJobTypes(List<HrJobTypes> allJobTypes) {
        this.allJobTypes = allJobTypes;
    }

    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }

    public List<HrSmKmp> getAllkmps() {
        return allkmps;

    }

    public void setAllkmps(List<HrSmKmp> allkmps) {
        this.allkmps = allkmps;
    }

    public List<HrDeptJobs> getDeptJobsesList() {
        return deptJobsesList;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public void setDeptJobsesList(List<HrDeptJobs> deptJobsesList) {
        this.deptJobsesList = deptJobsesList;
    }

    public List<HrJobTypes> getHrJobTypesList() {
        return hrJobTypesList;
    }

    public void setHrJobTypesList(List<HrJobTypes> hrJobTypesList) {
        this.hrJobTypesList = hrJobTypesList;
    }

    public HrSmKmp getHrSmKmp() {
        if (hrSmKmp == null) {
            hrSmKmp = new HrSmKmp();
        }
        return hrSmKmp;
    }

    public void setHrSmKmp(HrSmKmp hrSmKmp) {
        this.hrSmKmp = hrSmKmp;
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

    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
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

    public String getSmallheaderTitle() {
        return smallheaderTitle;
    }

    public void setSmallheaderTitle(String smallheaderTitle) {
        this.smallheaderTitle = smallheaderTitle;
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void createNewView() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            btnNewRender = false;
            createOrSearchBundle = "Search";
            headerTitle = "Add  ";
            smallheaderTitle = "";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            btnNewRender = false;
            createOrSearchBundle = "New";
            headerTitle = "Search";
            smallheaderTitle = "";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        updateStatus = 0;
        saveorUpdateBundle = "Save";

    }

    public void loadTree() {
        allDepartments = hrDepartmentsBeanLocal.findAllDepartment();
        root = new DefaultTreeNode("Root", null);
        populateNode(allDepartments, 0, root);
    }

    public void populateNode(List<HrDepartments> depts, int id, TreeNode node) {
        departments = new ArrayList<>();
        if (getAllDepartments() != null) {
            for (HrDepartments k : getAllDepartments()) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    departments.add(k);
                    populateNode(departments, k.getDepId(), childNode);
                }
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int deptId = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments.setDepId(deptId);
        hrDepartments = hrDepartmentsBeanLocal.findByDepartmentId(hrDepartments);
        hrJobTypesList = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
    }

    public void jobChangedListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrJobTypes.setId(Integer.valueOf(event.getNewValue().toString()));
            hrJobTypes = hrRecruitmentRequestsBeanLocal.findByName(hrJobTypes.getId());
            hrSmKmp.setJobId(hrJobTypes);

        }
    }

    public ArrayList<HrSmKmp> SearchByPositionName(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                hrSmKmp.setId(event.getNewValue().hashCode());
                hrSmKmp.setId(hrSmKmp.getId());
                allkmps = successionBean.findbyposition(hrSmKmp);
                kmpListDataModel = new ListDataModel(new ArrayList(allkmps));
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("unable to find");
        }
        return null;
    }

    public ArrayList<HrSmKmp> findListByDepartmentName(ValueChangeEvent event) {

        try {
            hrDepartments.setDepName(event.getNewValue().toString());
            allkmps = successionBean.findDepartmentName(hrDepartments);
            kmpListDataModel = new ListDataModel(new ArrayList(allkmps));

        } catch (Exception ex) {
            JsfUtil.addErrorMessage("unable to find");
        }
        return null;
    }

    public List<HrSmKmp> findAll() {
        return this.successionBean.findAll();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main methods">
    public void saveKeyManagerialPosition() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveKeyManagerialPosition", dataset)) {
                try {
                    if (updateStatus == 0) {
                        if (successionBean.searchbyJobAndDep(hrJobTypes, hrDepartments) == false) {
                            hrSmKmp = null;
                            updateStatus = 0;
                            saveorUpdateBundle = "Save";
                            JsfUtil.addFatalMessage("position  Already Exist.");
                        } else {
                            hrSmKmp.setDeptId(hrDepartments);
                            hrSmKmp.setJobId(hrJobTypes);
                            successionBean.save(hrSmKmp);
                            JsfUtil.addSuccessMessage("Saved Successfully.");
                            hrJobTypes = null;
                            hrDepartments = null;
                        }
                    } else {
                        hrSmKmp.setDeptId(hrDepartments);
                        HrJobTypes em = new HrJobTypes();
                        em.setId(hrJobTypes.getId());
                        hrSmKmp.setJobId(em);
                        successionBean.edit(hrSmKmp);
                        JsfUtil.addSuccessMessage("Updated Successfully.");
                        hrJobTypes = null;
                        hrDepartments = null;
                        saveorUpdateBundle = "Save";
                    }
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Error occured while save update");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveKeyManagerialPosition");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clean() {
        hrSmKmp = new HrSmKmp();
        hrJobTypes = new HrJobTypes();
        hrDepartments = new HrDepartments();
        hrJobTypesList = null;
        updateStatus = 0;
        saveorUpdateBundle = "Save";

    }

    public void KMP_vcl(SelectEvent event) {
        hrSmKmp = (HrSmKmp) event.getObject();
        hrSmKmp.setId(hrSmKmp.getId());
        hrDepartments.setDepId(hrSmKmp.getDeptId().getDepId());
        hrJobTypes = hrSmKmp.getJobId();
        hrJobTypesList = hrRecruitmentRequestsBeanLocal.listOfJobType(hrDepartments.getDepId());
        hrSmKmp = successionBean.readkmpDetail(hrSmKmp.getId());
        disableBtnCreate = false;
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        headerTitle = "Key managerial Positoin";
        setIcone("ui-icon-search");
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

//</editor-fold>
}
