/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

// <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import java.io.InputStream;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.ServiceProviderBeanLocal;
import et.gov.eep.prms.businessLogic.ContractInformationBeanLocal;
import et.gov.eep.fcms.businessLogic.LuCurrencyBeanLocal;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.prms.businessLogic.InsuranceRegisterationBeanLocal;
import et.gov.eep.prms.businessLogic.Bank_Clearance_RegistrationBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrRecruitmentRequestsBeanLocal;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import javax.annotation.PostConstruct;
import org.primefaces.convert.NumberConverter;
import org.primefaces.model.TreeNode;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.FileUploadEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.IAdministration;
import webService.EventEntry;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.utility.Dictionary;
import org.insa.model.DocumentModel;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.WorkFlow;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import org.primefaces.context.RequestContext;
import java.text.ParseException;
// </editor-fold>

/**
 *
 * @author Bayisa Bedasa
 */
//Insurance Registration view scoped CDI Named Bean class
@Named("InsuranceRegisteration")
@ViewScoped
public class InsuranceRegFormController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    InsuranceRegisterationBeanLocal insuranceRegisterationBeanLocal;
    @EJB
    LuCurrencyBeanLocal luCurrencyBeanLocal;
    @EJB
    ServiceProviderBeanLocal serviceProviderBeanLocal;
    @EJB
    Bank_Clearance_RegistrationBeanLocal bank_Clearance_RegistrationBeanLocal;
    @EJB
    ContractInformationBeanLocal contractInformationBeanLocal;
    @EJB
    HrRecruitmentRequestsBeanLocal hrRecruitmentRequestsBeanLocal;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsFacade;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    PrmsInsuranceRequisition insuranceregistration;
    @Inject
    PrmsServiceProvider prmsServiceProvider;
    @Inject
    FmsLuCurrency currency;
    @Inject
    PrmsContract prmsContract;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    @Inject
    PrmsServiceProvider prmsServiceProviderTo;
    @Inject
    PrmsServiceProviderDetail prmsServiceProviderBranch;
    @Inject
    HrDepartments hrDepartments;
    @Inject
    DataUpload dataUpload;
    @Inject
    Dictionary dictionary;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    WorkFlow workFlow;
    @Inject
    SessionBean sessionBean;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Variables">
    private String icone = "ui-icon-plus";
    private String createorUpdateBundle = "Save";
    private String userName;
    private boolean disableBtnCreate = false;
    private boolean renderRegisterationPage = false;
    private boolean renderSearchingPage = true;
    private boolean renderpnlToSearchPage;
    private boolean isRenderCreate = false;
    private boolean isRowFileSelected = false;
    boolean renderDecision;
    private String newOrSearchBundle = "New";
    private String selectedValue = "";
    private String fileName;
    private String docFormat;
    String lastInsuNo = "0";
    String newInsuNo;

    String[] onesNames = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    String[] tensNames = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    String[] specialNames = {"", " thousand", " million", " billion", " trillion", " quadrillion", " quintillion"};
    String str = "";
    private byte[] byteArrayData;
    int saveStatus = 0;
    int updateStatus = 0;
    int requestNotificationCounter = 0;
    int approvedStatus = 3;
    int fileReferenceNo;

    private BigDecimal value;//Amount Of Insur.Fc + 10% of itself.
    private BigDecimal converted;/*Foreing Currencies amount * (amount of insurance + 10% of amount of insurance of FC) w/n converted in to Local(Ethiopian) Birr.
     OR  value * Exchange Rate*/

    private NumberConverter numberConverter = new NumberConverter();
    private TreeNode departmentsRoot;
    private TreeNode selectedNode;
    DocumentModel docModel;
    StreamedContent downloads;
    PrmsInsuranceRequisition insuranceDMSelection;
    WfPrmsProcessed prmsProcessedDMSelection;
    PrmsLuDmArchive prmsLuDmArchiveSelection;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Lists and models">
    List<PrmsInsuranceRequisition> prmsInsuranceregistrationList;
    List<PrmsServiceProvider> serviceProviderListF;
    List<PrmsServiceProvider> serviceProviderListT;
    List<PrmsContract> contractNum;
    List<PrmsServiceProviderDetail> bankBranch;
    List<HrDepartments> allDepartments;
    List<WfPrmsProcessed> wfPrmsProcessedLists;
    List<FmsLuCurrency> luCurrencysLists;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    List<Integer> docIds = new ArrayList<>();
    ArrayList<HrDepartments> departmentsList;
    private DataModel<PrmsInsuranceRequisition> prmsInsuranceregistrationModel;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    private DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;
    DataModel<DocumentModel> docList;
    List<PrmsInsuranceRequisition> insuranceSearchParameterLst;
    // </editor-fold>

    // callback lifecycle method
    @PostConstruct
    public void init() {
        allDepartments = hrDepartmentsFacade.findAll();
        departmentsRoot = new DefaultTreeNode("Root", null);
        //populateAddressNode(allDepartments, 0, departmentsRoot);
        //recursive(allDepartments, 0, root);
//        departmentsRoot = getRoot();
        loadDepartmentsTree();
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
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

    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    public PrmsInsuranceRequisition getInsuranceregistration() {
        if (insuranceregistration == null) {
            insuranceregistration = new PrmsInsuranceRequisition();
        }
        return insuranceregistration;
    }

    public void setInsuranceregistration(PrmsInsuranceRequisition insuranceregistration) {
        this.insuranceregistration = insuranceregistration;
    }

    public PrmsServiceProvider getPrmsServiceProvider() {
        if (prmsServiceProvider == null) {
            prmsServiceProvider = new PrmsServiceProvider();
        }
        return prmsServiceProvider;
    }

    public void setPrmsServiceProvider(PrmsServiceProvider prmsServiceProvider) {
        this.prmsServiceProvider = prmsServiceProvider;
    }

    public FmsLuCurrency getCurrency() {
        if (currency == null) {
            currency = new FmsLuCurrency();
        }
        return currency;
    }

    public void setCurrency(FmsLuCurrency currency) {
        this.currency = currency;
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

    public PrmsLuDmArchive getPrmsLuDmArchive() {
        if (prmsLuDmArchive == null) {
            prmsLuDmArchive = new PrmsLuDmArchive();
        }
        return prmsLuDmArchive;
    }

    public void setPrmsLuDmArchive(PrmsLuDmArchive prmsLuDmArchive) {
        this.prmsLuDmArchive = prmsLuDmArchive;
    }

    public PrmsServiceProvider getPrmsServiceProviderTo() {
        if (prmsServiceProviderTo == null) {
            prmsServiceProviderTo = new PrmsServiceProvider();
        }
        return prmsServiceProviderTo;
    }

    public void setPrmsServiceProviderTo(PrmsServiceProvider prmsServiceProviderTo) {
        this.prmsServiceProviderTo = prmsServiceProviderTo;
    }

    public PrmsServiceProviderDetail getPrmsServiceProviderBranch() {
        if (prmsServiceProviderBranch == null) {
            prmsServiceProviderBranch = new PrmsServiceProviderDetail();
        }
        return prmsServiceProviderBranch;
    }

    public void setPrmsServiceProviderBranch(PrmsServiceProviderDetail prmsServiceProviderBranch) {
        this.prmsServiceProviderBranch = prmsServiceProviderBranch;
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

    public DataUpload getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
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

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    public PrmsInsuranceRequisition getInsuranceDMSelection() {
        return insuranceDMSelection;
    }

    public void setInsuranceDMSelection(PrmsInsuranceRequisition insuranceDMSelection) {
        this.insuranceDMSelection = insuranceDMSelection;
    }

    public WfPrmsProcessed getPrmsProcessedDMSelection() {
        return prmsProcessedDMSelection;
    }

    public void setPrmsProcessedDMSelection(WfPrmsProcessed prmsProcessedDMSelection) {
        this.prmsProcessedDMSelection = prmsProcessedDMSelection;
    }

    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        return prmsLuDmArchiveSelection;
    }

    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of variables">
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCreateorUpdateBundle() {
        return createorUpdateBundle;
    }

    public void setCreateorUpdateBundle(String createorUpdateBundle) {
        this.createorUpdateBundle = createorUpdateBundle;
    }

    public String getNewOrSearchBundle() {
        return newOrSearchBundle;
    }

    public void setNewOrSearchBundle(String newOrSearchBundle) {
        this.newOrSearchBundle = newOrSearchBundle;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public int getRequestNotificationCounter() {
        prmsInsuranceregistrationList = insuranceRegisterationBeanLocal.getInsuRequestLists();
        requestNotificationCounter = prmsInsuranceregistrationList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public BigDecimal getValue() {
        if (value == null) {
            value = new BigDecimal(BigInteger.ONE);
        }
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getConverted() {
        if (converted == null) {
            converted = new BigDecimal(BigInteger.ONE);
        }
        return converted;
    }

    public void setConverted(BigDecimal converted) {
        this.converted = converted;
    }

    public String getStr() {
        if (str == null) {
            str = new String();
        }
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderRegisterationPage() {
        return renderRegisterationPage;
    }

    public void setRenderRegisterationPage(boolean renderRegisterationPage) {
        this.renderRegisterationPage = renderRegisterationPage;
    }

    public boolean isRenderSearchingPage() {
        return renderSearchingPage;
    }

    public void setRenderSearchingPage(boolean renderSearchingPage) {
        this.renderSearchingPage = renderSearchingPage;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
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

    public boolean isIsRowFileSelected() {
        return isRowFileSelected;
    }

    public void setIsRowFileSelected(boolean isRowFileSelected) {
        this.isRowFileSelected = isRowFileSelected;
    }

    public TreeNode getDepartmentsRoot() {
        return departmentsRoot;
    }

    public void setDepartmentsRoot(TreeNode departmentsRoot) {
        this.departmentsRoot = departmentsRoot;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public StreamedContent getDownloads() throws Exception {
        if (isRowFileSelected == true) {
            downloads = dataUpload.getFile(docModel);
        } else {
            JsfUtil.addFatalMessage("Pls Select Row File U want to Download");
        }
        return downloads;
    }

    public void setDownloads(StreamedContent downloads) {
        this.downloads = downloads;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of lists and models">
    public List<PrmsInsuranceRequisition> getPrmsInsuranceregistrationList() {
        if (prmsInsuranceregistrationList == null) {
            prmsInsuranceregistrationList = new ArrayList<>();

        }
        return prmsInsuranceregistrationList;
    }

    public void setPrmsInsuranceregistrationList(List<PrmsInsuranceRequisition> prmsInsuranceregistrationList) {
        this.prmsInsuranceregistrationList = prmsInsuranceregistrationList;
    }

    public List<WfPrmsProcessed> getWfPrmsProcessedLists() {
        if (wfPrmsProcessedLists == null) {
            wfPrmsProcessedLists = new ArrayList<>();
        }
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedLists(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

    public List<PrmsContract> getContractNum() {
        if (contractNum == null) {
            contractNum = new ArrayList<>();
            contractNum = insuranceRegisterationBeanLocal.contractNumList(approvedStatus);
        }
        return contractNum;
    }

    public void setContractNum(List<PrmsContract> contractNum) {
        this.contractNum = contractNum;
    }

    public List<FmsLuCurrency> getLuCurrencysLists() {
        if (luCurrencysLists == null) {
            luCurrencysLists = new ArrayList<>();
            luCurrencysLists = insuranceRegisterationBeanLocal.fromLuCurrency();
        }
        return luCurrencysLists;
    }

    public void setLuCurrencysLists(List<FmsLuCurrency> luCurrencysLists) {
        this.luCurrencysLists = luCurrencysLists;
    }

    public List<PrmsLuDmArchive> getPrmsLuDmArchivesList() {
        if (prmsLuDmArchivesList == null) {
            prmsLuDmArchivesList = new ArrayList<>();
        }
        return prmsLuDmArchivesList;
    }

    public void setPrmsLuDmArchivesList(List<PrmsLuDmArchive> prmsLuDmArchivesList) {
        this.prmsLuDmArchivesList = prmsLuDmArchivesList;
    }

    public List<PrmsServiceProvider> getServiceProviderListF() {
        if (serviceProviderListF == null) {
            serviceProviderListF = new ArrayList<>();
            serviceProviderListF = insuranceRegisterationBeanLocal.serviceProviderListFrom();
        }
        return serviceProviderListF;
    }

    public void setServiceProviderListF(List<PrmsServiceProvider> serviceProviderListF) {
        this.serviceProviderListF = serviceProviderListF;
    }

    public List<PrmsServiceProvider> getServiceProviderListT() {
        if (serviceProviderListT == null) {
            serviceProviderListT = new ArrayList<>();
        }
        serviceProviderListT = insuranceRegisterationBeanLocal.serviceProviderInsuranceList();
        return serviceProviderListT;
    }

    public void setServiceProviderListT(List<PrmsServiceProvider> serviceProviderListT) {
        this.serviceProviderListT = serviceProviderListT;
    }

    public List<HrDepartments> getAllDepartments() {
        return allDepartments;
    }

    public void setAllDepartments(List<HrDepartments> allDepartments) {
        this.allDepartments = allDepartments;
    }

    public DataModel<DocumentModel> getDocList() {
        return docList;
    }

    public void setDocList(DataModel<DocumentModel> docList) {
        this.docList = docList;
    }

    public DocumentModel getDocModel() {
        return docModel;
    }

    public void setDocModel(DocumentModel docModel) {
        this.docModel = docModel;
    }

    public List<PrmsServiceProviderDetail> getBankBranch() {
        // if (bankBranch == null) {
        bankBranch = new ArrayList<>();
        if (prmsServiceProviderTo != null) {
            SelectItem[] listBranch = null;
            bankBranch = insuranceRegisterationBeanLocal.getBankBranches(prmsServiceProviderTo);
            if (bankBranch.size() > 0) {
                listBranch = new SelectItem[bankBranch.size() + 1];
                listBranch[0] = new SelectItem(null, "--- Select One ---");
                for (int i = 0; i < bankBranch.size(); i++) {
                    listBranch[i + 1] = new SelectItem(bankBranch.get(i).getServiceDtId(), bankBranch.get(i).getBranchName());
                }
            }
            return bankBranch;
            //bankBranch = new ArrayList<>();
        } else {
            SelectItem[] bankBranch = new SelectItem[1];
            bankBranch[0] = new SelectItem("", "--- Select Bank Name To ---");
        }
        return bankBranch;
    }

    public void setBankBranch(List<PrmsServiceProviderDetail> bankBranch) {
        this.bankBranch = bankBranch;
    }

    public ArrayList<HrDepartments> getDepartmentsList() {
        if (departmentsList == null) {
            departmentsList = new ArrayList<>();
        }
        return departmentsList;
    }

    public void setDepartmentsList(ArrayList<HrDepartments> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public DataModel<PrmsInsuranceRequisition> getPrmsInsuranceregistrationModel() {
        if (prmsInsuranceregistrationModel == null) {
            prmsInsuranceregistrationModel = new ListDataModel<>();
        }
        return prmsInsuranceregistrationModel;
    }

    public void setPrmsInsuranceregistrationModel(DataModel<PrmsInsuranceRequisition> prmsInsuranceregistrationModel) {
        this.prmsInsuranceregistrationModel = prmsInsuranceregistrationModel;
    }

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ListDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchivesDModel() {
        return prmsLuDmArchivesDModel;
    }

    public void setPrmsLuDmArchivesDModel(DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel) {
        this.prmsLuDmArchivesDModel = prmsLuDmArchivesDModel;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event changes">
    /**
     * treeNode Selection Action
     */
    public List<PrmsInsuranceRequisition> getInsuranceSearchParameterLst() {
        if (insuranceSearchParameterLst == null) {
            insuranceSearchParameterLst = new ArrayList<>();
            insuranceSearchParameterLst = insuranceRegisterationBeanLocal.getParamNameList();
        }
        return insuranceSearchParameterLst;
    }

    public void setInsuranceSearchParameterLst(List<PrmsInsuranceRequisition> insuranceSearchParameterLst) {
        this.insuranceSearchParameterLst = insuranceSearchParameterLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            insuranceregistration.setColumnName(event.getNewValue().toString());
            insuranceregistration.setColumnValue(null);
        }
    }

    public void onNodeSelection(NodeSelectEvent ev) {
        selectedNode = ev.getTreeNode();
        int keys = Integer.parseInt(selectedNode.getData().toString().split("--")[0]);
        hrDepartments = hrRecruitmentRequestsBeanLocal.getSelectDepartement(keys);
        insuranceregistration.setHrDeptId(hrDepartments);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg1').hide();");

    }

    public void handleDecisionOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();

        }
    }

    public void getXRate(ValueChangeEvent ev) {
        if (null != ev.getNewValue()) {
            currency = (FmsLuCurrency) ev.getNewValue();
            currency = insuranceRegisterationBeanLocal.autoXRate(currency);
            insuranceregistration.setTotalFc(value);
            insuranceregistration.setExchangeRate(currency.getXrate());
            str = new String();
            if (insuranceregistration.getAmountOfInsuranceFc() != null) {
                toWords();
            }
            insuranceregistration.setLocalCurrencyInword(str);
        }

    }

    public void updateTo(ValueChangeEvent ev) {
        if (!ev.getNewValue().toString().isEmpty()) {
            prmsServiceProviderTo.setServiceProId(new BigDecimal(ev.getNewValue().toString()));
            prmsServiceProviderTo = insuranceRegisterationBeanLocal.updateServFrom(prmsServiceProvider);
        }
    }

    public void updateBranch(ValueChangeEvent ev) {
        if (!ev.getNewValue().toString().isEmpty()) {
            prmsServiceProviderBranch.setServiceDtId(new BigDecimal(ev.getNewValue().toString()));
        }
    }

    public void updateConNum(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsContract = (PrmsContract) event.getNewValue();
        }
    }

    public void RequestListChange(ValueChangeEvent event) throws ParseException {
        if (event.getNewValue() != null) {
            insuranceregistration = (PrmsInsuranceRequisition) event.getNewValue();
            populateDataForApp();
        }
    }

    // downloading selected file 
    public void fileUploadListener(FileUploadEvent fileUploadEvent) {
        InputStream fileByteFile_ = null;
        fileName = fileUploadEvent.getFile().getFileName().split("\\.")[0];
        docFormat = fileUploadEvent.getFile().getFileName().split("\\.")[1];
//        String fileNameWzExtent = fileUploadEvent.getFile().getFileName();
//        String categoryBundle = "et/gov/eep/commonApplications/securityServer";
        try {
            fileByteFile_ = fileUploadEvent.getFile().getInputstream();
        } catch (Exception e) {
            System.out.println("Upload Error from Lisener==>" + e.getMessage());
        }
        byteArrayData = dataUpload.extractByteArray(fileByteFile_);
//        fileReferenceNo = dataUpload.uploadListener(fileByteFile_, docFormat, fileName, fileNameWzExtent, categoryBundle);
        if (byteArrayData != null) {
            JsfUtil.addSuccessMessage("File Uploaded Successfully");
        }
    }

    public void whenRowSelect(SelectEvent selected) {
        docModel = (DocumentModel) selected.getObject();
        setIsRowFileSelected(true);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other methods">
    public void SwitchedToNewButton() {
        createorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (newOrSearchBundle.equals("New")) {
            clearForNew();
            renderRegisterationPage = true;
            renderSearchingPage = false;
            newOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (newOrSearchBundle.equals("Search")) {
            renderRegisterationPage = false;
            renderSearchingPage = true;
            newOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void goBackSearchPageBtnAction() {
        renderRegisterationPage = false;
        renderpnlToSearchPage = false;
        renderSearchingPage = true;
    }

    public void switchedToSearchBtn() {
        clearForNew();
        renderRegisterationPage = false;
        renderSearchingPage = true;
    }

    public String searchInsuranceInformation() {
        insuranceregistration.setPreparedBy(workFlow.getUserAccount());
        prmsInsuranceregistrationList = insuranceRegisterationBeanLocal.searchByInsuranceNo(insuranceregistration);
        recreateprmsInsuranceregistrationModel();
        insuranceregistration=new PrmsInsuranceRequisition();
        return null;

    }

    private void recreateprmsInsuranceregistrationModel() {
        prmsInsuranceregistrationModel = null;
        prmsInsuranceregistrationModel = new ListDataModel<>(new ArrayList<>(getPrmsInsuranceregistrationList()));
    }

    public void recreateWorkFlow() {
        wfPrmsProcessedDModel = null;
        wfPrmsProcessedDModel = new ListDataModel(insuranceregistration.getWfPrmsProcessedLists());
    }

    public void recreateFileUpload() {
        prmsLuDmArchivesDModel = null;
        prmsLuDmArchivesDModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    private void loadDepartmentsTree() {
        allDepartments = hrDepartmentsFacade.findAll();
        departmentsRoot = new DefaultTreeNode("Root", null);
        populateAddressNode(allDepartments, 0, departmentsRoot);
    }

    public void populateAddressNode(List<HrDepartments> list, int id, TreeNode node) {
        departmentsList = new ArrayList<>();
        if (allDepartments != null) {
            for (HrDepartments k : allDepartments) {
                if (k.getParentId() != null && k.getParentId() == id) {
                    TreeNode childNode = new DefaultTreeNode(k.getDepId() + "--" + k.getDepName(), node);
                    departmentsList.add(k);
                    populateAddressNode(departmentsList, k.getDepId(), childNode);
                }
            }
        }
    }

    public void onRowSelect(SelectEvent event) throws ParseException {
        insuranceregistration = (PrmsInsuranceRequisition) event.getObject();
        populateDataForApp();

    }

    public void populateDataForApp() throws ParseException {
        System.out.println("======================== workflow size=" + insuranceregistration.getWfPrmsProcessedLists().size());
        str = insuranceregistration.getLocalCurrencyInword();
        renderRegisterationPage = true;
        renderSearchingPage = false;
        renderpnlToSearchPage = true;
        prmsServiceProviderTo = insuranceregistration.getServiceProId();
        prmsServiceProviderBranch = insuranceregistration.getServiceDtId();
        prmsContract = insuranceregistration.getContractNo();
        hrDepartments = insuranceregistration.getHrDeptId();
        prmsServiceProvider = insuranceregistration.getServiceProName();
        if (insuranceregistration.getFileRefNumber() != null) {
            prmsLuDmArchive = insuranceregistration.getFileRefNumber();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
            System.out.println("when searching doc id==" + prmsLuDmArchivesList.get(0).getDocumentId());
            System.out.println("File Name is==" + prmsLuDmArchivesList.get(0).getFileName());
        }
        if (workFlow.isPrepareStatus()) {
            String reqDate = insuranceregistration.getProcessedOn();//data from our DB pattern Tue/EEE Jan/MMM 09 00:11:00 EAT 2018 //yyyy-MM-dd'T'HH:mm:ss
            Date date = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(reqDate);
            wfPrmsProcessed.setProcessedOn(date);
        }

        createorUpdateBundle = "Update";
        recreateprmsInsuranceregistrationModel();
        recreateWorkFlow();
        recreateFileUpload();
    }

    public String generateInsuranceNo() {
        if (createorUpdateBundle.equals("Update")) {
            newInsuNo = insuranceregistration.getInsuranceNo();
        } else {
            getNextInsuranceNo();
        }

        return newInsuNo;

    }

    public String getNextInsuranceNo() {
        newInsuNo = insuranceRegisterationBeanLocal.getNextInsuranceNo();
        return newInsuNo;
    }

    // percentage converter method
    public void percentage() {
        value = insuranceregistration.getAmountOfInsuranceFc().add(insuranceregistration.getAmountOfInsuranceFc()).multiply(new BigDecimal(0.1));

    }

    public InsuranceRegFormController() {
        numberConverter.setPattern("#,##00.##");
        numberConverter.setMinIntegerDigits(2);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(2);
    }

    public void intoLc() {
        value = insuranceregistration.getAmountOfInsuranceFc().multiply(new BigDecimal(0.1)).add(insuranceregistration.getAmountOfInsuranceFc());
        System.out.println("today's  XRate amount" + insuranceregistration.getExchangeRate());
        converted = insuranceregistration.getExchangeRate().multiply(value);
        insuranceregistration.setLcAmount(converted);
        converted = insuranceregistration.getLcAmount();
    }

    //one and tens
    public String one_to_hund(int n) {

        while (n != 0) {
            if (n == 1) {
                str = str + "one" + " ";
                n = 0;
            } else if (n == 2) {
                str = str + "two" + " ";
                n = 0;
            } else if (n == 3) {
                str = str + "three" + " ";
                n = 0;
            } else if (n == 4) {
                str = str + "four" + " ";
                n = 0;
            } else if (n == 5) {
                str = str + "five" + " ";
                n = 0;
            } else if (n == 6) {
                str = str + "six" + " ";
                n = 0;
            } else if (n == 7) {
                str = str + "seven" + " ";
                n = 0;
            } else if (n == 8) {
                str = str + "eight" + " ";
                n = 0;
            } else if (n == 9) {
                str = str + "nine" + " ";
                n = 0;
            } else if (n == 10) {
                str = str + "ten" + " ";
                n = 0;
            } else if (n == 11) {
                str = str + "eleven" + " ";
                n = 0;
            } else if (n == 12) {
                str = str + "twelve" + " ";
                n = 0;
            } else if (n == 13) {
                str = str + "thirteen" + " ";
                n = 0;
            } else if (n == 14) {
                str = str + "fourteen" + " ";
                n = 0;
            } else if (n == 15) {
                str = str + "fifteen" + " ";
                n = 0;
            } else if (n == 16) {
                str = str + "sixteen" + " ";
                n = 0;
            } else if (n == 17) {
                str = str + "seventeen" + " ";
                n = 0;
            } else if (n == 18) {
                str = str + "eighteen" + " ";
                n = 0;
            } else if (n == 19) {
                str = str + "nineteen" + " ";
                n = 0;
            } else if (n >= 20 && n < 30) {
                str = str + "Twenty" + "-";
                n = n - 20;
            } else if (n >= 30 && n < 40) {
                str = str + "" + "Thirty" + "-";
                n = n - 30;
            } else if (n >= 40 && n < 50) {
                str = str + "Fourty" + "-";
                n = n - 40;
            } else if (n >= 50 && n < 60) {
                str = str + "Fifty" + "-";
                n = n - 50;
            } else if (n >= 60 && n < 70) {
                str = str + "Sixty" + "-";
                n = n - 60;
            } else if (n >= 70 && n < 80) {
                str = str + "Seventy" + "-";
                n = n - 70;
            } else if (n >= 80 && n < 90) {
                str = str + "Eighty" + "-";
                n = n - 80;
            } else if (n >= 90 && n < 100) {
                str = str + "Ninety" + "-";
                n = n - 90;
                System.out.println("special");
            }
        }
        return str;
    }

    //specials
    public String convert(int n) {
        while (n != 0) {
            if (n >= 1 && n < 100) {
                str = one_to_hund(n) + " ";
                n = 0;
            } else if (n >= 100 && n < 1000) {
                str = one_to_hund(n / 100);//since n/100 is between 0 and 9
                str = str + "hundred" + " ";
                n = n % 100;
            } else if (n >= 1000 && n < 100000) {
                str = one_to_hund(n / 1000);
                str = str + "thousand" + " ";
                n = n % 1000;
            } else if (n >= 100000 && n < 1000000) {
                System.out.println("hhtttt");
                str = one_to_hund(n / 100000);
                str = str + "hunderd" + " ";
                n = n % 100000;
            } else if (n >= 1000000 && n < 100000000) {
                str = one_to_hund(n / 1000000);
                str = str + "million" + " ";
                n = n % 1000000;
            } else if (n >= 100000000 && n < 1000000000) {
                str = one_to_hund(n / 100000000);
                str = str + "hundred" + " ";
                n = n % 100000000;
            } //            else if (n >= 1000000000 && n < 100000000000) {
            //                str = one_to_hund(n / 1000000000);
            //                str = str + "billion" + " ";
            //                n = n % 1000000000;
            //            } 
            else {
                str = "Your value is out of the range";
            }

        }
        return (str);
    }

    // output in word
    public String toWords() {
        int n;
        InsuranceRegFormController t = new InsuranceRegFormController();
        intoLc();
        n = (int) Math.round(converted.doubleValue());
        double cents = converted.doubleValue() - n;
        int centsAsInt = (int) Math.round((cents) * 100);
        System.out.println("Your LC in number is:" + converted.doubleValue());
        str = t.convert(n) + "Birr and " + one_to_hund(centsAsInt) + "cents only";
        System.out.println("You're Registered into Words!:" + "" + str);
        return ((str));

    }

    public void getDocValue() {
        docList = dataUpload.selectListOfFileByDocId(docIds);
    }

    private String clearForNew() {
        insuranceregistration = null;
        prmsContract = null;
        hrDepartments = null;
        prmsServiceProviderTo = null;
        prmsServiceProviderBranch = null;
        prmsInsuranceregistrationList = null;
        prmsInsuranceregistrationModel = null;
        prmsServiceProvider = null;
        wfPrmsProcessedDModel = null;
        prmsLuDmArchivesDModel = null;
        createorUpdateBundle = "Save";
        newOrSearchBundle = "Search";
        icone = "ui-icon-search";
        docList = null;
        renderpnlToSearchPage = false;
        wfPrmsProcessed.setProcessedOn(null);
        wfPrmsProcessed.setDecision(null);
        wfPrmsProcessed.setCommentGiven(null);
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update methods">
    public String createInsuranceInformation() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "createInsuranceInformation", dataset)) {
                if (createorUpdateBundle.equals("Save")) {
                    if (updateStatus == 0) {
                        try {
                            generateInsuranceNo();
                            insuranceregistration.setInsuranceNo(newInsuNo);
                            insuranceregistration.setHrDeptId(hrDepartments);
                            insuranceregistration.setServiceProId(prmsServiceProviderTo);
                            insuranceregistration.setContractNo(prmsContract);
                            insuranceregistration.setServiceDtId(prmsServiceProviderBranch);
                            insuranceregistration.setServiceProName(prmsServiceProvider);
                            if (fileName != null) {
                                prmsLuDmArchive.setFileName(fileName);
                                prmsLuDmArchive.setFileType(docFormat);
                                prmsLuDmArchive.setUploadFile(byteArrayData);
                                prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                            }
                            insuranceregistration.setFileRefNumber(prmsLuDmArchive);
                            insuranceregistration.setStatus(Constants.PREPARE_VALUE);
                            insuranceregistration.setProcessedOn(String.valueOf(wfPrmsProcessed.getProcessedOn()));
                            insuranceregistration.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            insuranceregistration.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                            wfPrmsProcessed.setDecision(String.valueOf(insuranceregistration.getStatus()));
                            wfPrmsProcessed.setInsuranceRegId(insuranceregistration);

                            insuranceRegisterationBeanLocal.create(insuranceregistration);
                            JsfUtil.addSuccessMessage("Insurance Requisition Information is Saved Successfully at " + newInsuNo);
                            return clearForNew();

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);
                        }

                    }
                } else if (createorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
                    try {
                        insuranceregistration.getInsuranceNo();
                        insuranceregistration.setHrDeptId(hrDepartments);
                        insuranceregistration.setServiceProId(prmsServiceProviderTo);
                        insuranceregistration.setContractNo(prmsContract);
                        insuranceregistration.setServiceDtId(prmsServiceProviderBranch);
                        insuranceregistration.setProcessedOn(String.valueOf(wfPrmsProcessed.getProcessedOn()));
                        insuranceregistration.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        insuranceregistration.setStatus(Constants.PREPARE_VALUE);
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(docFormat);
                        prmsLuDmArchive.setUploadFile(byteArrayData);
                        if (prmsLuDmArchive.getFileName() != null) {
                            insuranceregistration.setFileRefNumber(prmsLuDmArchive);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                        }

                        wfPrmsProcessed.setInsuranceRegId(insuranceregistration);
                        wfPrmsProcessed.setDecision(String.valueOf(insuranceregistration.getStatus()));
                        insuranceRegisterationBeanLocal.update(insuranceregistration);
                        insuranceregistration.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Insurance Requisition Information is Updated Successfully at" + insuranceregistration.getInsuranceNo());
                        createorUpdateBundle = "Save";
                        return clearForNew();

                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                    }
                } else if (createorUpdateBundle.equals("Update") && (workFlow.isApproveStatus() || workFlow.isCheckStatus())) {
                    try {
                        System.out.println("When Workflow");
                        if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            insuranceregistration.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            insuranceregistration.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            insuranceregistration.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            insuranceregistration.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                        }
                        insuranceRegisterationBeanLocal.update(insuranceregistration);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Insurance Requisition Information is Decided Successfully at==" + insuranceregistration.getInsuranceNo());
                        createorUpdateBundle = "Save";
                        return clearForNew();

                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addErrorMessage("Something Error Occured on Data Decision");
                    }
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
            ex.printStackTrace();
        }
        return null;
    }
    // </editor-fold>

}
