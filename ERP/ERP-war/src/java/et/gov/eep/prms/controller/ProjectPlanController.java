/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.businessLogic.ProjectPlanBeanLocal;
import et.gov.eep.prms.entity.PrmsProjectPlan;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
// </editor-fold>

/**
 * *****************************************************************************
 *
 * @author user
 * ****************************************************************************
 */
//Project Plan page view scoped CDI Named Bean class
@Named("projectPlanController")
@ViewScoped
public class ProjectPlanController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    ProjectPlanBeanLocal projectPlanBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    PrmsProjectPlan prmsProjectPlan;
    @Inject
    PmsCreateProjects pmsCreateProject;
    @Inject
    WorkFlow workFlow;
    private String selectedValue = "";
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean SessionBean;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Lists and models">
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<PrmsProjectPlan> prmsProjectPlanMdel;
    List<PrmsProjectPlan> prmsProjectPlanList;
    private List<PrmsProjectPlan> projectPlanLis1;
    List<PmsCreateProjects> pmsCreateProjectseList;
    List<PrmsProjectPlan> projectSearchParameterLst;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare instance variables">
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String duplicattion = null;
    private String purchaseType = "good";
    private String activeIndex;
    private String userName;
    String logerName;
    private String icone = "ui-icon-plus";
    String newProjectPlanNo;
    String lastProjectPlanNo = "0";

    private boolean disableBtnCreate = false;
    private boolean disablePurchaseTypeSelection;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderPnlToSearchPage;
    private boolean workName;
    private boolean serviceName;
    private boolean goodsName;
    private boolean nonConsultancy;
    private boolean consultancy;
    private boolean purchaseTypCommon = true;
    boolean renderDecision;
    boolean isRenderCreate;
    int updateStatus = 0;
    int requestNotificationCounter = 0;

    PrmsProjectPlan prmsProjectPlanSelect;
    // </editor-fold>

    //callback lifecycle methods
    @PostConstruct
    public void init() {
        try {
            projectPlanLis1 = projectPlanBeanLocal.searchByPlanNo_();
            setLogerName(SessionBean.getUserName());
            wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
            setUserName(workFlow.getUserName());

            if (workFlow.isPrepareStatus()) {
                renderDecision = false;
                isRenderCreate = true;
            }
            if (workFlow.isApproveStatus()) {
                renderDecision = true;
                isRenderCreate = false;
            }
            if (workFlow.isCheckStatus()) {
                renderDecision = true;
                isRenderCreate = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public PmsCreateProjects getPmsCreateProject() {
        if (pmsCreateProject == null) {
            pmsCreateProject = new PmsCreateProjects();
        }
        return pmsCreateProject;
    }

    public void setPmsCreateProject(PmsCreateProjects pmsCreateProject) {
        this.pmsCreateProject = pmsCreateProject;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    public PrmsProjectPlan getPrmsProjectPlan() {
        if (prmsProjectPlan == null) {
            prmsProjectPlan = new PrmsProjectPlan();
        }
        return prmsProjectPlan;
    }

    public void setPrmsProjectPlan(PrmsProjectPlan prmsProjectPlan) {
        this.prmsProjectPlan = prmsProjectPlan;
    }

    public PrmsProjectPlan getPrmsProjectPlanSelect() {
        return prmsProjectPlanSelect;
    }

    public void setPrmsProjectPlanSelect(PrmsProjectPlan prmsProjectPlanSelect) {
        this.prmsProjectPlanSelect = prmsProjectPlanSelect;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Lists and models">
    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public DataModel<PrmsProjectPlan> getPrmsProjectPlanMdel() {
        if (prmsProjectPlanMdel == null) {
            prmsProjectPlanMdel = new ListDataModel<>();
        }
        return prmsProjectPlanMdel;
    }

    public void setPrmsProjectPlanMdel(DataModel<PrmsProjectPlan> prmsProjectPlanMdel) {
        this.prmsProjectPlanMdel = prmsProjectPlanMdel;
    }

    public List<PrmsProjectPlan> getPrmsProjectPlanList() {
        if (prmsProjectPlanList == null) {
            prmsProjectPlanList = new ArrayList<>();
        }
        return prmsProjectPlanList;
    }

    public void setPrmsProjectPlanList(List<PrmsProjectPlan> prmsProjectPlanList) {
        this.prmsProjectPlanList = prmsProjectPlanList;
    }

    public List<PrmsProjectPlan> getProjectPlanLis1() {
        if (projectPlanLis1 == null) {
            projectPlanLis1 = new ArrayList<>();
        }

        return projectPlanLis1;
    }

    public void setProjectPlanLis1(List<PrmsProjectPlan> projectPlanLis1) {
        this.projectPlanLis1 = projectPlanLis1;
    }

    public List<PmsCreateProjects> getPmsCreateProjectseList() {
        if (pmsCreateProjectseList == null) {
            pmsCreateProjectseList = new ArrayList<>();
        }
        return pmsCreateProjectseList;
    }

    public void setPmsCreateProjectseList(List<PmsCreateProjects> pmsCreateProjectseList) {
        this.pmsCreateProjectseList = pmsCreateProjectseList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of variables">
    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isDisablePurchaseTypeSelection() {
        return disablePurchaseTypeSelection;
    }

    public void setDisablePurchaseTypeSelection(boolean disablePurchaseTypeSelection) {
        this.disablePurchaseTypeSelection = disablePurchaseTypeSelection;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public boolean isPurchaseTypCommon() {
        return purchaseTypCommon;
    }

    public void setPurchaseTypCommon(boolean purchaseTypCommon) {
        this.purchaseTypCommon = purchaseTypCommon;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlToSearchPage() {
        return renderPnlToSearchPage;
    }

    public void setRenderPnlToSearchPage(boolean renderPnlToSearchPage) {
        this.renderPnlToSearchPage = renderPnlToSearchPage;
    }

    public boolean isWorkName() {
        return workName;
    }

    public void setWorkName(boolean workName) {
        this.workName = workName;
    }

    public boolean isServiceName() {
        return serviceName;
    }

    public void setServiceName(boolean serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isGoodsName() {
        return goodsName;
    }

    public void setGoodsName(boolean goodsName) {
        this.goodsName = goodsName;
    }

    public boolean isNonConsultancy() {
        return nonConsultancy;
    }

    public void setNonConsultancy(boolean nonConsultancy) {
        this.nonConsultancy = nonConsultancy;
    }

    public boolean isConsultancy() {
        return consultancy;
    }

    public void setConsultancy(boolean consultancy) {
        this.consultancy = consultancy;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public int getRequestNotificationCounter() {

        projectPlanLis1 = projectPlanBeanLocal.searchByPlanNo_();
        requestNotificationCounter = projectPlanLis1.size();

        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event changes">

    public List<PrmsProjectPlan> getProjectSearchParameterLst() {
        if (projectSearchParameterLst == null) {
            projectSearchParameterLst = new ArrayList<>();
            projectSearchParameterLst = projectPlanBeanLocal.getParamNameList();
        }
        return projectSearchParameterLst;
    }

    public void setProjectSearchParameterLst(List<PrmsProjectPlan> projectSearchParameterLst) {
        this.projectSearchParameterLst = projectSearchParameterLst;
    }
    
    
     public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsProjectPlan.setColumnName(event.getNewValue().toString());
            prmsProjectPlan.setColumnValue(null);
        }
    }
    
    public void rowSelect(SelectEvent event) {

        prmsProjectPlan = (PrmsProjectPlan) event.getObject();

        if (prmsProjectPlan.getProjectName() != null) {
            pmsCreateProject = prmsProjectPlan.getProjectName();
        }
        populateWorkFlow();
    }

    public void changeServiceType(ValueChangeEvent e) {

        if (null != e.getNewValue()) {

            String select = "select";
            select = e.getNewValue().toString();

            if (select.equalsIgnoreCase("Consultancy")) {
                purchaseTypCommon = false;
                nonConsultancy = false;
                consultancy = true;
            } else if (select.equalsIgnoreCase("Non Consultancy")) {

                nonConsultancy = true;
                consultancy = false;
                purchaseTypCommon = true;
            }
        }
    }

    public void changePrRqType(ValueChangeEvent e) {

        if (null != e.getNewValue()) {

            purchaseType = e.getNewValue().toString();
            if (purchaseType.equalsIgnoreCase("good")) {
                goodsName = true;
                serviceName = false;
                workName = false;
                nonConsultancy = false;
                consultancy = false;
                purchaseTypCommon = true;
            } else if (purchaseType.equalsIgnoreCase("Service")) {
                goodsName = false;
                serviceName = true;
                workName = false;
                nonConsultancy = false;
                consultancy = false;
                purchaseTypCommon = true;
            } else if (purchaseType.equalsIgnoreCase("Work")) {
                goodsName = false;
                serviceName = false;
                workName = true;
                nonConsultancy = false;
                consultancy = false;
                purchaseTypCommon = true;
            }
        }

    }

    public void RequestListChange(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            prmsProjectPlan = (PrmsProjectPlan) event.getNewValue();
            populateWorkFlow();
        }
    }

    public void handleDecision(ValueChangeEvent eve) {

        if (eve.getNewValue() != null) {
            selectedValue = eve.getNewValue().toString();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other methods">
    public String generateProjectplanNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newProjectPlanNo = prmsProjectPlan.getProjectPlanNo();
        } else {
            if (purchaseType != null) {
                newProjectPlanNo = getGoodsOrServiceOrWorkProjectSeqNo(purchaseType);
            }
        }
        return newProjectPlanNo;
    }

    public String getGoodsOrServiceOrWorkProjectSeqNo(String purchaseType) {
        System.out.println("ooo " + purchaseType);
        String goods_Service_Work_ProjectNo = projectPlanBeanLocal.getGoodsOrServiceOrWorkProjectSeqNo(purchaseType);
        return goods_Service_Work_ProjectNo;
    }

    public void goBackSearchButtonAction() {
        renderPnlCreateParty = false;
        renderPnlManPage = true;
        renderPnlToSearchPage = false;
    }

    public void createProjectPlan() {

        saveorUpdateBundle = "Save";
        renderPnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            purchaseType = "good";
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            clear();
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void recreatworkflow() {
        wfPrmsProcessedDModel = new ListDataModel(new ArrayList(prmsProjectPlan.getPrmsWorkFlowProccedList()));
    }

    public List<PmsCreateProjects> getProjectIds() {
        pmsCreateProjectseList = projectPlanBeanLocal.getProjectId();
        return pmsCreateProjectseList;
    }

    public Date getminDate1() {
        return this.prmsProjectPlan.getPrepareDocumntStrtDate();
    }

    public Date getminDate2() {
        return this.prmsProjectPlan.getAdvrtiseSpcificNticeStrtDt();
    }

    public Date getminDate3() {
        return this.prmsProjectPlan.getOpenPreQulifictionStrtDat();

    }

    public Date getminDate4() {

        return this.prmsProjectPlan.getPreQlfPrplEvlRprtStrtDt();
    }

    public Date getminDate5() {

        return this.prmsProjectPlan.getSubmisionEvalRecommStrtDte();
    }

    public Date getminDate6() {

        return this.prmsProjectPlan.getSubmApprlEvalRprtStrtDt();
    }

    public Date getminDate7() {

        return this.prmsProjectPlan.getInvtionShrtLstBiderStrtDt();
    }

    public Date getminDate8() {

        return this.prmsProjectPlan.getBidOpeningDate();
    }

    public Date getminDate9() {
        return this.prmsProjectPlan.getEvltTchniclFinanStrtDte();
    }

    public Date getminDate10() {
        return this.prmsProjectPlan.getSbmtTchnclFinanComiteStrt();
    }

    public Date getminDate11() {
        return this.prmsProjectPlan.getSbmtTchnclFinanMgmtStrtDt();
    }

    public Date getminDate12() {
        return this.prmsProjectPlan.getSbmtTchnclFinanBrordStrtD();
    }

    public Date getminDate13() {
        return this.prmsProjectPlan.getNotificationAwardStrtDate();
    }

    public Date getminDate14() {
        return this.prmsProjectPlan.getContrctDocmntPrprtioStrtDt();
    }

    public Date getminDate15() {
        return this.prmsProjectPlan.getServicePeriodStrtDt();
    }

    public Date getminDate16() {
        return this.prmsProjectPlan.getTermOfRefStrtDt();
    }

    public Date getminDate17() {
        return this.prmsProjectPlan.getAdvrExpInterFloatStrtDt();
    }

    public Date getminDate18() {
        return this.prmsProjectPlan.getEvExpIntShortLisStrtDt();
    }

    public Date getminDate19() {
        return this.prmsProjectPlan.getSubShrtRepRecStrtDt();
    }

    public Date getminDate20() {
        return this.prmsProjectPlan.getSubShrtRepAppMgtStrtDt();
    }

    public Date getminDate21() {
        return this.prmsProjectPlan.getIssueRqtRfpFltPrdStrtDt();
    }

    public Date getminDate22() {
        return this.prmsProjectPlan.getOpenTechPropStrtDt();
    }

    public Date getminDate23() {
        return this.prmsProjectPlan.getEvalTechStrtDt();
    }

    public Date getminDate24() {
        return this.prmsProjectPlan.getSbmtTchnclRepComiteStrt();

    }

    public Date getminDate25() {
        return this.prmsProjectPlan.getSbmtTchncMgmtStrtDt();

    }

    public Date getminDate26() {
        return this.prmsProjectPlan.getSbmtTchncBoardStrtDt();

    }

    public Date getminDate27() {
        return this.prmsProjectPlan.getContExecCompStrtDt();

    }

    public Date getminDate28() {
        return this.prmsProjectPlan.getConsttrprdsrtdt();

    }

    public void recreatModel() {

        prmsProjectPlanMdel = null;
        prmsProjectPlanMdel = new ListDataModel(new ArrayList<>(getPrmsProjectPlanList()));
    }

    public void search_ProjectPlan() {

        prmsProjectPlan.setPreparedBy(String.valueOf(workFlow.getUserAccount()));
        prmsProjectPlanList = projectPlanBeanLocal.searchByPlanNo(prmsProjectPlan);

        recreatModel();
        prmsProjectPlan=new PrmsProjectPlan();
    }

    public void populateWorkFlow() {

        prmsProjectPlan.setId(prmsProjectPlan.getId());
        prmsProjectPlan = projectPlanBeanLocal.getProjectPlanId(prmsProjectPlan.getId());
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderPnlToSearchPage = true;
        disablePurchaseTypeSelection = true;
        saveorUpdateBundle = "Update";
        wfPrmsProcessed.setProcessedOn(prmsProjectPlan.getDateRigistered());
        purchaseType = prmsProjectPlan.getPurchaseType();

        if (purchaseType.equalsIgnoreCase("good")) {
            serviceName = false;
            workName = false;
            goodsName = true;
            purchaseTypCommon = true;
        } else if (purchaseType.equalsIgnoreCase("Service")) {
            serviceName = true;
            workName = false;
            goodsName = false;
            purchaseTypCommon = true;
        } else if (purchaseType.equalsIgnoreCase("Work")) {
            serviceName = false;
            workName = true;
            goodsName = false;
            purchaseTypCommon = true;
        }
        recreatworkflow();
    }

    public void clear() {
        prmsProjectPlan = null;
        saveorUpdateBundle = "Save";
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
        pmsCreateProject = null;
        wfPrmsProcessed = null;
        purchaseType = "good";
        renderPnlToSearchPage = false;
        disablePurchaseTypeSelection = false;
    }

    public void clearSearch() {

        prmsProjectPlan = null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update method">
    public String save_ProjectPlan() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");

            if (security.checkAccess(SessionBean.getUserName(), "save_ProjectPlan", dataset)) {
                try {
                    if (saveorUpdateBundle.equals("Save")) {
                        generateProjectplanNo();
                        prmsProjectPlan.setPurchaseType(purchaseType);
                        prmsProjectPlan.setProjectPlanNo(newProjectPlanNo);
                        prmsProjectPlan.setProjectName(pmsCreateProject);

                        prmsProjectPlan.setPreparedBy(String.valueOf(SessionBean.getUserId()));
                        Date currentDate = Calendar.getInstance().getTime();
                        prmsProjectPlan.setStatus(Constants.PREPARE_VALUE);
                        wfPrmsProcessed.setProjectProcurementPlanId(prmsProjectPlan);
                        prmsProjectPlan.setStatus(Constants.PREPARE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(prmsProjectPlan.getStatus()));//-
                        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
                        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
                        prmsProjectPlan.setDateRigistered(currentDate);
                        projectPlanBeanLocal.save(prmsProjectPlan);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Project Plan Information is Saved Successfully");
                        clear();
                    } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equals("Update")) {
                        prmsProjectPlan.setPurchaseType(purchaseType);
                        projectPlanBeanLocal.upDate(prmsProjectPlan);

                        JsfUtil.addSuccessMessage("Project Plan Information is Updated Successfully");
                        clear();
                    } else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equals("Update")) {

                        try {
                            if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                                workFlow.setUserStatus(Constants.APPROVE_VALUE);
                                prmsProjectPlan.setStatus(Constants.APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                            } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                                workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                                prmsProjectPlan.setStatus(Constants.CHECK_APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                            } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                                workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                                prmsProjectPlan.setStatus(Constants.APPROVE_REJECT_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                            } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                                workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                                prmsProjectPlan.setStatus(Constants.CHECK_REJECT_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                            }

                            projectPlanBeanLocal.upDate(prmsProjectPlan);
                            JsfUtil.addSuccessMessage("Project Plan Information is Decided Successfully");
                            clear();

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                        }
                    } else {
                        projectPlanBeanLocal.upDate(prmsProjectPlan);
                        JsfUtil.addSuccessMessage("Project Plan is Updated Successfully");
                        clear();
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    JsfUtil.addErrorMessage("Data is Not saved");
                }

                return null;
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                //..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    // </editor-fold>

}
