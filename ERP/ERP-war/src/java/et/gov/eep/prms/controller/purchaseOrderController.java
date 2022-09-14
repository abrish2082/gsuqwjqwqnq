package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.prms.businessLogic.AwardBeanLocal;
import et.gov.eep.prms.businessLogic.AwardDetailBeanLocal;
import et.gov.eep.prms.businessLogic.BidRegBeanLocal;
import et.gov.eep.prms.businessLogic.QuatationBeanLocal;
import et.gov.eep.prms.businessLogic.ContractInformationBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.businessLogic.FinancialEvaluationBeanLocal;
import et.gov.eep.prms.businessLogic.PurchaseOrderBeanLocal;
import et.gov.eep.prms.businessLogic.PurchaseReqBeanLocal;
import et.gov.eep.prms.entity.PrmsPurOrderDetail;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.prms.entity.PrmsPoCurrency;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsSupplyProfileDetail;
import et.gov.eep.prms.mapper.PrmsContractDetailFacade;
import et.gov.eep.prms.mapper.PrmsPurchaseRequestFacade;
import et.gov.eep.prms.mapper.PrmsQuotationDetailFacade;
import et.gov.eep.prms.mapper.PrmsQuotationFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

@Named(value = "purchaseOrderController")
@ViewScoped
public class purchaseOrderController implements Serializable {
//

    @Inject
    SessionBean SessionBean;
    @EJB
    private ContractInformationBeanLocal contractInformationBeanLocal;
    @EJB
    private PurchaseReqBeanLocal purchaseReqBeanLocal;
    @EJB
    private PurchaseOrderBeanLocal purchaseOrderBeanLocal;
    @EJB
    private AwardBeanLocal awardBeanLocal;
    @EJB
    private AwardDetailBeanLocal awardDetailBeanLocal;
    @EJB
    PrmsContractDetailFacade PrmsContractDetailFacade;
    @EJB
    QuatationBeanLocal quatationBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;

    @Inject
    PrmsSupplyProfileDetail prmsSupplyProfileDetail;
    @Inject
    private PrmsPurchaseOrder papmsPurchaseOrder;
    @Inject
    private PrmsPurchaseRequest prmsPurchaseRequest;
    @Inject
    private PrmsContract prmsContract;
    @Inject
    PrmsQuotation prmsQuotation;
    @Inject
    private PrmsAward papmsAward;
    @Inject
    private PrmsSupplyProfile eepVendorReg;
    @Inject
    private PrmsBid eepBidReg;
    @Inject
    private MmsItemRegistration mmsItemRegistration;
    @Inject
    private PrmsAwardDetail papmsAwardDetail;
    @Inject
    private PrmsContractAmendment prmsContractAmendment;
    @Inject
    PrmsBidAmend prmsBidAmend;
    @Inject
    private PrmsPurOrderDetail papmsPurchaseOrderDetail;
    @Inject
    PrmsAward prmsAward;
    @Inject
    PrmsPoCurrency prmsPoCurrency;
    List<PrmsAwardDetail> prmsAwardDetails;
    List<HrDepartments> hrDepartmentses;
    List<PrmsSupplyProfile> prmsSupplyProfiList;
    private List<PrmsAwardDetail> awardDetailslist = new ArrayList<>();
    private List<PrmsContractAmendment> contractListFromAmendment;
    List<PrmsBidAmend> bidListFromAmendment;
    List<PrmsPurchaseOrder> purchaseOrderSearchParameterLst;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<PrmsAwardDetail> papmsAwardDetailsModel;
    DataModel<PrmsSupplyProfileDetail> PrmsSupplyProfileDetailModel;
    DataModel<PrmsQuotationDetail> prmsQuotationDetailModel;
    DataModel<PrmsPoCurrency> prmsPoCurrencysModel;

    private String saveorUpdateBundle = "Save";
    double vat = 0.15;
    double groundTotal = 0.0;
    double totalSum = 0.0;
    double withHolding = 0.0;
    double unitprice = 0;
    long quantity = 0;
    double totalvat = 0.0;
    int saveStatus = 0;
    double totalPrice = 0;
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    boolean renderBidGoods = true;
    boolean renderBidService = false;
    boolean renderBidWork = false;
    boolean renderFinacialResult = true;
    boolean renderSuppFromPosQual = false;
    boolean renderInternational = false;
    boolean renderLocal;
    boolean renderBidItem = false;
    boolean renderPerfoma = false;

    private boolean performa;
    private boolean renderMarketTable;
    private boolean performaDataTable;
    private boolean serviceTable;
    private boolean renderBid = true;
    private boolean goodsTable;
    private boolean goodsDataTable;
    private boolean performatable;
    private boolean works;
    private boolean worksTable;
    private boolean worksBidNo;
    private boolean goodsBidNo;
    private boolean serviceBidNoForCons;
    private boolean serviceBidNoForNcons;
    private boolean serviceType;
    private boolean consultancy;
    private boolean nonConsultancy;
    private boolean itemName;
    private boolean serviceName;
    private boolean worksName;
    private boolean renderItemUnitMeasure;
    private boolean renderServiceUnitMeasure;
    private boolean renderWorksUnitMeasure;
    private boolean renderItemAwardType;
    private boolean renderServiceAwardType;
    private boolean renderWorksAwardType;
    private boolean renderItemBidderCode;
    private boolean renderServiceBidderCode;
    private boolean renderWorksBidderCode;
    private boolean renderItemAddButon;
    private boolean renderServiceAddButon;
    private boolean renderServiceWorksAddButon;
    private String icone = "ui-icon-plus";
    private boolean goodsFrmBid;
    private boolean servicefrmBid;
    private boolean workNameFrmBid;

    private boolean goodsFrmProfoma;
    private boolean serviceFrmProfoma;
    private boolean workFrmProfoma;

    private boolean purchaseTypeFrmBid = true;
    private boolean purchaseTypefrmPerforma;
    private String activeIndex;
    int updateStatus = 0;
    private String selectOptPartyName;
    DataModel<PrmsPurOrderDetail> prmsPurOrderDetailsModel;
    DataModel<PrmsPurchaseOrder> prmsPurchaseOrdersModel;
    Set<String> addressCheck = new HashSet<>();
    private PrmsPurchaseOrder selectPurchaseOrder;
    @EJB
    PrmsQuotationFacade prmsQuotationFacade;
    @EJB
    PrmsPurchaseRequestFacade PrmsPurchaseRequestFacade;
    @Inject
    WorkFlow workFlow;
    private String selectedValue = "";
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public purchaseOrderController() {

    }

    boolean renderDecision;
    boolean isRenderCreate;

    @PostConstruct
    public void init() {

        System.out.println("-------------------+++--------------------------");

//        Date currentDate = Calendar.getInstance().getTime();
//        wfPrmsProcessed.setProcessedOn(currentDate);
//        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
//        System.out.println("is check" + workFlow.isCheckStatus() + "is app "
//                + workFlow.isApproveStatus()
//                + "is prep " + workFlow.isPrepareStatus());
        setLogerName(SessionBean.getUserName());
        System.out.println(" [ SessionBean.getUserName() ]  ==>" + SessionBean.getUserName());
        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(workFlow.getUserName());
        System.out.println("[User Name]==>" + getUserName());
//        Date currentDate = Calendar.getInstance().getTime();
//        wfPrmsProcessed.setProcessedOn(currentDate);
//******************************************************************************
//******************************************************************************
        System.out.println("is check" + workFlow.isCheckStatus() + "is app "
                + workFlow.isApproveStatus() + "is prep "
                + workFlow.isPrepareStatus());
        System.out.println("[Here IS ERROR==>]" + workFlow.getUserStatus());
//        setUserName(workFlow.getUserName());
        try {
//        prmsQuotationList = prmsQuotationFacade.findAll();
//        prmsQuotationList = prmsQuotationFacade.findAll();
            purchaseOrderLis1 = purchaseOrderBeanLocal.findPreparedPoNumbers();
//            wfPrmsProcessed.setProcessedBy(workFlow.getUserName());//SessionBean.getUserId());
            System.out.println("---------------------------------------------");
            String temp_ = workFlow.getUserName();
            System.out.println("USER NAME IS==>" + temp_);
            System.out.println("---------------------------------------------");
//            setUserName(workFlow.getUserName());

//            setLogerName(SessionBean.getUserName());
//            wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
            setUserName(temp_);
            System.out.println("[User Name]==>" + getUserName());
            System.out.println("USER NAME TEMP==>" + temp_);

            System.out.println("is check-----------" + workFlow.isCheckStatus() + "is app------------ "
                    + workFlow.isApproveStatus() + "is prep------------------- " + workFlow.isPrepareStatus());

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

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
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

    public List<HrDepartments> getHrDepartmentses() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartmentses;
    }

    public void setHrDepartmentses(List<HrDepartments> hrDepartmentses) {
        this.hrDepartmentses = hrDepartmentses;
    }

    List<PrmsQuotation> prmsQuotationList;
    List<PrmsPurchaseRequest> PrmsPurchaseRequestList;

    public boolean isGoodsFrmBid() {
        return goodsFrmBid;
    }

    public void setGoodsFrmBid(boolean goodsFrmBid) {
        this.goodsFrmBid = goodsFrmBid;
    }

    public boolean isServicefrmBid() {
        return servicefrmBid;
    }

    public void setServicefrmBid(boolean servicefrmBid) {
        this.servicefrmBid = servicefrmBid;
    }

    public boolean isWorkNameFrmBid() {
        return workNameFrmBid;
    }

    public void setWorkNameFrmBid(boolean workNameFrmBid) {
        this.workNameFrmBid = workNameFrmBid;
    }

    public boolean isGoodsFrmProfoma() {
        return goodsFrmProfoma;
    }

    public void setGoodsFrmProfoma(boolean goodsFrmProfoma) {
        this.goodsFrmProfoma = goodsFrmProfoma;
    }

    public boolean isServiceFrmProfoma() {
        return serviceFrmProfoma;
    }

    public void setServiceFrmProfoma(boolean serviceFrmProfoma) {
        this.serviceFrmProfoma = serviceFrmProfoma;
    }

    public boolean isWorkFrmProfoma() {
        return workFrmProfoma;
    }

    public void setWorkFrmProfoma(boolean workFrmProfoma) {
        this.workFrmProfoma = workFrmProfoma;
    }

    public PrmsAward getPrmsAward() {
        if (prmsAward == null) {
            prmsAward = new PrmsAward();
        }
        return prmsAward;
    }

    public void setPrmsAward(PrmsAward prmsAward) {
        this.prmsAward = prmsAward;
    }

    public PrmsPoCurrency getPrmsPoCurrency() {
        if (prmsPoCurrency == null) {
            prmsPoCurrency = new PrmsPoCurrency();
        }
        return prmsPoCurrency;
    }

    public void setPrmsPoCurrency(PrmsPoCurrency prmsPoCurrency) {
        this.prmsPoCurrency = prmsPoCurrency;
    }

    public boolean isPurchaseTypeFrmBid() {
        return purchaseTypeFrmBid;
    }

    public void setPurchaseTypeFrmBid(boolean purchaseTypeFrmBid) {
        this.purchaseTypeFrmBid = purchaseTypeFrmBid;
    }

    public boolean isPurchaseTypefrmPerforma() {
        return purchaseTypefrmPerforma;
    }

    public void setPurchaseTypefrmPerforma(boolean purchaseTypefrmPerforma) {
        this.purchaseTypefrmPerforma = purchaseTypefrmPerforma;
    }

    public DataModel<PrmsSupplyProfileDetail> getPrmsSupplyProfileDetailModel() {
        return PrmsSupplyProfileDetailModel;
    }

    public DataModel<PrmsPoCurrency> getPrmsPoCurrencysModel() {
        if (prmsPoCurrencysModel == null) {
            prmsPoCurrencysModel = new ListDataModel<>();
        }
        return prmsPoCurrencysModel;
    }

    public void setPrmsPoCurrencysModel(DataModel<PrmsPoCurrency> prmsPoCurrencysModel) {
        this.prmsPoCurrencysModel = prmsPoCurrencysModel;
    }

    public DataModel<PrmsQuotationDetail> getPrmsQuotationDetailModel() {
        return prmsQuotationDetailModel;
    }

    public void setPrmsQuotationDetailModel(DataModel<PrmsQuotationDetail> prmsQuotationDetailModel) {
        this.prmsQuotationDetailModel = prmsQuotationDetailModel;
    }

    public void setPrmsSupplyProfileDetailModel(DataModel<PrmsSupplyProfileDetail> PrmsSupplyProfileDetailModel) {
        this.PrmsSupplyProfileDetailModel = PrmsSupplyProfileDetailModel;
    }

    public List<PrmsQuotation> getPrmsQuotationList() {
        return prmsQuotationList;
    }

    public List<PrmsPurchaseRequest> getPrmsPurchaseRequestList() {
        if (PrmsPurchaseRequestList == null) {
            PrmsPurchaseRequestList = new ArrayList<>();
        }
        return PrmsPurchaseRequestList;
    }

    public void setPrmsPurchaseRequestList(List<PrmsPurchaseRequest> PrmsPurchaseRequestList) {
        this.PrmsPurchaseRequestList = PrmsPurchaseRequestList;
    }

    public void setPrmsQuotationList(List<PrmsQuotation> prmsQuotationList) {
        this.prmsQuotationList = prmsQuotationList;
    }

    public PrmsPurchaseOrder getSelectPurchaseOrder() {
        return selectPurchaseOrder;
    }

    public void setSelectPurchaseOrder(PrmsPurchaseOrder selectPurchaseOrder) {
        this.selectPurchaseOrder = selectPurchaseOrder;
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

    public PrmsContract getPrmsContract() {
        if (prmsContract == null) {
            prmsContract = new PrmsContract();
        }
        return prmsContract;
    }

    public void setPrmsContract(PrmsContract prmsContract) {
        this.prmsContract = prmsContract;
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

    public DataModel<PrmsPurOrderDetail> getPrmsPurOrderDetailsModel() {

        if (prmsPurOrderDetailsModel == null) {
            prmsPurOrderDetailsModel = new ArrayDataModel<>();
        }
        return prmsPurOrderDetailsModel;
    }

    public void setPrmsPurOrderDetailsModel(DataModel<PrmsPurOrderDetail> prmsPurOrderDetailsModel) {
        this.prmsPurOrderDetailsModel = prmsPurOrderDetailsModel;
    }

    public DataModel<PrmsPurchaseOrder> getPrmsPurchaseOrdersModel() {
        if (prmsPurchaseOrdersModel == null) {
            prmsPurchaseOrdersModel = new ArrayDataModel<>();
        }
        return prmsPurchaseOrdersModel;
    }

    public void setPrmsPurchaseOrdersModel(DataModel<PrmsPurchaseOrder> prmsPurchaseOrdersModel) {
        this.prmsPurchaseOrdersModel = prmsPurchaseOrdersModel;
    }
    List<PrmsPurchaseOrder> prmsPurchaseOrders;

    public List<PrmsPurchaseOrder> getPrmsPurchaseOrders() {
        if (prmsPurchaseOrders == null) {
            prmsPurchaseOrders = new ArrayList<>();
        }
        return prmsPurchaseOrders;
    }

    public void setPrmsPurchaseOrders(List<PrmsPurchaseOrder> prmsPurchaseOrders) {
        this.prmsPurchaseOrders = prmsPurchaseOrders;
    }

    public void recreateprmsPurOrderDetailsModel() {

        List<PrmsPurOrderDetail> ppods = new ArrayList<>();
        totalSum = 0.0;

        for (int i = 0; i < papmsPurchaseOrder.getPrmsPurOrderDetailList().size(); i++) {
            papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).setTotals(papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i)
                    .getUnitPrice().doubleValue() * papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getQuantity().doubleValue());
            totalSum = totalSum + papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getTotals();
        }

        totalvat = 0.15 * totalSum;
        groundTotal = totalvat + totalSum;

        if (totalSum >= 10000) {

            withHolding = totalSum * 0.02;
            setWithHolding(withHolding);
            papmsPurchaseOrder.setWithhold(withHolding);
        }

        prmsPurOrderDetailsModel = null;
        prmsPurOrderDetailsModel = new ListDataModel(
                papmsPurchaseOrder.getPrmsPurOrderDetailList());
    }

    public void recreatePrmsSupplyProfileDetailModel() {

        PrmsSupplyProfileDetailModel = null;
        PrmsSupplyProfileDetailModel = new ListDataModel(
                eepVendorReg.getPrmsSupplyProfileDetailList());
    }

    public void recreateprmsQuotationDetailModel() {

        for (int i = 0; i < prmsQuotation.getPrmsQuotationDetailList().size(); i++) {

            unitprice = prmsQuotation.getPrmsQuotationDetailList().get(i).getUnitPrice();
            quantity = prmsQuotation.getPrmsQuotationDetailList().get(i).getQuantity();
            totalPrice = quantity * unitprice;
            totalSum = totalSum + totalPrice;
            setTotalSum(totalSum);
            totalvat = prmsQuotation.getPrmsQuotationDetailList().get(i).getSupId().getVatTypeId().getVatRate() * totalSum;
            setTotalvat(totalvat);
            groundTotal = totalSum + totalvat;
            setGroundTotal(groundTotal);
        }

        prmsQuotationDetailModel = null;
        prmsQuotationDetailModel = new ListDataModel(prmsQuotation.getPrmsQuotationDetailList());
    }

    public void recreateprmsContractDetailsModel() {

        totalSum = 0.0;

        for (int i = 0; i < prmsContract.getPrmsContractDetailList().size(); i++) {
            prmsContract.getPrmsContractDetailList().get(i).setTotals(prmsContract.getPrmsContractDetailList().get(i)
                    .getUnitPrice().doubleValue() * prmsContract.getPrmsContractDetailList().get(i).getQuantity().doubleValue());
            totalSum = totalSum + prmsContract.getPrmsContractDetailList().get(i).getTotals();
        }

        totalvat = 0.15 * totalSum;
        groundTotal = totalvat + totalSum;

        if (totalSum >= 10000) {
            withHolding = totalSum * 0.02;
        }

        prmsContractDetailmodel = null;
        prmsContractDetailmodel = new ListDataModel(new ArrayList(prmsContract.getPrmsContractDetailList()));

    }

    public void updateDatail() {
        papmsPurchaseOrderDetail = getPrmsPurOrderDetailsModel().getRowData();
    }

    public void recreateprmsPurchaseOrdersModel() {
        prmsPurchaseOrdersModel = null;
        prmsPurchaseOrdersModel = new ListDataModel(new ArrayList<>(getPrmsPurchaseOrders()));
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
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

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSelectOptPartyName() {
        return selectOptPartyName;
    }

    public void setSelectOptPartyName(String selectOptPartyName) {
        this.selectOptPartyName = selectOptPartyName;
    }

    public void createNewParty() {

        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
        }
    }

    public void newBtnAction() {
        clearpurchaseorder();
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

    public void goBackToSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public void searchBtnAction() {
        renderPnlManPage = true;
        renderPnlCreateParty = false;
    }

    public boolean isRenderLocal() {
        return renderLocal;
    }

    public void setRenderLocal(boolean renderLocal) {
        this.renderLocal = renderLocal;
    }

    public boolean isRenderBidItem() {
        return renderBidItem;
    }

    public void setRenderBidItem(boolean renderBidItem) {
        this.renderBidItem = renderBidItem;
    }

    public boolean isRenderPerfoma() {
        return renderPerfoma;
    }

    public void setRenderPerfoma(boolean renderPerfoma) {
        this.renderPerfoma = renderPerfoma;
    }

    public boolean isGoodsTable() {
        return goodsTable;
    }

    public void setGoodsTable(boolean goodsTable) {
        this.goodsTable = goodsTable;
    }

    public boolean isGoodsDataTable() {
        return goodsDataTable;
    }

    public void setGoodsDataTable(boolean goodsDataTable) {
        this.goodsDataTable = goodsDataTable;
    }

    public boolean isGoodsBidNo() {
        return goodsBidNo;
    }

    public void setGoodsBidNo(boolean goodsBidNo) {
        this.goodsBidNo = goodsBidNo;
    }

    public boolean isConsultancy() {
        return consultancy;
    }

    public void setConsultancy(boolean consultancy) {
        this.consultancy = consultancy;
    }

    public boolean isItemName() {
        return itemName;
    }

    public void setItemName(boolean itemName) {
        this.itemName = itemName;
    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = prmsPurchaseOrdersModel.getRowIndex();
        papmsPurchaseOrder = prmsPurchaseOrders.get(rowIndex);

        recreateprmsPurchaseOrdersModel();
        recreatePurchaseOrderDtlModel();

    }

    public void onContactRowEdit(RowEditEvent event) {

        int rowIndex = prmsPurOrderDetailsModel.getRowIndex();
//        PrmsPurOrderDetail contactPerson = new PrmsPurOrderDetail();

        PrmsPurOrderDetail comContPerson = (PrmsPurOrderDetail) event.getObject();

        boolean found = false;
        for (int i = 0; i < papmsPurchaseOrder.getPrmsPurOrderDetailList().size(); i++) {
            if (i != rowIndex) {
//                if (papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getItemName().equals(comContPerson.getItemName())
//                        ) {
//                    found = true;
//                    break;
//                }
            }
        }
        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");

            //  comContPerson.setItemName(null);
            papmsPurchaseOrder.getPrmsPurOrderDetailList().set(rowIndex, comContPerson);
            recreatePurchaseOrderDtlModel();
        } else {
            papmsPurchaseOrder.getPrmsPurOrderDetailList().set(rowIndex, comContPerson);
            recreatePurchaseOrderDtlModel();
        }
    }

    public void onContactRowCancel(RowEditEvent event) {

    }

    /**
     *
     */
    public void removeContactPersonInfo() {

        int rowIndex = prmsPurOrderDetailsModel.getRowIndex();
        papmsPurchaseOrderDetail = papmsPurchaseOrder.getPrmsPurOrderDetailList().get(rowIndex);
        papmsPurchaseOrder.getPrmsPurOrderDetailList().remove(rowIndex);
        recreateprmsPurOrderDetailsModel();

        if (saveorUpdateBundle.equals("Update")) {
            purchaseOrderBeanLocal.deletePurchaseOredeDtail(papmsPurchaseOrderDetail);
        }
    }

    public void recreateContractCurrDetailModel() {
        prmsPoCurrencysModel = null;
        prmsPoCurrencysModel = new ListDataModel(new ArrayList<>(papmsPurchaseOrder.getPrmsPoCurrencyList()));
    }

    public void searchPurchaseOrder() {

        papmsPurchaseOrder.setRequestedBy(String.valueOf(workFlow.getUserAccount()));
        prmsPurchaseOrders = purchaseOrderBeanLocal.searchPurchaseOrderNo(0, workFlow.getUserAccount());

//         for (int i = 0; i < papmsPurchaseOrder.getPrmsContractCurrencyDetailList().size(); i++) {
//            FmsLuCurrency tempfmsLuCurrency = papmsPurchaseOrder.getPrmsContractCurrencyDetailList().get(i).getCurrencyId();
//            //savedCurrencyIds.add(tempfmsLuCurrency);
//        }
        recreateprmsPurchaseOrdersModel();
        recreateContractCurrDetailModel();
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public List<PrmsAwardDetail> getPrmsAwardDetails() {
        if (prmsAwardDetails == null) {
            prmsAwardDetails = new ArrayList<>();
        }
        return prmsAwardDetails;
    }

    public void setPrmsAwardDetails(List<PrmsAwardDetail> prmsAwardDetails) {
        this.prmsAwardDetails = prmsAwardDetails;
    }

    public PrmsPurchaseOrder getPapmsPurchaseOrder() {
        if (papmsPurchaseOrder == null) {
            papmsPurchaseOrder = new PrmsPurchaseOrder();
        }
        return papmsPurchaseOrder;
    }

    public void setPapmsPurchaseOrder(PrmsPurchaseOrder papmsPurchaseOrder) {
        this.papmsPurchaseOrder = papmsPurchaseOrder;
    }

    public List<PrmsAwardDetail> getAwardList() {
        awardDetailslist = awardDetailBeanLocal.findAll();

        return awardDetailslist;
    }

    public SelectItem[] getAwardLists() {
        return JsfUtil.getSelectItems(purchaseOrderBeanLocal.getAwardLists(), true);

    }

    public PrmsSupplyProfile getEepVendorReg() {
        if (eepVendorReg == null) {
            eepVendorReg = new PrmsSupplyProfile();
        }

        return eepVendorReg;
    }

    public void setEepVendorReg(PrmsSupplyProfile eepVendorReg) {
        this.eepVendorReg = eepVendorReg;
    }

    public PrmsBid getEepBidReg() {
        if (eepBidReg == null) {
            eepBidReg = new PrmsBid();
        }
        return eepBidReg;
    }

    public void setEepBidReg(PrmsBid eepBidReg) {
        this.eepBidReg = eepBidReg;
    }

    public AwardDetailBeanLocal getAwardDetailBeanLocal() {
        return awardDetailBeanLocal;
    }

    public void setAwardDetailBeanLocal(AwardDetailBeanLocal awardDetailBeanLocal) {
        this.awardDetailBeanLocal = awardDetailBeanLocal;
    }

    public List<PrmsAwardDetail> getAwardDetailslist() {
        return awardDetailslist;
    }

    public void setAwardDetailslist(List<PrmsAwardDetail> awardDetailslist) {
        this.awardDetailslist = awardDetailslist;
    }

    public ArrayList<PrmsAward> getListOfApprovedInspReqList() {
        return listOfApprovedInspReqList;
    }

    public void setListOfApprovedInspReqList(ArrayList<PrmsAward> listOfApprovedInspReqList) {
        this.listOfApprovedInspReqList = listOfApprovedInspReqList;
    }

    public List<PrmsAwardDetail> getAwardDetails() {
        return awardDetails;
    }

    public void setAwardDetails(List<PrmsAwardDetail> awardDetails) {
        this.awardDetails = awardDetails;
    }

    public PrmsPurOrderDetail getPapmsPurchaseOrderDetail() {
        if (papmsPurchaseOrderDetail == null) {
            papmsPurchaseOrderDetail = new PrmsPurOrderDetail();
        }
        return papmsPurchaseOrderDetail;
    }

    public void setPapmsPurchaseOrderDetail(PrmsPurOrderDetail papmsPurchaseOrderDetail) {
        this.papmsPurchaseOrderDetail = papmsPurchaseOrderDetail;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public PurchaseOrderBeanLocal getPurchaseOrderBeanLocal() {
        return purchaseOrderBeanLocal;
    }

    public void setPurchaseOrderBeanLocal(PurchaseOrderBeanLocal purchaseOrderBeanLocal) {
        this.purchaseOrderBeanLocal = purchaseOrderBeanLocal;
    }

    public AwardBeanLocal getAwardBeanLocal() {
        return awardBeanLocal;
    }

    public PrmsAwardDetail getPapmsAwardDetail() {
        return papmsAwardDetail;
    }

    public void setPapmsAwardDetail(PrmsAwardDetail papmsAwardDetail) {
        this.papmsAwardDetail = papmsAwardDetail;
    }

    public void setAwardBeanLocal(AwardBeanLocal awardBeanLocal) {
        this.awardBeanLocal = awardBeanLocal;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getGroundTotal() {
        return groundTotal;
    }

    public void setGroundTotal(double groundTotal) {
        this.groundTotal = groundTotal;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public double getWithHolding() {
        return withHolding;
    }

    public void setWithHolding(double withHolding) {
        this.withHolding = withHolding;
    }

    public double getTotalvat() {
        return totalvat;
    }

    public void setTotalvat(double totalvat) {
        this.totalvat = totalvat;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<PrmsPurchaseOrder> searchPurchaseOrderByPoNo(String PoNo) {
        ArrayList<PrmsPurchaseOrder> Purchaseorder = null;
        papmsPurchaseOrder.setPacNo(PoNo);

        Purchaseorder = purchaseOrderBeanLocal.searchPurchaseOrderByPoNo(papmsPurchaseOrder);

        return Purchaseorder;
    }

    public PrmsAward getPapmsAward() {

        if (papmsAward == null) {
            papmsAward = new PrmsAward();
        }

        return papmsAward;
    }

    public void setPapmsAward(PrmsAward papmsAward) {
        this.papmsAward = papmsAward;
    }

    public DataModel<PrmsAwardDetail> getPapmsAwardDetailsModel() {
        return papmsAwardDetailsModel;
    }

    public void setPapmsAwardDetailsModel(DataModel<PrmsAwardDetail> papmsAwardDetailsModel) {
        this.papmsAwardDetailsModel = papmsAwardDetailsModel;
    }

    public List<PrmsPurchaseOrder> getPurchaseOrderSearchParameterLst() {
        if (purchaseOrderSearchParameterLst == null) {
            purchaseOrderSearchParameterLst = new ArrayList<>();
            purchaseOrderSearchParameterLst = purchaseOrderBeanLocal.getParamNameList();
        }
        return purchaseOrderSearchParameterLst;
    }

    public void setPurchaseOrderSearchParameterLst(List<PrmsPurchaseOrder> purchaseOrderSearchParameterLst) {
        this.purchaseOrderSearchParameterLst = purchaseOrderSearchParameterLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            papmsPurchaseOrder.setColumnName(event.getNewValue().toString());
            papmsPurchaseOrder.setColumnValue(null);
        }
    }

    public String addPurchaseOrderDetail() {

        papmsPurchaseOrder.addPurchaseOrderDetialInfo(papmsPurchaseOrderDetail);
        recreatePurchaseOrderDtlModel();
        clearPopUp();
        return null;

    }
    List l = new ArrayList<PrmsPurOrderDetail>();

    public void recreatePurchaseOrderDtlModel() {

        l = null;
        l = new ArrayList<PrmsPurOrderDetail>();
        l = papmsPurchaseOrder.getPrmsPurOrderDetailList();
        prmsPurOrderDetailsModel = null;
        prmsPurOrderDetailsModel = new ListDataModel(l);

    }

    private void clearPopUp() {

        papmsPurchaseOrderDetail = null;
    }

//    Ooooops
    public void save_PurchaseOrder() {

        try {
            AAA securityService = new AAA();
            IAdministration security
                    = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");

            if (security.checkAccess(SessionBean.getUserName(), "save_PurchaseOrder", dataset)) {

                if (saveorUpdateBundle.equals("Save")) {

                    if (updateStatus == 0) {

                        try {
                            Date currentDate = Calendar.getInstance().getTime();
                            papmsPurchaseOrder.setPacNo(newcheckListNo);
                            papmsPurchaseOrder.setRequestedBy(String.valueOf(SessionBean.getUserId()));
                            papmsPurchaseOrder.setRigistrationDate(currentDate);
                            //papmsPurchaseOrder.getPrmsWorkFlowProccedList().add(wfPrmsProcessed);
                            wfPrmsProcessed.setPurchaseOrderId(papmsPurchaseOrder);

                            System.out.println("<=======================================================");
                            System.out.println("<===== [ - Decisions - ]" + wfPrmsProcessed.getDecision());
                            System.out.println("<=======================================================");
//                        papmsContract.setStatus(Integer.parseInt(wfPrmsProcessed.getDecision()));
                            papmsPurchaseOrder.setStatus(Constants.PREPARE_VALUE);
                            System.out.println("<===== [ - 02 - ] =====================");
//                            papmsPurchaseOrder.setAwardId(papmsAward);                         
                            System.out.println("<===== [ - 2 - ] =====================");
                            papmsPurchaseOrder.setRequestedBy(String.valueOf(SessionBean.getUserId()));
                            papmsPurchaseOrder.setRigistrationDate(currentDate);

//                            papmsContract.getPrmsWorkFlowProccedList().add(wfPrmsProcessed);
                            wfPrmsProcessed.setDecision(String.valueOf(papmsPurchaseOrder.getStatus()));//-
                            wfPrmsProcessed.setPurchaseOrderId(papmsPurchaseOrder);
//                            wfPrmsProcessed.setProcessedOn(currentDate);
                            wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
//                            wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);//-
                            int sizes = papmsPurchaseOrder.getPrmsPoCurrencyList().size();
                            System.out.println("PO Details ==>" + sizes);
                            for (int count = 0; count < sizes; count++) {
                                prmsPoCurrency = new PrmsPoCurrency();
                                prmsPoCurrency.setCurrencyId(papmsPurchaseOrder.getPrmsPoCurrencyList().get(count).getCurrencyId());
                                prmsPoCurrency.setPoId(papmsPurchaseOrder.getPrmsPoCurrencyList().get(count).getPoId());
                                prmsPoCurrency.setAmount(papmsPurchaseOrder.getPrmsPoCurrencyList().get(count).getAmount());
//                                papmsPurchaseOrder.getPrmsPoCurrencyList().get(count).setPoId(papmsPurchaseOrder);
                            }
//                            
                            System.out.println("now PO Currency Saved " + papmsPurchaseOrder.getPrmsPoCurrencyList().size());
                            purchaseOrderBeanLocal.create(papmsPurchaseOrder);
                            wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            System.out.println("22222");
//                            papmsPurchaseOrder.getPrmsPoCurrencyList().add(prmsPoCurrency);
                            JsfUtil.addSuccessMessage("Purchase Order Information is Successfuly Registered!");
                            clearpurchaseorder();

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);
                        }
                    }

                } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equals("Update")) {

                    try {
                        purchaseOrderBeanLocal.update(papmsPurchaseOrder);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Purchase Order Information is Successfuly updated!!");
                        saveorUpdateBundle = "Save";
                        clearpurchaseorder();

                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addErrorMessage("Something Error occured on Data modified");
                    }

                } else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equals("Update")) {

                    try {
                        if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                            System.out.println("ONE 11111111111");
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            papmsPurchaseOrder.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                            System.out.println("ONE 2");
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            papmsPurchaseOrder.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                            System.out.println("ONE 3");
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            papmsPurchaseOrder.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                            System.out.println("ONE 4");
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            papmsPurchaseOrder.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                        }

                        papmsPurchaseOrder.setStatus(workFlow.getUserStatus());
                        purchaseOrderBeanLocal.update(papmsPurchaseOrder);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Purchase Order Information has been Sucessfuly Approved");
                        clearpurchaseorder();
                        saveorUpdateBundle = "Save";
                        updateStatus = 0;

                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                    }
                }

//                else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equals("Update")) {
//
//                    try {
//                        if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
//                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
//                            papmsPurchaseOrder.setStatus(Constants.APPROVE_VALUE);
//                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
//                        } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
//                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
//                            papmsPurchaseOrder.setStatus(Constants.CHECK_APPROVE_VALUE);
//                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
//                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
//                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
//                            papmsPurchaseOrder.setStatus(Constants.APPROVE_REJECT_VALUE);
//                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
//                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
//                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
//                            papmsPurchaseOrder.setStatus(Constants.CHECK_REJECT_VALUE);
//                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
//                        }
//
//                        System.out.println("[1]. setDecision===>" + wfPrmsProcessed.getDecision());
//                        System.out.println("******************************************************");
//                        wfPrmsProcessed.setDecision(String.valueOf(papmsPurchaseOrder.getStatus()));//-
//                        wfPrmsProcessed.setPurchaseOrderId(papmsPurchaseOrder);
//                        Date currentDate = Calendar.getInstance().getTime();
//                        wfPrmsProcessed.setProcessedOn(currentDate);
//                        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
//                        System.out.println("******************************************************");
//
//                        purchaseOrderBeanLocal.update(papmsPurchaseOrder);
////                        saveWorkFlowInformation();
//                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);// AFTER supplierSpecficationBeanLocal.update(prmsSuppSpecification); 
//                        JsfUtil.addSuccessMessage("Contract Information is Successfuly Updated !!");
//                        saveorUpdateBundle = "Save";
//                        clearpurchaseorder();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
//                    }
//                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<>(
                        qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                //...add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    public String save_PurchaseOrder() {
//
//        try {
//            if (saveorUpdateBundle.equals("Save")) {
//                papmsPurchaseOrder.setPacNo(newcheckListNo);
//                purchaseOrderBeanLocal.create(papmsPurchaseOrder);
//                JsfUtil.addSuccessMessage("Purchase Order Information is Successfuly rigistered!!");
//            } else {
//                purchaseOrderBeanLocal.update(papmsPurchaseOrder);
//                saveorUpdateBundle = "Save";
//                JsfUtil.addSuccessMessage("Purchase Order Information is Successfuly updated!!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JsfUtil.addErrorMessage("some thing occured on save");
//        }
//        return clearpurchaseorder();
//
//    }
    public void searchPoId(SelectEvent event) {

        String PoNo = event.getObject().toString();
        papmsPurchaseOrder.setPacNo(PoNo);
        papmsPurchaseOrder = purchaseOrderBeanLocal.getPoId(papmsPurchaseOrder);
        saveorUpdateBundle = "Update";
        saveStatus = 1;
        int sizes = papmsPurchaseOrder.getPrmsPurOrderDetailList().size();
        for (int i = 0; i < sizes; i++) {

            unitprice = papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getUnitPrice();
            quantity = papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getQuantity();

            totalPrice = quantity * unitprice;
            totalSum = totalSum + totalPrice;
            totalvat = 0.15 * totalSum;
            groundTotal = totalSum + totalvat;

            if (totalSum >= 10000) {
                withHolding = totalSum * 0.02;
            }
        }

        recreatePurchaseOrderDtlModel();

    }

    public SelectItem[] getApprovedAwardList() {
        List<String> array = null;
        array = purchaseOrderBeanLocal.getApprovedWithBidPurchaseTypePRList("0");
        return JsfUtil.getSelectItems(array, true);

    }

    ArrayList<PrmsAward> listOfApprovedInspReqList;

    public SelectItem[] getApprovedInspectionReqLists() {
        listOfApprovedInspReqList = new ArrayList<>();

        listOfApprovedInspReqList = purchaseOrderBeanLocal.getInspectionListByStatus(papmsAward);
        return JsfUtil.getSelectItems(listOfApprovedInspReqList, true);
    }

    public void handleApprovedAwardList(ValueChangeEvent event) {

        String requestAttributes[] = event.getNewValue().toString().split("--");

        String id = (requestAttributes[0]);

        papmsAward.setAwardId(id);

        papmsAward = purchaseOrderBeanLocal.SearchAwardByAwardId(id);

        saveStatus = 1;
        recreateAwardDtlModelDetail();

    }

    public void recreateAwardDtlModelDetail() {
        papmsAwardDetailsModel = null;
        papmsAwardDetailsModel = new ListDataModel(new ArrayList<>(papmsAward.getPrmsAwardDetailList()));

    }

    List<PrmsAwardDetail> awardDetails;

    private String clearpurchaseorder() {

        eepBidReg = null;
        prmsContract = null;
        eepBidReg = null;
        prmsPoCurrencysModel = null;
        papmsPurchaseOrder = null;
        hrDepartmentsesList = null;
        prmsContractList = null;
        prmsPurOrderDetailsModel = null;
        PrmsPurchaseRequestList = null;
        prmsContractDetailmodel = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        groundTotal = 0.0;
        totalSum = 0.0;
        totalvat = 0.0;
        withHolding = 0.0;

        wfPrmsProcessed = null;

        return null;
    }

    public SelectItem[] getListOfVendorName() {
        return JsfUtil.getSelectItems(purchaseOrderBeanLocal.VendorName(), true);

    }

    public void getValueofAwardNo(ValueChangeEvent event) {

        if (null != event.getNewValue().toString()) {
            String AwardNo = event.getNewValue().toString();
            prmsAwardDetails = purchaseOrderBeanLocal.getAwardDetailLists(AwardNo);

            for (int i = 0; i < prmsAwardDetails.size(); i++) {
                papmsPurchaseOrderDetail = new PrmsPurOrderDetail();
                //  papmsPurchaseOrderDetail.setCode((prmsAwardDetails.get(i).getMaterialId().toString()));
//                papmsPurchaseOrderDetail.setItemName(String.valueOf(prmsAwardDetails.get(i).getItemName()));
                papmsPurchaseOrderDetail.setUnitMeasure(String.valueOf(prmsAwardDetails.get(i).getUnitMeasure()));
                papmsPurchaseOrderDetail.setQuantity(new Long(prmsAwardDetails.get(i).getQuantity()));
//                papmsPurchaseOrderDetail.setUnitPrice((prmsAwardDetails.get(i).getUnitPrice()));
//
//                long abc = prmsAwardDetails.get(i).getQuantity();
//                papmsPurchaseOrderDetail.setQuantity(abc);
//                quantity = papmsPurchaseOrderDetail.getQuantity();
//                long ab = prmsAwardDetails.get(i).getUnitPrice();
//                papmsPurchaseOrderDetail.setUnitPrice(ab);
                unitprice = papmsPurchaseOrderDetail.getUnitPrice();
                double totalprice = quantity * unitprice;
                papmsPurchaseOrderDetail.setTotals(totalprice);
                totalSum = totalSum + totalprice;
                totalvat = 0.15 * totalSum;
                groundTotal = totalSum + totalvat;

                if (totalSum >= 10000) {
                    withHolding = totalSum * 0.02;
                }
                papmsPurchaseOrder.addPurchaseOrderDetialInfo(papmsPurchaseOrderDetail);
                //  papmsPurchaseOrder.getPrmsPurOrderDetailList().add(papmsPurchaseOrderDetail);

            }
            recreatePurchaseOrderDtlModel();
        }

    }

    public SelectItem[] getListdepartment() {
        return JsfUtil.getSelectItems(purchaseOrderBeanLocal.searchdeptName(), true);

    }

    public SelectItem[] bidNumberList() {
        return JsfUtil.getSelectItems(purchaseOrderBeanLocal.bidNumberList(), true);

    }

    public SelectItem[] contractList() {
        return JsfUtil.getSelectItems(contractInformationBeanLocal.ContractList(), true);

    }

    public SelectItem[] getPrList() {
        return JsfUtil.getSelectItems(purchaseReqBeanLocal.getPurchReqNo(), true);

    }
    String newcheckListNo;
    String LastcheckListNo = "0";

    public String generateCheckListNo() {

        PrmsPurchaseOrder LastCheckNo = purchaseOrderBeanLocal.getLastPaymentNo();
        if (LastCheckNo != null) {
            LastcheckListNo = LastCheckNo.getPoId();
        }
        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();
        int newcheckListN = 0;

        if (LastcheckListNo.equals("0")) {
            newcheckListN = 1;
            newcheckListNo = "po-" + newcheckListN + "/" + f.format(now);
        } else {
            String[] lastInspNos = LastcheckListNo.split("-");
            String lastDatesPatern = lastInspNos[0];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            newcheckListN = Integer.parseInt(lastDatesPaterns[0]);
            newcheckListN = newcheckListN + 1;
            newcheckListNo = "po-" + newcheckListN + "/" + f.format(now);
        }

        return newcheckListNo;
    }

    public String generateSpecficationNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = papmsPurchaseOrder.getPacNo();

        } else {
            PrmsPurchaseOrder LastCheckNo = purchaseOrderBeanLocal.getLastPaymentNo();
            if (LastCheckNo != null) {
                LastcheckListNo = LastCheckNo.getPoId();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newBidNoLast = 0;
            if (LastcheckListNo.equals("0")) {
                newBidNoLast = 1;
                newcheckListNo = "PO-" + newBidNoLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
                newBidNoLast = newBidNoLast + 1;
                newcheckListNo = "PO-" + newBidNoLast + "/" + f.format(now);
            }
        }
        return newcheckListNo;

    }

    /**
     *
     */
    public void populateWorkFlow() {

        eepBidReg = papmsPurchaseOrder.getBidId();
        prmsQuotation = papmsPurchaseOrder.getQuotationId();
        eepVendorReg = papmsPurchaseOrder.getSuppId();
        prmsContract = papmsPurchaseOrder.getContractId();
        wfPrmsProcessed.setProcessedOn(papmsPurchaseOrder.getRigistrationDate());

        for (int i = 0; i < papmsPurchaseOrder.getPrmsPurOrderDetailList().size(); i++) {
            totalPrice = papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getQuantity()
                    * papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getUnitPrice();
            System.out.println("tota row select" + papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getQuantity()
                    * papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).getUnitPrice());
            papmsPurchaseOrder.getPrmsPurOrderDetailList().get(i).setTotal(totalPrice);
        }

        if ("Bid".equals(papmsPurchaseOrder.getType())) {

            if (eepVendorReg != null) {
                prmsContract.setSuppId(eepVendorReg);

                if ("Goods".equals(eepBidReg.getBidCategory())) {
                    goodsFrmBid = true;
                } else if ("Service".equals(eepBidReg.getBidCategory())) {
                    servicefrmBid = true;
                } else if ("Work".equals(eepBidReg.getBidCategory())) {
                    workNameFrmBid = true;
                }

                String bidType = eepBidReg.getBidType();

                if ("international".equals(bidType)) {
                    System.out.println("---------------");
                    renderLocal = true;
                } else {
                    renderLocal = false;
                }

                prmsAward.setSuppId(prmsAward.getSuppId());
//            prmsAward = PrmsContractDetailFacade.FindBySupplyIid(prmsContract.getContractId());
                eepBidReg = purchaseOrderBeanLocal.getBidType(eepBidReg.getRefNo());
                prmsContractList = BidRegBeanLocal.findbycontractId(eepBidReg);
                wfPrmsProcessed.setProcessedOn(papmsPurchaseOrder.getRigistrationDate());
                purchaseTypeFrmBid = true;
                purchaseTypefrmPerforma = false;
                renderBid = true;
                renderPerfoma = false;
            } else {
                System.out.println("Workflow is Vender Null");
            }

        } else if ("Quotation".equals(papmsPurchaseOrder.getType())) {

            if ("Goods".equals(prmsQuotation.getPurchaseType())) {
                goodsFrmProfoma = true;
            }

            purchaseTypeFrmBid = false;
            purchaseTypefrmPerforma = true;
            renderBid = false;
            renderPerfoma = true;
            prmsSupplyProfiList = purchaseOrderBeanLocal.getsupplierNameFromQuotation(prmsQuotation.getQuotationNo());
            PrmsPurchaseRequestList = purchaseOrderBeanLocal.getPrNo(prmsQuotation.getQuotationNo());
            hrDepartmentsesList = purchaseOrderBeanLocal.getDeptName(prmsQuotation.getQuotationNo());
        }

        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";

        recreateprmsPurOrderDetailsModel();
        recreatworkflow();

    }

    /**
     * *
     *
     * @param event
     */
    public void rowSelect(SelectEvent event) {

        papmsPurchaseOrder = (PrmsPurchaseOrder) event.getObject();
        prmsContract = papmsPurchaseOrder.getContractId();
        recreateContractCurrDetailModel();
        populateWorkFlow();
        recreatworkflow();
    }

    List<PrmsAward> PrmsAwardList;
    List<PrmsContract> prmsContractList;
    List<PrmsSupplyProfile> PrmsSupplyProfileList;
    List<HrDepartments> hrDepartmentsList;

    public void recreateprmsQoutationDetailsModel() {
        for (int i = 0; i < prmsQuotation.getPrmsQuotationDetailList().size(); i++) {
            unitprice = prmsQuotation.getPrmsQuotationDetailList().get(i).getUnitPrice();
            quantity = prmsQuotation.getPrmsQuotationDetailList().get(i).getQuantity();
            totalPrice = quantity * unitprice;
            prmsQuotation.getPrmsQuotationDetailList().get(i).setTotalPrice(totalPrice);
            setTotalSum(totalSum + totalPrice);
            setTotalvat(prmsQuotation.getPrmsQuotationDetailList().get(i).getSupId().getVatTypeId().getVatRate() * totalSum);
            setGroundTotal(totalSum + totalvat);

            if (totalSum >= 10000) {
                withHolding = totalSum * 0.02;
            }

            papmsPurchaseOrder.add(papmsPurchaseOrderDetail);
        }
        prmsPurOrderDetailsModel = null;
        prmsPurOrderDetailsModel = new ListDataModel(new ArrayList(papmsPurchaseOrder.getPrmsPurOrderDetailList()));
    }

    public List<PrmsSupplyProfile> getPrmsSupplyProfileList() {
        if (PrmsSupplyProfileList == null) {
            PrmsSupplyProfileList = new ArrayList<>();
        }
        return PrmsSupplyProfileList;
    }

    public void setPrmsSupplyProfileList(List<PrmsSupplyProfile> PrmsSupplyProfileList) {
        this.PrmsSupplyProfileList = PrmsSupplyProfileList;
    }

    public List<PrmsContract> getPrmsContractList() {
        return prmsContractList;
    }

    public void setPrmsContractList(List<PrmsContract> prmsContractList) {
        this.prmsContractList = prmsContractList;
    }

    public List<PrmsAward> getPrmsAwardList() {
        return PrmsAwardList;
    }

    public void setPrmsAwardList(List<PrmsAward> PrmsAwardList) {
        this.PrmsAwardList = PrmsAwardList;
    }
    @EJB
    BidRegBeanLocal BidRegBeanLocal;
    @EJB
    FinancialEvaluationBeanLocal finanlEvalutionBeanLocal;

    /**
     * *************************************************************************
     *
     * @param event
     * /************************************************************************
     */
    public void display(ValueChangeEvent event) {

        System.out.println("in event====" + event.getNewValue().toString());

        if (event.getNewValue() != null) {

            eepBidReg = new PrmsBid();
            eepBidReg = (PrmsBid) event.getNewValue();
            prmsContract.setBidId(eepBidReg);
            eepBidReg = purchaseOrderBeanLocal.getBidType(
                    event.getNewValue().toString());

            System.out.println("checking bid by--- " + eepBidReg);
            //   prmsBidAmendsList = purchaseOrderBeanLocal.checkAsBidAmended(eepBidReg);
            papmsPurchaseOrder.setBidId(eepBidReg);

            prmsBidAmend.setBidId(eepBidReg);
            bidListFromAmendment = purchaseOrderBeanLocal.checkBidFromAmended(eepBidReg);
            if (bidListFromAmendment.size() > 0) {
                prmsBidAmend = purchaseOrderBeanLocal.getBidAmendInfoByBidId(prmsBidAmend);
                papmsPurchaseOrder.setBidId(eepBidReg);
                eepBidReg.setBidCategory(eepBidReg.getBidCategory());
                eepBidReg.setBidType(eepBidReg.getBidType());
            }

            System.out.println("eepBidReg Number ---> " + eepBidReg.getId());
            prmsContractList = BidRegBeanLocal.findbycontractId(eepBidReg);
//            if (papmsPurchaseOrder.getPrmsPurOrderDetailList().size() > 0) {
//                System.out.println("oh1");
//                papmsPurchaseOrder.getPrmsPurOrderDetailList().clear();
//            }

            if ("Goods".equals(eepBidReg.getBidCategory())) {
                System.out.println("*****************************************");
                goodsFrmBid = true;
                servicefrmBid = false;
                workNameFrmBid = false;

            } else if ("Service".equals(eepBidReg.getBidCategory())) {
                servicefrmBid = true;
                goodsFrmBid = false;
                worksName = false;

            } else if ("Work".equals(eepBidReg.getBidCategory())) {
                workNameFrmBid = true;
                goodsFrmBid = false;
                servicefrmBid = false;
            }

            String bidType = eepBidReg.getBidType();

            if ("international".equals(bidType)) {
                System.out.println("-----------------------------------------");
                renderLocal = true;
            } else {
                renderLocal = false;
            }

            System.out.println("oh2");
//            papmsPurchaseOrder.getPrmsPurOrderDetailList().clear();
        }
    }

    @EJB
    PrmsQuotationDetailFacade PrmsQuotationDetailFacade;

    public void display2(ValueChangeEvent event) {

        if (event.getNewValue() != null) {

            prmsQuotation = (PrmsQuotation) event.getNewValue();
            papmsPurchaseOrder.setQuotationId(papmsPurchaseOrder.getQuotationId());
            PrmsSupplyProfileList = quatationBeanLocal.FindBySuppID(prmsQuotation);
            papmsPurchaseOrder.setQuotationId(prmsQuotation);
            prmsQuotation = PrmsQuotationDetail.getQuotationId();
        }

    }
    private String choosebid = "true";
    private String choosequotation = "false";

    public String getChoosebid() {
        return choosebid;
    }

    public void setChoosebid(String choosebid) {
        this.choosebid = choosebid;
    }

    public String getChoosequotation() {
        return choosequotation;
    }

    public boolean isRenderBidGoods() {
        return renderBidGoods;
    }

    public void setRenderBidGoods(boolean renderBidGoods) {
        this.renderBidGoods = renderBidGoods;
    }

    public boolean isRenderBidService() {
        return renderBidService;
    }

    public void setRenderBidService(boolean renderBidService) {
        this.renderBidService = renderBidService;
    }

    public boolean isRenderBidWork() {
        return renderBidWork;
    }

    public void setRenderBidWork(boolean renderBidWork) {
        this.renderBidWork = renderBidWork;
    }

    public boolean isRenderInternational() {
        return renderInternational;
    }

    public void setRenderInternational(boolean renderInternational) {
        this.renderInternational = renderInternational;
    }

    public void setChoosequotation(String choosequotation) {
        this.choosequotation = choosequotation;
    }

    public String getSlected() {
        return slected;
    }

    public boolean isRenderFinacialResult() {
        return renderFinacialResult;
    }

    public void setRenderFinacialResult(boolean renderFinacialResult) {
        this.renderFinacialResult = renderFinacialResult;
    }

    public boolean isRenderSuppFromPosQual() {
        return renderSuppFromPosQual;
    }

    public void setRenderSuppFromPosQual(boolean renderSuppFromPosQual) {
        this.renderSuppFromPosQual = renderSuppFromPosQual;
    }

    public boolean isRenderMarketTable() {
        return renderMarketTable;
    }

    public void setRenderMarketTable(boolean renderMarketTable) {
        this.renderMarketTable = renderMarketTable;
    }

    public boolean isServiceTable() {
        return serviceTable;
    }

    public void setServiceTable(boolean serviceTable) {
        this.serviceTable = serviceTable;
    }

    public boolean isRenderBid() {
        return renderBid;
    }

    public void setRenderBid(boolean renderBid) {
        this.renderBid = renderBid;
    }

    public boolean isWorks() {
        return works;
    }

    public void setWorks(boolean works) {
        this.works = works;
    }

    public boolean isWorksTable() {
        return worksTable;
    }

    public void setWorksTable(boolean worksTable) {
        this.worksTable = worksTable;
    }

    public boolean isWorksBidNo() {
        return worksBidNo;
    }

    public void setWorksBidNo(boolean worksBidNo) {
        this.worksBidNo = worksBidNo;
    }

    public boolean isServiceBidNoForCons() {
        return serviceBidNoForCons;
    }

    public void setServiceBidNoForCons(boolean serviceBidNoForCons) {
        this.serviceBidNoForCons = serviceBidNoForCons;
    }

    public boolean isServiceBidNoForNcons() {
        return serviceBidNoForNcons;
    }

    public void setServiceBidNoForNcons(boolean serviceBidNoForNcons) {
        this.serviceBidNoForNcons = serviceBidNoForNcons;
    }

    public boolean isServiceType() {
        return serviceType;
    }

    public void setServiceType(boolean serviceType) {
        this.serviceType = serviceType;
    }

    public boolean isServiceName() {
        return serviceName;
    }

    public void setServiceName(boolean serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isWorksName() {
        return worksName;
    }

    public void setWorksName(boolean worksName) {
        this.worksName = worksName;
    }

    public boolean isRenderItemUnitMeasure() {
        return renderItemUnitMeasure;
    }

    public void setRenderItemUnitMeasure(boolean renderItemUnitMeasure) {
        this.renderItemUnitMeasure = renderItemUnitMeasure;
    }

    public boolean isRenderServiceUnitMeasure() {
        return renderServiceUnitMeasure;
    }

    public void setRenderServiceUnitMeasure(boolean renderServiceUnitMeasure) {
        this.renderServiceUnitMeasure = renderServiceUnitMeasure;
    }

    public boolean isRenderWorksUnitMeasure() {
        return renderWorksUnitMeasure;
    }

    public void setRenderWorksUnitMeasure(boolean renderWorksUnitMeasure) {
        this.renderWorksUnitMeasure = renderWorksUnitMeasure;
    }

    public boolean isRenderItemAwardType() {
        return renderItemAwardType;
    }

    public void setRenderItemAwardType(boolean renderItemAwardType) {
        this.renderItemAwardType = renderItemAwardType;
    }

    public boolean isRenderServiceAwardType() {
        return renderServiceAwardType;
    }

    public void setRenderServiceAwardType(boolean renderServiceAwardType) {
        this.renderServiceAwardType = renderServiceAwardType;
    }

    public boolean isRenderWorksAwardType() {
        return renderWorksAwardType;
    }

    public void setRenderWorksAwardType(boolean renderWorksAwardType) {
        this.renderWorksAwardType = renderWorksAwardType;
    }

    public boolean isRenderItemBidderCode() {
        return renderItemBidderCode;
    }

    public void setRenderItemBidderCode(boolean renderItemBidderCode) {
        this.renderItemBidderCode = renderItemBidderCode;
    }

    public boolean isRenderServiceBidderCode() {
        return renderServiceBidderCode;
    }

    public void setRenderServiceBidderCode(boolean renderServiceBidderCode) {
        this.renderServiceBidderCode = renderServiceBidderCode;
    }

    public boolean isRenderWorksBidderCode() {
        return renderWorksBidderCode;
    }

    public void setRenderWorksBidderCode(boolean renderWorksBidderCode) {
        this.renderWorksBidderCode = renderWorksBidderCode;
    }

    public boolean isRenderItemAddButon() {
        return renderItemAddButon;
    }

    public void setRenderItemAddButon(boolean renderItemAddButon) {
        this.renderItemAddButon = renderItemAddButon;
    }

    public boolean isRenderServiceAddButon() {
        return renderServiceAddButon;
    }

    public void setRenderServiceAddButon(boolean renderServiceAddButon) {
        this.renderServiceAddButon = renderServiceAddButon;
    }

    public boolean isRenderServiceWorksAddButon() {
        return renderServiceWorksAddButon;
    }

    public void setRenderServiceWorksAddButon(boolean renderServiceWorksAddButon) {
        this.renderServiceWorksAddButon = renderServiceWorksAddButon;
    }

    public void setSlected(String slected) {
        this.slected = slected;
    }

    String slected = "Select One";

    public void handlechoose(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("Bid")) {
                choosebid = "true";
                choosequotation = "false";
            } else {
                choosebid = "false";
                choosequotation = "true";
            }
        }
    }
    @Inject
    PrmsContractDetail prmsContractDetail;

    public PrmsContractDetail getPrmsContractDetail() {
        if (prmsContractDetail == null) {
            prmsContractDetail = new PrmsContractDetail();
        }
        return prmsContractDetail;
    }

    public void setPrmsContractDetail(PrmsContractDetail prmsContractDetail) {
        this.prmsContractDetail = prmsContractDetail;
    }

    public void VCLpurchseRequest(ValueChangeEvent event) {
        prmsPurchaseRequest = (PrmsPurchaseRequest) event.getNewValue();
        papmsPurchaseOrder.setPurchaseRequestId(prmsPurchaseRequest);
    }
    DataModel<PrmsContractDetail> prmsContractDetailmodel = new ListDataModel<>();

    public DataModel<PrmsContractDetail> getPrmsContractDetailmodel() {
        return prmsContractDetailmodel;
    }

    public void setPrmsContractDetailmodel(DataModel<PrmsContractDetail> prmsContractDetailmodel) {
        this.prmsContractDetailmodel = prmsContractDetailmodel;
    }
    List<PrmsContractDetail> prmsContractDetailmodelList;

    public List<PrmsContractDetail> getPrmsContractDetailmodelList() {
        if (prmsContractDetailmodelList == null) {
            prmsContractDetailmodelList = new ArrayList<>();
        }
        return prmsContractDetailmodelList;
    }

    public void setPrmsContractDetailmodelList(List<PrmsContractDetail> prmsContractDetailmodelList) {
        this.prmsContractDetailmodelList = prmsContractDetailmodelList;
    }

    /**
     *
     */
    public void recreateDataModel() {

        for (int i = 0; i < prmsContractDetailmodelList.size(); i++) {
            unitprice = prmsContractDetailmodelList.get(i).getUnitPrice();
            quantity = prmsContractDetailmodelList.get(i).getQuantity();
            totalPrice = quantity * unitprice;
            prmsContractDetailmodelList.get(i).setTotalPrice(totalPrice);
            totalSum = totalSum + totalPrice;//totalSum = totalSum + papmsAward.getPrmsAwardDetailList().get(i).getTotals();
            totalvat = prmsContractDetailmodelList.get(i).getContractId().getSuppId().getVatTypeId().getVatRate() * totalSum;
            groundTotal = totalSum + totalvat;

            // Calculate WithHold Price
            //totalvat = 0.15 * totalSum;
            if (totalSum >= 10000) {
                withHolding = totalSum * 0.02;
            }
//
//            papmsPurchaseOrderDetail.setQuantity(prmsContractDetailmodelList.get(i).getQuantity());
//            papmsPurchaseOrderDetail.setUnitPrice(prmsContractDetailmodelList.get(i).getUnitPrice());
//            papmsPurchaseOrderDetail.setItemId(prmsContractDetailmodelList.get(i).getItemId());
//            papmsPurchaseOrderDetail.setServiceId(prmsContractDetailmodelList.get(i).getServiceId());
//            papmsPurchaseOrder.add(papmsPurchaseOrderDetail);
        }
        prmsPurOrderDetailsModel = null;
        prmsPurOrderDetailsModel = new ListDataModel(new ArrayList(papmsPurchaseOrder.getPrmsPurOrderDetailList()));
    }

    public PrmsSupplyProfileDetail getPrmsSupplyProfileDetail() {
        if (prmsSupplyProfileDetail == null) {
            prmsSupplyProfileDetail = new PrmsSupplyProfileDetail();
        }
        return prmsSupplyProfileDetail;
    }

    public void setPrmsSupplyProfileDetail(PrmsSupplyProfileDetail prmsSupplyProfileDetail) {
        this.prmsSupplyProfileDetail = prmsSupplyProfileDetail;
    }

    /**
     * *************************************************************************
     *
     * @param event
     */
    public void valueChangeFrContNo(ValueChangeEvent event) {

        if (event.getNewValue() != null) {

            System.out.println("in " + event.getNewValue().toString());
//            BigDecimal bd = new BigDecimal(String.valueOf(event.getNewValue().toString()));
            papmsPurchaseOrder.getPrmsPurOrderDetailList().clear();
//            papmsPurchaseOrder.getPrmsContractCurrencyDetailList().clear();
            System.out.println("oh3");
            totalPrice = 0.0;
            totalSum = 0.0;
            groundTotal = 0.0;
            withHolding = 0.0;
            prmsContract = (PrmsContract) event.getNewValue();
            setContractListFromAmendment(purchaseOrderBeanLocal.checkContractIdFromAmended(prmsContract));
            System.out.println(" list ---" + getContractListFromAmendment().size());

            if (getContractListFromAmendment().size() > 0) {
                System.out.println("oh This " + prmsContract.getContractId() + " Contract Id is Amended");
                setPrmsContractAmendment(purchaseOrderBeanLocal.getContractAmendedInfoByContractId(prmsContract));
                prmsContract.setSuppId(getPrmsContractAmendment().getSuppId());
                prmsContract.setPaymenttype(getPrmsContractAmendment().getPaymenttype());
                prmsContract.setContractamount(getPrmsContractAmendment().getContractamount());
            }

            papmsPurchaseOrder.setContractId(prmsContract);
//            papmsPurchaseOrder.setSuppId(prmsContract.getSuppId());
            System.out.println("***SUPPLIER ID's ===> " + prmsContract.getSuppId());
            mmsItemRegistration = prmsContractDetail.getItemId();
            prmsContract = PrmsContractDetailFacade.FindBySupplyIid(prmsContract);
            System.out.println("********SUPPLIER ID's FROM [ AWARD ]===> ********" + prmsAward.getSuppId());

//          for (int i = 0; i < prmsContract.getPrmsContractCurrencyDetailList().size(); i++) {
//
//                PrmsContractCurrencyDetail tempConCurr = new PrmsContractCurrencyDetail();
//                tempConCurr = prmsContract.getPrmsContractCurrencyDetailList().get(i);
//                papmsPurchaseOrder.getPrmsContractCurrencyDetailList().add(tempConCurr);
//            }
            prmsContractList = BidRegBeanLocal.findbycontractId(eepBidReg);
            prmsContractDetailmodelList = purchaseOrderBeanLocal.getItemList(prmsContract.getContractId());
            papmsPurchaseOrder = new PrmsPurchaseOrder();
            papmsPurchaseOrder.setSuppId(prmsContract.getSuppId());
            //        papmsPurchaseOrder.getContractId().getPrmsContractCurrencyDetailList();
            System.out.println("sup Name-----" + prmsContract.getSuppId());
            System.out.println("sup Name [Contract]-----" + prmsContract.getContractNo());

            for (int i = 0; i < prmsContractDetailmodelList.size(); i++) {
                papmsPurchaseOrderDetail = new PrmsPurOrderDetail();
                papmsPurchaseOrderDetail.setPoId(papmsPurchaseOrder);
                papmsPurchaseOrderDetail.setItemId(prmsContractDetailmodelList.get(i).getItemId());
                papmsPurchaseOrderDetail.setQuantity(prmsContractDetailmodelList.get(i).getQuantity());
                papmsPurchaseOrderDetail.setUnitPrice(prmsContractDetailmodelList.get(i).getUnitPrice());
                System.out.println("Item Name*" + prmsContractDetailmodelList.get(i).getItemId());
                System.out.println("Unit Price*" + prmsContractDetailmodelList.get(i).getUnitPrice());
                papmsPurchaseOrder.getPrmsPurOrderDetailList().add(papmsPurchaseOrderDetail);
            }
            for (int i = 0; i < prmsContract.getPrmsContractCurrencyDetailList().size(); i++) {
                System.out.println("A22222222 ");
//                prmsContractCurrencyDetail = new PrmsContractCurrencyDetail();
                prmsPoCurrency = new PrmsPoCurrency();
                prmsPoCurrency.setPoId(papmsPurchaseOrder);
                prmsPoCurrency.setAmount(prmsContract.getPrmsContractCurrencyDetailList().get(i).getAmount());
                prmsPoCurrency.setCurrencyId(prmsContract.getPrmsContractCurrencyDetailList().get(i).getCurrencyId());
                papmsPurchaseOrder.getPrmsPoCurrencyList().add(prmsPoCurrency);
                System.out.println("one === " + papmsPurchaseOrder.getPrmsPoCurrencyList().get(i).getAmount());
                System.out.println("two === " + papmsPurchaseOrder.getPrmsPoCurrencyList().get(i).getCurrencyId());
                System.out.println("three === " + papmsPurchaseOrder.getPrmsPoCurrencyList().get(i).getPoId());
            }
            recreateDataModel();
            recreateContractCurrDetailModel();
        }
    }

    @EJB
    PurchaseReqBeanLocal PurchaseReqBeanLocal;
    @EJB
    HrDepartmentsFacade HrDepartmentsFacade;
    @Inject
    HrDepartments hrDepartments;

    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }
    @Inject
    PrmsQuotationDetail PrmsQuotationDetail;
    @Inject
    PrmsFinancialEvalResult prmsFinancialEvalResult;
    List<HrDepartments> hrDepartmentsesList;

    public List<HrDepartments> getHrDepartmentsesList() {
        if (hrDepartmentsesList == null) {
            hrDepartmentsList = new ArrayList<>();
        }
        return hrDepartmentsesList;

    }

    public void setHrDepartmentsesList(List<HrDepartments> hrDepartmentsesList) {
        this.hrDepartmentsesList = hrDepartmentsesList;
    }

    public PrmsFinancialEvalResult getPrmsFinancialEvalResult() {
        if (prmsFinancialEvalResult == null) {
            prmsFinancialEvalResult = new PrmsFinancialEvalResult();
        }
        return prmsFinancialEvalResult;
    }

    public void setPrmsFinancialEvalResult(PrmsFinancialEvalResult prmsFinancialEvalResult) {
        this.prmsFinancialEvalResult = prmsFinancialEvalResult;
    }

    public PrmsQuotationDetail getPrmsQuotationDetail() {
        if (PrmsQuotationDetail == null) {
            PrmsQuotationDetail = new PrmsQuotationDetail();
        }
        return PrmsQuotationDetail;
    }

    public void setPrmsQuotationDetail(PrmsQuotationDetail PrmsQuotationDetail) {
        this.PrmsQuotationDetail = PrmsQuotationDetail;
    }

    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    public void VCLQuotation(ValueChangeEvent event) {
        prmsPurchaseRequest = (PrmsPurchaseRequest) event.getNewValue();
        papmsPurchaseOrder.setPurchaseRequestId(prmsPurchaseRequest);
        prmsPurchaseRequest.setReqstrDepId(prmsPurchaseRequest.getReqstrDepId());
        papmsPurchaseOrder.setDepId(prmsPurchaseRequest.getReqstrDepId());

    }
    @EJB
    PrmsSupplyProfileFacade prmsSupplyProfileFacade;
    List<PrmsQuotationDetail> PrmsQuotationDetailList;

    List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlList;

    public List<PrmsFinancialEvlResultyDtl> getPrmsFinancialEvlResultyDtlList() {
        if (prmsFinancialEvlResultyDtlList == null) {
            prmsFinancialEvlResultyDtlList = new ArrayList<>();
        }
        return prmsFinancialEvlResultyDtlList;
    }

    public void setPrmsFinancialEvlResultyDtlList(List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlList) {
        this.prmsFinancialEvlResultyDtlList = prmsFinancialEvlResultyDtlList;
    }

    public List<PrmsQuotationDetail> getPrmsQuotationDetailList() {
        if (PrmsQuotationDetailList == null) {
            PrmsQuotationDetailList = new ArrayList<>();
        }
        return PrmsQuotationDetailList;
    }

    public void setPrmsQuotationDetailList(List<PrmsQuotationDetail> PrmsQuotationDetailList) {
        this.PrmsQuotationDetailList = PrmsQuotationDetailList;
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ************************************************************************
     */
    public void VCLsupplier(ValueChangeEvent event) {

        System.out.println("in event" + event.getNewValue().toString());

        if (event.getNewValue() != null) {

            if (papmsPurchaseOrder.getPrmsPurOrderDetailList().size() > 0) {
                System.out.println("Oh4");
                papmsPurchaseOrder.getPrmsPurOrderDetailList().clear();
            }
            eepVendorReg = (PrmsSupplyProfile) event.getNewValue();
            papmsPurchaseOrder.setSuppId(eepVendorReg);
            eepVendorReg.setVendorName(event.getNewValue().toString());
            prmsFinancialEvlResultyDtlList = purchaseOrderBeanLocal.supplierName(eepVendorReg);
            PrmsQuotationDetailList = PrmsQuotationDetailFacade.FindByForienId(eepVendorReg);
            System.out.println("size" + prmsQuotation.getPrmsQuotationDetailList().size());

            for (int j = 0; j < prmsQuotation.getPrmsQuotationDetailList().size(); j++) {
                for (int i = 0; i < prmsFinancialEvlResultyDtlList.size(); i++) {
                    papmsPurchaseOrderDetail = new PrmsPurOrderDetail();
                    unitprice = prmsQuotation.getPrmsQuotationDetailList().get(j).getUnitPrice();
                    quantity = prmsQuotation.getPrmsQuotationDetailList().get(j).getQuantity();
                    totalPrice = quantity * unitprice;
                    papmsPurchaseOrderDetail.setTotal(totalPrice);
                    setTotalSum(totalSum + totalPrice);
                    setTotalvat(PrmsQuotationDetailList.get(i).getSupId().getVatTypeId().getVatRate() * totalSum);
                    setGroundTotal(totalSum + totalvat);
                    papmsPurchaseOrderDetail.setPoId(papmsPurchaseOrder);
                    papmsPurchaseOrderDetail.setQuantity(Long.valueOf(prmsQuotation.getPrmsQuotationDetailList().get(j).getQuantity()));
                    papmsPurchaseOrderDetail.setUnitPrice(new Double(prmsQuotation.getPrmsQuotationDetailList().get(j).getUnitPrice()).longValue());
                    papmsPurchaseOrderDetail.setItemId(PrmsQuotationDetailList.get(i).getMaterialCodeId());
                    System.out.println("****Item name" + prmsFinancialEvlResultyDtlList.get(i).getItemId() + prmsFinancialEvlResultyDtlList.get(i).getId());
                    papmsPurchaseOrderDetail.setServiceId(PrmsQuotationDetailList.get(i).getServAndWorkId());
                    papmsPurchaseOrder.getPrmsPurOrderDetailList().add(papmsPurchaseOrderDetail);

                }
            }
            String purchaseType = prmsQuotation.getPurchaseType();
            if ("Goods".equals(purchaseType)) {
                System.out.println("____*");

                goodsFrmProfoma = true;

            }
            prmsPurOrderDetailsModel = null;
            prmsPurOrderDetailsModel = new ListDataModel(new ArrayList(papmsPurchaseOrder.getPrmsPurOrderDetailList()));
        }
    }
    DataModel<PrmsSupplyProfileDetail> PrmsSupplyProfilemodel = new ListDataModel<>();

    public DataModel<PrmsSupplyProfileDetail> getPrmsSupplyProfilemodel() {
        return PrmsSupplyProfilemodel;
    }

    public void setPrmsSupplyProfilemodel(DataModel<PrmsSupplyProfileDetail> PrmsSupplyProfilemodel) {
        this.PrmsSupplyProfilemodel = PrmsSupplyProfilemodel;
    }

    public void receatePrmsSupplyProfilemodel() {

        for (int i = 0; i < eepVendorReg.getPrmsSupplyProfileDetailList().size(); i++) {

            unitprice = Long.valueOf(eepVendorReg.getPrmsSupplyProfileDetailList().get(i).getUnitPrice());
            quantity = Long.valueOf(eepVendorReg.getPrmsSupplyProfileDetailList().get(i).getQuantity());
            totalPrice = quantity * unitprice;
            totalSum = totalSum + totalPrice;
            setTotalSum(totalSum);
            totalvat = eepVendorReg.getPrmsSupplyProfileDetailList().get(i).getSuppId().getVatTypeId().getVatRate() * totalSum;
            setTotalvat(totalvat);
            groundTotal = totalSum + totalvat;
            setGroundTotal(groundTotal);

            if (totalSum >= 10000) {
                withHolding = totalSum * 0.02;
            }
        }

        PrmsSupplyProfilemodel = null;
        PrmsSupplyProfilemodel = new ListDataModel(new ArrayList(eepVendorReg.getPrmsSupplyProfileDetailList()));
    }

    /**
     * *
     *
     * @return
     */
    public List<PrmsSupplyProfile> getPrmsSupplyProfiList() {

        if (prmsSupplyProfiList == null) {

            prmsSupplyProfiList = new ArrayList<>();
        }
        return prmsSupplyProfiList;
    }

    public void setPrmsSupplyProfiList(List<PrmsSupplyProfile> prmsSupplyProfiList) {
        this.prmsSupplyProfiList = prmsSupplyProfiList;
    }

    /**
     *
     * @param event
     */
    public void changeEventSupp(ValueChangeEvent event) {

        System.out.println("in event" + event.getNewValue().toString());

        if (null != event.getNewValue()) {
            System.out.println("Oh5");
            papmsPurchaseOrder.getPrmsPurOrderDetailList().clear();

            prmsQuotation = (PrmsQuotation) event.getNewValue();
            papmsPurchaseOrder.setQuotationId(prmsQuotation);
            prmsSupplyProfiList = purchaseOrderBeanLocal.getsupplierNameFromQuotation(
                    event.getNewValue().toString());
            PrmsPurchaseRequestList = purchaseOrderBeanLocal.getPrNo(event.getNewValue().toString());
            hrDepartmentsesList = purchaseOrderBeanLocal.getDeptName(event.getNewValue().toString());
//            System.out.println("uuu" + PrmsPurchaseRequestList.get(0).getReqstrDepId());

        }
    }

    /**
     * *************************************************************************
     *
     *************************************************************************
     */
    public void ClearValues() {

        papmsPurchaseOrder = null;
        hrDepartments = null;
        prmsQuotationList = null;
        prmsPurchaseRequest = null;
        PrmsSupplyProfileList = null;
        PrmsQuotationDetailList = null;
    }

    public SelectItem[] getListOfBidNo() {
        return JsfUtil.getSelectItems(purchaseOrderBeanLocal.BidNoFormContract(), true);
    }

    public SelectItem[] getListOfQuotation() {
        return JsfUtil.getSelectItems(purchaseOrderBeanLocal.quotationFromResult(), true);
    }

    /**
     * ************************************************************************
     *
     * @param event
     * ************************************************************************
     */
    public void changeEventSuppCode(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            eepBidReg = (PrmsBid) event.getNewValue();
            papmsAward.setBidId(eepBidReg);
        }
        if (eepBidReg.getBidCategory().equalsIgnoreCase("Goods")) {
            renderBidGoods = true;
            renderBidService = false;
            renderBidWork = false;
        } else {

            if (eepBidReg.getBidCategory().equalsIgnoreCase("Service")) {
                renderBidGoods = false;
                renderBidService = true;
                renderBidWork = false;
            } else {
                renderBidGoods = false;
                renderBidService = false;
                renderBidWork = true;
            }
        }

        if (eepBidReg.getBidType().equalsIgnoreCase("International")) {
            renderInternational = true;
            renderLocal = false;
        } else {
            renderInternational = false;
            renderLocal = true;
        }
    }

    /**
     * *************************************************************************
     *
     *
     *************************************************************************
     */
    /**
     * *************************************************************************
     *
     * @param e
     * ************************************************************************
     */
    public void changeItemService(ValueChangeEvent e) {

        if (null != e.getNewValue()) {

            String select = "select";
            select = e.getNewValue().toString();
            System.out.println("oh6");
//            papmsPurchaseOrder.getPrmsPurOrderDetailList().clear();

            if (select.equalsIgnoreCase("Bid")) {

                purchaseTypeFrmBid = true;
                purchaseTypefrmPerforma = false;
                goodsTable = true;
                goodsDataTable = true;
                renderBid = true;
                works = false;
                worksTable = false;
                serviceTable = false;
                performa = false;
                performaDataTable = false;
                performatable = false;
                renderBidItem = true;
                renderPerfoma = false;

            } else {
                purchaseTypeFrmBid = false;
                purchaseTypefrmPerforma = true;
                goodsDataTable = false;
                renderBidItem = false;
                renderPerfoma = true;
                performa = true;
                goodsTable = false;
                renderBid = false;
                performatable = true;
                serviceTable = false;
                works = false;
                worksTable = false;
                serviceBidNoForCons = false;
                serviceBidNoForNcons = false;
            }
        }
    }

    public void handleDecision(ValueChangeEvent eve) {
        if (eve.getNewValue() != null) {
            selectedValue = eve.getNewValue().toString();
        }
    }

    /**
     * *************************************************************************
     *
     * Methods should be added for Workflow Notification and Pending Lists
     *
     *************************************************************************
     */
    int requestNotificationCounter = 0;
    private List<PrmsPurchaseOrder> purchaseOrderLis1;

    public List<PrmsPurchaseOrder> getPurchaseOrderLis1() {
        if (purchaseOrderLis1 == null) {
            purchaseOrderLis1 = new ArrayList<>();
        }
        return purchaseOrderLis1;
    }

    public void setPurchaseOrderLis1(List<PrmsPurchaseOrder> purchaseOrderLis1) {
        this.purchaseOrderLis1 = purchaseOrderLis1;
    }

    public int getRequestNotificationCounter() {
        purchaseOrderLis1 = purchaseOrderBeanLocal.getPOReauested();
        requestNotificationCounter = purchaseOrderLis1.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ***********************************************************************
     */
    public void RequestListChange(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            papmsPurchaseOrder = (PrmsPurchaseOrder) event.getNewValue();
            populateWorkFlow();
        }
    }

    /**
     * *************************************************************************
     *
     * Behabtu Codes
     *
     *************************************************************************
     */
    String logerName;

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public void recreatworkflow() {

        wfPrmsProcessedDModel = new ListDataModel(new ArrayList(papmsPurchaseOrder.getPrmsWorkFlowProccedList()));
    }

    /**
     * @return the contractListFromAmendment
     */
    public List<PrmsContractAmendment> getContractListFromAmendment() {
        if (contractListFromAmendment == null) {
            contractListFromAmendment = new ArrayList<>();
        }
        return contractListFromAmendment;
    }

    /**
     * @param contractListFromAmendment the contractListFromAmendment to set
     */
    public void setContractListFromAmendment(List<PrmsContractAmendment> contractListFromAmendment) {
        this.contractListFromAmendment = contractListFromAmendment;
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
}
