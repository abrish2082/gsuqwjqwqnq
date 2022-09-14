/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.BidAmendBeanLocal;
import et.gov.eep.prms.businessLogic.PreliminaryBeanLocal;
import et.gov.eep.prms.businessLogic.VendorRegBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsPreminilaryEvaluation;
import et.gov.eep.prms.entity.PrmsPreminilaryEvalutionDt;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
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

/**
 *
 * @author user
 */
@Named("preminilaryController")
@ViewScoped

public class PreminilaryControler implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="declaration and initialization">
    @EJB
    PreliminaryBeanLocal premininaryBeanLocal;
    @EJB
    BidAmendBeanLocal amendBeanLocal;
    @EJB
    VendorRegBeanLocal vendorRegBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @Inject
    PrmsPreminilaryEvaluation prmsPreminirayEval;
    @Inject
    PrmsPreminilaryEvalutionDt preminilaryEvalutionDt;
    @Inject
    PrmsBid prmsBid;
    @Inject
    PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;
    List<PrmsBid> prmsBidList;
    List<PrmsSupplyProfile> prmsSupplyProfileList;
    List<PrmsPreminilaryEvaluation> preminilaryEvaluationList;
    List<PrmsPreminilaryEvaluation> preminilaryEvaluationSearchParameterLst;
    DataModel<PrmsPreminilaryEvalutionDt> preminilaryEvalutionDtMdl;
    DataModel<PrmsPreminilaryEvaluation> preminilaryEvaluationMdl;
    DataModel<WfPrmsProcessed> workFlowDataModel;
    PrmsPreminilaryEvaluation preminilaryEvaluationSelected;
    Set<String> actionPlnDetlCheck = new HashSet<>();
    Set<String> actionPlnDetlCheckFrSpCode = new HashSet<>();
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean UncheckedBidCriteria = true;
    private String activeIndex;
    private String icone = "ui-icon-plus";
    String selectedValue = "";
    Integer requestNotificationCounter = 0;
    int updateStatus = 0;
    int addUpdateStatus = 0;
    private String userName = "";
    private int rowIndex;
    private int selectedRowIndex;
    private boolean renderpnlToSearchPage;
    private boolean isRenderedIconWorkflow = false;
    private boolean isDecision = false;
    private boolean isCommentGiven = false;
    private boolean isCreateButton;
    private List<PrmsBid> listOFBid;
    List<PrmsPreminilaryEvalutionDt> evalutionDtList;
    private PrmsPreminilaryEvalutionDt selectedDetail;

    private String[] selectedBidCriteria;
    private int editRowData = 0;

    String newPrelmryEvNo;
    String prelinimaryEvNum = "0";
    String addOrUpdateBundle = "Add";
   //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Post Construct ">
    @PostConstruct
    public void init() {
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        setUserName(workFlow.getUserName());
        if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
            isDecision = true;
            isCommentGiven = true;
            isCreateButton = false;

        } else {
            isDecision = false;
            isCommentGiven = false;
            isCreateButton = true;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter ">
    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public String getAddOrUpdateBundle() {
        return addOrUpdateBundle;
    }

    public void setAddOrUpdateBundle(String addOrUpdateBundle) {
        this.addOrUpdateBundle = addOrUpdateBundle;
    }

    public String[] getSelectedBidCriteria() {
        return selectedBidCriteria;
    }

    public void setSelectedBidCriteria(String[] selectedBidCriteria) {
        this.selectedBidCriteria = selectedBidCriteria;
    }

    public PrmsPreminilaryEvalutionDt getSelectedDetail() {
        return selectedDetail;
    }

    public void setSelectedDetail(PrmsPreminilaryEvalutionDt selectedDetail) {
        this.selectedDetail = selectedDetail;
    }
    /*This method is used to cout request notification 
    
     */

    public Integer getRequestNotificationCounter() {
        preminilaryEvaluationList = premininaryBeanLocal.getPrelmnryOnList();
        requestNotificationCounter = preminilaryEvaluationList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
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

    public DataModel<WfPrmsProcessed> getWorkFlowDataModel() {
        if (workFlowDataModel == null) {
            workFlowDataModel = new ListDataModel<>();
        }
        return workFlowDataModel;
    }

    public void setWorkFlowDataModel(DataModel<WfPrmsProcessed> workFlowDataModel) {
        this.workFlowDataModel = workFlowDataModel;
    }

    public PrmsSupplyProfile getPrmsSupplyProfile() {
        if (prmsSupplyProfile == null) {
            prmsSupplyProfile = new PrmsSupplyProfile();
        }
        return prmsSupplyProfile;
    }

    public void setPrmsSupplyProfile(PrmsSupplyProfile prmsSupplyProfile) {
        this.prmsSupplyProfile = prmsSupplyProfile;
    }

    public PrmsPreminilaryEvaluation getPreminilaryEvaluationSelected() {
        return preminilaryEvaluationSelected;
    }

    public void setPreminilaryEvaluationSelected(PrmsPreminilaryEvaluation preminilaryEvaluationSelected) {
        this.preminilaryEvaluationSelected = preminilaryEvaluationSelected;
    }

    public List<PrmsSupplyProfile> getPrmsSupplyProfileList() {
        if (prmsSupplyProfileList == null) {
            prmsSupplyProfileList = new ArrayList<>();
        }
        return prmsSupplyProfileList;
    }

    public void setPrmsSupplyProfileList(List<PrmsSupplyProfile> prmsSupplyProfileList) {
        this.prmsSupplyProfileList = prmsSupplyProfileList;
    }

    public DataModel<PrmsPreminilaryEvaluation> getPreminilaryEvaluationMdl() {
        if (preminilaryEvaluationMdl == null) {
            preminilaryEvaluationMdl = new ListDataModel<>();
        }
        return preminilaryEvaluationMdl;
    }

    public void setPreminilaryEvaluationMdl(DataModel<PrmsPreminilaryEvaluation> preminilaryEvaluationMdl) {
        this.preminilaryEvaluationMdl = preminilaryEvaluationMdl;
    }

    public List<PrmsPreminilaryEvaluation> getPreminilaryEvaluationList() {
        return preminilaryEvaluationList;
    }

    public void setPreminilaryEvaluationList(List<PrmsPreminilaryEvaluation> preminilaryEvaluationList) {
        this.preminilaryEvaluationList = preminilaryEvaluationList;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isIsCreateButton() {
        return isCreateButton;
    }

    public void setIsCreateButton(boolean isCreateButton) {
        this.isCreateButton = isCreateButton;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
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

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }

    public boolean isIsDecision() {
        return isDecision;
    }

    public void setIsDecision(boolean isDecision) {
        this.isDecision = isDecision;
    }

    public boolean isIsCommentGiven() {
        return isCommentGiven;
    }

    public void setIsCommentGiven(boolean isCommentGiven) {
        this.isCommentGiven = isCommentGiven;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DataModel<PrmsPreminilaryEvalutionDt> getPreminilaryEvalutionDtMdl() {
        if (preminilaryEvalutionDtMdl == null) {
            preminilaryEvalutionDtMdl = new ListDataModel<>();

        }
        return preminilaryEvalutionDtMdl;
    }

    public void setPreminilaryEvalutionDtMdl(DataModel<PrmsPreminilaryEvalutionDt> preminilaryEvalutionDtMdl) {
        this.preminilaryEvalutionDtMdl = preminilaryEvalutionDtMdl;
    }

    public PrmsBid getPrmsBid() {
        if (prmsBid == null) {
            prmsBid = new PrmsBid();
        }
        return prmsBid;
    }

    public void setPrmsBid(PrmsBid prmsBid) {
        this.prmsBid = prmsBid;
    }

    public List<PrmsBid> getPrmsBidList() {
        if (prmsBidList == null) {
            prmsBidList = new ArrayList<>();
//            for (int i = 0; i < prmsBidList.size(); i++) {
//                prmsBid = prmsBidList.get(i);
//                prmsBidList.add(prmsBid);
//            }
        }

        return prmsBidList;
    }

    public void setPrmsBidList(List<PrmsBid> prmsBidList) {
        this.prmsBidList = prmsBidList;
    }

    public PrmsPreminilaryEvalutionDt getPreminilaryEvalutionDt() {
        if (preminilaryEvalutionDt == null) {
            preminilaryEvalutionDt = new PrmsPreminilaryEvalutionDt();
        }
        return preminilaryEvalutionDt;
    }

    public void setPreminilaryEvalutionDt(PrmsPreminilaryEvalutionDt preminilaryEvalutionDt) {
        this.preminilaryEvalutionDt = preminilaryEvalutionDt;
    }

    public PrmsPreminilaryEvaluation getPrmsPreminirayEval() {
        if (prmsPreminirayEval == null) {
            prmsPreminirayEval = new PrmsPreminilaryEvaluation();
        }
        return prmsPreminirayEval;
    }

    public void setPrmsPreminirayEval(PrmsPreminilaryEvaluation prmsPreminirayEval) {
        this.prmsPreminirayEval = prmsPreminirayEval;
    }

    public List<PrmsBid> getListOFBid() {
        if (listOFBid == null) {
            listOFBid = new ArrayList<PrmsBid>();
        }
        return listOFBid;
    }

    public void setListOFBid(List<PrmsBid> listOFBid) {
        this.listOFBid = listOFBid;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public boolean isUncheckedBidCriteria() {
        return UncheckedBidCriteria;
    }

    public void setUncheckedBidCriteria(boolean UncheckedBidCriteria) {
        this.UncheckedBidCriteria = UncheckedBidCriteria;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public List<PrmsPreminilaryEvaluation> getPreminilaryEvaluationSearchParameterLst() {
        if (preminilaryEvaluationSearchParameterLst == null) {
            preminilaryEvaluationSearchParameterLst = new ArrayList<>();
            preminilaryEvaluationSearchParameterLst = premininaryBeanLocal.getParamNameList();
        }
        return preminilaryEvaluationSearchParameterLst;
    }

    public void setPreminilaryEvaluationSearchParameterLst(List<PrmsPreminilaryEvaluation> preminilaryEvaluationSearchParameterLst) {
        this.preminilaryEvaluationSearchParameterLst = preminilaryEvaluationSearchParameterLst;
    }
    

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="method ">
 /*This method is used show list of request  from someone 
    
     */
     public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsPreminirayEval.setColumnName(event.getNewValue().toString());
            prmsPreminirayEval.setColumnValue(null);
        }
    }
    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsPreminirayEval = (PrmsPreminilaryEvaluation) event.getNewValue();
            popUp();
            saveorUpdateBundle = "Update";
        }
    }

    public void rowSelect(SelectEvent event) {
        prmsPreminirayEval = (PrmsPreminilaryEvaluation) event.getObject();
        popUp();
        saveorUpdateBundle = "Update";
        addOrUpdateBundle = "Modify";

    }

    public void popUp() {
        prmsPreminirayEval.setId(prmsPreminirayEval.getId());
        prmsPreminirayEval = premininaryBeanLocal.getProjectPlanId(prmsPreminirayEval.getId());

        renderPnlManPage = true;
        renderPnlCreateParty = true;
        renderpnlToSearchPage = true;
        if (!saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
            System.out.println("is preparer");
            wfPrmsProcessed.setProcessedOn(prmsPreminirayEval.getDateRigistered());
            System.out.println("date=   " + wfPrmsProcessed.getProcessedOn());
        }
        setIsRenderedIconWorkflow(true);

        prmsBid = prmsPreminirayEval.getBidId();
        prmsBid.setId(prmsBid.getId());
        prmsSupplyProfileList = premininaryBeanLocal.getSupplierName(prmsBid.getRefNo());
        selectedBidCriteria = prmsBid.getBidCriteriaId().split(",");
        prmsBidList = premininaryBeanLocal.getBidCriteriaList(prmsBid.getRefNo());

        reCreatModel();
    }

    public void search_PreliminaryEvalution() {
        prmsPreminirayEval.setPreparedBy(workFlow.getUserAccount());
        preminilaryEvaluationList = premininaryBeanLocal.getBidPrelmnryEvNo(prmsPreminirayEval);
        recreatPreliminaryJEval();
        prmsPreminirayEval=new PrmsPreminilaryEvaluation();
    }

    public void valueChangeBidNo(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            prmsBid.setId(event.getNewValue().toString());
            prmsBidList = premininaryBeanLocal.getBidCriteriaList(event.getNewValue().toString());
            prmsSupplyProfileList = premininaryBeanLocal.getSupplierName(event.getNewValue().toString());
        }

    }

    public SelectItem[] getBidNo() {
        return JsfUtil.getSelectItems(premininaryBeanLocal.getBidNoLst(), true);
    }

    public SelectItem[] getBidNou() {
        return JsfUtil.getSelectItems(Arrays.asList(prmsBid.getBidCriteriaId().split(";")), true);
    }

    public void reCreatModel() {
        preminilaryEvalutionDtMdl = null;
        preminilaryEvalutionDtMdl = new ListDataModel(new ArrayList(prmsPreminirayEval.getPrmsPreminilaryEvalutionDtList()));
    }

    public void recreatPreliminaryJEval() {
        preminilaryEvaluationMdl = null;
        preminilaryEvaluationMdl = new ListDataModel<>(new ArrayList<>(getPreminilaryEvaluationList()));
    }
    String aa = "";
    String bb = "";

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb;
    }

    public void clear() {
        preminilaryEvalutionDt = null;
        selectedBidCriteria = null;
        prmsSupplyProfile = null;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }
    /*This method is used to add a data to a datatable 
    
     */

    public String addToDataTable() {

        int size = selectedBidCriteria.length;
        int listSize = prmsBidList.size();
        if (size != 0) {
            String newbidCreatria = "";
            for (int i = 0; i < size; i++) {
                if (newbidCreatria.equals("")) {
                    newbidCreatria = selectedBidCriteria[i];
                } else {
                    newbidCreatria = newbidCreatria + "," + selectedBidCriteria[i];
                }
            }

            preminilaryEvalutionDt.setBidCriteria(newbidCreatria);
            if (size == listSize) {
                preminilaryEvalutionDt.setCriteriaResult("Pass");
            } else {
                preminilaryEvalutionDt.setCriteriaResult("Fail");
            }
            preminilaryEvalutionDt.setSupplierId(prmsSupplyProfile);
        }
        if (actionPlnDetlCheck.contains(preminilaryEvalutionDt.getSupplierId().getVendorName())) {
            JsfUtil.addFatalMessage("duplicate bidder code is not allowed !!");
            return null;
        } else if (actionPlnDetlCheck.contains(preminilaryEvalutionDt.getSupplierCode())) {
            JsfUtil.addFatalMessage("duplicate Supplier  is not allowed !!");
            return null;
        } else if (actionPlnDetlCheck.contains(preminilaryEvalutionDt.getSupplierId().getVendorName()) || actionPlnDetlCheck.contains(preminilaryEvalutionDt.getSupplierCode())) {
            JsfUtil.addFatalMessage("duplicate bidder  and supplier is not allowed !!");
            return null;

        } else {

            prmsPreminirayEval.add(preminilaryEvalutionDt);
            actionPlnDetlCheck.add(preminilaryEvalutionDt.getSupplierCode());
            actionPlnDetlCheck.add(preminilaryEvalutionDt.getSupplierId().getVendorName());
            preminilaryEvalutionDt.setPreminaryId(prmsPreminirayEval);
        }

        reCreatModel();

        clear();

        return null;
    }

    /*This method is used to generate auto number 
   
     */
    public String generatePrelmnryNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newPrelmryEvNo = prmsPreminirayEval.getPreliminarEvalNo();
        } else {
            PrmsPreminilaryEvaluation lastPrelmnryEvNum = premininaryBeanLocal.getPreliminarNo();
            if (lastPrelmnryEvNum != null) {
                prelinimaryEvNum = lastPrelmnryEvNum.getId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newMarketLast = 0;
            if (prelinimaryEvNum.equals("0")) {
                newMarketLast = 1;

                newPrelmryEvNo = "PE-NO-" + newMarketLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = prelinimaryEvNum.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newMarketLast = Integer.parseInt(lastDatesPaterns[0]);
                newMarketLast = newMarketLast + 1;
                newPrelmryEvNo = "PE-NO-" + newMarketLast + "/" + f.format(now);
            }
        }
        return newPrelmryEvNo;
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedValue = event.getNewValue().toString();
        }
    }
    /*This method is used to clear search
    
     */

    public void clearSearch() {
        prmsPreminirayEval = null;
        preminilaryEvalutionDt = null;
        preminilaryEvalutionDtMdl = null;

    }
    /*This method is used to exchange save and new icon
    
     */

    public void createPremilaryPlan() {
        saveorUpdateBundle = "Save";
        clearSearch();
        renderpnlToSearchPage = false;
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;

            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void goBackToSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }
    /*This method is used to clear page 
    
    
     */

    public void clearAll() {
        preminilaryEvalutionDt = null;
        prmsPreminirayEval = null;
        preminilaryEvalutionDtMdl = null;
        saveorUpdateBundle = "Save";
        prmsBid = null;
        wfPrmsProcessed = null;

    }
    /*This method is used to save 
    
    
     */

    public String save_preminaryEvaluation() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveForBidSubmission", dataset)) {

                try {
                    if (saveorUpdateBundle.equals("Save")) {
                        wfPrmsProcessed.setPreliminaryEvaluationId(prmsPreminirayEval);
                        prmsPreminirayEval.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        prmsPreminirayEval.setStatus(Constants.PREPARE_VALUE);
                        prmsPreminirayEval.setDateRigistered(wfPrmsProcessed.getProcessedOn());
                        wfPrmsProcessed.setDecision(wfPrmsProcessed.getDecision());
                        prmsPreminirayEval.setPreliminarEvalNo(newPrelmryEvNo);
                        premininaryBeanLocal.save(prmsPreminirayEval);
                        prmsPreminirayEval.getWfPrmsProcessedCollection().add(wfPrmsProcessed);
                        clearAll();
                        JsfUtil.addSuccessMessage("Data is Created");
                    } else if (saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
                        prmsPreminirayEval.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        premininaryBeanLocal.equals(prmsPreminirayEval);
                        premininaryBeanLocal.Edit(prmsPreminirayEval);
                        saveorUpdateBundle = "Save";
                        clearAll();
                        JsfUtil.addSuccessMessage("Data is Updated");
                    } else if (saveorUpdateBundle.equals("Update") && (workFlow.isApproveStatus() || workFlow.isCheckStatus())) {
                        if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            prmsPreminirayEval.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        }
                        if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            prmsPreminirayEval.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            prmsPreminirayEval.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            prmsPreminirayEval.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                        }
                        premininaryBeanLocal.Edit(prmsPreminirayEval);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        wfPrmsProcessed = null;
                        JsfUtil.addSuccessMessage("Data is Created");
                    }

                } catch (Exception e) {
                    clearAll();
                    e.printStackTrace();
                    JsfUtil.addErrorMessage("Data is Not saved");
                }
                return null;
            } else {
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
    /*This method is used to select row
    
     */

    public void edit(SelectEvent event) {
        setRowIndex(preminilaryEvalutionDtMdl.getRowIndex());
        editRowData = 1;
        preminilaryEvalutionDt = preminilaryEvalutionDtMdl.getRowData();

        prmsSupplyProfile = preminilaryEvalutionDt.getSupplierId();
        prmsBid = prmsPreminirayEval.getBidId();
        selectedBidCriteria = preminilaryEvalutionDt.getBidCriteria().split(",");

    }

        //</editor-fold>
}
