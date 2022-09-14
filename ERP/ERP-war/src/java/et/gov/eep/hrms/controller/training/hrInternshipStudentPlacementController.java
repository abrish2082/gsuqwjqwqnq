/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

//<editor-fold defaultstate="collapsed" desc="Implorts">
import java.io.Serializable;
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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrInternshipStudentDetailsBeanLocal;
import et.gov.eep.hrms.businessLogic.training.HrInternshipStudsPlacementBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudentPlacement;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.entity.training.HrTdUniversities;
//</editor-fold>

@Named(value = "hrInternshipStudentPlacementController")
@ViewScoped
public class hrInternshipStudentPlacementController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity bussiness logic & variables">
    @EJB
    HrInternshipStudsPlacementBeanLocal hrInternshipStudPlacementBeanLocal;

    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;

    @EJB
    HrInternshipStudentDetailsBeanLocal hrInternshipStudentDetailsBeanLocal;

    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;

    @EJB
    HrDepartmentsBeanLocal hrDepartmentsFacade;

    @Inject
    HrTdIspStudentDetails hrTdIspStudentDetails;

    @Inject
    HrTdIspStudentPlacement hrTdIspStudentPlacement;

    @Inject
    HrTdIspStudents hrTdIspStudents;

    @Inject
    HrDepartments hrDepartments;

    @Inject
    HrTdUniversities hrTdUniversities;

    @Inject
    SessionBean sessionBeanLocal;
    boolean btnaddvisibility = true;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";
    private int updateStatus = 0;
    private boolean disabled;

    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    private static List<HrDepartments> araListe;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    DataModel<HrTdIspStudentPlacement> studplacementListDataModel = new ListDataModel<>();
    String SaveOrUpdateButton = "Save";
    private List<HrDepartments> depList = new ArrayList<>();
    private List<HrTdIspStudentDetails> newList = new ArrayList<>();
//</editor-fold>

    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAllDepartmentInfo();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters & setters">   
    public HrTdIspStudentDetails getHrTdIspStudentDetails() {
        if (hrTdIspStudentDetails == null) {
            hrTdIspStudentDetails = new HrTdIspStudentDetails();
        }
        return hrTdIspStudentDetails;
    }

    public void setHrTdIspStudentDetails(HrTdIspStudentDetails HrTdIspStudentDetails) {
        this.hrTdIspStudentDetails = HrTdIspStudentDetails;
    }

    public HrTdIspStudentPlacement getHrTdIspStudentPlacement() {
        if (hrTdIspStudentPlacement == null) {
            hrTdIspStudentPlacement = new HrTdIspStudentPlacement();
        }
        return hrTdIspStudentPlacement;
    }

    public void setHrTdIspStudentPlacement(HrTdIspStudentPlacement hrTdIspStudentPlacement) {
        this.hrTdIspStudentPlacement = hrTdIspStudentPlacement;
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

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public List<HrDepartments> getDepList() {
        depList = hrDepartmentsBeanLocal.findAllDepartment();
        return depList;
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

    public DataModel<HrTdIspStudentPlacement> getStudplacementListDataModel() {
        return studplacementListDataModel;
    }

    public void setStudplacementListDataModel(DataModel<HrTdIspStudentPlacement> studplacementListDataModel) {
        this.studplacementListDataModel = studplacementListDataModel;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot2() {
        return root2;
    }

    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getDepartmentList() {
        return DepartmentList;
    }

    public void setDepartmentList(TreeNode DepartmentList) {
        this.DepartmentList = DepartmentList;
    }

    public static List<HrDepartments> getAraListe() {
        return araListe;
    }

    public static void setAraListe(List<HrDepartments> araListe) {
        hrInternshipStudentPlacementController.araListe = araListe;
    }

    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }
// </editor-fold >
    // <editor-fold defaultstate="collapsed" desc="Search">

    public void selectStudent(SelectEvent event) {
        hrTdIspStudentDetails = (HrTdIspStudentDetails) event.getObject();
        hrTdIspStudentDetails = hrInternshipStudentDetailsBeanLocal.findByStudFnameObj(hrTdIspStudentDetails);
        hrTdIspStudentPlacement.setInternshipStudentDetailsId(hrTdIspStudentDetails);
        hrTdIspStudentPlacement = hrInternshipStudPlacementBeanLocal.findbyStdId(hrTdIspStudentPlacement);
        hrDepartments = hrTdIspStudentPlacement.getDeptId();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        setIcone("ui-icon-search");
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
    }

    public void populateStudentPlacement(SelectEvent event) {
        hrTdIspStudentPlacement = (HrTdIspStudentPlacement) event.getObject();
        hrTdIspStudentPlacement.setId(hrTdIspStudentPlacement.getId());
        hrTdIspStudentPlacement = hrInternshipStudPlacementBeanLocal.getSelectedRequest(hrTdIspStudentPlacement.getId());
        hrTdIspStudentDetails = hrTdIspStudentPlacement.getInternshipStudentDetailsId();
        hrDepartments = hrTdIspStudentPlacement.getDeptId();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = true;
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }

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

        }
    }

    public void newButtonAction() {
        clearInternshipPlacement();
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

    public ArrayList<HrTdIspStudentPlacement> findInternshipPlacement() {
        if (hrTdIspStudentDetails.getFirstName() != null) {
            studplacementListDataModel = new ListDataModel(new ArrayList(hrInternshipStudentDetailsBeanLocal.findByFname(hrTdIspStudentDetails)));
        }
        return hrInternshipStudentDetailsBeanLocal.findByFname(hrTdIspStudentDetails);
    }

    public HrTdIspStudentDetails findByStudFnameObj(ValueChangeEvent event) {
        hrTdIspStudentDetails.setFirstName(event.getNewValue().toString());
        return hrInternshipStudentDetailsBeanLocal.findByStudFnameObj(hrTdIspStudentDetails);
    }

    public List<HrTdIspStudentDetails> getStudList() {
        if (updateStatus == 0) {
            newList = hrInternshipStudentDetailsBeanLocal.findBystatus();
            return newList;
        } else {
            newList = hrInternshipStudentDetailsBeanLocal.findBystatusp();
            return newList;
        }
    }

    public void studentListner(ValueChangeEvent event) {
        Integer id = Integer.parseInt(event.getNewValue().toString());
        hrTdIspStudentDetails.setId(id);
        hrTdIspStudentDetails = hrInternshipStudentDetailsBeanLocal.findByidObj(hrTdIspStudentDetails);
        hrTdIspStudents = hrTdIspStudentDetails.getInternshipStudentId();
        hrTdIspStudentPlacement.setInternshipStudentDetailsId(hrTdIspStudentDetails);
    }

    public void departmentListner(ValueChangeEvent event) {
        Integer id = Integer.parseInt(event.getNewValue().toString());
        hrDepartments.setDepId(id);
        hrTdIspStudentPlacement.setDeptId(hrDepartments);
    }

    public void recursive(List<HrDepartments> liste, int id, TreeNode node) {
        araListe = new ArrayList<>();
        if (allDepartmentsList != null) {
            for (HrDepartments k : allDepartmentsList) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    araListe.add(k);
                    recursive(araListe, k.getDepId(), childNode);
                }
            }
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        hrTdIspStudentPlacement.setDeptId(hrDepartments);
        RequestContext context2 = RequestContext.getCurrentInstance();
        context2.execute("PF('digDepSearch').hide();");

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void saveInternshipPlacement() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveInternshipPlacement", dataset)) {
                if (hrTdIspStudentPlacement.getDeptId() != null) {
                    try {
                        hrTdIspStudentPlacement.setInternshipStudentDetailsId(hrTdIspStudentDetails);
                        hrTdIspStudentPlacement.setDeptId(hrDepartments);
                        if (updateStatus == 0) {
                            hrTdIspStudentDetails.setStatus(1);
                            hrInternshipStudentDetailsBeanLocal.update(hrTdIspStudentDetails);
                            hrInternshipStudPlacementBeanLocal.create(hrTdIspStudentPlacement);
                            JsfUtil.addSuccessMessage("Save Successfully.");
                            clearInternshipPlacement();
                        } else {
                            hrInternshipStudPlacementBeanLocal.update(hrTdIspStudentPlacement);
                            JsfUtil.addSuccessMessage("Update Successfully.");
                            clearInternshipPlacement();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addErrorMessage("Error occurs while save update");
                    }
                } else {
                    JsfUtil.addFatalMessage("Please Select Departmnt!!!");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveInternshipPlacement");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearInternshipPlacement() {
        depList.clear();
        hrDepartments = null;
        hrTdIspStudentPlacement = null;
        hrTdIspStudentDetails = null;
    }

    public void resetInternshipPlacement() {
        hrTdIspStudentDetails = null;
        hrTdIspStudentPlacement = null;
        hrDepartments = null;
    }

//</editor-fold>
}
