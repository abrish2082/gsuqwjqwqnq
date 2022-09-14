/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.ClaimReordingFormBeanLocal;
import et.gov.eep.prms.businessLogic.GoodsEntranceBeanLocal;
import et.gov.eep.prms.businessLogic.LetterOfCreditRegiBeanLocal;
import et.gov.eep.prms.businessLogic.SupplierPerformanceBeanLocal;
import et.gov.eep.prms.businessLogic.PurchaseOrderBeanLocal;
import et.gov.eep.prms.businessLogic.TechnicalEvaluationBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsClaimRecordingForm;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsSupplierPerformanceInfo;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import webService.AAA;
import webService.IAdministration;
import securityBean.SessionBean;
import webService.EventEntry;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBElement;
import securityBean.WorkFlow;
import securityBean.Constants;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import javax.annotation.PostConstruct;

/**
 *
 * @author user
 */
@Named(value = "supplierPerformanceContriller")
@ViewScoped
public class SupplierPerformanceContriller implements Serializable {

    @EJB
    private SupplierPerformanceBeanLocal supplierPerformanceBeanLocal;
    @EJB
    private PurchaseOrderBeanLocal purchaseOrderBeanLocal;
    @EJB
    private ClaimReordingFormBeanLocal claimReordingFormBeanLocal;
    @EJB
    private GoodsEntranceBeanLocal entranceBeanLocal;
    @EJB
    private LetterOfCreditRegiBeanLocal creditRegiBeanLocal;
    @EJB
    private TechnicalEvaluationBeanLocal technicalEvaluationBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    @Inject
    private PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo;
    @Inject
    private PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    private PrmsBid prmsBid;
    @Inject
    private PrmsContractAmendment prmsContractAmendment;
    @Inject
    PrmsLcRigistration prmsLcRigistration;
    @Inject
    private PrmsQuotation prmsQuotation;
    @Inject
    PrmsClaimRecordingForm prmsClaimRecordingForm;
    @Inject
    PrmsContract prmsCotract;
    @Inject
    PrmsGoodsEntrance prmsGoodsEntrance;
    @Inject
    private PrmsPurchaseOrder prmsPurchaseOrder;
    @Inject
    private WfPrmsProcessed wfPrmsProcessed;
    @Inject
    private WorkFlow workFlow;
    @Inject
    SessionBean sessionBean;
    @Inject
    PrmsBidAmend prmsBidAmend;

    int saveStatus = 0;
    private String saveorUpdateBundle = "save";
    private String icone = "ui-icon-plus";
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private String activeIndex;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean disableBtnCreate = false;
    private boolean renderForIntnalSupplier = false;
    private boolean renderGoods = false;
    private boolean renderBid = false;
    private boolean renderPerforma = false;
    private boolean renderForClaim = false;
    private boolean renderContract = false;
    private boolean renderPo = false;
    private boolean renderpnlToSearchPage;
    private DataModel<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfosmodel;
    private DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    Set<String> addressCheck = new HashSet<>();
    private String selectedValue = "";
    int requestNotificationCounter = 0;
    List<PrmsPurchaseOrder> puchaseOderLists;
    List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfos;
    List<PrmsContract> contractList;
    List<PrmsBid> bidNumberList;
    private List<PrmsContractAmendment> prmsContractAmendmentLst;
    List<PrmsBidAmend> bidListFromAmendment;
    List<PrmsSupplierPerformanceInfo> supplierPerfParameterLst;
    private PrmsSupplierPerformanceInfo selectSupplierPerforma;
    private boolean renderDecision = false;
    private String loggerName;
    private boolean isRendercreate;
    private boolean isRenderNotify;

    public SupplierPerformanceContriller() {
    }

    @PostConstruct
    public void initial() {
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        setLoggerName(workFlow.getUserName());
        if (workFlow.isPrepareStatus()) {
            renderDecision = false;
            isRendercreate = true;
            isRenderNotify = false;
        } else if (workFlow.isApproveStatus()) {
            renderDecision = true;
            isRendercreate = false;
            isRenderNotify = true;
        } else if (workFlow.isCheckStatus()) {
            renderDecision = true;
            isRendercreate = false;
            isRenderNotify = true;
        }

    }

    public void searchSupProformance() {
//        prmsSupplierPerformanceInfo.setPreparedBy(workFlow.getUserAccount());
        prmsSupplierPerformanceInfos = supplierPerformanceBeanLocal.searchCheckList(prmsSupplierPerformanceInfo);
        recreateprmsSupplierPerformanceInfosmodel();
        prmsSupplierPerformanceInfo = new PrmsSupplierPerformanceInfo();

    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        saveStatus = 1;

        int rowIndex = prmsSupplierPerformanceInfosmodel.getRowIndex();
        prmsSupplierPerformanceInfo = prmsSupplierPerformanceInfos.get(rowIndex);
        recreateprmsSupplierPerformanceInfosmodel();
    }

    public void recreateprmsSupplierPerformanceInfosmodel() {
        prmsSupplierPerformanceInfosmodel = null;
        prmsSupplierPerformanceInfosmodel = new ListDataModel(new ArrayList<>(getPrmsSupplierPerformanceInfos()));
    }

    public List<PrmsSupplierPerformanceInfo> getPrmsSupplierPerformanceInfos() {
        if (prmsSupplierPerformanceInfos == null) {
            prmsSupplierPerformanceInfos = new ArrayList<>();
        }
        return prmsSupplierPerformanceInfos;
    }

    public void setPrmsSupplierPerformanceInfos(List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfos) {
        this.prmsSupplierPerformanceInfos = prmsSupplierPerformanceInfos;
    }

    public PrmsLcRigistration getPrmsLcRigistration() {
        if (prmsLcRigistration == null) {
            prmsLcRigistration = new PrmsLcRigistration();
        }
        return prmsLcRigistration;
    }

    public void setPrmsLcRigistration(PrmsLcRigistration prmsLcRigistration) {
        this.prmsLcRigistration = prmsLcRigistration;
    }

    public boolean isRenderBid() {
        return renderBid;
    }

    public void setRenderBid(boolean renderBid) {
        this.renderBid = renderBid;
    }

    public boolean isRenderForIntnalSupplier() {
        return renderForIntnalSupplier;
    }

    public void setRenderForIntnalSupplier(boolean renderForIntnalSupplier) {
        this.renderForIntnalSupplier = renderForIntnalSupplier;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isRenderPerforma() {
        return renderPerforma;
    }

    public void setRenderPerforma(boolean renderPerforma) {
        this.renderPerforma = renderPerforma;
    }

    public PrmsQuotation getPrmsQuotation() {
        if (prmsQuotation == null) {
            prmsQuotation = new PrmsQuotation();
        }
        return prmsQuotation;
    }

    public void setPrmsQuotation(PrmsQuotation prmsQuotation) {
        this.prmsQuotation = prmsQuotation;
    }

    public PrmsGoodsEntrance getPrmsGoodsEntrance() {
        if (prmsGoodsEntrance == null) {
            prmsGoodsEntrance = new PrmsGoodsEntrance();
        }
        return prmsGoodsEntrance;
    }

    public List<PrmsContract> getContractList() {
        if (contractList == null) {
            contractList = new ArrayList<>();
        }
        return contractList;
    }

    public void setContractList(List<PrmsContract> contractList) {
        this.contractList = contractList;
    }

    public List<PrmsBid> getBidNumberList() {
        if (bidNumberList == null) {
            bidNumberList = new ArrayList<>();
            bidNumberList = purchaseOrderBeanLocal.bidNumberList();
        }
        return bidNumberList;
    }

    public void setBidNumberList(List<PrmsBid> bidNumberList) {
        this.bidNumberList = bidNumberList;
    }

    public void setPrmsGoodsEntrance(PrmsGoodsEntrance prmsGoodsEntrance) {
        this.prmsGoodsEntrance = prmsGoodsEntrance;
    }

    public PrmsContract getPrmsCotract() {
        if (prmsCotract == null) {
            prmsCotract = new PrmsContract();

        }
        return prmsCotract;
    }

    public void setPrmsCotract(PrmsContract prmsCotract) {
        this.prmsCotract = prmsCotract;
    }

    public PrmsClaimRecordingForm getPrmsClaimRecordingForm() {
        if (prmsClaimRecordingForm == null) {
            prmsClaimRecordingForm = new PrmsClaimRecordingForm();
        }

        return prmsClaimRecordingForm;
    }

    public void setPrmsClaimRecordingForm(PrmsClaimRecordingForm PrmsClaimRecordingForm) {
        this.prmsClaimRecordingForm = PrmsClaimRecordingForm;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getAddressInfoButton() {
        return addressInfoButton;
    }

    public void setAddressInfoButton(String addressInfoButton) {
        this.addressInfoButton = addressInfoButton;
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

    public boolean isRenderGoods() {
        return renderGoods;
    }

    public void setRenderGoods(boolean renderGoods) {
        this.renderGoods = renderGoods;
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

    public PrmsSupplierPerformanceInfo getSelectSupplierPerforma() {
        return selectSupplierPerforma;
    }

    public void setSelectSupplierPerforma(PrmsSupplierPerformanceInfo selectSupplierPerforma) {
        this.selectSupplierPerforma = selectSupplierPerforma;
    }

    public DataModel<PrmsSupplierPerformanceInfo> getPrmsSupplierPerformanceInfosmodel() {
        if (prmsSupplierPerformanceInfosmodel == null) {
            prmsSupplierPerformanceInfosmodel = new ListDataModel(new ArrayList<>(getPrmsSupplierPerformanceInfos()));
        }
        return prmsSupplierPerformanceInfosmodel;
    }

    public void setPrmsSupplierPerformanceInfosmodel(DataModel<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfosmodel) {
        this.prmsSupplierPerformanceInfosmodel = prmsSupplierPerformanceInfosmodel;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }

    public List<PrmsSupplierPerformanceInfo> getSupplierPerfParameterLst() {
        if (supplierPerfParameterLst == null) {
            supplierPerfParameterLst = new ArrayList<>();
            supplierPerfParameterLst = supplierPerformanceBeanLocal.getParamNameList();
        }
        return supplierPerfParameterLst;
    }

    public void setSupplierPerfParameterLst(List<PrmsSupplierPerformanceInfo> supplierPerfParameterLst) {
        this.supplierPerfParameterLst = supplierPerfParameterLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsSupplierPerformanceInfo.setColumnName(event.getNewValue().toString());
            prmsSupplierPerformanceInfo.setColumnValue(null);
        }
    }

    public void createNewParty() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            prmsSupplierPerformanceInfo.setSelectOpt(1);
            renderBid = true;
            renderContract = true;
            renderPo = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public PrmsPurchaseOrder getPrmsPurchaseOrder() {
        if (prmsPurchaseOrder == null) {
            prmsPurchaseOrder = new PrmsPurchaseOrder();
        }
        return prmsPurchaseOrder;
    }

    public void setPrmsPurchaseOrder(PrmsPurchaseOrder prmsPurchaseOrder) {
        this.prmsPurchaseOrder = prmsPurchaseOrder;
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

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public PrmsSupplierPerformanceInfo getPrmsSupplierPerformanceInfo() {
        if (prmsSupplierPerformanceInfo == null) {
            prmsSupplierPerformanceInfo = new PrmsSupplierPerformanceInfo();
        }
        return prmsSupplierPerformanceInfo;
    }

    public void setPrmsSupplierPerformanceInfo(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
        this.prmsSupplierPerformanceInfo = prmsSupplierPerformanceInfo;
    }

    public void saveSupplierInfo() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "SAVESUPPLIERPERFORMANCEINFO", dataset)) {
                if (saveStatus == 0) {
                    try {
                        setPrmsSupplierPerformanceInfo(prmsSupplierPerformanceInfo);
                        prmsSupplierPerformanceInfo.setSupplierPerfoNo(newcheckListNo);
                        if (prmsSupplierPerformanceInfo.getSelectOpt().equals("2")) {
                            prmsSupplierPerformanceInfo.setPoId(prmsPurchaseOrder);
                            prmsSupplierPerformanceInfo.setQuotationId(prmsQuotation);
                        } else {
                            prmsSupplierPerformanceInfo.setBidId(prmsBid);
                            prmsSupplierPerformanceInfo.setContractId(prmsCotract);
                            prmsSupplierPerformanceInfo.setLcId(prmsLcRigistration);
                            prmsSupplierPerformanceInfo.setClaimId(prmsClaimRecordingForm);
                            prmsSupplierPerformanceInfo.setGoodsId(prmsGoodsEntrance);
                            wfPrmsProcessed.setSuppPerformanceId(prmsSupplierPerformanceInfo);
                        }
                        prmsSupplierPerformanceInfo.getPrmsWorkFlowProccedList().add(wfPrmsProcessed);
                        prmsSupplierPerformanceInfo.setStatus(Constants.PREPARE_VALUE);
                        prmsSupplierPerformanceInfo.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                        wfPrmsProcessed.setDecision(String.valueOf(prmsSupplierPerformanceInfo.getStatus()));
                        supplierPerformanceBeanLocal.create(prmsSupplierPerformanceInfo);
                        JsfUtil.addSuccessMessage("Supplier Performance Information is successfully registered!!");
                        clearSupplierPerformance();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);

                    }
                } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    try {
                        if (prmsSupplierPerformanceInfo.getSelectOpt().equals("2")) {
                            prmsSupplierPerformanceInfo.setPoId(prmsPurchaseOrder);
                            prmsSupplierPerformanceInfo.setQuotationId(prmsQuotation);
                        } else {
                            prmsSupplierPerformanceInfo.setBidId(prmsBid);
                            prmsSupplierPerformanceInfo.setContractId(prmsCotract);
                            prmsSupplierPerformanceInfo.setLcId(prmsLcRigistration);
                            prmsSupplierPerformanceInfo.setClaimId(prmsClaimRecordingForm);
                            prmsSupplierPerformanceInfo.setGoodsId(prmsGoodsEntrance);
                            wfPrmsProcessed.setSuppPerformanceId(prmsSupplierPerformanceInfo);
                        }
                        prmsSupplierPerformanceInfo.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                        supplierPerformanceBeanLocal.update(prmsSupplierPerformanceInfo);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Supplier Performance Information is successfully updated!!");
                        saveorUpdateBundle = "Save";
                        clearSupplierPerformance();
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                    }

                } else if (workFlow.isApproveStatus() || (workFlow.isCheckStatus()) && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        prmsSupplierPerformanceInfo.setStatus(Constants.APPROVE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        prmsSupplierPerformanceInfo.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        prmsSupplierPerformanceInfo.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        prmsSupplierPerformanceInfo.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                    }
                    wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                    supplierPerformanceBeanLocal.update(prmsSupplierPerformanceInfo);
                    JsfUtil.addSuccessMessage("Supplier Performance Information is successfully updated!!");
                    clearSupplierPerformance();
                    saveorUpdateBundle = "Save";
                    clearSupplierPerformance();
                }

            } else {
                JsfUtil.addInformationMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
        }
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

    private String clearSupplierPerformance() {
        prmsSupplierPerformanceInfo = null;
        saveorUpdateBundle = "Save";
        saveStatus = 0;
        prmsBid = null;
        prmsQuotation = null;
        prmsClaimRecordingForm = null;
        prmsGoodsEntrance = null;
        prmsLcRigistration = null;
        prmsCotract = null;
        contractList.clear();
        prmsPurchaseOrder = null;

        return null;

    }

    public String getNewcheckListNo() {
        return newcheckListNo;
    }

    public void setNewcheckListNo(String newcheckListNo) {
        this.newcheckListNo = newcheckListNo;
    }

    public String getLastcheckListNo() {
        return LastcheckListNo;
    }

    public void setLastcheckListNo(String LastcheckListNo) {
        this.LastcheckListNo = LastcheckListNo;
    }

    public ArrayList<PrmsSupplierPerformanceInfo> searchRequestForPayment(String reqPaymentNo) {
        ArrayList<PrmsSupplierPerformanceInfo> serviceProvide = null;
        prmsSupplierPerformanceInfo.setSuppInfoId(reqPaymentNo);
        serviceProvide = supplierPerformanceBeanLocal.searchRequestForPayment(prmsSupplierPerformanceInfo);
        return serviceProvide;
    }

    public void getRequestNo(SelectEvent event) {

        String requestNo = event.getObject().toString();
        prmsSupplierPerformanceInfo.setSuppInfoId(requestNo);
        prmsSupplierPerformanceInfo = supplierPerformanceBeanLocal.getRequestNo(prmsSupplierPerformanceInfo);
        saveStatus = 1;
        saveorUpdateBundle = "Update";

    }

    public SelectItem[] getListOfVendorName() {
        return JsfUtil.getSelectItems(purchaseOrderBeanLocal.VendorName(), true);
    }

    public SelectItem[] getpurchaseOrderList() {
        return JsfUtil.getSelectItems(supplierPerformanceBeanLocal.purchaseOrderList(), true);

    }

//    public SelectItem[] bidNumberList() {
//        return JsfUtil.getSelectItems(purchaseOrderBeanLocal.bidNumberList(), true);
//
//    }
    public SelectItem[] getQuotationNo() {
        return JsfUtil.getSelectItems(technicalEvaluationBeanLocal.getQuotationNo(), true);
    }

    public void rowSelect(SelectEvent event) {
        prmsSupplierPerformanceInfo = (PrmsSupplierPerformanceInfo) event.getObject();
        populateDataForApp();
    }

    String newcheckListNo;
    String LastcheckListNo = "0";

    public String generateContractNo() {

        PrmsSupplierPerformanceInfo LastSupplierPerfomance = supplierPerformanceBeanLocal.getLastPaymentNo();
        if (LastSupplierPerfomance != null) {
            LastcheckListNo = LastSupplierPerfomance.getSuppInfoId();
        }
        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();
        int newcheckListN = 0;

        if (LastcheckListNo.equals("0")) {
            newcheckListN = 1;
            newcheckListNo = "PerformanceNo-" + newcheckListN + "/" + f.format(now);
        } else {
            String[] lastInspNos = LastcheckListNo.split("-");
            String lastDatesPatern = lastInspNos[0];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            newcheckListN = Integer.parseInt(lastDatesPaterns[0]);
            newcheckListN = newcheckListN + 1;
            newcheckListNo = "PerformanceNo-" + newcheckListN + "/" + f.format(now);
        }

        return newcheckListNo;
    }

    public String generateSpecficationNo() {
        PrmsSupplierPerformanceInfo LastSupplierPerfomance = supplierPerformanceBeanLocal.getLastPerformanceId();
        if (LastSupplierPerfomance != null) {
            LastcheckListNo = LastSupplierPerfomance.getSuppInfoId();
        }
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        int newBidNoLast = 0;
        if (LastcheckListNo.equals("0")) {
            newBidNoLast = 1;
            newcheckListNo = "PerformanceNo-" + newBidNoLast + "/" + eYear;
        } else {
            String[] lastInspNos = LastcheckListNo.split("-");
            String lastDatesPatern = lastInspNos[0];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
            newBidNoLast = newBidNoLast + 1;
            newcheckListNo = "PerformanceNo-" + newBidNoLast + "/" + eYear;
        }
        return newcheckListNo;

    }

    public void changeTransferType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            if (e.getNewValue().toString().equalsIgnoreCase("1")) {
                setRenderBid(true);
                setRenderPerforma(false);
                prmsSupplierPerformanceInfo.setSelectOpt(1);
                renderContract = true;
                renderPo = false;

            } else {
                setRenderBid(false);
                setRenderPerforma(true);
                renderContract = false;
                prmsSupplierPerformanceInfo.setSelectOpt(2);
                renderPo = true;
            }
        }
    }

    public List<PrmsPurchaseOrder> getPuchaseOderLists() {
        if (puchaseOderLists == null) {
            puchaseOderLists = new ArrayList<>();
            puchaseOderLists = supplierPerformanceBeanLocal.getPoNos(prmsQuotation);
        }
        return puchaseOderLists;
    }

    public void setPuchaseOderLists(List<PrmsPurchaseOrder> puchaseOderLists) {
        this.puchaseOderLists = puchaseOderLists;
    }

    String purchaseType;
    String purchaseType2;

    public void getPurchasetype(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            prmsBid = (PrmsBid) event.getNewValue();
            prmsSupplierPerformanceInfo.setBidId(prmsBid);
            prmsSupplierPerformanceInfo.setBidNo(prmsBid.getRefNo());
            purchaseType = prmsBid.getBidCategory();
            prmsSupplierPerformanceInfo.setMarketApproch(prmsBid.getBidType());
            prmsBidAmend.setBidId(prmsBid);
            bidListFromAmendment = supplierPerformanceBeanLocal.checkBidFromAmended(prmsBid);
            if (bidListFromAmendment.size() > 0) {
                prmsBidAmend = supplierPerformanceBeanLocal.getBidAmendInfoByBidId(prmsBidAmend);
                prmsSupplierPerformanceInfo.setBidNo(prmsBid.getRefNo());
                purchaseType = prmsBid.getBidCategory();
                prmsSupplierPerformanceInfo.setMarketApproch(prmsBid.getBidType());
            }

            prmsSupplierPerformanceInfo.setType(purchaseType);
            contractList = supplierPerformanceBeanLocal.getContratList2(prmsBid);

            if (prmsBid.getBidType().equals("international")) {
                renderForIntnalSupplier = true;
                renderGoods = true;
                renderForClaim = true;
            } else if (prmsBid.getBidType().equals("local")) {
                renderForIntnalSupplier = false;
                renderGoods = false;
                renderForClaim = false;

            }

        }
    }

    List<PrmsQuotationDetail> prmsQuotationDetailList;

    public void getValueofQuotationNo(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            prmsQuotation = (PrmsQuotation) event.getNewValue();
            prmsSupplierPerformanceInfo.setQuotationId(prmsQuotation);
            prmsSupplierPerformanceInfo.setQuotationNo(prmsQuotation.getQuotationNo());
            puchaseOderLists = supplierPerformanceBeanLocal.getPoNos(prmsQuotation);
            String QuotationNo = event.getNewValue().toString();
            prmsQuotationDetailList = supplierPerformanceBeanLocal.getQuotationList(QuotationNo);
            prmsSupplierPerformanceInfo.setQuotationId(prmsQuotationDetailList.get(0).getQuotationId());
            purchaseType2 = prmsQuotationDetailList.get(0).getQuotationId().getPurchaseType();
            prmsSupplierPerformanceInfo.setType2(purchaseType2);
        }
    }

    public void handleContract(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            prmsCotract = (PrmsContract) event.getNewValue();
            prmsCotract = supplierPerformanceBeanLocal.getOthersByContractId(prmsCotract);
            puchaseOderLists = supplierPerformanceBeanLocal.getPoNumberList(prmsCotract);
            prmsSupplierPerformanceInfo.setContractId(prmsCotract);
            prmsContractAmendmentLst = supplierPerformanceBeanLocal.checkAmendOrNot(prmsCotract);
            if (prmsContractAmendmentLst.size() > 0) {
                prmsContractAmendment = supplierPerformanceBeanLocal.getContractAmendment(prmsCotract);
                prmsCotract.setContractId(prmsContractAmendment.getContractId().getContractId());
                prmsCotract.setContractenddateam(prmsContractAmendment.getContractenddateam());
                prmsCotract.setSuppId(prmsContractAmendment.getSuppId());
                prmsCotract.setContractamount(prmsContractAmendment.getContractamount());
            }

            contractAmount();

        }
    }

    public void handleSelectPo(ValueChangeEvent event) {
        if (null != event.getNewValue().toString()) {
            prmsPurchaseOrder = (PrmsPurchaseOrder) event.getNewValue();
            prmsSupplierPerformanceInfo.setPoId(prmsPurchaseOrder);
        }
    }

    List<PrmsClaimRecordingForm> ClaimNoList = new ArrayList<>();

    public List<PrmsClaimRecordingForm> getClaimNoList() {
        ClaimNoList = claimReordingFormBeanLocal.findAllClaimInfo();
        return ClaimNoList;
    }

    List<PrmsGoodsEntrance> GoodsNoList = new ArrayList<>();

    public List<PrmsGoodsEntrance> getGoodsNoList() {
        GoodsNoList = entranceBeanLocal.findAllGoodsInfo();
        return GoodsNoList;
    }

    List<PrmsLcRigistration> LcNoList = new ArrayList<>();

    public List<PrmsLcRigistration> getLcNoList() {
        LcNoList = creditRegiBeanLocal.findAllLcInfo();
        return LcNoList;
    }

    public void handleSelectClaim(ValueChangeEvent event) {

        prmsClaimRecordingForm = (PrmsClaimRecordingForm) event.getNewValue();
        prmsSupplierPerformanceInfo.setClaimId(prmsClaimRecordingForm);
        prmsSupplierPerformanceInfo.setClaimDescription(prmsClaimRecordingForm.getClaimDescription());
        prmsSupplierPerformanceInfo.setUndeliveredBalance(prmsClaimRecordingForm.getUndeliveredBalance());
    }

    public void handleSelectGoods(ValueChangeEvent event) {

        prmsGoodsEntrance = (PrmsGoodsEntrance) event.getNewValue();
        prmsSupplierPerformanceInfo.setGeNo(event.getNewValue().toString());
        prmsSupplierPerformanceInfo.setGeDescription(prmsGoodsEntrance.getDescription());
        prmsSupplierPerformanceInfo.setGoodsAmount(prmsGoodsEntrance.getGoodsAmount());

    }

    public void handleSelectLc(ValueChangeEvent event) {
        prmsLcRigistration = (PrmsLcRigistration) event.getNewValue();
        prmsSupplierPerformanceInfo.setLcId(prmsLcRigistration);
    }

    /**
     * @return the wfPrmsProcessed
     */
    public WfPrmsProcessed getWfPrmsProcessed() {
        return wfPrmsProcessed;
    }

    /**
     * @param wfPrmsProcessed the wfPrmsProcessed to set
     */
    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    /**
     * @return the wfPrmsProcessedDModel
     */
    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ArrayDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }

    /**
     * @param wfPrmsProcessedDModel the wfPrmsProcessedDModel to set
     */
    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();

        }
    }

    /**
     * @return the selectedValue
     */
    public String getSelectedValue() {
        return selectedValue;
    }

    /**
     * @param selectedValue the selectedValue to set
     */
    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    /**
     * @return the workFlow
     */
    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    /**
     * @param workFlow the workFlow to set
     */
    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public int getRequestNotificationCounter() {
        prmsSupplierPerformanceInfos = supplierPerformanceBeanLocal.getPerformanceLists();
        requestNotificationCounter = prmsSupplierPerformanceInfos.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public void RequestListChange(ValueChangeEvent eve) {
        prmsSupplierPerformanceInfo = (PrmsSupplierPerformanceInfo) eve.getNewValue();
        populateDataForApp();

    }

    public void populateDataForApp() {
        prmsSupplierPerformanceInfo.setGoodsAmount(prmsGoodsEntrance.getGoodsAmount());
        prmsSupplierPerformanceInfo.setProcessedOn(wfPrmsProcessed.getProcessedOn());
        prmsSupplierPerformanceInfo.setSuppInfoId(prmsSupplierPerformanceInfo.getSuppInfoId());
        prmsSupplierPerformanceInfo.setLcNo(prmsLcRigistration.getLcNo());
        prmsSupplierPerformanceInfo.setNoofLcExtention(prmsLcRigistration.getNoOfLcExtention());
        prmsSupplierPerformanceInfo = supplierPerformanceBeanLocal.getSelectedRequest(prmsSupplierPerformanceInfo.getSuppInfoId());
        renderpnlToSearchPage = true;
        prmsBid = prmsSupplierPerformanceInfo.getBidId();
        prmsCotract = prmsSupplierPerformanceInfo.getContractId();
        contractList = new ArrayList<>();
        contractList.add(prmsCotract);
        prmsGoodsEntrance.setRegistrationNo(prmsSupplierPerformanceInfo.getGeNo());
        prmsClaimRecordingForm = prmsSupplierPerformanceInfo.getClaimId();
        prmsLcRigistration = prmsSupplierPerformanceInfo.getLcId();
        prmsPurchaseOrder = prmsSupplierPerformanceInfo.getPoId();
        puchaseOderLists = new ArrayList<>();
        puchaseOderLists.add(prmsPurchaseOrder);
        prmsQuotation = prmsSupplierPerformanceInfo.getQuotationId();
        saveStatus = 1;
        recreateprmsSupplierPerformanceInfosmodel();
        if (prmsSupplierPerformanceInfo.getSelectOpt() == 1) {
            setRenderBid(true);
            setRenderPerforma(false);
            if (prmsBid.getBidType().equals("international")) {

                renderForIntnalSupplier = true;
                renderGoods = true;
                renderForClaim = true;

            } else if (prmsBid.getBidType().equals("local")) {

                renderForIntnalSupplier = false;
                renderGoods = false;
                renderForClaim = false;
            }

        } else {

            setRenderBid(false);
            setRenderPerforma(true);
        }
        if (workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(prmsSupplierPerformanceInfo.getProcessedOn());
        }
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
    }

    /**
     * @return the loggerName
     */
    public String getLoggerName() {
        return loggerName;
    }

    /**
     * @param loggerName the loggerName to set
     */
    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    /**
     * @return the renderDecision
     */
    public boolean isRenderDecision() {
        return renderDecision;
    }

    /**
     * @param renderDecision the renderDecision to set
     */
    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    /**
     * @return the isRendercreate
     */
    public boolean isIsRendercreate() {
        return isRendercreate;
    }

    /**
     * @param isRendercreate the isRendercreate to set
     */
    public void setIsRendercreate(boolean isRendercreate) {
        this.isRendercreate = isRendercreate;
    }

    public boolean isIsRenderNotify() {
        return isRenderNotify;
    }

    public void setIsRenderNotify(boolean isRenderNotify) {
        this.isRenderNotify = isRenderNotify;
    }

    /**
     * @return the renderForClaim
     */
    public boolean isRenderForClaim() {
        return renderForClaim;
    }

    /**
     * @param renderForClaim the renderForClaim to set
     */
    public void setRenderForClaim(boolean renderForClaim) {
        this.renderForClaim = renderForClaim;
    }

    /**
     * @return the renderContract
     */
    public boolean isRenderContract() {
        return renderContract;
    }

    /**
     * @param renderContract the renderContract to set
     */
    public void setRenderContract(boolean renderContract) {
        this.renderContract = renderContract;
    }

    /**
     * @return the renderPo
     */
    public boolean isRenderPo() {
        return renderPo;
    }

    /**
     * @param renderPo the renderPo to set
     */
    public void setRenderPo(boolean renderPo) {
        this.renderPo = renderPo;
    }

    /**
     * @return the prmsContractAmendmentLst
     */
    public List<PrmsContractAmendment> getPrmsContractAmendmentLst() {
        if (prmsContractAmendmentLst == null) {
            prmsContractAmendmentLst = new ArrayList<>();
        }
        return prmsContractAmendmentLst;
    }

    /**
     * @param prmsContractAmendmentLst the prmsContractAmendmentLst to set
     */
    public void setPrmsContractAmendmentLst(List<PrmsContractAmendment> prmsContractAmendmentLst) {
        this.prmsContractAmendmentLst = prmsContractAmendmentLst;
    }

    /**
     * @return the prmsContractAmendment
     */
    public PrmsContractAmendment getPrmsContractAmendment() {
        if (prmsContractAmendment == null) {
            prmsContractAmendment = new PrmsContractAmendment();
        }
        return prmsContractAmendment;
    }

    /**
     * @param prmsContractAmendment the prmsContractAmendment to set
     */
    public void setPrmsContractAmendment(PrmsContractAmendment prmsContractAmendment) {
        this.prmsContractAmendment = prmsContractAmendment;
    }

    public List<PrmsBidAmend> getBidListFromAmendment() {
        if (bidListFromAmendment == null) {
            bidListFromAmendment = new ArrayList<>();
        }
        return bidListFromAmendment;
    }

    public void setBidListFromAmendment(List<PrmsBidAmend> bidListFromAmendment) {
        this.bidListFromAmendment = bidListFromAmendment;
    }

    public PrmsBidAmend getPrmsBidAmend() {
        if (prmsBidAmend == null) {
            prmsBidAmend = new PrmsBidAmend();
        }
        return prmsBidAmend;
    }

    public void setPrmsBidAmend(PrmsBidAmend prmsBidAmend) {
        this.prmsBidAmend = prmsBidAmend;
    }

//    int noOfCurrencies = prmsCotract;
    public void contractAmount() {

        String contractAmount = "";
        for (int i = 0; i < prmsCotract.getPrmsContractCurrencyDetailList().size(); i++) {
            if (prmsCotract.getPrmsContractCurrencyDetailList().size() > 0) {
                contractAmount = contractAmount + prmsCotract.getPrmsContractCurrencyDetailList().get(i).getAmount() + " " + prmsCotract.getPrmsContractCurrencyDetailList().get(i).getCurrencyId().getCurrency() + ",";
            }

        }
        prmsSupplierPerformanceInfo.setContractAmount(contractAmount);

    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

}
