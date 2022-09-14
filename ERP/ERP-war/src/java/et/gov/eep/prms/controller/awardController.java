package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.businessLogic.AwardBeanLocal;
import et.gov.eep.prms.businessLogic.InsuranceRegisterationBeanLocal;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.businessLogic.PurchaseOrderBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsFailsupplerAward;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail;
import et.gov.eep.prms.entity.PrmsThechnicalEvaluation;
import et.gov.eep.prms.entity.PrmsThechincalEvaluationDet;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsAwardFileUpload;
import java.io.File;
import java.io.InputStream;
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
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.commons.io.FileUtils;
import org.insa.client.DmsHandler;
import org.insa.model.DocumentModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.Utility;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

@Named(value = "awardController")
@ViewScoped
public class awardController implements Serializable {

    @Inject
    SessionBean SessionBean;
    @EJB
    InsuranceRegisterationBeanLocal insuranceRegisterationBeanLocal;
    @EJB
    private AwardBeanLocal awardBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    @Inject
    private PrmsPurchaseOrder papmsPurchaseOrder;
    @Inject
    private PrmsAward papmsAward;
    @Inject
    private PrmsSupplyProfile eepVendorReg;
    @Inject
    private PrmsBid eepBidReg;
    @Inject
    private PrmsBidDetail prmsBidDetail;
    @Inject
    private PrmsFailsupplerAward prmsFailsupplerAward;
    @Inject
    private ComLuCountry comLuCountry;
    @Inject
    private PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl;
    @Inject
    private PrmsFinancialEvaluaDetail prmsFinancialEvaluaDetail;
    @Inject
    private MmsItemRegistration mmsItemRegistration;
    @Inject
    private PrmsAwardDetail papmsAwardDetail;
    @Inject
    private PrmsThechnicalEvaluation prmsThechnicalEvaluation;
    @Inject
    private PrmsThechincalEvaluationDet PrmsThechincalEvaluationDet;
    @Inject
    DataUpload dataUpload;
    @Inject
    PrmsAwardFileUpload prmsAwardFileUpload;
    private String userName;
    String logerName;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    private List<PrmsAward> prmsAwardsbystus;
    @Inject
    WorkFlow workFlow;
    private String saveorUpdateBundle = "Save";
    private boolean disableServiceAndWorkRdbtnWhenSearch = false;
    private boolean renderPnlCreateServiceRegistration = false;
    double vat = 0.15;
    double groundTotal = 0.0;
    double contractGarate = 0.0;
    double withHolding = 0.0;
    double totalSum = 0.0;
    double totalvat = 0.0;
    double totalprice = 0.0;
    int saveStatus = 0;
    double unitprice = 0.0;
    int quantity = 0;
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlManPage = true;
    private boolean isFileSelected = false;
    private boolean renderpnlToSearchPage;
    private String activeIndex;
    private String fileName;
    private String fileType;
    private byte[] byteFile;
    DocumentModel documentModel = new DocumentModel();
    DmsHandler dmsHandler = new DmsHandler();
    DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    @Inject
    PrmsLuDmArchive prmsLuDmArchiveSelection;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    List<PrmsAward> awardSearchParameterLst;
    List<DocumentModel> newDoclist = new ArrayList<DocumentModel>();
    List<Integer> savedDocIds = new ArrayList<>();
    DataModel<DocumentModel> docValueModel;
    boolean renderBidGoods = true;
    boolean renderBidService = false;
    boolean renderBidWork = false;
    boolean renderFinacialResult = true;
    boolean renderSuppFromPosQual = false;
    boolean renderInternational = false;
    boolean renderLocal = true;
    int updateStatus = 0;
    private String selectOptPartyName;
    private String icone = "ui-icon-plus";
    DataModel<PrmsAwardDetail> PrmsAwardDetailModel;
    DataModel<PrmsFailsupplerAward> prmsFailsupplerAwardsDataModel;
    DataModel<PrmsAward> PrmsAwardsModel;
    ArrayList<PrmsSupplyProfile> prmsSupplyProfiList;
    ArrayList<PrmsSupplyProfile> prmsSupplyProfiListForFin;
    private ArrayList<PrmsSupplyProfile> prmsSupplyProfiListFailedForFin;
    ArrayList<PrmsSupplyProfile> prmsSupplyProfiListForPos;
    private ArrayList<PrmsSupplyProfile> prmsSupplyProfiListForTechEva;
    ArrayList<PrmsSupplyProfile> prmsSupplyProfiListFomPost;
    List<PrmsBidDetail> prmsBidDetailsList;
    List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlsList;
    ArrayList<MmsItemRegistration> itemListS;
    ArrayList<PrmsServiceAndWorkReg> serviceList;
    ArrayList<PrmsServiceAndWorkReg> workList;
    ArrayList<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailsList;
    List<PrmsAwardDetail> prmsAwardDetails;
    private List<PrmsBid> bidListForAward;
    private PrmsSupplyProfile prmsSupplyProfilefroFAilSup;
    Set<String> addressCheck = new HashSet<>();
    private PrmsAward selectAward;
    @EJB
    private PurchaseOrderBeanLocal purchaseOrderBeanLocal;
    List<PrmsBid> prmsBidList;

    public awardController() {
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PrmsAwardDetail getPapmsAwardDetail() {
        return papmsAwardDetail;
    }

    public void setPapmsAwardDetail(PrmsAwardDetail papmsAwardDetail) {
        this.papmsAwardDetail = papmsAwardDetail;
    }

    public String getAddressInfoButton() {
        return addressInfoButton;
    }

    public void setAddressInfoButton(String addressInfoButton) {
        this.addressInfoButton = addressInfoButton;
    }

    public PrmsSupplyProfile getPrmsSupplyProfilefroFAilSup() {
        if (prmsSupplyProfilefroFAilSup == null) {
            prmsSupplyProfilefroFAilSup = new PrmsSupplyProfile();
        }
        return prmsSupplyProfilefroFAilSup;
    }

    public void setPrmsSupplyProfilefroFAilSup(PrmsSupplyProfile prmsSupplyProfilefroFAilSup) {
        this.prmsSupplyProfilefroFAilSup = prmsSupplyProfilefroFAilSup;
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

    public PrmsFinancialEvlResultyDtl getPrmsFinancialEvlResultyDtl() {
        return prmsFinancialEvlResultyDtl;
    }

    public void setPrmsFinancialEvlResultyDtl(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl) {
        this.prmsFinancialEvlResultyDtl = prmsFinancialEvlResultyDtl;
    }

    public List<PrmsAwardDetail> getPrmsAwardDetails() {
        return prmsAwardDetails;
    }

    public void setPrmsAwardDetails(List<PrmsAwardDetail> prmsAwardDetails) {
        this.prmsAwardDetails = prmsAwardDetails;
    }

    public String getSelectOptPartyName() {
        return selectOptPartyName;
    }

    public void setSelectOptPartyName(String selectOptPartyName) {
        this.selectOptPartyName = selectOptPartyName;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public DataModel<PrmsAwardDetail> getPrmsAwardDetailModel() {
        if (PrmsAwardDetailModel == null) {
            PrmsAwardDetailModel = new ArrayDataModel<>();
        }
        return PrmsAwardDetailModel;
    }

    public void setPrmsAwardDetailModel(DataModel<PrmsAwardDetail> PrmsAwardDetailModel) {
        this.PrmsAwardDetailModel = PrmsAwardDetailModel;
    }

    public DataModel<PrmsAward> getPrmsAwardsModel() {
        if (PrmsAwardsModel == null) {
            PrmsAwardsModel = new ArrayDataModel<>();
        }
        return PrmsAwardsModel;
    }

    public void setPrmsAwardsModel(DataModel<PrmsAward> PrmsAwardsModel) {
        this.PrmsAwardsModel = PrmsAwardsModel;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }

    public PrmsAward getSelectAward() {
        return selectAward;
    }

    public void setSelectAward(PrmsAward selectAward) {
        this.selectAward = selectAward;
    }

    public PrmsAward getPapmsAward() {
        if (papmsAward == null) {
            papmsAward = new PrmsAward();
        }
        return papmsAward;
    }

    public void setPrmsAward(PrmsAward papmsAward) {
        this.papmsAward = papmsAward;
    }

    public PrmsAwardDetail getPrmsAwardDetail() {
        if (papmsAwardDetail == null) {
            papmsAwardDetail = new PrmsAwardDetail();
        }
        return papmsAwardDetail;
    }

    public void setPrmsAwardDetail(PrmsAwardDetail papmsAwardDetail) {
        this.papmsAwardDetail = papmsAwardDetail;
    }

    public PrmsBid getEepBidReg() {
        if (eepBidReg == null) {
            eepBidReg = new PrmsBid();
        }
        return eepBidReg;
    }

    public PrmsBidDetail getPrmsBidDetail() {
        if (prmsBidDetail == null) {
            prmsBidDetail = new PrmsBidDetail();
        }
        return prmsBidDetail;
    }

    public List<PrmsBidDetail> getPrmsBidDetailsList() {
        if (prmsBidDetailsList == null) {
            prmsBidDetailsList = new ArrayList<>();
        }
        return prmsBidDetailsList;
    }

    public void setPrmsBidDetailsList(List<PrmsBidDetail> prmsBidDetailsList) {
        this.prmsBidDetailsList = prmsBidDetailsList;
    }

    public void setPrmsBidDetail(PrmsBidDetail prmsBidDetail) {
        this.prmsBidDetail = prmsBidDetail;
    }

    public ArrayList<PrmsSupplyProfile> getPrmsSupplyProfiList() {
        if (prmsSupplyProfiList == null) {
            prmsSupplyProfiList = new ArrayList<>();
        }
        return prmsSupplyProfiList;
    }

    public void setPrmsSupplyProfiList(ArrayList<PrmsSupplyProfile> prmsSupplyProfiList) {
        this.prmsSupplyProfiList = prmsSupplyProfiList;
    }

    public List<PrmsFinancialEvlResultyDtl> getPrmsFinancialEvlResultyDtlsList() {
        return prmsFinancialEvlResultyDtlsList;
    }

    public void setPrmsFinancialEvlResultyDtlsList(List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlsList) {
        this.prmsFinancialEvlResultyDtlsList = prmsFinancialEvlResultyDtlsList;
    }

    public ArrayList<MmsItemRegistration> getItemListS() {
        return itemListS;
    }

    public void setItemListS(ArrayList<MmsItemRegistration> itemListS) {
        this.itemListS = itemListS;
    }

    public ArrayList<PrmsServiceAndWorkReg> getServiceList() {
        return serviceList;
    }

    public void setServiceList(ArrayList<PrmsServiceAndWorkReg> serviceList) {
        this.serviceList = serviceList;
    }

    public ArrayList<PrmsServiceAndWorkReg> getWorkList() {
        return workList;
    }

    public void setWorkList(ArrayList<PrmsServiceAndWorkReg> workList) {
        this.workList = workList;
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
    String purchaseType;

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
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

    public ArrayList<PrmsSupplyProfile> getPrmsSupplyProfiListFomPost() {
        if (prmsSupplyProfiListFomPost == null) {
            prmsSupplyProfiListFomPost = new ArrayList<>();
        }
        return prmsSupplyProfiListFomPost;
    }

    public void setPrmsSupplyProfiListFomPost(ArrayList<PrmsSupplyProfile> prmsSupplyProfiListFomPost) {
        this.prmsSupplyProfiListFomPost = prmsSupplyProfiListFomPost;
    }

    public PrmsFinancialEvaluaDetail getPrmsFinancialEvaluaDetail() {
        if (prmsFinancialEvaluaDetail == null) {
            prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
        }
        return prmsFinancialEvaluaDetail;
    }

    public void setPrmsFinancialEvaluaDetail(PrmsFinancialEvaluaDetail prmsFinancialEvaluaDetail) {
        this.prmsFinancialEvaluaDetail = prmsFinancialEvaluaDetail;
    }

    public ArrayList<PrmsFinancialEvaluaDetail> getPrmsFinancialEvaluaDetailsList() {
        if (prmsFinancialEvaluaDetailsList == null) {
            prmsFinancialEvaluaDetailsList = new ArrayList<>();
        }
        return prmsFinancialEvaluaDetailsList;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            papmsAward.setColumnName(event.getNewValue().toString());
            papmsAward.setColumnValue(null);
        }
    }

    public void setPrmsFinancialEvaluaDetailsList(ArrayList<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailsList) {
        this.prmsFinancialEvaluaDetailsList = prmsFinancialEvaluaDetailsList;
    }

    public List<PrmsAward> getAwardSearchParameterLst() {
        if (awardSearchParameterLst == null) {
            awardSearchParameterLst = new ArrayList<>();
            awardSearchParameterLst = awardBeanLocal.getParamNameList();
        }
        return awardSearchParameterLst;
    }

    public void setAwardSearchParameterLst(List<PrmsAward> awardSearchParameterLst) {
        this.awardSearchParameterLst = awardSearchParameterLst;
    }

    public void getBidderFullAddress(ValueChangeEvent event) {
        if (null != event.getNewValue() && !event.getNewValue().equals("")) {
            eepVendorReg = (PrmsSupplyProfile) event.getNewValue();
            papmsAward.setSuppId(eepVendorReg);
            papmsAward.setBidId(eepBidReg);
            int size = eepVendorReg.getPrmsFinancialEvlResultyDtlList().size();
            prmsFinancialEvlResultyDtlsList = new ArrayList<>();
            PrmsFinancialEvaluaDetail finEvalDet;
            prmsFinancialEvaluaDetailsList = awardBeanLocal.getQuantityAndUnitPriceForAward(papmsAward);
            for (int i = 0; i < prmsFinancialEvaluaDetailsList.size(); i++) {
                finEvalDet = new PrmsFinancialEvaluaDetail();
                finEvalDet = prmsFinancialEvaluaDetailsList.get(i);
                if (eepBidReg.getBidCategory().equalsIgnoreCase("Goods")) {
                    renderBidGoods = true;
                    renderBidService = false;
                    renderBidWork = false;
                    papmsAwardDetail.setMaterialId(finEvalDet.getItemRegistrationId());
                    papmsAwardDetail.setUnitPrice(finEvalDet.getUnitPrice());
                    papmsAwardDetail.setQuantity(finEvalDet.getQuantity());
                    papmsAward.getPrmsAwardDetailList().add(papmsAwardDetail);
                } else {
                    renderBidGoods = false;
                    renderBidService = false;
                    renderBidWork = true;
                    papmsAwardDetail.setServiceWorkId(prmsFinancialEvlResultyDtl.getServiceId());
                    papmsAwardDetail.setUnitPrice(prmsFinancialEvaluaDetail.getUnitPrice());
                    papmsAwardDetail.setQuantity(prmsFinancialEvaluaDetail.getQuantity());
                    papmsAward.getPrmsAwardDetailList().add(papmsAwardDetail);
                }
            }

            recreateAwardDtlModelDetail();
        }
    }

    public ArrayList<PrmsSupplyProfile> getPrmsSupplyProfiListForFin() {
        if (prmsSupplyProfiListForFin == null) {
            prmsSupplyProfiListForFin = new ArrayList<>();
        }
        return prmsSupplyProfiListForFin;
    }

    public void setPrmsSupplyProfiListForFin(ArrayList<PrmsSupplyProfile> prmsSupplyProfiListForFin) {
        this.prmsSupplyProfiListForFin = prmsSupplyProfiListForFin;
    }

    public ArrayList<PrmsSupplyProfile> getPrmsSupplyProfiListForPos() {
        if (prmsSupplyProfiListForPos == null) {
            prmsSupplyProfiListForPos = new ArrayList<>();
        }
        return prmsSupplyProfiListForPos;
    }

    public void setPrmsSupplyProfiListForPos(ArrayList<PrmsSupplyProfile> prmsSupplyProfiListForPos) {
        this.prmsSupplyProfiListForPos = prmsSupplyProfiListForPos;
    }

    public boolean isRenderInternational() {
        return renderInternational;
    }

    public void setRenderInternational(boolean renderInternational) {
        this.renderInternational = renderInternational;
    }

    public boolean isRenderLocal() {
        return renderLocal;
    }

    public void setRenderLocal(boolean renderLocal) {
        this.renderLocal = renderLocal;

    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public ArrayList<PrmsSupplyProfile> getFailerLocation(ArrayList<PrmsSupplyProfile> evalResult, int type) {
        ArrayList<PrmsSupplyProfile> supplierList = new ArrayList<>();
        PrmsSupplyProfile supp;
        for (int i = 0; i < evalResult.size(); i++) {
            supp = new PrmsSupplyProfile();
            if (type == 1) {
                supp = evalResult.get(i);
            } else if (type == 2) {
                supp = evalResult.get(i);
            } else if (type == 3) {
                supp = evalResult.get(i);
            }
            supplierList.add(supp);
        }
        return supplierList;
    }

    public void changeEventSuppCode(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            eepBidReg = (PrmsBid) event.getNewValue();
            PrmsSupplyProfile supProfile;
            if (eepBidReg.getPostQualification().equals("Applicable")) {
                prmsSupplyProfiListForFin = awardBeanLocal.getsupplierNameFromPostQual(eepBidReg);
                prmsSupplyProfiListForTechEva = awardBeanLocal.getFfailSupplierTechnicalEval(eepBidReg);
                prmsSupplyProfiListForTechEva = getFailerLocation(prmsSupplyProfiListForTechEva, 3);
                prmsSupplyProfiListFomPost = awardBeanLocal.getfailedSupplierNameFromPost(eepBidReg);
                prmsSupplyProfiListFomPost = getFailerLocation(prmsSupplyProfiListFomPost, 1);
                prmsSupplyProfiListFailedForFin = awardBeanLocal.getfailedSupplierNameFromFinc(eepBidReg);
                prmsSupplyProfiListFailedForFin = getFailerLocation(prmsSupplyProfiListFailedForFin, 2);
                prmsSupplyProfiListForTechEva.addAll(prmsSupplyProfiListFomPost);
                prmsSupplyProfiListForTechEva.addAll(prmsSupplyProfiListFailedForFin);
                for (int i = 0; i < prmsSupplyProfiListForTechEva.size(); i++) {
                    supProfile = new PrmsSupplyProfile();
                    prmsFailsupplerAward = new PrmsFailsupplerAward();
                    supProfile = prmsSupplyProfiListForTechEva.get(i);
                    prmsFailsupplerAward.setSuppId(supProfile);
                    papmsAward.getPrmsFailsupplerAwardsList().add(prmsFailsupplerAward);
                    prmsFailsupplerAward.setAwardId(papmsAward);
                }
                recreateFialedSupplierDataModel();
            } else {
                prmsSupplyProfiListForFin = awardBeanLocal.getsupplierNameFromResult(eepBidReg);
                prmsSupplyProfiListForTechEva = awardBeanLocal.getFfailSupplierTechnicalEval(eepBidReg);
                prmsSupplyProfiListForTechEva = getFailerLocation(prmsSupplyProfiListForTechEva, 3);
                prmsSupplyProfiListFailedForFin = awardBeanLocal.getfailedSupplierNameFromFinc(eepBidReg);
                prmsSupplyProfiListFailedForFin = getFailerLocation(prmsSupplyProfiListFailedForFin, 2);
                prmsSupplyProfiListForTechEva.addAll(prmsSupplyProfiListFailedForFin);
                for (int i = 0; i < prmsSupplyProfiListForTechEva.size(); i++) {
                    supProfile = new PrmsSupplyProfile();
                    prmsFailsupplerAward = new PrmsFailsupplerAward();
                    supProfile = prmsSupplyProfiListForTechEva.get(i);
                    prmsFailsupplerAward.setSuppId(supProfile);
                    papmsAward.getPrmsFailsupplerAwardsList().add(prmsFailsupplerAward);
                    prmsFailsupplerAward.setAwardId(papmsAward);
                }
                recreateFialedSupplierDataModel();
            }
            String purchaseTypes;
            purchaseTypes = eepBidReg.getBidCategory();
            if (purchaseTypes.equalsIgnoreCase("Goods")) {
                renderBidGoods = true;
                renderBidService = false;
                renderBidWork = false;
                PrmsAwardDetailModel = null;
            }
            if (purchaseTypes.equalsIgnoreCase("Service")) {
                renderBidGoods = false;
                renderBidService = true;
                renderBidWork = false;
                PrmsAwardDetailModel = null;
            } else {
                renderBidGoods = false;
                renderBidService = false;
                renderBidWork = true;
                PrmsAwardDetailModel = null;
            }
            if (eepBidReg.getBidType().equalsIgnoreCase("International")) {
                renderInternational = true;
                renderLocal = false;
            } else {
                renderInternational = false;
                renderLocal = true;
            }
        }
    }

    public void changeEventSuppCodee(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            eepBidReg = (PrmsBid) event.getNewValue();
            papmsAward.setBidId(eepBidReg);
            PrmsSupplyProfile supProfile;
            if (eepBidReg.getPostQualification().equals("Applicable")) {
                prmsSupplyProfiListForFin = awardBeanLocal.getsupplierNameFromPostQual(eepBidReg);
                prmsSupplyProfiListForTechEva = awardBeanLocal.getFfailSupplierTechnicalEval(eepBidReg);
                prmsSupplyProfiListForTechEva = getFailerLocation(prmsSupplyProfiListForTechEva, 3);
                prmsSupplyProfiListFomPost = awardBeanLocal.getfailedSupplierNameFromPost(eepBidReg);
                prmsSupplyProfiListFomPost = getFailerLocation(prmsSupplyProfiListFomPost, 1);
                prmsSupplyProfiListFailedForFin = awardBeanLocal.getfailedSupplierNameFromFinc(eepBidReg);
                prmsSupplyProfiListFailedForFin = getFailerLocation(prmsSupplyProfiListFailedForFin, 2);
                prmsSupplyProfiListForTechEva.addAll(prmsSupplyProfiListFomPost);
                prmsSupplyProfiListForTechEva.addAll(prmsSupplyProfiListFailedForFin);
                for (int i = 0; i < prmsSupplyProfiListForTechEva.size(); i++) {
                    supProfile = new PrmsSupplyProfile();
                    prmsFailsupplerAward = new PrmsFailsupplerAward();
                    supProfile = prmsSupplyProfiListForTechEva.get(i);
                    prmsFailsupplerAward.setSuppId(supProfile);
                    prmsFailsupplerAward.setReasonForFail(prmsFailsupplerAward.getReasonForFail());
                    papmsAward.getPrmsFailsupplerAwardsList().add(prmsFailsupplerAward);
                    prmsFailsupplerAward.setAwardId(papmsAward);
                }
                recreateFialedSupplierDataModel();
            } else {
                prmsSupplyProfiListForFin = awardBeanLocal.getsupplierNameFromResult(eepBidReg);
                prmsSupplyProfiListForTechEva = awardBeanLocal.getFfailSupplierTechnicalEval(eepBidReg);
                prmsSupplyProfiListForTechEva = getFailerLocation(prmsSupplyProfiListForTechEva, 3);
                prmsSupplyProfiListFailedForFin = awardBeanLocal.getfailedSupplierNameFromFinc(eepBidReg);
                prmsSupplyProfiListFailedForFin = getFailerLocation(prmsSupplyProfiListFailedForFin, 2);
                prmsSupplyProfiListForTechEva.addAll(prmsSupplyProfiListFailedForFin);
                for (int i = 0; i < prmsSupplyProfiListForTechEva.size(); i++) {
                    supProfile = new PrmsSupplyProfile();
                    prmsFailsupplerAward = new PrmsFailsupplerAward();
                    supProfile = prmsSupplyProfiListForTechEva.get(i);
                    prmsFailsupplerAward.setSuppId(supProfile);
                    prmsFailsupplerAward.setReasonForFail(prmsFailsupplerAward.getReasonForFail());
                    papmsAward.getPrmsFailsupplerAwardsList().add(prmsFailsupplerAward);
                    prmsFailsupplerAward.setAwardId(papmsAward);
                }
                recreateFialedSupplierDataModel();
            }
            String purchaseTypes;
            purchaseTypes = eepBidReg.getBidCategory();
            if (purchaseTypes.equalsIgnoreCase("Goods")) {
                renderBidGoods = true;
                renderBidService = false;
                renderBidWork = false;
                PrmsAwardDetailModel = null;
            }
            if (purchaseTypes.equalsIgnoreCase("Service")) {
                renderBidGoods = false;
                renderBidService = true;
                renderBidWork = false;
                PrmsAwardDetailModel = null;
            } else {
                renderBidGoods = false;
                renderBidService = false;
                renderBidWork = true;
                PrmsAwardDetailModel = null;
            }

            purchaseType = eepBidReg.getPostQualification();
            if (purchaseType.equalsIgnoreCase("Applicable")) {
                renderFinacialResult = false;
                renderSuppFromPosQual = true;
            } else {
                renderFinacialResult = true;
                renderSuppFromPosQual = false;
            }
            if (eepBidReg.getBidType().equalsIgnoreCase("International")) {
                renderInternational = true;
                renderLocal = false;
            } else {
                renderInternational = false;
                renderLocal = true;
            }

        }
    }

    public void setEepBidReg(PrmsBid eepBidReg) {
        this.eepBidReg = eepBidReg;
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

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
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

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
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

    public ArrayList<PrmsAward> searchAwardByAwardId(String awardNo) {
        ArrayList<PrmsAward> LocalAward = null;
        papmsAward.setAwardNo(awardNo);
        LocalAward = awardBeanLocal.searchAwardByAwardId(papmsAward);
        return LocalAward;
    }

    ArrayList<PrmsSupplyProfile> listOfApprovedInspReqList;

    public SelectItem[] getApprovedInspectionReqLists() {
        listOfApprovedInspReqList = new ArrayList<>();
        listOfApprovedInspReqList = awardBeanLocal.searchVendorName(eepVendorReg);
        return JsfUtil.getSelectItems(listOfApprovedInspReqList, true);
    }

    public void searchAwardByAwardNo() {
        papmsAward.setPreparedBy(workFlow.getUserAccount());
        prmsAwards = awardBeanLocal.searchAward(papmsAward);
        recreatePrmsAwardsModel();
        papmsAward=new PrmsAward();

    }

    public String saveForAwardRigstration() {
        System.out.println("hi");
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveForAwardRigstration", dataset)) {
                if (updateStatus == 0) {
                    try {
                        System.out.println("hi there");
                        papmsAward.setAwardNo(newcheckListNo);
                        prmsFailsupplerAward.setAwardId(papmsAward);
                        papmsAwardDetail.setAwardId(papmsAward);
                        papmsAward.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        papmsAward.setStatus(Constants.PREPARE_VALUE);
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(fileType);
                        prmsLuDmArchive.setUploadFile(byteFile);
                        prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                        papmsAward.setDocumentId(prmsLuDmArchive);
                        awardBeanLocal.create(papmsAward);
                        JsfUtil.addSuccessMessage("Award information is registered!!");
                        return ClearAward();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);
                        return null;
                    }
                } else {
                    try {
                        prmsLuDmArchive.setFileName(fileName);
                        prmsLuDmArchive.setFileType(fileType);
                        prmsLuDmArchive.setUploadFile(byteFile);
                        prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                        papmsAward.setDocumentId(prmsLuDmArchive);
                        awardBeanLocal.update(papmsAward);
                        ClearAward();
                        saveorUpdateBundle = "Save";
                        return ClearAward();
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                    }
                }
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
            ex.printStackTrace();
        }
        return null;
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

    private String ClearAward() {
        papmsAward = null;
        PrmsAwardDetailModel = null;
        prmsFailsupplerAwardsDataModel = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        groundTotal = 0.0;
        totalSum = 0.0;
        totalvat = 0.0;
        eepBidReg = null;
        eepVendorReg = null;
        withHolding = 0.0;
        contractGarate = 0.0;
        return null;
    }

    public void getTotalPrice() {
        int sizes = papmsAward.getPrmsAwardDetailList().size();
        if ((sizes > 0)) {
            for (int j = 0; j < sizes; j++) {
                unitprice = papmsAward.getPrmsAwardDetailList().get(j).getUnitPrice();
                quantity = papmsAward.getPrmsAwardDetailList().get(j).getQuantity();
                totalprice = quantity * unitprice;
                totalSum = totalSum + totalprice;
                totalvat = 0.15 * totalSum;
                groundTotal = totalSum + totalvat;
            }
        } else {
            for (int i = 0; i < sizes; i++) {
                unitprice = papmsAward.getPrmsAwardDetailList().get(i).getUnitPrice();
                quantity = papmsAward.getPrmsAwardDetailList().get(i).getQuantity();
                totalprice = unitprice * quantity;
                totalSum = totalSum + totalprice;
                totalvat = 0.15 * totalSum;
                groundTotal = totalSum + totalvat;
            }
        }
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public double getContractGarate() {
        return contractGarate;
    }

    public void setContractGarate(double contractGarate) {
        this.contractGarate = contractGarate;
    }

    public double getWithHolding() {
        return withHolding;
    }

    public void setWithHolding(double withHolding) {
        this.withHolding = withHolding;
    }

    public PrmsFailsupplerAward getPrmsFailsupplerAward() {
        if (prmsFailsupplerAward == null) {
            prmsFailsupplerAward = new PrmsFailsupplerAward();
        }
        return prmsFailsupplerAward;
    }

    public void setPrmsFailsupplerAward(PrmsFailsupplerAward prmsFailsupplerAward) {
        this.prmsFailsupplerAward = prmsFailsupplerAward;
    }

    public DataModel<PrmsFailsupplerAward> getPrmsFailsupplerAwardsDataModel() {
        if (prmsFailsupplerAwardsDataModel == null) {
            prmsFailsupplerAwardsDataModel = new ArrayDataModel<>();
        }
        return prmsFailsupplerAwardsDataModel;
    }

    public void setPrmsFailsupplerAwardsDataModel(DataModel<PrmsFailsupplerAward> prmsFailsupplerAwardsDataModel) {
        this.prmsFailsupplerAwardsDataModel = prmsFailsupplerAwardsDataModel;
    }

    public void createNewParty() {
        ClearAward();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateServiceRegistration = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateServiceRegistration = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public void recreateAwardDtlModelDetail() {
        totalSum = 0.0;
        for (int i = 0; i < papmsAward.getPrmsAwardDetailList().size(); i++) {
            papmsAward.getPrmsAwardDetailList().get(i).setTotals(papmsAward.getPrmsAwardDetailList().get(i).getUnitPrice() * papmsAward.getPrmsAwardDetailList().get(i).getQuantity().doubleValue());
            totalSum = totalSum + papmsAward.getPrmsAwardDetailList().get(i).getTotals();
        }
        totalvat = 0.15 * totalSum;
        groundTotal = totalvat + totalSum;
        contractGarate = totalSum * 0.1;
        if (totalSum >= 10000) {
            withHolding = totalSum * 0.02;
        }
        PrmsAwardDetailModel = null;
        PrmsAwardDetailModel = new ListDataModel(new ArrayList<>(papmsAward.getPrmsAwardDetailList()));
    }

    public void recreateFialedSupplierDataModel() {
        prmsFailsupplerAwardsDataModel = null;
        prmsFailsupplerAwardsDataModel = new ListDataModel(new ArrayList<>(papmsAward.getPrmsFailsupplerAwardsList()));
    }

    public List<PrmsAward> getPrmsAwardsbystus() {
        return prmsAwardsbystus;
    }

    public void setPrmsAwardsbystus(List<PrmsAward> prmsAwardsbystus) {
        this.prmsAwardsbystus = prmsAwardsbystus;
    }

    @PostConstruct
    public void init() {
        prmsAwardsbystus = awardBeanLocal.searchawardBystatus();
        setLogerName(SessionBean.getUserName());
        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(workFlow.getUserName());
        bidListForAward = awardBeanLocal.BidNoFormFinancialResult();
    }

    public void searchAwardNo(SelectEvent event) {
        String AwardNo = event.getObject().toString();
        papmsAward.setAwardNo(AwardNo);
        papmsAward = awardBeanLocal.getAwardNo(papmsAward);
        saveStatus = 1;
        saveorUpdateBundle = "Update";
        int sizes = papmsAward.getPrmsAwardDetailList().size();
        for (int i = 0; i < sizes; i++) {
            unitprice = papmsAward.getPrmsAwardDetailList().get(i).getUnitPrice();
            quantity = papmsAward.getPrmsAwardDetailList().get(i).getQuantity();
            totalprice = unitprice * quantity;
            papmsAward.getPrmsAwardDetailList().get(i).setTotals(totalprice);
            totalvat = 0.15 * totalSum;
            groundTotal = totalvat + totalSum;
            contractGarate = totalSum * 0.1;
            if (totalSum >= 10000) {
                withHolding = totalSum * 0.02;
            }

        }
        recreateAwardDtlModelDetail();
    }
    List<PrmsAward> prmsAwards;

    public List<PrmsAward> getPrmsAwards() {
        if (prmsAwards == null) {
            prmsAwards = new ArrayList<>();
        }
        return prmsAwards;
    }

    public void setPrmsAwards(List<PrmsAward> prmsAwards) {
        this.prmsAwards = prmsAwards;
    }

    public void onContactRowEdit(RowEditEvent event) {
        int rowIndex = PrmsAwardDetailModel.getRowIndex();
        PrmsAwardDetail contactPerson = new PrmsAwardDetail();
        PrmsAwardDetail comContPerson = (PrmsAwardDetail) event.getObject();
        boolean found = false;
        for (int i = 0; i < papmsAward.getPrmsAwardDetailList().size(); i++) {
        }
        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");
            papmsAward.getPrmsAwardDetailList().set(rowIndex, comContPerson);
            recreateAwardDtlModelDetail();
        } else {
            papmsAward.getPrmsAwardDetailList().set(rowIndex, comContPerson);
            recreateAwardDtlModelDetail();
        }
    }

    public void onContactRowCancel(RowEditEvent event) {

    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateServiceRegistration = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = PrmsAwardsModel.getRowIndex();
        papmsAward = prmsAwards.get(rowIndex);
        recreateFialedSupplierDataModel();
        recreatePrmsAwardsModel();
    }

    public void recreatePrmsAwardsModel() {
        PrmsAwardsModel = null;
        PrmsAwardsModel = new ListDataModel(new ArrayList<>(getPrmsAwards()));
    }

    public void updateAwardDetail() {
        papmsAwardDetail = getPrmsAwardDetailModel().getRowData();

    }

    public ComLuCountry getComLuCountry() {
        if (comLuCountry == null) {
            comLuCountry = new ComLuCountry();
        }
        return comLuCountry;
    }

    public void setComLuCountry(ComLuCountry comLuCountry) {
        this.comLuCountry = comLuCountry;
    }

    public SelectItem[] getListOfCategory() {
        return JsfUtil.getSelectItems(awardBeanLocal.findAll(), true);
    }

    public SelectItem[] getListOfBidNo() {
        return JsfUtil.getSelectItems(awardBeanLocal.BidNoFormFinancialResult(), true);
    }

    String newcheckListNo;
    String LastcheckListNo = "0";

    public String generateCheckListNo() {
        PrmsAward LastCheckNo = awardBeanLocal.getLastPaymentNo();
        if (LastCheckNo != null) {
            LastcheckListNo = LastCheckNo.getAwardId();
        }
        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();
        int newcheckListN = 0;
        if (LastcheckListNo.equals("0")) {
            newcheckListN = 1;
            newcheckListNo = "award-" + newcheckListN + "/" + f.format(now);
        } else {
            String[] lastInspNos = LastcheckListNo.split("-");
            String lastDatesPatern = lastInspNos[0];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            newcheckListN = Integer.parseInt(lastDatesPaterns[0]);
            newcheckListN = newcheckListN + 1;
            newcheckListNo = "award-" + newcheckListN + "/" + f.format(now);
        }
        return newcheckListNo;
    }

    public String generateSpecficationNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = papmsAward.getAwardNo();
        } else {
            PrmsAward LastCheckNo = awardBeanLocal.getLastPaymentNo();
            if (LastCheckNo != null) {
                LastcheckListNo = LastCheckNo.getAwardId();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newBidNoLast = 0;
            if (LastcheckListNo.equals("0")) {
                newBidNoLast = 1;
                newcheckListNo = "award-NO-" + newBidNoLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
                newBidNoLast = newBidNoLast + 1;
                newcheckListNo = "award-NO-" + newBidNoLast + "/" + f.format(now);
            }
        }
        return newcheckListNo;

    }

    public void rowSelect(SelectEvent event) {
        papmsAward = (PrmsAward) event.getObject();
        renderpnlToSearchPage = true;
        eepBidReg = papmsAward.getBidId();
        eepVendorReg = papmsAward.getSuppId();
        prmsFinancialEvaluaDetailsList = awardBeanLocal.getQuantityAndUnitPriceForAward(papmsAward);
        PrmsSupplyProfile supProfile;
        if (eepBidReg.getPostQualification().equals("Applicable")) {
            //Suppliers who won the bid
            prmsSupplyProfiListForFin = awardBeanLocal.getsupplierNameFromPostQual(eepBidReg);

            //Suppliers who failed at technical evaluation
            prmsSupplyProfiListForTechEva = awardBeanLocal.getFfailSupplierTechnicalEval(eepBidReg);
            prmsSupplyProfiListForTechEva = getFailerLocation(prmsSupplyProfiListForTechEva, 3);

            //Suppliers who failed at post qulification
            prmsSupplyProfiListFomPost = awardBeanLocal.getfailedSupplierNameFromPost(eepBidReg);
            prmsSupplyProfiListFomPost = getFailerLocation(prmsSupplyProfiListFomPost, 1);

            //Suppliers who failed at financial evaluation
            prmsSupplyProfiListFailedForFin = awardBeanLocal.getfailedSupplierNameFromFinc(eepBidReg);
            prmsSupplyProfiListFailedForFin = getFailerLocation(prmsSupplyProfiListFailedForFin, 2);

            prmsSupplyProfiListForTechEva.addAll(prmsSupplyProfiListFomPost);

            prmsSupplyProfiListForTechEva.addAll(prmsSupplyProfiListFailedForFin);

            for (int i = 0; i < prmsSupplyProfiListForTechEva.size(); i++) {
                supProfile = new PrmsSupplyProfile();
                prmsFailsupplerAward = new PrmsFailsupplerAward();
                supProfile = prmsSupplyProfiListForTechEva.get(i);
                prmsFailsupplerAward.setSuppId(supProfile);
                papmsAward.getPrmsFailsupplerAwardsList().add(prmsFailsupplerAward);
                prmsFailsupplerAward.setAwardId(papmsAward);
            }

            recreateFialedSupplierDataModel();
        } else {
            //Suppliers who won the bid
            prmsSupplyProfiListForFin = awardBeanLocal.getsupplierNameFromResult(eepBidReg);

            //Suppliers who failed at technical evaluation
            prmsSupplyProfiListForTechEva = awardBeanLocal.getFfailSupplierTechnicalEval(eepBidReg);
            prmsSupplyProfiListForTechEva = getFailerLocation(prmsSupplyProfiListForTechEva, 3);

            //Suppliers who failed at financial evaluation
            prmsSupplyProfiListFailedForFin = awardBeanLocal.getfailedSupplierNameFromFinc(eepBidReg);
            prmsSupplyProfiListFailedForFin = getFailerLocation(prmsSupplyProfiListFailedForFin, 2);
            prmsSupplyProfiListForTechEva.addAll(prmsSupplyProfiListFailedForFin);
            for (int i = 0; i < prmsSupplyProfiListForTechEva.size(); i++) {
                supProfile = new PrmsSupplyProfile();
                prmsFailsupplerAward = new PrmsFailsupplerAward();
                supProfile = prmsSupplyProfiListForTechEva.get(i);
                prmsFailsupplerAward.setSuppId(supProfile);
                papmsAward.getPrmsFailsupplerAwardsList().add(prmsFailsupplerAward);
                prmsFailsupplerAward.setAwardId(papmsAward);
            }
            recreateFialedSupplierDataModel();
        }

        renderPnlCreateServiceRegistration = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        renderPnlCreateServiceRegistration = true;

        disableServiceAndWorkRdbtnWhenSearch = true;

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
        if (papmsAward.getDocumentId() != null) {
            prmsLuDmArchive = papmsAward.getDocumentId();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
        }
        recreateAwardDtlModelDetail();
        recreateFialedSupplierDataModel();
        recreateDmsDataModel();

    }

    public SelectItem[] fromLuCurrency() {
        return JsfUtil.getSelectItems(insuranceRegisterationBeanLocal.fromLuCurrency(), true);
    }

    public void onContactRowEd(RowEditEvent event) {

        int rowIndex = prmsFailsupplerAwardsDataModel.getRowIndex();
        PrmsFailsupplerAward contactPerson = new PrmsFailsupplerAward();

        PrmsFailsupplerAward comContPerson = (PrmsFailsupplerAward) event.getObject();

        boolean found = false;
        for (int i = 0; i < papmsAward.getPrmsFailsupplerAwardsList().size(); i++) {
            if (i != rowIndex) {
                if (papmsAward.getPrmsFailsupplerAwardsList().get(i).getReasonForFail().equals(comContPerson.getReasonForFail())
                        && papmsAward.getPrmsFailsupplerAwardsList().get(i).getReasonForFail().equals(comContPerson.getReasonForFail())) {
                    found = true;
                    break;
                }
            }
        }
        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");
            papmsAward.getPrmsFailsupplerAwardsList().set(rowIndex, comContPerson);
            recreateFialedSupplierDataModel();
        } else {
            papmsAward.getPrmsFailsupplerAwardsList().set(rowIndex, comContPerson);
            recreateFialedSupplierDataModel();
        }
    }

    public void removeContactPersonInfo() {
        int rowIndex = prmsFailsupplerAwardsDataModel.getRowIndex();
        prmsFailsupplerAward = papmsAward.getPrmsFailsupplerAwardsList().get(rowIndex);
        papmsAward.getPrmsFailsupplerAwardsList().remove(rowIndex);
        recreateFialedSupplierDataModel();
        if (saveorUpdateBundle.equals("Update")) {
            awardBeanLocal.deleteprmsFailsupplerAward(prmsFailsupplerAward);
        }
    }
    DataModel<DocumentModel> docModel;

    public DataModel<DocumentModel> getDocModel() {
        docModel = dataUpload.getDocValueModel();
        return docModel;
    }

    public void setDocModel(DataModel<DocumentModel> docModel) {
        this.docModel = docModel;
    }

    public DataUpload getDataUpload() {
        if (dataUpload == null) {
            dataUpload = new DataUpload();
        }
        return dataUpload;
    }

    public void setDataUpload(DataUpload dataUpload) {
        this.dataUpload = dataUpload;
    }
    int docId;
    int uploadedDocId = -1;

    public void uploadListener(FileUploadEvent event) {
        InputStream inputStream = null;
        try {
            fileName = event.getFile().getFileName().split("\\.")[0];
            fileType = event.getFile().getFileName().split("\\.")[1];
            inputStream = event.getFile().getInputstream();
            byteFile = dataUpload.extractByteArray(inputStream);
            if (byteFile != null) {
                JsfUtil.addSuccessMessage("Upload Successfully File Name " + fileName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public DmsHandler getDmsHandler() {
        return dmsHandler;
    }

    public void setDmsHandler(DmsHandler dmsHandler) {
        this.dmsHandler = dmsHandler;
    }

    public List<DocumentModel> getNewDoclist() {
        return newDoclist;
    }

    public void setNewDoclist(List<DocumentModel> newDoclist) {
        this.newDoclist = newDoclist;
    }

    public List<Integer> getSavedDocIds() {
        return savedDocIds;
    }

    public void setSavedDocIds(List<Integer> savedDocIds) {
        this.savedDocIds = savedDocIds;
    }

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getUploadedDocId() {
        return uploadedDocId;
    }

    public void setUploadedDocId(int uploadedDocId) {
        this.uploadedDocId = uploadedDocId;
    }

    public void onRowSelect(SelectEvent event) {
        prmsLuDmArchive = (PrmsLuDmArchive) event.getObject();
        isFileSelected = true;
    }

    public void uploadListenerr(FileUploadEvent event) {
        try {
            documentModel = new DocumentModel();
            documentModel.setDocFormat(event.getFile().getFileName().split("\\.")[1]);
            documentModel.setName(event.getFile().getFileName().split("\\.")[0]);
            documentModel.setUserId(Long.valueOf("2"));
            String categoryBundle = "et/gov/eep/commonApplications/securityServer";
            documentModel.setApp(Utility.getBundleValue(categoryBundle, "categoryName"));
            documentModel.setCreater("Tehetena");
            File fileDoc = new File(event.getFile().getFileName());
            FileUtils.writeByteArrayToFile(fileDoc, dmsHandler.extractByteArray1(event.getFile().getInputstream()));
            documentModel.setDate(et.gov.eep.mms.controller.StringDateManipulation.todayInEC());
            documentModel.setFile(fileDoc);
            documentModel.setByteFile(dmsHandler.extractByteArray1(event.getFile().getInputstream()));
            int savedDocId = dmsHandler.saveDocument(documentModel);
            if (savedDocId != -1) {
                savedDocIds.add(savedDocId);
                newDoclist.add(documentModel);
                setDocValueModel(new ListDataModel(newDoclist));
                JsfUtil.addSuccessMessage("Upload Successfully!!!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onRowSelectf(SelectEvent event) {
        dm = (DocumentModel) event.getObject();
    }
    StreamedContent fileDown;

    public StreamedContent getFileDown() throws Exception {
        if (isFileSelected = true) {
            dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Pls Select File Name");
        }
        return file;
    }

    public void setFileDown(StreamedContent fileDown) {
        this.fileDown = fileDown;
    }

    DataModel<DocumentModel> docList;
    StreamedContent file;

    public DataModel<DocumentModel> getDocList() {
        if (docList == null) {
            docList = new ListDataModel<>();
        }
        return docList;
    }

    public void setDocList(DataModel<DocumentModel> docList) {
        this.docList = docList;
    }
    DocumentModel dm = new DocumentModel();

    public DocumentModel getDm() {
        return dm;
    }

    public void setDm(DocumentModel dm) {
        this.dm = dm;
    }

    public ArrayList<PrmsSupplyProfile> getPrmsSupplyProfiListForTechEva() {
        if (prmsSupplyProfiListForTechEva == null) {
            prmsSupplyProfiListForTechEva = new ArrayList<>();
        }
        return prmsSupplyProfiListForTechEva;
    }

    public void setPrmsSupplyProfiListForTechEva(ArrayList<PrmsSupplyProfile> prmsSupplyProfiListForTechEva) {
        this.prmsSupplyProfiListForTechEva = prmsSupplyProfiListForTechEva;
    }

    public ArrayList<PrmsSupplyProfile> getPrmsSupplyProfiListFailedForFin() {
        if (prmsSupplyProfiListFailedForFin == null) {
            prmsSupplyProfiListFailedForFin = new ArrayList<>();
        }
        return prmsSupplyProfiListFailedForFin;
    }

    public void setPrmsSupplyProfiListFailedForFin(ArrayList<PrmsSupplyProfile> prmsSupplyProfiListFailedForFin) {
        this.prmsSupplyProfiListFailedForFin = prmsSupplyProfiListFailedForFin;
    }

    public List<PrmsBid> getBidListForAward() {
        if (bidListForAward == null) {
            bidListForAward = new ArrayList<>();
        }
        return bidListForAward;
    }

    public void setBidListForAward(List<PrmsBid> bidListForAward) {
        this.bidListForAward = bidListForAward;
    }

    public boolean isDisableServiceAndWorkRdbtnWhenSearch() {
        return disableServiceAndWorkRdbtnWhenSearch;
    }

    public void setDisableServiceAndWorkRdbtnWhenSearch(boolean disableServiceAndWorkRdbtnWhenSearch) {
        this.disableServiceAndWorkRdbtnWhenSearch = disableServiceAndWorkRdbtnWhenSearch;
    }

    public boolean isRenderPnlCreateServiceRegistration() {
        return renderPnlCreateServiceRegistration;
    }

    public void setRenderPnlCreateServiceRegistration(boolean renderPnlCreateServiceRegistration) {
        this.renderPnlCreateServiceRegistration = renderPnlCreateServiceRegistration;
    }

    public void createNewServiceRegInfo() {
        ClearAward();
        saveorUpdateBundle = "Save";

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateServiceRegistration = true;
            renderPnlManPage = false;
            disableServiceAndWorkRdbtnWhenSearch = false;

        }
    }

    public void whenSeachClicked() {
        ClearAward();
        renderPnlCreateServiceRegistration = false;
        renderPnlManPage = true;
    }

    public PrmsAwardFileUpload getPrmsAwardFileUpload() {
        return prmsAwardFileUpload;
    }

    public void setPrmsAwardFileUpload(PrmsAwardFileUpload prmsAwardFileUpload) {
        this.prmsAwardFileUpload = prmsAwardFileUpload;
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected = true) {
            System.out.println("Selected Dile Name is " + prmsLuDmArchive.getFileName());
            dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Pls Select File Name");
        }
        return file;
    }

    public void remove(DocumentModel document) {
        document = documentModel;
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        recreateDmsDataModel();
    }

    public void recreateDmsDataModel() {

        prmsLuDmArchivesDModel = null;
        prmsLuDmArchivesDModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    public boolean isIsFileSelected() {
        return isFileSelected;
    }

    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected = isFileSelected;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchivesDModel() {
        if (prmsLuDmArchivesDModel == null) {
            prmsLuDmArchivesDModel = new ArrayDataModel<>();
        }
        return prmsLuDmArchivesDModel;
    }

    public void setPrmsLuDmArchivesDModel(DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel) {
        this.prmsLuDmArchivesDModel = prmsLuDmArchivesDModel;
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

    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        if (prmsLuDmArchiveSelection == null) {
            prmsLuDmArchiveSelection = new PrmsLuDmArchive();
        }
        return prmsLuDmArchiveSelection;
    }

    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
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

    public void goBackSearchPageBtnAction() {
        renderPnlCreateServiceRegistration = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

}
