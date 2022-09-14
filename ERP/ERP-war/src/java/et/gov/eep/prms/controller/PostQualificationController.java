/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.PostQualificationBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidderRegDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.mms.businessLogic.MmsItemRegisrtationBean;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsPostDetail;
import et.gov.eep.prms.entity.PrmsPostQualification;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.SelectItem;
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

@Named(value = "PostQualificationController")
@ViewScoped

public class PostQualificationController implements Serializable {

    @EJB
    PostQualificationBeanLocal postQualificationBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @Inject
    PrmsPostQualification prmsPostQualification;
    @Inject
    private PrmsPostDetail prmsPostDetail;
    @Inject
    private PrmsBid prmsBid;
    @Inject
    PrmsBidderRegDetail prmsBidderRegDetail;
    @Inject
    PrmsFinancialEvalResult prmsFinancialEvalResult;
    @Inject
    PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl;
    @Inject
    private PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    private PrmsBidDetail bidDetail;
    @Inject
    private MmsItemRegistration mmsItemRegistration;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    private WorkFlow workFlow;
    @Inject
    SessionBean sessionBean;
    private String saveorUpdateBundle = "Save";
    int saveStatus = 0;
    DataModel<PrmsPostQualification> prmsPostQualificationModel;
    DataModel<PrmsPostQualification> tblprmsPostQualificationModel;
    DataModel<PrmsPostDetail> prmsPostDetailModel;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    List<PrmsPostQualification> postQualificationList;
    List<PrmsPostQualification> postQualificationSearchParameterLst;
    private List<PrmsSupplyProfile> supplierListSecond;
    private List<PrmsPostQualification> tblpostQualificationList;
    List<PrmsFinancialEvalResult> financialEvalResultList;
    List<PrmsFinancialEvlResultyDtl> resulFormList;
    Set<String> postQualificationDetlCheck = new HashSet<>();
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private String activeIndex;
    private String icone = "ui-icon-plus";
    private boolean renderItem = false;
    private boolean renderLot;
    private boolean renderpnlToSearchPage;

    private String selectedValue = "";
    private int selectedRowIndex;
    private boolean renderDecision = false;
    private boolean isRendercreate;
    private String loggerName;
    private boolean isRenderNotify;

    int updateStatus = 0;
    int requestNotificationCounter = 0;
    PrmsPostQualification selectPrmsPostQualification;

    public PostQualificationController() {

    }

    @PostConstruct
    public void initial() {
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        setLoggerName(workFlow.getUserName());
        System.out.println("is app---" + workFlow.isApproveStatus() + "is check===" + workFlow.isCheckStatus() + "is prep==" + workFlow.isPrepareStatus());
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

    public PrmsPostQualification getSelectPrmsPostQualification() {
        return selectPrmsPostQualification;
    }

    public void setSelectPrmsPostQualification(PrmsPostQualification selectPrmsPostQualification) {
        this.selectPrmsPostQualification = selectPrmsPostQualification;
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

    public String getLastPQNo() {
        return lastPQNo;
    }

    public void setLastPQNo(String lastPQNo) {
        this.lastPQNo = lastPQNo;
    }

    public List<PrmsPostQualification> getTblpostQualificationList() {
        if (tblpostQualificationList == null) {
            tblpostQualificationList = new ArrayList<>();
        }
        return tblpostQualificationList;
    }

    public void setTblpostQualificationList(List<PrmsPostQualification> tblpostQualificationList) {
        this.tblpostQualificationList = tblpostQualificationList;
    }

    public String getNewPQNo() {
        return newPQNo;
    }

    public void setNewPQNo(String newPQNo) {
        this.newPQNo = newPQNo;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
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

    public DataModel<PrmsPostQualification> getPrmsPostQualificationModel() {
        if (prmsPostQualificationModel == null) {
            prmsPostQualificationModel = new ListDataModel<>();
        }

        return prmsPostQualificationModel;
    }

    public void setPrmsPostQualificationModel(DataModel<PrmsPostQualification> prmsPostQualificationModel) {
        this.prmsPostQualificationModel = prmsPostQualificationModel;
    }

    public List<PrmsPostQualification> getPostQualificationList() {
        if (postQualificationList == null) {
            postQualificationList = new ArrayList<>();
        }
        return postQualificationList;
    }

    public void setPostQualificationList(List<PrmsPostQualification> postQualificationList) {
        this.postQualificationList = postQualificationList;
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

    public Set<String> getPostQualificationDetlCheck() {
        return postQualificationDetlCheck;
    }

    public void setPostQualificationDetlCheck(Set<String> postQualificationDetlCheck) {
        this.postQualificationDetlCheck = postQualificationDetlCheck;
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

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
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

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getRequestNotificationCounter() {
        postQualificationList = postQualificationBeanLocal.getPostRqLists();
        requestNotificationCounter = postQualificationList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public void RequestListChange(ValueChangeEvent eve) {
        //   if (eve.getNewValue() != null) {
        //   postQualificationList = postQualificationBeanLocal.searchByPostQ(prmsPostQualification);
        prmsPostQualification = (PrmsPostQualification) eve.getNewValue();
        populateDataForApp();
        // }
    }

    public void populateDataForApp() {
        prmsPostQualification.setPostId(prmsPostQualification.getPostId());
        prmsPostQualification.setPostId(prmsPostQualification.getPostId());
        prmsPostQualification.setBidId(prmsFinancialEvalResult.getBidId());
//        prmsPostQualification = postQualificationBeanLocal.getReqPQN(prmsPostQualification.getPostId());
//        prmsPostQualification.setBidNo(prmsPostQualification.getBidNo());
//         prmsPurchasePlnDetail = actionPlanBeanLocal.getPlanNo(event.getObject().toString());
        prmsFinancialEvalResult = prmsPostQualification.getFinancId();
        // prmsBid = prmsPostQualification.getBidId();
        renderPnlManPage = false;
        renderPnlCreateParty = true;
//        status = 1;
        saveorUpdateBundle = "Update";
        recreatePostDetail();

    }

    public boolean isRenderItem() {
        return renderItem;
    }

    public void setRenderItem(boolean renderItem) {
        this.renderItem = renderItem;
    }

    public boolean isRenderLot() {
        return renderLot;
    }

    public void setRenderLot(boolean renderLot) {
        this.renderLot = renderLot;
    }

    public PrmsFinancialEvlResultyDtl getPrmsFinancialEvlResultyDtl() {
        if (prmsFinancialEvlResultyDtl == null) {
            prmsFinancialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();

        }
        return prmsFinancialEvlResultyDtl;
    }

    public void setPrmsFinancialEvlResultyDtl(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl) {
        this.prmsFinancialEvlResultyDtl = prmsFinancialEvlResultyDtl;
    }

    public PrmsPostQualification getPrmsPostQualification() {
        if (prmsPostQualification == null) {
            prmsPostQualification = new PrmsPostQualification();
        }
        return prmsPostQualification;
    }

    public void setPrmsPostQualification(PrmsPostQualification prmsPostQualification) {
        this.prmsPostQualification = prmsPostQualification;
    }

    public PrmsBidderRegDetail getPrmsBidderRegDetail() {
        if (prmsBidderRegDetail == null) {
            prmsBidderRegDetail = new PrmsBidderRegDetail();
        }
        return prmsBidderRegDetail;
    }

    public void setPrmsBidderRegDetail(PrmsBidderRegDetail prmsBidderRegDetail) {
        this.prmsBidderRegDetail = prmsBidderRegDetail;
    }

    private void ClearPrmsPostQualification() {
        prmsPostQualification = null;
        prmsFinancialEvalResult = null;
        prmsBid = null;
        prmsPostDetail = null;
        prmsPostDetailModel = null;
        wfPrmsProcessed.setProcessedOn(null);
        wfPrmsProcessed.setCommentGiven(null);
    }

    public void recreatPrmsPostQualificationModel() {
        prmsPostQualificationModel = null;
        prmsPostQualificationModel = new ListDataModel<>(new ArrayList<>(getPostQualificationList()));
    }

    public PostQualificationBeanLocal getPostQualificationBeanLocal() {
        return postQualificationBeanLocal;
    }

    private void recreatePostDetail() {
        prmsPostDetailModel = null;
        prmsPostDetailModel = new ListDataModel<>(new ArrayList<>(prmsPostQualification.getPrmsPostDetailList()));
    }

    public void setPostQualificationBeanLocal(PostQualificationBeanLocal postQualificationBeanLocal) {
        this.postQualificationBeanLocal = postQualificationBeanLocal;
    }

    public DataModel<PrmsPostQualification> getTblprmsPostQualificationModel() {
        if (tblprmsPostQualificationModel == null) {
            tblprmsPostQualificationModel = new ArrayDataModel<>();
        }
        return tblprmsPostQualificationModel;
    }

    public void setTblprmsPostQualificationModel(DataModel<PrmsPostQualification> tblprmsPostQualificationModel) {
        this.tblprmsPostQualificationModel = tblprmsPostQualificationModel;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

//    public void bidderAndSupplier() {
//        prmsFinancialEvalResult.getFinancialevaId().getBidId();
//    }
    private List<PrmsBidDetail> bidDetForLot;
    private List<PrmsBidDetail> bidDetForItem;
    private List<PrmsSupplyProfile> supplierList;
    List<PrmsFinancialEvlResultyDtl> bidderNameList;
    private List<PrmsFinancialEvlResultyDtl> nextBidderNameList;
    private List<PrmsFinancialEvlResultyDtl> nextLotNameList;
    List<MmsItemRegistration> itemCodeList;
    List<PrmsFinancialEvlResultyDtl> lotNumberList;

    public List<MmsItemRegistration> getItemCodeList() {
        return itemCodeList;
    }

    public void setItemCodeList(List<MmsItemRegistration> itemCodeList) {
        this.itemCodeList = itemCodeList;
    }

    public List<PrmsFinancialEvlResultyDtl> getLotNumberList() {
        return lotNumberList;
    }

    public void setLotNumberList(List<PrmsFinancialEvlResultyDtl> lotNumberList) {
        this.lotNumberList = lotNumberList;
    }

//    public void getBidNo(ValueChangeEvent e) {
//        if (e.getNewValue() != null) {
//            prmsFinancialEvalResult = (PrmsFinancialEvalResult) e.getNewValue();
//            if (prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().size() > 0) {
//                prmsBid = prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(0).getBidDetailId().getBidId();
//            } else {
//                System.out.println("----No bid no for-----" + prmsFinancialEvalResult);
//            }
//
//            for (int i = 0; i < prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().size(); i++) {
//                //  System.out.println("12");
//                supplierList = postQualificationBeanLocal.getItemNameLists(prmsBid);
////                PrmsBidDetail supLot = new PrmsBidDetail();
////                PrmsSupplyProfile supp = new PrmsSupplyProfile();
////                if (prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(i).getResultRank() == 1) {
////                    supLot = prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(i).getBidDetailId();
////                    bidDetForLot.add(supLot);
////                    supp = prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(i).getSupplierId();
////                    supplierList.add(supp);
////                }
//            }
//
//        }
//
//    }
    int nominatedRank = 1;

    public void getBidNo(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            System.out.println("value not null");
//            prmsFinancialEvlResultyDtl = (PrmsFinancialEvlResultyDtl) e.getNewValue();

            prmsFinancialEvalResult = (PrmsFinancialEvalResult) e.getNewValue();
            System.out.println("bid n" + prmsFinancialEvalResult.getBidId());
            System.out.println("financial no" + prmsFinancialEvalResult.getFinancialResultNo());
//            prmsFinancialEvalResult = prmsFinancialEvlResultyDtl.getFnancialResultId();
//            System.out.println("financila id from master"+prmsFinancialEvalResult.getFinancialevaId()+
//                    "financila id from Detail"+prmsFinancialEvalResult.getFinancialevaId());
            System.out.println("selected id==" + prmsFinancialEvalResult.getId());
//             prmsFinancialEvalResult.setBidId(prmsBid);
            prmsFinancialEvlResultyDtl.setFnancialResultId(prmsFinancialEvalResult);
//            prmsFinancialEvlResultyDtl.setBidDetailId(bidDetail);
            System.out.println("financila id master id from detail" + prmsFinancialEvlResultyDtl.getFnancialResultId());
//            // prmsFinancialEvlResultyDtl.setFnancialResultId(prmsFinancialEvalResult);
//            bidderNameList = postQualificationBeanLocal.getBidNameLists(prmsFinancialEvalResult, nominatedRank);
//           //  supplierList = filterSupplyerList1(bidderNameList);
            //    supplierList = postQualificationBeanLocal.getBidNameLists(prmsFinancialEvalResult, nominatedRank);
//           
//            System.out.println("bidder===" + prmsFinancialEvlResultyDtl.getSupplierId());
//            
            //prmsFinancialEvlResultyDtl = (PrmsFinancialEvlResultyDtl) e.getNewValue();
//            prmsFinancialEvalResult = (PrmsFinancialEvalResult) e.getNewValue();
//            System.out.println("bid n" + prmsFinancialEvalResult.getBidId());
//            
//            financialEvalResultList = postQualificationBeanLocal.getBidNameLists(prmsFinancialEvalResult, nominatedRank);
//            System.out.println("bid list" + bidderNameList);
//            System.out.println("bidder===" + prmsFinancialEvlResultyDtl.getSupplierId());
//            
//            prmsFinancialEvlResultyDtl = (PrmsFinancialEvlResultyDtl) e.getNewValue();
//            System.out.println("bid n" + prmsFinancialEvlResultyDtl.getFnancialResultId().getBidId());
            bidderNameList = postQualificationBeanLocal.getBidNameLists(prmsFinancialEvlResultyDtl, nominatedRank);
//            System.out.println("bid list" + bidderNameList);
//            System.out.println("bidder===" + prmsFinancialEvlResultyDtl.getSupplierId());
//              if (e.getNewValue() != null) {
//            System.out.println("value not null");
//            prmsFinancialEvalResult = (PrmsFinancialEvalResult) e.getNewValue();
//            if (prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().size() > 0) {
//                prmsBid = prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(0).getBidDetailId().getBidId();
//            } else {
//                System.out.println("----No bid no for-----" + prmsFinancialEvalResult);
//            }
        }
    }

    public void getChangedItemLot(ValueChangeEvent e) {
        if (e.getNewValue() != null) {
            System.out.println("value not null");
            prmsFinancialEvlResultyDtl = (PrmsFinancialEvlResultyDtl) e.getNewValue();
            if (prmsFinancialEvlResultyDtl.getItemId() != null) {
                System.out.println(" it is item" + e.getNewValue().toString());
                renderItem = true;
                renderLot = false;
                itemCodeList = postQualificationBeanLocal.getItemCodeList(prmsFinancialEvlResultyDtl);
            } else if (prmsFinancialEvlResultyDtl.getLotNo() != null) {
                System.out.println("it is Lot Number");
                renderLot = true;
                renderItem = false;
                lotNumberList = postQualificationBeanLocal.getLotNumberList(prmsFinancialEvlResultyDtl);
            }
//            prmsFinancialEvalResult = (PrmsFinancialEvalResult) e.getNewValue();
//            //if (prmsFinancialEvalResult.getBidId().getPrmsBidDetailList().size() > 0)
//             if (prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().size()>0){
//               // if (prmsFinancialEvalResult.getBidId().getPrmsBidDetailList().get(0).getLotName().equals("")) {
//                    if(prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(0).getLotNo().equals(""))
//                    supplierList = postQualificationBeanLocal.getItemNameLists(prmsBid,prmsSupplyProfile);
//                } else if (prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(0).getItemId().equals("")) {
//
//                    bidDetForLot = postQualificationBeanLocal.getLotNames(prmsBid);
//                }
        }

    }

//    public SelectItem[] listOfFinEvlNO() {
//        financialEvalResultList = postQualificationBeanLocal.listOfFinEvlNO();
//        return JsfUtil.getSelectItems(financialEvalResultList, true);
//    }
    public List<PrmsFinancialEvalResult> getFinancialEvalResultList() {
        if (financialEvalResultList == null) {
            financialEvalResultList = new ArrayList<>();
            financialEvalResultList = postQualificationBeanLocal.listOfFinEvlNO();
        }
        return financialEvalResultList;
    }

    public void setFinancialEvalResultList(List<PrmsFinancialEvalResult> financialEvalResultList) {
        this.financialEvalResultList = financialEvalResultList;
    }

    public List<PrmsFinancialEvlResultyDtl> getResulFormList() {
        if (resulFormList == null) {
            resulFormList = new ArrayList<>();
            resulFormList = postQualificationBeanLocal.getResultFormLists();
            System.out.println("lists" + resulFormList);
        }
        return resulFormList;
    }

    public void setResulFormList(List<PrmsFinancialEvlResultyDtl> resulFormList) {
        this.resulFormList = resulFormList;
    }

    public List<PrmsFinancialEvlResultyDtl> getBidderNameList() {
        return bidderNameList;
    }

    public void setBidderNameList(List<PrmsFinancialEvlResultyDtl> bidderNameList) {
        this.bidderNameList = bidderNameList;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsPostQualification.setColumnName(event.getNewValue().toString());
            prmsPostQualification.setColumnValue(null);
        }
    }

    public List<PrmsPostQualification> getPostQualificationSearchParameterLst() {
        if (postQualificationSearchParameterLst == null) {
            postQualificationSearchParameterLst = new ArrayList<>();
            postQualificationSearchParameterLst = postQualificationBeanLocal.getParamNameList();
        }
        return postQualificationSearchParameterLst;
    }

    public void setPostQualificationSearchParameterLst(List<PrmsPostQualification> postQualificationSearchParameterLst) {
        this.postQualificationSearchParameterLst = postQualificationSearchParameterLst;
    }

    public void createNewPostQualification() {

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

    public void clearDetail() {
        prmsPostDetail = null;
        prmsSupplyProfile = null;
        prmsFinancialEvlResultyDtl = null;
    }

    private boolean checkStatus;

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public void addTable() {

        System.out.println("-----------" + prmsPostDetail.getResults());
        if (checkStatus == false) {

            prmsPostQualification.getPrmsPostDetailList().add(prmsPostDetail);
            prmsPostDetail.setFinalDecision(decision);
            prmsPostDetail.setPostId(prmsPostQualification);
            System.out.println("supplier name" + prmsFinancialEvlResultyDtl.getSupplierId());
            prmsPostDetail.setBidderId(prmsFinancialEvlResultyDtl.getSupplierId());

            System.out.println("hhhhhhhh");
            if (prmsFinancialEvlResultyDtl.getItemId() != null) {
                prmsPostDetail.setMaterialId(prmsFinancialEvlResultyDtl.getItemId());
                System.out.println("item name" + prmsFinancialEvlResultyDtl.getItemId().getMatName());
            } else if (prmsFinancialEvlResultyDtl.getLotNo() != null) {
                System.out.println("when lot");
                prmsPostDetail.setLotName(prmsFinancialEvlResultyDtl.getLotNo());
            }

        } else if (checkStatus == true) {
            prmsPostQualification.getPrmsPostDetailList().set(saveStatus, prmsPostDetail);
        }
        // System.out.println("=========" + prmsPostQualification.getPrmsPostDetailList().size());
        recreatePostDetail();
        clearDetail();
    }

    public void edit() {
        System.out.println("Edit CommandLink action");
        checkStatus = true;
//        if (saveorUpdateBundle.equals("Update")) {
//            for (int i = 0; i < prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().size(); i++) {
//                PrmsBidDetail supLot = new PrmsBidDetail();
//                PrmsSupplyProfile supp = new PrmsSupplyProfile();
//                if (prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(i).getResultRank() == 1) {
//                    // supLot = prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(i).getBidDetailId();
//
//                  //  bidDetForLot.add(supLot);
//                    supp = prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(i).getSupplierId();
//                    supplierList.add(supp);
//                }
//            }
//        }

        prmsPostDetail = prmsPostDetailModel.getRowData();
        selectedRowIndex = prmsPostDetailModel.getRowIndex();
        bidderNameList = postQualificationBeanLocal.getBidNameLists(prmsFinancialEvlResultyDtl, nominatedRank);

        // prmsSupplyProfile = prmsPostDetail.getBidderId();
        // System.out.println("the supplier id is from finacialDet "+prmsFinancialEvlResultyDtl.getSupplierId());
//          prmsPostDetail.setBidderId(prmsFinancialEvlResultyDtl.getSupplierId());
//          System.out.println("the supplier id on profile "+prmsSupplyProfile);
//         
        // prmsFinancialEvlResultyDtl.setSupplierId(prmsSupplyProfile);
//           System.out.println("the vendor name "+prmsFinancialEvlResultyDtl.getSupplierId());
    }

    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = prmsPostQualificationModel.getRowIndex();
        prmsPostQualification = postQualificationList.get(rowIndex);

        recreatPrmsPostQualificationModel();
//        recreateAddressInfoDataModel();

    }

    public void rowSelect(SelectEvent event) {

        prmsPostQualification = (PrmsPostQualification) event.getObject();
//        prmsPostQualification.setPostId(prmsPostQualification.getPostId());
//        prmsPostQualification = postQualificationBeanLocal.getReqPQN(prmsPostQualification.getPostId());
//        prmsPostQualification.setBidNo(prmsPostQualification.getBidNo());
//         prmsPurchasePlnDetail = actionPlanBeanLocal.getPlanNo(event.getObject().toString());
        prmsFinancialEvalResult = prmsPostQualification.getFinancId();
        renderpnlToSearchPage = true;
        prmsFinancialEvlResultyDtl.setFnancialResultId(prmsFinancialEvalResult);
        if (workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(prmsPostQualification.getProcessedOn());
        }
        // prmsBid = prmsPostQualification.getBidId();
        renderPnlManPage = false;
        renderPnlCreateParty = true;
//        status = 1;
        saveorUpdateBundle = "Update";
        recreatePostDetail();
        recreatPrmsPostQualificationModel();

    }

    public String savePost() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "savePost", dataset)) {
                System.out.println("in save area");
                if (saveorUpdateBundle.equals("Save")) {
                    if (saveStatus == 0) {
                        try {
                            generatePQNo();
//                    setPrmsPostQualification(prmsPostQualification);
                            prmsPostQualification.setPqNo(newPQNo);
                            prmsPostQualification.setBidId(prmsFinancialEvalResult.getBidId());
                            prmsPostQualification.setFinancId(prmsFinancialEvalResult);
                            // prmsPostQualification.setBidId(prmsBid);
//                             prmsPostDetail.setFinalDecision(decision);
                            //  prmsPostQualification.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                            //  System.out.println("get prep==="+wfPrmsProcessed.getProcessedBy());
//                    Date currentDate = Calendar.getInstance().getTime();
//                    prmsPostQualification.setStaus(Integer.parseInt(wfPrmsProcessed.getDecision()));
//                    prmsPostQualification.getPrmsWorkFlowProccedList().add(wfPrmsProcessed);
//                    wfPrmsProcessed.setPostId(prmsPostQualification);
//                    wfPrmsProcessed.setProcessedon(currentDate);

                            System.out.println("1");

//                            wfPrmsProcessed.setPostId(prmsPostQualification);
                            prmsPostQualification.setStatus(Constants.PREPARE_VALUE);

                            prmsPostQualification.setProcessedOn(wfPrmsProcessed.getProcessedOn());
                            wfPrmsProcessed.setDecision(String.valueOf(prmsPostQualification.getStatus()));
                            System.out.println("3");
                            prmsPostQualification.getPrmsWorkFlowProccedList().add(wfPrmsProcessed);
                            System.out.println("2");
                            postQualificationBeanLocal.create(prmsPostQualification);
                            System.out.println("After Save");
                            JsfUtil.addSuccessMessage("Post Qualification information is registered!!");
                            ClearPrmsPostQualification();

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addSuccessMessage("some thing is going to error" + e);
                            System.out.println("the error==" + e);
                            ClearPrmsPostQualification();
                            return null;
                        }
                    }
                } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    try {
                        postQualificationBeanLocal.update(prmsPostQualification);
                        JsfUtil.addSuccessMessage("Post Qualification information is updated ");
                        saveorUpdateBundle = "Save";
                        ClearPrmsPostQualification();
                    } catch (Exception e) {
                        JsfUtil.addSuccessMessage("error== w/n data modification" + e);
                        ClearPrmsPostQualification();
                    }
                } else if (workFlow.isApproveStatus() || workFlow.isCheckStatus() && saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_VALUE);
                        prmsPostQualification.setStatus(Constants.APPROVE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                        prmsPostQualification.setStatus(Constants.CHECK_APPROVE_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_APPROVE_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                        workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                        prmsPostQualification.setStatus(Constants.APPROVE_REJECT_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                    } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                        workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                        prmsPostQualification.setStatus(Constants.CHECK_REJECT_VALUE);
                        wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                    }
                    wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                    postQualificationBeanLocal.update(prmsPostQualification);
                    JsfUtil.addSuccessMessage("Post Qualification information is updated ");
                    saveorUpdateBundle = "Save";
                    ClearPrmsPostQualification();
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {

        }
        return null;
    }

    String lastPQNo = "0";
    String newPQNo;

    public String generatePQNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newPQNo = prmsPostQualification.getPqNo();

        } else {
            PrmsPostQualification lastPQNoss = postQualificationBeanLocal.getPQNos();
            if (lastPQNoss != null) {
                lastPQNo = lastPQNoss.getPostId().toString();

            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newPQNoList = 0;
            if (lastPQNo.equals("0")) {
                newPQNoList = 1;
                newPQNo = "PostQ-NO-" + newPQNoList + "/" + f.format(now);
            } else {
                String[] lastInspNos = lastPQNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newPQNoList = Integer.parseInt(lastDatesPaterns[0]);
                newPQNoList = newPQNoList + 1;
                newPQNo = "PostQ-NO-" + newPQNoList + "/" + f.format(now);
            }
        }
        return newPQNo;

    }

    //         EventEntry eventEntry = new EventEntry();
//         eventEntry.setSessionId(SessionBean.getSessionID());
//         eventEntry.setUserId(SessionBean.getUserId());
//         QName qualifiedName = new QName("", "project");
//         JAXBElement<String> test = new JAXBElement<String> (qualifiedName,String.class,null,SessionBean.getUserName());
//         eventEntry.setUserLogin(test);
////..... add more information by calling fields defined in the object 
//            security.addEventLog(eventEntry, dataset);
//            }
    //       } catch (Exception ex) {
    //    }
//    public void setSelectedSupplier(ValueChangeEvent e) {
//        setPrmsSupplyProfile((PrmsSupplyProfile) e.getNewValue());
//
//        for (int i = 0; i < prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().size(); i++) {
//            PrmsBidDetail supLot = new PrmsBidDetail();
//            PrmsSupplyProfile supp = new PrmsSupplyProfile();
//            supplierList = postQualificationBeanLocal.getItemNameLists(prmsBid,prmsSupplyProfile);
//            if (prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(i).getResultRank() == 1) {
//                supLot = prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(i).getBidDetailId();
//                bidDetForLot.add(supLot);
//                supp = prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().get(i).getSupplierId();
//                supplierList.add(supp);
//            }
//        }
//
//    }
    public void setSelectedBidDetail(ValueChangeEvent e) {
        bidDetail = (PrmsBidDetail) e.getNewValue();
    }

    public void searchByPostQ() {
        prmsPostQualification.setPreparedBy(Integer.valueOf(workFlow.getPreparedBy()));
        postQualificationList = postQualificationBeanLocal.searchByPostQ(prmsPostQualification);
        recreatPrmsPostQualificationModel();
        prmsPostQualification = new PrmsPostQualification();
    }

    /**
     * @return the bidDetForLot
     */
    public List<PrmsBidDetail> getBidDetForLot() {
        if (bidDetForLot == null) {
            bidDetForLot = new ArrayList<>();
        }
        return bidDetForLot;
    }

    /**
     * @param bidDetForLot the bidDetForLot to set
     */
    public void setBidDetForLot(List<PrmsBidDetail> bidDetForLot) {
        this.bidDetForLot = bidDetForLot;
    }

    public List<PrmsBidDetail> getBidDetForItem() {
        if (bidDetForItem == null) {
            bidDetForItem = new ArrayList<>();
        }
        return bidDetForItem;
    }

    public void setBidDetForItem(List<PrmsBidDetail> bidDetForItem) {
        this.bidDetForItem = bidDetForItem;
    }

    /**
     * @return the supplierList
     */
    public List<PrmsSupplyProfile> getSupplierList() {
        if (supplierList == null) {
            supplierList = new ArrayList<>();
        }
        return supplierList;
    }

    /**
     * @return the prmsSupplyProfile
     */
    public PrmsSupplyProfile getPrmsSupplyProfile() {
        if (prmsSupplyProfile == null) {
            prmsSupplyProfile = new PrmsSupplyProfile();
        }
        return prmsSupplyProfile;
    }

    /**
     * @param prmsSupplyProfile the prmsSupplyProfile to set
     */
    public void setPrmsSupplyProfile(PrmsSupplyProfile prmsSupplyProfile) {
        this.prmsSupplyProfile = prmsSupplyProfile;
    }

    /**
     * @return the bidDetail
     */
    public PrmsBidDetail getBidDetail() {
        if (bidDetail == null) {
            bidDetail = new PrmsBidDetail();
        }
        return bidDetail;
    }

    /**
     * @param bidDetail the bidDetail to set
     */
    public void setBidDetail(PrmsBidDetail bidDetail) {
        this.bidDetail = bidDetail;
    }

    /**
     * @return the prmsBid
     */
    public PrmsBid getPrmsBid() {
        if (prmsBid == null) {
            prmsBid = new PrmsBid();
        }
        return prmsBid;
    }

    /**
     * @param prmsBid the prmsBid to set
     */
    public void setPrmsBid(PrmsBid prmsBid) {
        this.prmsBid = prmsBid;
    }

    /**
     * @return the selectedRowIndex
     */
    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    /**
     * @param selectedRowIndex the selectedRowIndex to set
     */
    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    /**
     * @return the prmsPostDetail
     */
    public PrmsPostDetail getPrmsPostDetail() {
        if (prmsPostDetail == null) {
            prmsPostDetail = new PrmsPostDetail();
        }
        return prmsPostDetail;
    }

    /**
     * @param prmsPostDetail the prmsPostDetail to set
     */
    public void setPrmsPostDetail(PrmsPostDetail prmsPostDetail) {
        this.prmsPostDetail = prmsPostDetail;
    }

    /**
     * @return the prmsPostDetailModel
     */
    public DataModel<PrmsPostDetail> getPrmsPostDetailModel() {
        if (prmsPostDetailModel == null) {
            prmsPostDetailModel = new ListDataModel();
        }
        return prmsPostDetailModel;
    }

    /**
     * @param prmsPostDetailModel the prmsPostDetailModel to set
     */
    public void setPrmsPostDetailModel(DataModel<PrmsPostDetail> prmsPostDetailModel) {
        this.prmsPostDetailModel = prmsPostDetailModel;
    }

    /**
     * @return the supplierListSecond
     */
    public List<PrmsSupplyProfile> getSupplierListSecond() {
        if (supplierListSecond == null) {
            supplierListSecond = new ArrayList<>();
        }
        return supplierListSecond;
    }

    /**
     * @param supplierListSecond the supplierListSecond to set
     */
    public void setSupplierListSecond(List<PrmsSupplyProfile> supplierListSecond) {
        this.supplierListSecond = supplierListSecond;
    }
    private String decision = "";

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public void callingNextSupp(ValueChangeEvent calling) {
        System.out.println("=============== value=" + calling.getNewValue());

        if (calling.getNewValue() != null) {
            //if (prmsPostDetail.getFinalDecision().equals("Next Supplier")) {
            if (calling.getNewValue().equals("Next Supplier")) {
                nominatedRank++;
                System.out.println("=============== next");
                if (prmsFinancialEvlResultyDtl.getItemId() != null) {
                    nextBidderNameList = postQualificationBeanLocal.getListofNextSupplier(prmsFinancialEvalResult, nominatedRank, prmsFinancialEvlResultyDtl);
                    supplierListSecond = filterSupplyerList(nextBidderNameList);
                    System.out.println("=============== next supp list size= " + nextBidderNameList.size());
                } else if (prmsFinancialEvlResultyDtl.getLotNo() != null) {
                    nextBidderNameList = postQualificationBeanLocal.getListOfLots(prmsFinancialEvalResult, nominatedRank, prmsFinancialEvlResultyDtl);
                    supplierListSecond = filterSupplyerList(nextBidderNameList);
                    System.out.println("=============== next lot list size= " + nextLotNameList.size());
                }

                // System.out.println("2-------------");
            } else if (calling.getNewValue().equals("Close Bid")) {
                System.out.println("Not Next Supplier");
            }
        }
    }

    public List<PrmsSupplyProfile> filterSupplyerList(List<PrmsFinancialEvlResultyDtl> financialResDet) {
        if (financialResDet.size() > 0) {
            for (int i = 0; i < financialResDet.size(); i++) {
                supplierListSecond.add(financialResDet.get(i).getSupplierId());
            }
            return supplierListSecond;
        } else {
            return null;
        }
    }

    public List<PrmsSupplyProfile> filterSupplyerList1(List<PrmsFinancialEvlResultyDtl> financialResDet) {
        if (financialResDet.size() > 0) {
            for (int i = 0; i < financialResDet.size(); i++) {
                supplierList.add(financialResDet.get(i).getSupplierId());
            }
            return supplierList;
        } else {
            return null;
        }
    }

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ArrayDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public int getNominatedRank() {
        return nominatedRank;
    }

    public void setNominatedRank(int nominatedRank) {
        this.nominatedRank = nominatedRank;
    }

    /**
     * @return the nextBidderNameList
     */
    public List<PrmsFinancialEvlResultyDtl> getNextBidderNameList() {
        if (nextBidderNameList == null) {
            nextBidderNameList = new ArrayList<>();
        }
        return nextBidderNameList;
    }

    /**
     * @param nextBidderNameList the nextBidderNameList to set
     */
    public void setNextBidderNameList(List<PrmsFinancialEvlResultyDtl> nextBidderNameList) {
        this.nextBidderNameList = nextBidderNameList;
    }

    /**
     * @return the nextLotNameList
     */
    public List<PrmsFinancialEvlResultyDtl> getNextLotNameList() {
        if (nextLotNameList == null) {
            nextLotNameList = new ArrayList<>();
        }
        return nextLotNameList;
    }

    /**
     * @param nextLotNameList the nextLotNameList to set
     */
    public void setNextLotNameList(List<PrmsFinancialEvlResultyDtl> nextLotNameList) {
        this.nextLotNameList = nextLotNameList;
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

    public SessionBean getSessionBean() {
        if (sessionBean == null) {
            sessionBean = new SessionBean();
        }
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
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

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    /**
     * @return the loggerName
     */
    public boolean isIsRenderNotify() {
        return isRenderNotify;
    }

    public void setIsRenderNotify(boolean isRenderNotify) {
        this.isRenderNotify = isRenderNotify;
    }

    /**
     * @return the mmsItemRegistration
     */
    public MmsItemRegistration getMmsItemRegistration() {
        if (mmsItemRegistration == null) {
            mmsItemRegistration = new MmsItemRegistration();
        }
        return mmsItemRegistration;
    }

    /**
     * @param mmsItemRegistration the mmsItemRegistration to set
     */
    public void setMmsItemRegistration(MmsItemRegistration mmsItemRegistration) {
        this.mmsItemRegistration = mmsItemRegistration;
    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

}
