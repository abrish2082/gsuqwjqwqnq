/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.adminstrator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
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
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;

/**
 *
 * @author Binyam
 */
@Named(value = "costcenterController")
@ViewScoped
public class costcenterController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="@EJB and @Inject declaration">
//@Inject

    @Inject
    SessionBean SessionBean;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;

    //@EJB
    @EJB
    private HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    private HrDepartmentsFacade hrDepartmentsFacade;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    private TreeNode root;
    private TreeNode root2;
    private TreeNode selectedNode;
    TreeNode DepartmentList;
    List<HrDepartments> allDepartmentsList = new ArrayList<>();
    systemRegistrationController syst = new systemRegistrationController();
    private String costSaveOrUpdateButton = "Save";
    private FmsCostCenter selectedCostCenter;
    FmsCostCenter fmsCC = new FmsCostCenter();
    private List<FmsCostCenter> costcenterList;
    private DataModel<FmsCostCenter> costcenterDModel;
    private String createOrSearchBundle = "New";
    private String headerTitle = "Cost Center Search";
    private String icone = "ui-icon-plus";
    private static List<HrDepartments> araListe;
    private Boolean Costupdate = false;
    private boolean renderPnlToSearchPage;
    private boolean isRenderCreate = true;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
//</editor-fold>

    public costcenterController() {
    }

    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAll();
        root = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, root);
        root2 = getRoot();

    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public HrDepartments getHrDepartments() {
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public HrRecruitmentRequestsBeanLocal getHrRecruitmentRequestsBeanLocal() {
        return hrRecruitmentRequestsBeanLocal;
    }

    public void setHrRecruitmentRequestsBeanLocal(HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal) {
        this.hrRecruitmentRequestsBeanLocal = hrRecruitmentRequestsBeanLocal;
    }

    public FmsLuSystem getFmsLuSystem() {
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public FmsCostCenter getFmsCostCenter() {
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public FmsLuSystemBeanLocal getFmsLuSystemBeanLocal() {
        return fmsLuSystemBeanLocal;
    }

    public void setFmsLuSystemBeanLocal(FmsLuSystemBeanLocal fmsLuSystemBeanLocal) {
        this.fmsLuSystemBeanLocal = fmsLuSystemBeanLocal;
    }

    public FmsCostCenterBeanLocal getFmsCostCenterBeanLocal() {
        return fmsCostCenterBeanLocal;
    }

    public void setFmsCostCenterBeanLocal(FmsCostCenterBeanLocal fmsCostCenterBeanLocal) {
        this.fmsCostCenterBeanLocal = fmsCostCenterBeanLocal;
    }

    public HrDepartmentsFacade getHrDepartmentsFacade() {
        return hrDepartmentsFacade;
    }

    public void setHrDepartmentsFacade(HrDepartmentsFacade hrDepartmentsFacade) {
        this.hrDepartmentsFacade = hrDepartmentsFacade;
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

    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }

    public static List<HrDepartments> getAraListe() {
        return araListe;
    }

    public static void setAraListe(List<HrDepartments> araListe) {
        costcenterController.araListe = araListe;
    }

    public FmsCostCenter getSelectedCostCenter() {
        return selectedCostCenter;
    }

    public void setSelectedCostCenter(FmsCostCenter selectedCostCenter) {
        this.selectedCostCenter = selectedCostCenter;
    }

    public Boolean getCostupdate() {
        return Costupdate;
    }

    public void setCostupdate(Boolean Costupdate) {
        this.Costupdate = Costupdate;
    }

    public String getCostSaveOrUpdateButton() {
        return costSaveOrUpdateButton;
    }

    public void setCostSaveOrUpdateButton(String costSaveOrUpdateButton) {
        this.costSaveOrUpdateButton = costSaveOrUpdateButton;
    }

    public FmsCostCenter getFmsCC() {
        return fmsCC;
    }

    public void setFmsCC(FmsCostCenter fmsCC) {
        this.fmsCC = fmsCC;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public List<FmsCostCenter> getCostcenterList() {
        if (costcenterList == null) {
            costcenterList = new ArrayList<>();
        }
        return costcenterList;
    }

    public void setCostcenterList(List<FmsCostCenter> costcenterList) {
        this.costcenterList = costcenterList;
    }

    public DataModel<FmsCostCenter> getCostcenterDModel() {
        return costcenterDModel;
    }

    public void setCostcenterDModel(DataModel<FmsCostCenter> costcenterDModel) {
        this.costcenterDModel = costcenterDModel;
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

    public boolean isRenderPnlToSearchPage() {
        return renderPnlToSearchPage;
    }

    public void setRenderPnlToSearchPage(boolean renderPnlToSearchPage) {
        this.renderPnlToSearchPage = renderPnlToSearchPage;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    //</editor-fold>
    //recursive
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

    //search button action
    public void goBackSearchButtonAction() {
        renderPnlToSearchPage = false;
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;

    }

    //value chage event for system change
    public void SysChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsCostCenterBeanLocal.findLuSystem(fmsLuSystem);
            fmsCostCenter.setSystemId(fmsLuSystem);
        }
    }

    //node select event
    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        int key = Integer.parseInt((selectedNode.getData().toString()).split("--")[0]);
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(key);
        fmsCC = fmsCostCenterBeanLocal.findbyDepId(hrDepartments);
        if (fmsCC.getCostCenterId() != null) {
            JsfUtil.addFatalMessage("This department has already been assigned a cost center.");
            hrDepartments = new HrDepartments();
        } else {
        }
    }

    //select event
    public void populateCC(SelectEvent event) {
        fmsCostCenter = (FmsCostCenter) event.getObject();
        Costupdate = true;
        costSaveOrUpdateButton = "Update";
        renderPnlCreateGatePass = true;
        renderPnlToSearchPage = true;
        renderPnlManPage = false;
        createOrSearchBundle = "New";
        headerTitle = "Cost Center Registration";
        setIcone("ui-icon-plus");
    }

    //action event
    public void navAction(ActionEvent event) {
        fmsLuSystem = fmsLuSystemBeanLocal.findBySytemCode2(fmsCostCenter.getSystemId());
        syst.dispReceivedData(fmsLuSystem);
    }

    //select item
    public SelectItem[] getSystemList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    //select item
    public SelectItem[] getStatus() {
        return JsfUtil.getSelectItems(stat(), true);
    }

    //array list for status
    public ArrayList<String> stat() {
        ArrayList<String> position = new ArrayList<>();
        position.add("Active");
        position.add("Inactive");
        return position;
    }

    //search cost
    public void CostSearch() {
        costcenterList = new ArrayList<>();
        if (fmsCostCenter == null) {
            costcenterList = fmsCostCenterBeanLocal.findAll();
            costcenterDModel = new ListDataModel(new ArrayList(costcenterList));
        } else {
            costcenterList = fmsCostCenterBeanLocal.searchCost(fmsCostCenter);
            costcenterDModel = new ListDataModel(new ArrayList(costcenterList));
        }
    }

    //list for find all
    public List<FmsCostCenter> findCCList() {
        return fmsCostCenterBeanLocal.findAll();
    }

    //navigate
    public String navigate() {
        return "success";
    }

    //save cost center
    public void createCostC() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "createCostC", dataset)) {

                if (fmsCostCenter != null) {
                    if (Costupdate == false) {
                        fmsCostCenterBeanLocal.create(fmsCostCenter);
                        JsfUtil.addSuccessMessage("Cost Center Created Successfully");
                    } else {
                        fmsCostCenterBeanLocal.edit(fmsCostCenter);
                        JsfUtil.addSuccessMessage("Cost Center Updated Successfully");
                        Costupdate = false;
                    }
                } else {
                    JsfUtil.addFatalMessage("Cost Center form must have values");
                }
                resetCC();

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
        }
    }

    //delete cost center
    public void deleteCostC() {
        fmsCostCenterBeanLocal.deleteCC(fmsCostCenter);
        JsfUtil.addSuccessMessage("Cost Center Deleted Successfully");
        fmsCostCenter = new FmsCostCenter();
        hrDepartments = new HrDepartments();
    }

    //reset cost center
    public void resetCC() {
        try {
            fmsCostCenter = new FmsCostCenter();
            hrDepartments = new HrDepartments();
            costSaveOrUpdateButton = "Save";
            costcenterDModel = null;
        } catch (Exception e) {
            throw e;
        }
    }

    //create and search button
    public void createNewCostCenterView() {
        renderPnlToSearchPage = false;
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            resetCC();
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Cost Center Registration";
            setIcone("ui-icon-search");
            costSaveOrUpdateButton = "Save";
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Cost Center Search";
            setIcone("ui-icon-plus");
            costSaveOrUpdateButton = "Update";
        }
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsCostCenter.setColumnName(event.getNewValue().toString());
            fmsCostCenter.setColumnValue(null);
            System.out.println("fmsCostCenter.getColumnName(); " + fmsCostCenter.getColumnName());
            System.out.println("fmsCostCenter.getColumnValue(); " + fmsCostCenter.getColumnValue());

        }
    }
   List<FmsCostCenter> FmsCostCenterSearchingParameterList;

    public List<FmsCostCenter> getFmsCostCenterSearchingParameterList() {
        
        if (FmsCostCenterSearchingParameterList == null) {
            FmsCostCenterSearchingParameterList = new ArrayList<>();
            FmsCostCenterSearchingParameterList = fmsCostCenterBeanLocal.findCostcenterlist1();
            System.out.println("FmsCostCenterSearchingParameterList==" + FmsCostCenterSearchingParameterList);
        }
        return FmsCostCenterSearchingParameterList;

    }

    public void setFmsCostCenterSearchingParameterList(List<FmsCostCenter> FmsCostCenterSearchingParameterList) {
        this.FmsCostCenterSearchingParameterList = FmsCostCenterSearchingParameterList;
    }

    DataModel<FmsCostCenter> fmsCostCenterDataModel;

    public DataModel<FmsCostCenter> getFmsCostCenterDataModel() {
        return fmsCostCenterDataModel;
    }

    public void setFmsCostCenterDataModel(DataModel<FmsCostCenter> fmsCostCenterDataModel) {
        this.fmsCostCenterDataModel = fmsCostCenterDataModel;
    }

   

   

    public void recreateJvDataModel() {
        fmsCostCenterDataModel = null;
        fmsCostCenterDataModel = new ListDataModel(new ArrayList(fmsFmsCostCenterList1));
    }
    List<FmsCostCenter> fmsFmsCostCenterList1;

    public void search_vouchers() {
//        fmsLuSystem.setPreparedBy(sessionBeanLocal.getUserId());
//        fmsLuSystem.setType("CHPV");
        fmsFmsCostCenterList1 = fmsCostCenterBeanLocal.searchAllVochNo(fmsCostCenter);
        recreateJvDataModel();
        fmsCostCenter = new FmsCostCenter();
    }

}
