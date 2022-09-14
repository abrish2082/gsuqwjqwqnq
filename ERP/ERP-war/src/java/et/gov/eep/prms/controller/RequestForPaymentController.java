package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.bank.FmsBank;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.prms.businessLogic.RequestforPaymentBeanLocal;
import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.businessLogic.MmsGRNBEanLocal;
import et.gov.eep.prms.businessLogic.ContractInformationBeanLocal;
import et.gov.eep.prms.businessLogic.ImportShippingInstructBeanLocal;
import et.gov.eep.prms.businessLogic.PurchaseReqBeanLocal;
import et.gov.eep.prms.businessLogic.SupplierPerformanceBeanLocal;
import et.gov.eep.commonApplications.utility.Dictionary;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import webService.AAA;
import webService.IAdministration;
import webService.EventEntry;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.context.RequestContext;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;

@Named(value = "requestForPaymentController")
@ViewScoped
public class RequestForPaymentController implements Serializable {

    private String saveorUpdateBundle = "Save";
    int saveStatus = 0;
    private String icone = "ui-icon-plus";
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private String checkAsBidPayment = "bid";
    private boolean renderBid = true;
    private boolean renderProforma = false;
    private String checkAsByContract = "contract";
    private boolean renderContract = true;
    private boolean renderPO;
    private String activeIndex;
    private String paymentType = "Bank Transfer";
    private String userName;
    boolean renderWhenBank = false;
    boolean disableWhenUpdating = false;
    boolean contNoList = true;
    boolean poNolist;
    boolean contNoTxt;
    boolean poNoTxt;
    private boolean renderProjectNames = true;
    private boolean whenInternationalBid = false;
    boolean hideGrn = false;
    boolean renderDecision;
    private boolean isRenderCreate = false;
    private boolean renderpnlToSearchPage;
    String selectedValue = "";
    @EJB
    private PurchaseReqBeanLocal purchaseReqBeanLocal;
    @EJB
    private MmsGRNBEanLocal mmsGRNBEanLocal;
    @EJB
    private ContractInformationBeanLocal contractInformationBeanLocal;
    @EJB
    private RequestforPaymentBeanLocal requestforPaymentBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    private HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    private ImportShippingInstructBeanLocal importShippingInstructBeanLocal;
    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;
    @Inject
    private PrmsPaymentRequisition prmsPaymentRequisition;
    @Inject
    private PrmsPurchaseRequest prmsPurchaseRequest;
    @Inject
    private PrmsContract prmsContract;
    @Inject
    PrmsContractCurrencyDetail prmsContractCurrencyDetail;
    @Inject
    private PrmsContractAmendment prmsContractAmendment;
    @Inject
    private PrmsPurchaseOrder prmsPurchaseOrder;
    @Inject
    private HrDepartments hrDepartments;
    private TreeNode selected;
    private TreeNode parent;
    private TreeNode childs;
    @Inject
    private FmsBank fmsBank;
    @Inject
    private MmsGrn mmsGrn;
    @Inject
    private PrmsBid prmsBid;
    @Inject
    PrmsBidAmend prmsBidAmend;
    @Inject
    PrmsBidDetail prmsBidDetail;
    @Inject
    private PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    PmsCreateProjects pmsCreateProjects;
    @Inject
    PrmsQuotation prmsQuotation;
    @Inject
    MmsStoreInformation mmsStoreInformation;
    @Inject
    MmsInspection mmsInspection;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    Dictionary dictionary;
    @Inject
    WorkFlow workflow;
    @Inject
    SessionBean sessionBean;
    private DataModel<PrmsPaymentRequisition> prmsPaymentRequisitions;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    Set<String> addressCheck = new HashSet<>();
    List<PrmsPaymentRequisition> prmsPaymentRequisitions1;
    List<PrmsPaymentRequisition> requestForPaymentSearchParameterLst;
    List<HrDepartments> allDepartmentsList;
    List<PrmsQuotation> ProformaLists;
    List<HrDepartments> araListe;
    List<PrmsBid> bidNumbersShow;
    List<PrmsBid> bidContractedList;
    List<PrmsContract> contractList;
    List<PrmsPurchaseOrder> purchaseOrderList;
    List<PrmsBidDetail> PRNumberList;
    List<PrmsPaymentRequisition> requestList;
    List<PrmsBidAmend> bidListFromAmendment;
    List<PrmsContractAmendment> contractListFromAmendment;
    List<PrmsContractCurrencyDetail> prmsContractCurrencyDetailList;
    List<String> amountLists;
//    List<MmsStoreInformation> storeNameLists;
    List<MmsGrn> populategrnNoLists;
//    List<MmsGrn> populatePoNoLists;
    private PrmsPaymentRequisition selectPayment;
    Integer requestNotificationCounter = 0;
    int approvedStatus = 3;

    public RequestForPaymentController() {
    }

    public PrmsContract getPrmsContract() {
        if (prmsContract == null) {
            prmsContract = new PrmsContract();
        }
        return prmsContract;
    }

    public void setPrmsContract(PrmsContract prmsContract) {
        this.prmsContract = prmsContract;
    }

    public PrmsContractCurrencyDetail getPrmsContractCurrencyDetail() {
        if (prmsContractCurrencyDetail == null) {
            prmsContractCurrencyDetail = new PrmsContractCurrencyDetail();
        }
        return prmsContractCurrencyDetail;
    }

    public void setPrmsContractCurrencyDetail(PrmsContractCurrencyDetail prmsContractCurrencyDetail) {
        this.prmsContractCurrencyDetail = prmsContractCurrencyDetail;
    }

    public PrmsContractAmendment getPrmsContractAmendment() {
        if (prmsContractAmendment == null) {
            prmsContractAmendment = new PrmsContractAmendment();
        }
        return prmsContractAmendment;
    }

    public void setPrmsContractAmendment(PrmsContractAmendment prmsContractAmendment) {
        this.prmsContractAmendment = prmsContractAmendment;
    }

    public PrmsPaymentRequisition getSelectPayment() {
        return selectPayment;
    }

    public MmsInspection getMmsInspection() {
        return mmsInspection;
    }

    public void setMmsInspection(MmsInspection mmsInspection) {
        this.mmsInspection = mmsInspection;
    }

    public void setSelectPayment(PrmsPaymentRequisition selectPayment) {
        this.selectPayment = selectPayment;
    }

    public PrmsPurchaseRequest getPrmsPurchaseRequest() {
        if (prmsPurchaseRequest == null) {
            prmsPurchaseRequest = new PrmsPurchaseRequest();
        }
        return prmsPurchaseRequest;
    }

    public void setPrmsPurchaseRequest(PrmsPurchaseRequest prmsPurchaseRequest) {
        this.prmsPurchaseRequest = prmsPurchaseRequest;
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

    public void recreateprmsPaymentRequisitions() {
        prmsPaymentRequisitions = null;
        prmsPaymentRequisitions = new ListDataModel(new ArrayList<>(getPrmsPaymentRequisitions1()));
    }

    public void recreateWorkFlow() {
        wfPrmsProcessedDModel = null;
        wfPrmsProcessedDModel = new ListDataModel(prmsPaymentRequisition.getWfPrmsProcessedLists());
    }

    public DataModel<PrmsPaymentRequisition> getPrmsPaymentRequisitions() {
        if (prmsPaymentRequisitions == null) {
            prmsPaymentRequisitions = new ListDataModel(new ArrayList<>(getRequestForPaymentSearchParameterLst()));
        }
        return prmsPaymentRequisitions;
    }

    public void setPrmsPaymentRequisitions(DataModel<PrmsPaymentRequisition> prmsPaymentRequisitions) {
        this.prmsPaymentRequisitions = prmsPaymentRequisitions;
    }

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {

        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitions1() {
        if (prmsPaymentRequisitions1 == null) {
            prmsPaymentRequisitions1 = new ArrayList<>();
        }
        return prmsPaymentRequisitions1;
    }

    public void setPrmsPaymentRequisitions1(List<PrmsPaymentRequisition> prmsPaymentRequisitions1) {
        this.prmsPaymentRequisitions1 = prmsPaymentRequisitions1;
    }

    public List<PrmsPaymentRequisition> getRequestForPaymentSearchParameterLst() {
        if (requestForPaymentSearchParameterLst == null) {
            requestForPaymentSearchParameterLst = new ArrayList<>();
            requestForPaymentSearchParameterLst = requestforPaymentBeanLocal.getColumnNameList();
        }
        return requestForPaymentSearchParameterLst;
    }

    public void setRequestForPaymentSearchParameterLst(List<PrmsPaymentRequisition> requestForPaymentSearchParameterLst) {
        this.requestForPaymentSearchParameterLst = requestForPaymentSearchParameterLst;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
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

        int rowIndex = prmsPaymentRequisitions.getRowIndex();
        prmsPaymentRequisition = prmsPaymentRequisitions1.get(rowIndex);
//        hrEmployees = prmsBidOfferSubmission.getBidId().getRefNo();
//        //party.setComContactPersonList(party.getComContactPersonList());
//        addressInfo = party.getAddressInfoId();
//       

        recreateprmsPaymentRequisitions();

    }

    public void createNewParty() {
        clearPaymentRequest();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
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

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
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

    public boolean isContNoList() {
        return contNoList;
    }

    public void setContNoList(boolean contNoList) {
        this.contNoList = contNoList;
    }

    public boolean isPoNolist() {
        return poNolist;
    }

    public void setPoNolist(boolean poNolist) {
        this.poNolist = poNolist;
    }

    public boolean isContNoTxt() {
        return contNoTxt;
    }

    public void setContNoTxt(boolean contNoTxt) {
        this.contNoTxt = contNoTxt;
    }

    public boolean isPoNoTxt() {
        return poNoTxt;
    }

    public void setPoNoTxt(boolean poNoTxt) {
        this.poNoTxt = poNoTxt;
    }

    public boolean isRenderProjectNames() {
        return renderProjectNames;
    }

    public void setRenderProjectNames(boolean renderProjectNames) {
        this.renderProjectNames = renderProjectNames;
    }

    public boolean isWhenInternationalBid() {
        return whenInternationalBid;
    }

    public void setWhenInternationalBid(boolean whenInternationalBid) {
        this.whenInternationalBid = whenInternationalBid;
    }

    public boolean isHideGrn() {
        return hideGrn;
    }

    public void setHideGrn(boolean hideGrn) {
        this.hideGrn = hideGrn;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public PmsCreateProjects getPmsCreateProjects() {
        if (pmsCreateProjects == null) {
            pmsCreateProjects = new PmsCreateProjects();
        }
        return pmsCreateProjects;
    }

    public void setPmsCreateProjects(PmsCreateProjects pmsCreateProjects) {
        this.pmsCreateProjects = pmsCreateProjects;
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

    public MmsStoreInformation getMmsStoreInformation() {
        if (mmsStoreInformation == null) {
            mmsStoreInformation = new MmsStoreInformation();
        }
        return mmsStoreInformation;
    }

    public void setMmsStoreInformation(MmsStoreInformation mmsStoreInformation) {
        this.mmsStoreInformation = mmsStoreInformation;
    }

    public MmsGrn getMmsGrn() {
        if (mmsGrn == null) {
            mmsGrn = new MmsGrn();
        }
        return mmsGrn;
    }

    public void setMmsGrn(MmsGrn mmsGrn) {
        this.mmsGrn = mmsGrn;
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

    public PrmsBidAmend getPrmsBidAmend() {
        if (prmsBidAmend == null) {
            prmsBidAmend = new PrmsBidAmend();
        }
        return prmsBidAmend;
    }

    public void setPrmsBidAmend(PrmsBidAmend prmsBidAmend) {
        this.prmsBidAmend = prmsBidAmend;
    }

    public PrmsBidDetail getPrmsBidDetail() {
        if (prmsBidDetail == null) {
            prmsBidDetail = new PrmsBidDetail();
        }
        return prmsBidDetail;
    }

    public void setPrmsBidDetail(PrmsBidDetail prmsBidDetail) {
        this.prmsBidDetail = prmsBidDetail;
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

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public FmsBank getFmsBank() {
        if (fmsBank == null) {
            fmsBank = new FmsBank();
        }
        return fmsBank;
    }

    public void setFmsBank(FmsBank fmsBank) {
        this.fmsBank = fmsBank;
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

    public PrmsPaymentRequisition getPrmsPaymentRequisition() {
        if (prmsPaymentRequisition == null) {
            prmsPaymentRequisition = new PrmsPaymentRequisition();
        }
        return prmsPaymentRequisition;
    }

    public void setPrmsPaymentRequisition(PrmsPaymentRequisition prmsPaymentRequisition) {
        this.prmsPaymentRequisition = prmsPaymentRequisition;
    }

    public Dictionary getDictionary() {
        if (dictionary == null) {
            dictionary = new Dictionary();
        }
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public ArrayList<PrmsPaymentRequisition> searchRequestForPayment(String reqPaymentNo) {
        ArrayList<PrmsPaymentRequisition> serviceProvide = null;
        prmsPaymentRequisition.setReqPaymentNo(reqPaymentNo);
        serviceProvide = requestforPaymentBeanLocal.searchRequestForPayment(prmsPaymentRequisition);
        return serviceProvide;
    }

    public void searchPaymentRequestInfo() {
        prmsPaymentRequisition.setPreparedBy(workflow.getUserAccount());
        prmsPaymentRequisitions1 = requestforPaymentBeanLocal.searchCheckList(prmsPaymentRequisition);

        recreateprmsPaymentRequisitions();

    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("decision is selected");
            selectedValue = event.getNewValue().toString();

        }
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsPaymentRequisition.setColumnName(event.getNewValue().toString());
            prmsPaymentRequisition.setColumnValue(null);
        }
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public String savePaymentRequestInfo() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "savePaymentRequestInfo", dataset)) {
                if (saveStatus == 0) {
                    try {
                        generateSpecficationNo();
                        prmsPaymentRequisition.setReqPaymentNo(newcheckListNo);
                        prmsPaymentRequisition.setPurchaseType(checkAsBidPayment);
                        if (prmsPaymentRequisition.getPaymentType() != null && prmsPaymentRequisition.getPaymentType().equals(paymentType)) {
                            prmsPaymentRequisition.setBankId(fmsBank);
                        }
                        prmsPaymentRequisition.setDepId(hrDepartments);
                        prmsPaymentRequisition.setRemainingAmount(remainingAmount);

                        prmsPaymentRequisition.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        prmsPaymentRequisition.setPreparedDate(wfPrmsProcessed.getProcessedOn());
                        prmsPaymentRequisition.setStatus(Constants.PREPARE_VALUE);
                        if (checkAsBidPayment.equals("bid")) {
                            prmsPaymentRequisition.setBidId(prmsBid);
                            prmsPaymentRequisition.setContractId(prmsContract);
                            prmsPaymentRequisition.setGrnId(mmsGrn);
                        } else if (checkAsBidPayment.equals("proforma")) {
                            prmsPaymentRequisition.setQuatationId(prmsQuotation);
                            prmsPaymentRequisition.setPoId(prmsPurchaseOrder);
                        }

//                        prmsPaymentRequisition.getWfPrmsProcessedCollection().add(wfPrmsProcessed);
//                        wfPrmsProcessed.setPaymentReqId(prmsPaymentRequisition);
//                        saveOnWorkFlows();
                        // wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                        prmsPaymentRequisition.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                        wfPrmsProcessed.setPaymentReqId(prmsPaymentRequisition);
                        wfPrmsProcessed.setDecision(String.valueOf(prmsPaymentRequisition.getStatus()));
                        requestforPaymentBeanLocal.create(prmsPaymentRequisition);
                        System.out.println("20" + wfPrmsProcessed.getDecision());
                        JsfUtil.addSuccessMessage("Payment Request Information is registered!!");
                        return clearPaymentRequest();

                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);
                        return null;
                    }

                } else if (saveorUpdateBundle.equals("Update") && workflow.isPrepareStatus()) {
                    try {
                        //prmsPaymentRequisition.setStatus(dictionary.getUPDATED());
                        if (prmsPaymentRequisition.getPaymentType() != null && prmsPaymentRequisition.getPaymentType().equals(paymentType)) {
                            System.out.println("yesy " + paymentType);
                            prmsPaymentRequisition.setBankId(fmsBank);
                        }
                        prmsPaymentRequisition.setPreparedDate(wfPrmsProcessed.getProcessedOn());
                        prmsPaymentRequisition.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        requestforPaymentBeanLocal.update(prmsPaymentRequisition);
                        JsfUtil.addSuccessMessage("Payment Request Information is updated!!");
                        clearPaymentRequest();
                        saveorUpdateBundle = "Save";
                        return clearPaymentRequest();
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                    }

                } else if (saveorUpdateBundle.equals("Update") && (workflow.isApproveStatus() || workflow.isCheckStatus())) {

                    System.out.println("when Appp======");
                    if (selectedValue.equalsIgnoreCase("Approve") && workflow.isApproveStatus()) {
                        System.out.println("1");
                        workflow.setUserStatus(Constants.APPROVE_VALUE);
                        prmsPaymentRequisition.setStatus(Constants.APPROVE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workflow.isCheckStatus()) {
                        System.out.println("1");
                        workflow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        prmsPaymentRequisition.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isApproveStatus()) {
                        System.out.println("1");
                        workflow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        prmsPaymentRequisition.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workflow.isCheckStatus()) {
                        workflow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        prmsPaymentRequisition.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                    }
                    requestforPaymentBeanLocal.update(prmsPaymentRequisition);
                    wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                    JsfUtil.addSuccessMessage("PaymentRequest Information is successfully Decided at" + prmsPaymentRequisition.getReqPaymentNo());
                    clearPaymentRequest();
                    saveorUpdateBundle = "Save";
                    return clearPaymentRequest();
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//              ..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {

        }
        return null;
    }

//    public void saveOnWorkFlows() {
//        System.out.println("here");
//        wfPrmsProcessed.setPaymentReqId(prmsPaymentRequisition);
//        System.out.println("so end");
//    }
    private String clearPaymentRequest() {
        prmsPaymentRequisition = new PrmsPaymentRequisition();
        prmsBid = null;
        prmsContract = null;
        checkAsBidPayment = "bid";
        prmsSupplyProfile = null;
        prmsPaymentRequisitions = null;
        prmsPaymentRequisitions1 = null;
        prmsQuotation = null;
        prmsPurchaseOrder = null;
        pmsCreateProjects = null;
        hrDepartments = null;
        fmsBank = null;
        wfPrmsProcessedDModel = null;
        saveorUpdateBundle = "Save";
        saveStatus = 0;
        mmsGrn = null;
        remainingAmount = 0.0;
        wfPrmsProcessed.setProcessedOn(null);
        wfPrmsProcessed.setDecision(null);
        wfPrmsProcessed.setCommentGiven(null);
        disableWhenUpdating = false;
        return null;
    }

    public void getRequestNo(SelectEvent event) {

        String requestNo = event.getObject().toString();
        prmsPaymentRequisition.setReqPaymentNo(requestNo);
        prmsPaymentRequisition = requestforPaymentBeanLocal.getRequestNo(prmsPaymentRequisition);

        saveStatus = 1;
        saveorUpdateBundle = "save";

    }

//    public List<PrmsBid> getBidNumbersShow() {
//        if (bidNumbersShow == null) {
//            bidNumbersShow = new ArrayList<>();
//            bidNumbersShow = contractInformationBeanLocal.bidNumberList();
//        }
//        return bidNumbersShow;
//    }
//
//    public void setBidNumbersShow(List<PrmsBid> bidNumbersShow) {
//        this.bidNumbersShow = bidNumbersShow;
//    }
    public List<PrmsBid> getBidContractedList() {
        if (bidContractedList == null) {
            bidContractedList = new ArrayList<>();
            bidContractedList = requestforPaymentBeanLocal.getBidContractedOnly(approvedStatus);
        }
        return bidContractedList;
    }

    public void setBidContractedList(List<PrmsBid> bidContractedList) {
        this.bidContractedList = bidContractedList;
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

    public List<PrmsPurchaseOrder> getPurchaseOrderList() {
        if (purchaseOrderList == null) {
            purchaseOrderList = new ArrayList<>();
        }
        return purchaseOrderList;
    }

    public void setPurchaseOrderList(List<PrmsPurchaseOrder> purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }

    public List<PrmsBidDetail> getPRNumberList() {
        return PRNumberList;
    }

    public void setPRNumberList(List<PrmsBidDetail> PRNumberList) {
        this.PRNumberList = PRNumberList;
    }

    public SelectItem[] getListOfVendorName() {
        return JsfUtil.getSelectItems(requestforPaymentBeanLocal.VendorName(), true);

    }

    public SelectItem[] getListdepartment() {
        return JsfUtil.getSelectItems(hrDepartmentsBeanLocal.findAll(), true);

    }

    public SelectItem[] getgrnNo() {
        return JsfUtil.getSelectItems(mmsGRNBEanLocal.findAll(), true);
    }

    public void changesForContctNo(ValueChangeEvent change) {
        if (!change.getNewValue().toString().isEmpty()) {
            prmsContract = (PrmsContract) change.getNewValue();
            contractListFromAmendment = requestforPaymentBeanLocal.checkContractIdFromAmended(prmsContract);
            if (contractListFromAmendment.size() > 0) {
                System.out.println("oh This " + prmsContract.getContractId() + " Contract Id is Amended");
                prmsContractAmendment = requestforPaymentBeanLocal.getContractAmendedInfoByContractId(prmsContract);
                System.out.println("supplier from Contract Amendment ---" + prmsContractAmendment.getSuppId());
                System.out.println("Payment Type from Contract Amendment --- " + prmsContractAmendment.getPaymenttype());
                System.out.println("Contract # from Contract Amendment --- " + prmsContractAmendment.getContractamount());
                prmsContract.setSuppId(prmsContractAmendment.getSuppId());
                prmsContract.setPaymenttype(prmsContractAmendment.getPaymenttype());
                prmsContract.setContractamount(prmsContractAmendment.getContractamount());
            }
            prmsSupplyProfile = prmsContract.getSuppId();
            pmsCreateProjects = prmsContract.getProjectId();
            prmsContractCurrencyDetailList = requestforPaymentBeanLocal.getContractAmoutList(prmsContract);
//            for (int indexRow = 0; indexRow < prmsContractCurrencyDetailList.size(); indexRow++) {
//                double amt = prmsContractCurrencyDetailList.get(indexRow).getAmount();
//            }
            amountLists = requestforPaymentBeanLocal.getAmoutList(prmsContract);
            prmsContractCurrencyDetail.setContractId(prmsContract);
            System.out.println("here we listed " + amountLists);
//            prmsContract.setContractamount(Double.valueOf(amountLists.toString()));
            System.out.println("cont id " + prmsContractCurrencyDetail.getContractId());
            System.out.println("cont amount " + prmsContract.getContractamount());
            if (prmsContract.getProjectId() != null) {
                System.out.println("projet id----" + prmsContract.getProjectId());
                renderProjectNames = true;

            } else if (prmsContract.getProjectId() == null) {
                System.out.println("no proj id " + prmsContract.getProjectId());
                renderProjectNames = false;
            }

            prmsPaymentRequisition.setTotalAmount(prmsContract.getContractamount());
//            System.out.println("contractId====" + prmsContract.getContractId() + "supp id" + prmsContract.getSuppId() + "sup name===" + prmsContract.getSuppId().getVendorName() + "bid Total=====" + prmsContract.getContractamount());
            prmsPaymentRequisition.setContractId(prmsContract);
//            mmsInspection.setInspectionId(Integer.parseInt(prmsContract.toString()));
            mmsInspection.setInspectionId(Integer.parseInt(prmsContract.getContractId().toString()));
            System.out.println("inspectio id=====" + mmsInspection.getInspectionId() + "cont id>>>" + mmsInspection.getContractId());
            mmsInspection.setContractId(prmsContract);
            System.out.println("cont========" + mmsInspection.getContractId());
            System.out.println("insp========" + mmsInspection.getInspectionId());
            populategrnNoLists = requestforPaymentBeanLocal.getGrnNo(mmsInspection);
            System.out.println("mms=" + populategrnNoLists);
            if (checkAsBidPayment.equals(checkAsBidPayment)) {
                prmsPaymentRequisition.setPaymentType(prmsContract.getPaymenttype());
                if (prmsPaymentRequisition.getPaymentType() != null && prmsPaymentRequisition.getPaymentType().equals("Bank Transfer")) {
                    renderWhenBank = false;
                } else {
                    renderWhenBank = true;
                }
            }
        }
    }

    public void changesForPoNo(ValueChangeEvent ev) {
        if (!ev.getNewValue().toString().isEmpty()) {
            prmsPurchaseOrder = (PrmsPurchaseOrder) ev.getNewValue();
            prmsSupplyProfile = prmsPurchaseOrder.getSuppId();

            double totalPrice = 0;
            for (int i = 0; i < prmsPurchaseOrder.getPrmsPurOrderDetailList().size(); i++) {
                totalPrice = totalPrice + (prmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getQuantity() * prmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getUnitPrice());
            }
            prmsPaymentRequisition.setTotalAmount(totalPrice);

            System.out.println("poId---" + prmsPurchaseOrder.getPoId() + "supp Name---" + prmsPurchaseOrder.getSuppId().getVendorName());
        }
    }

    public void forBankChanges(ValueChangeEvent chnage) {
        if (chnage.getNewValue() != null) {
            System.out.println("changes in pT");
            if (chnage.getNewValue().equals(paymentType)) {
                System.out.println("----PT---" + paymentType);
                renderWhenBank = false;
            } else {
                renderWhenBank = true;
            }

        }
    }

    public void getStoreName(ValueChangeEvent ev) {
        if (populategrnNoLists.size() > 0) {
            System.out.println("size above");
            if (ev.getNewValue() != null) {
                System.out.println("here am");
//            String grNo=ev.getNewValue().toString();
                mmsGrn = (MmsGrn) ev.getNewValue();
                mmsStoreInformation = mmsGrn.getStoreId();
                System.out.println("store Name===" + mmsGrn.getStoreId());
            }
        }
    }
//    }
//     public SelectItem[] getdeprtmnt() {
//        return JsfUtil.getSelectItems(requestforPaymentBeanLocal.depList(), true);
//    }
//    public SelectItem[] departmentList() {
//        return JsfUtil.getSelectItems(hrDepartmentsBeanLocal.searchdeptName(), true);
//    }

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

    public SelectItem[] getListOfSupplier() {
        return JsfUtil.getSelectItems(contractInformationBeanLocal.VendorName(), true);

    }

    public SelectItem[] bidNumberList() {
        return JsfUtil.getSelectItems(contractInformationBeanLocal.bidNumberList(), true);

    }

//    public SelectItem[] contractList() {
//        return JsfUtil.getSelectItems(requestforPaymentBeanLocal.ContractList(), true);
//
//    }
    public SelectItem[] getPrList() {
        return JsfUtil.getSelectItems(purchaseReqBeanLocal.getPurchReqNo(), true);

    }

    public String getCheckAsBidPayment() {
        return checkAsBidPayment;
    }

    public void setCheckAsBidPayment(String checkAsBidPayment) {
        this.checkAsBidPayment = checkAsBidPayment;
    }

    public String getCheckAsByContract() {
        return checkAsByContract;
    }

    public void setCheckAsByContract(String checkAsByContract) {
        this.checkAsByContract = checkAsByContract;
    }

    public boolean isRenderBid() {
        return renderBid;
    }

    public void setRenderBid(boolean renderBid) {
        this.renderBid = renderBid;
    }

    public boolean isRenderProforma() {
        return renderProforma;
    }

    public void setRenderProforma(boolean renderProforma) {
        this.renderProforma = renderProforma;
    }

    public boolean isRenderPO() {
        return renderPO;
    }

    public void setRenderPO(boolean renderPO) {
        this.renderPO = renderPO;
    }

    public boolean isRenderContract() {
        return renderContract;
    }

    public void setRenderContract(boolean renderContract) {
        this.renderContract = renderContract;
    }

    public List<HrDepartments> getAllDepartmentsList() {
        return allDepartmentsList;
    }

    public void setAllDepartmentsList(List<HrDepartments> allDepartmentsList) {
        this.allDepartmentsList = allDepartmentsList;
    }

    public List<PrmsQuotation> getProformaLists() {
        if (ProformaLists == null) {
            ProformaLists = new ArrayList<>();
            ProformaLists = requestforPaymentBeanLocal.getProformaNo(approvedStatus);
        }
        return ProformaLists;
    }

    public void setProformaLists(List<PrmsQuotation> ProformaLists) {
        this.ProformaLists = ProformaLists;
    }

    public List<HrDepartments> getAraListe() {
        return araListe;
    }

    public void setAraListe(List<HrDepartments> araListe) {
        this.araListe = araListe;
    }

//    public List<MmsStoreInformation> getStoreNameLists() {
//        if (storeNameLists == null) {
//            storeNameLists = new ArrayList<>();
//            storeNameLists = requestforPaymentBeanLocal.getStoreNameLists();
//        }
//        return storeNameLists;
//    }
//
//    public void setStoreNameLists(List<MmsStoreInformation> storeNameLists) {
//        this.storeNameLists = storeNameLists;
//    }
    public List<MmsGrn> getPopulategrnNoLists() {
        if (populategrnNoLists == null) {
            populategrnNoLists = new ArrayList<>();

        }
        return populategrnNoLists;
    }

    public void setPopulategrnNoLists(List<MmsGrn> populategrnNoLists) {
        this.populategrnNoLists = populategrnNoLists;
    }

//    public List<MmsGrn> getPopulatePoNoLists() {
//        if (populatePoNoLists == null) {
//            populatePoNoLists = new ArrayList<>();
//        }
//        return populatePoNoLists;
//    }
//
//    public void setPopulatePoNoLists(List<MmsGrn> populatePoNoLists) {
//        this.populatePoNoLists = populatePoNoLists;
//    }
    public TreeNode getSelected() {
        return selected;
    }

    public void setSelected(TreeNode selected) {
        this.selected = selected;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getChilds() {
        return childs;
    }

    public void setChilds(TreeNode childs) {
        this.childs = childs;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isRenderWhenBank() {
        return renderWhenBank;
    }

    public void setRenderWhenBank(boolean renderWhenBank) {
        this.renderWhenBank = renderWhenBank;
    }

    public boolean isDisableWhenUpdating() {
        return disableWhenUpdating;
    }

    public void setDisableWhenUpdating(boolean disableWhenUpdating) {
        this.disableWhenUpdating = disableWhenUpdating;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public void whenSelected(NodeSelectEvent events) {
        selected = events.getTreeNode();
        int key = Integer.parseInt((selected.getData().toString()).split("--")[0]);
        hrDepartments = importShippingInstructBeanLocal.getSelectDepartement(key);
        prmsPaymentRequisition.setDepId(hrDepartments);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlgEepDep').hide();");
    }

    @PostConstruct
    public void init() {
        allDepartmentsList = hrDepartmentsFacade.findAll();
        parent = new DefaultTreeNode("Root", null);
        recursive(allDepartmentsList, 0, parent);
        childs = getParent();
        wfPrmsProcessed.setProcessedBy(workflow.getUserAccount());
        setUserName(workflow.getUserName());
        if (workflow.isPrepareStatus()) {
            renderDecision = false;
            isRenderCreate = true;
        }
        if (workflow.isCheckStatus()) {
            renderDecision = true;
            isRenderCreate = false;
        }
        if (workflow.isApproveStatus()) {
            renderDecision = true;
            isRenderCreate = false;
        }
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

    public List<PrmsPaymentRequisition> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<PrmsPaymentRequisition> requestList) {
        this.requestList = requestList;
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

    public List<PrmsContractAmendment> getContractListFromAmendment() {
        if (contractListFromAmendment == null) {
            contractListFromAmendment = new ArrayList<>();
        }
        return contractListFromAmendment;
    }

    public void setContractListFromAmendment(List<PrmsContractAmendment> contractListFromAmendment) {
        this.contractListFromAmendment = contractListFromAmendment;
    }

    public List<PrmsContractCurrencyDetail> getPrmsContractCurrencyDetailList() {
        if (prmsContractCurrencyDetailList == null) {
            prmsContractCurrencyDetailList = new ArrayList<>();
        }
        return prmsContractCurrencyDetailList;
    }

    public void setPrmsContractCurrencyDetailList(List<PrmsContractCurrencyDetail> prmsContractCurrencyDetailList) {
        this.prmsContractCurrencyDetailList = prmsContractCurrencyDetailList;
    }

    public List<String> getAmountLists() {
        if (amountLists == null) {
            amountLists = new ArrayList<>();
        }
        return amountLists;
    }

    public void setAmountLists(List<String> amountLists) {
        this.amountLists = amountLists;
    }

    public Integer getRequestNotificationCounter() {
        requestList = requestforPaymentBeanLocal.getRequestsOnLists();
        requestNotificationCounter = requestList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public void RequestListChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            prmsPaymentRequisition = (PrmsPaymentRequisition) event.getNewValue();
            populateDataForApproval();
        }
    }

    public void rowSelect(SelectEvent event) {
        prmsPaymentRequisition = (PrmsPaymentRequisition) event.getObject();
        populateDataForApproval();
    }

    public void populateDataForApproval() {
        prmsPaymentRequisition.setPaymentReqId(prmsPaymentRequisition.getPaymentReqId());
        hrDepartments = prmsPaymentRequisition.getDepId();
        checkAsBidPayment = prmsPaymentRequisition.getPurchaseType();
        remainingAmount = prmsPaymentRequisition.getRemainingAmount();
        renderpnlToSearchPage = true;
        if (workflow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(prmsPaymentRequisition.getPreparedDate());
        }
        prmsPaymentRequisition = requestforPaymentBeanLocal.getSelectedRequest(prmsPaymentRequisition.getPaymentReqId());

        if (prmsPaymentRequisition.getPurchaseType().equalsIgnoreCase("bid")) {
            prmsContract = prmsPaymentRequisition.getContractId();
            prmsBid = prmsPaymentRequisition.getBidId();
            disableWhenUpdating = true;
            renderBid = true;
//            renderContract = true;
            renderProforma = false;
//            renderPO = false;
            contNoList = false;
            contNoTxt = true;
            poNoTxt = false;
            poNolist = false;
            prmsBid = prmsPaymentRequisition.getBidId();
            prmsContract = prmsPaymentRequisition.getContractId();
            if (prmsContract.getPaymenttype().equals(paymentType)) {
                renderWhenBank = false;
                fmsBank = prmsPaymentRequisition.getBankId();
            } else {
                renderWhenBank = true;
            }
//            if (prmsPaymentRequisition.getContractId().getProjectId() != null) {
//                renderProjectNames = false;
//            } else if (prmsPaymentRequisition.getContractId().getProjectId() == null) {
//                renderProjectNames = true;
//            }
        } else if (prmsPaymentRequisition.getPurchaseType().equalsIgnoreCase("proforma")) {
            disableWhenUpdating = true;
            renderProforma = true;
//            renderPO = true;
            renderBid = false;
//            renderContract = false;
            poNolist = false;
            poNoTxt = true;
            contNoList = false;
            contNoTxt = false;
            renderProjectNames = false;
            prmsQuotation = prmsPaymentRequisition.getQuatationId();
            prmsPurchaseOrder = prmsPaymentRequisition.getPoId();
            prmsSupplyProfile.getVendorName();
            System.out.println("nuu");
            if (prmsPaymentRequisition.getPaymentType().equals(paymentType)) {
                System.out.println("hele" + prmsPaymentRequisition.getPaymentType());
                renderWhenBank = false;
                fmsBank = prmsPaymentRequisition.getBankId();
            } else {
                System.out.println("kul");
                renderWhenBank = true;
            }
        }

        renderPnlCreateParty = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";

        saveStatus = 1;
        recreateprmsPaymentRequisitions();
        recreateWorkFlow();
    }

    public void changesInBidNo(ValueChangeEvent eve) {

        if (!eve.getNewValue().toString().isEmpty()) {
            String[] budgetYr;
            prmsBid = (PrmsBid) eve.getNewValue();
            bidListFromAmendment = requestforPaymentBeanLocal.checkBidFromAmended(prmsBid);
            prmsBidAmend.setBidId(prmsBid);
            System.out.println("--------Bid Id from Amend  ---------" + prmsBidAmend.getBidId().getId());
//            System.out.println("here u --- " + prmsBidAmend.getId());
            if (bidListFromAmendment.size() > 0) {
                System.out.println("oh There's Bid id " + prmsBid + " in Bid Amend");
//                prmsBidAmend = requestforPaymentBeanLocal.getBidFromAmended(prmsBid);
                prmsBidAmend = requestforPaymentBeanLocal.getBidAmendInfoByBidId(prmsBidAmend);
                System.out.println("Purchase Type/Bid Category on Amendement " + prmsBidAmend.getBidCatId());
                System.out.println("Bid Type on Amendement " + prmsBidAmend.getBidType());
                prmsBid.setBidCategory(prmsBidAmend.getBidCatId());
                prmsBid.setBidType(prmsBidAmend.getBidType());
            } else {
                System.out.println("oh No Bid id " + prmsBid + " in Bid Amend");
            }

            budgetYr = prmsBid.getRefNo().split("/");
            prmsPaymentRequisition.setBugetYear(budgetYr[1]);
            contractList = requestforPaymentBeanLocal.getContractNo(prmsBid, approvedStatus);
            //PRNumberList = requestforPaymentBeanLocal.getPRNumber(prmsBid);
            // System.out.println("PR No List--------" + PRNumberList);
            prmsPaymentRequisition.setBidId(prmsBid);
            prmsPaymentRequisition.setContractId(prmsContract);
            if (prmsBid.getBidType().equals("international")) {
                whenInternationalBid = true;
            } else {
                whenInternationalBid = false;
            }
            if (prmsBid.getBidCategory().equals("Goods")) {
                System.out.println("goods");
                hideGrn = true;
            } else if (prmsBid.getBidCategory().equals("Service") || (prmsBid.getBidCategory()).equals("Work")) {
                System.out.println("Serv/Work");
                hideGrn = false;
            }
        }
    }

    String newcheckListNo;
    String LastcheckListNo = "0";

    public String generateSpecficationNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = prmsPaymentRequisition.getReqPaymentNo();

        } else {
            PrmsPaymentRequisition LastPaymentNo = requestforPaymentBeanLocal.getLastPaymentNo();
            if (LastPaymentNo != null) {
                LastcheckListNo = LastPaymentNo.getPaymentReqId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newBidNoLast = 0;
            if (LastcheckListNo.equals("0")) {
                newBidNoLast = 1;
                newcheckListNo = "Payment-NO-" + newBidNoLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
                newBidNoLast = newBidNoLast + 1;
                newcheckListNo = "Payment-NO-" + newBidNoLast + "/" + f.format(now);
            }
        }
        return newcheckListNo;

    }

    public void changeBidAndProforma(ValueChangeEvent change) {
        prmsPaymentRequisition.setPurchaseType(checkAsBidPayment);
        if (change.getNewValue() != null) {
            if (change.getNewValue().toString().equalsIgnoreCase("bid")) {
                prmsQuotation = null;
                prmsPurchaseOrder = null;
                renderBid = true;
//                renderContract = true;
                renderProforma = false;
//                renderPO = false;
                contNoList = true;
                contNoTxt = false;
                poNolist = false;
                poNoTxt = false;
                renderProjectNames = true;
                prmsPaymentRequisition.setPurchaseType("bid");
            } else if (change.getNewValue().toString().equalsIgnoreCase("proforma")) {
                prmsBid = null;
                prmsContract = null;
                renderBid = false;
//                renderContract = false;
                renderProforma = true;
//                renderPO = true;
                poNolist = true;
                poNoTxt = false;
                contNoList = false;
                contNoTxt = false;
                renderProjectNames = false;
                whenInternationalBid = false;
                hideGrn = false;
                prmsPaymentRequisition.setPurchaseType("proforma");
            }
        }
    }

    public void changeToContractOrPO(ValueChangeEvent evented) {
        prmsPaymentRequisition.setAgreementType(checkAsByContract);
        if (!evented.getNewValue().toString().isEmpty()) {
            if (evented.getNewValue().toString().equalsIgnoreCase("contract")) {
//                setRenderContract(true);
//                setRenderPO(false);
                renderContract = true;
                renderPO = false;
                prmsPaymentRequisition.setAgreementType("contract");
            } else if (evented.getNewValue().toString().equalsIgnoreCase("po")) {
//                setRenderPO(true);
//                setRenderContract(false);
                renderPO = true;
                renderContract = false;
                prmsPaymentRequisition.setAgreementType("po");
            }
        }
    }

    public void changesProformaNumber(ValueChangeEvent ev) {
        if (!ev.getNewValue().toString().isEmpty()) {
            prmsQuotation = (PrmsQuotation) ev.getNewValue();
            String[] budgetYear;
            System.out.println("====Quotid====" + prmsQuotation.getQuatationId());
            System.out.println("====Qut Num" + prmsQuotation.getQuotationNo());
            budgetYear = prmsQuotation.getQuotationNo().split("/");
            prmsPaymentRequisition.setBugetYear(budgetYear[1]);
            purchaseOrderList = requestforPaymentBeanLocal.getPONumber(prmsQuotation, approvedStatus);

//            System.err.println("----PO LIsts---" + purchaseOrderList);
            prmsPaymentRequisition.setQuatationId(prmsQuotation);
            prmsPaymentRequisition.setPoId(prmsPurchaseOrder);
        }

    }

//    public void storeNameChangeEvent(ValueChangeEvent storeChange) {
//        if (!storeChange.getNewValue().toString().isEmpty()) {
//            mmsStoreInformation = (MmsStoreInformation) storeChange.getNewValue();
//            System.out.println("Grn no---" + mmsStoreInformation.getStoreId());
//            populategrnNoLists = requestforPaymentBeanLocal.getGrnNoByStoreId(mmsStoreInformation);
//            System.out.println("Grn list" + populategrnNoLists);
//        }
//    }
//    public void grnNoChangeEvent(ValueChangeEvent grnNoChange) {
//        if (!grnNoChange.getNewValue().toString().isEmpty()) {
//            mmsGrn = (MmsGrn) grnNoChange.getNewValue();
//            System.out.println("grn id  " + mmsGrn.getGrnId() + "po no  " + mmsGrn.getInspectionId().getPurchaseOId().getPacNo());
//            populatePoNoLists = requestforPaymentBeanLocal.getPoNoByGrnNo(mmsGrn);
//
//        }
//    }
    public Date minForApp() {
        return this.prmsPaymentRequisition.getPreparedDate();
    }
    double remainingAmount = 0.0;

    public void remainAmountChanging() {
        System.out.println("12");
        if (prmsPaymentRequisition.getTotalAmount() != null && prmsPaymentRequisition.getPaidAmount() != null) {
            if (prmsPaymentRequisition.getTotalAmount() >= prmsPaymentRequisition.getPaidAmount()) {
                System.out.println("total" + prmsPaymentRequisition.getTotalAmount() + " paid" + prmsPaymentRequisition.getPaidAmount());
                remainingAmount = prmsPaymentRequisition.getTotalAmount() - prmsPaymentRequisition.getPaidAmount();
            } else {
                remainingAmount = prmsPaymentRequisition.getTotalAmount() - prmsPaymentRequisition.getPaidAmount();
                JsfUtil.addFatalMessage("paid Amount must <= Total Amount");
            }
        }
    }

    public void limitValueLessthanContractAmount() {
        if (prmsPaymentRequisition.getTotalAmount() != null && prmsPaymentRequisition.getRequestedAmount() != null) {
            if (prmsPaymentRequisition.getRequestedAmount() > prmsPaymentRequisition.getTotalAmount()) {
                JsfUtil.addFatalMessage("Requested Amount must <= Total Amount");
            } else {
                prmsPaymentRequisition.setRequestedAmount(0.0);
            }
        }

    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

}
//         wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
