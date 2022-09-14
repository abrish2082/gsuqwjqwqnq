/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.commonApplications.businessLogic.WfMmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSivDtlBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreInformationBeanLocal;
import et.gov.eep.mms.businessLogic.MmsStoreReqBeanLocal;
import et.gov.eep.mms.entity.MmsBinCard;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;
import java.io.Serializable;
import java.math.BigDecimal;
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
import securityBean.Constants;

/**
 *
 * @author Terminal2
 */
@Named(value = "transactionRollbackController")
@ViewScoped
public class TransactionRollbackController implements Serializable {

    @Inject
    private MmsStoreInformation storeEntity;
    @Inject
    MmsSiv siv;
    @Inject
    private MmsBinCard mmsBinCard;
    @Inject
    MmsItemRegistration ItemRegistration;
    @Inject
    WfMmsProcessed wfMmsProcessed;
    @Inject
    MmsStorereq storereq;

    @EJB
    private MmsStoreInformationBeanLocal storeInfoInterface;
    @EJB
    MmsSivBeanLocal sivbeanLocal;
    @EJB
    MmsStoreReqBeanLocal storeReqBeanLocal;
    @EJB
    MmsBinCardBeanLocal mmsBinCardBeanLocal;
    @EJB
    WfMmsProcessedBeanLocal wfMmsProcessedBeanLocal;
    @EJB
    MmsSivDtlBeanLocal sivDtlBeanLocal;
    List<MmsStoreInformation> StoreList;
    List<MmsSiv> sivList = new ArrayList<>();
    DataModel<MmsSivDetail> sivDetailsModel;

    /**
     * Creates a new instance of TransactionRollbackController
     */
    public TransactionRollbackController() {
    }

    @PostConstruct
    public void init() {
        StoreList = storeInfoInterface.findAllStoreInfo();
    }

    public MmsStoreInformation getStoreEntity() {
        if (storeEntity == null) {
            storeEntity = new MmsStoreInformation();
        }
        return storeEntity;
    }

    public void setStoreEntity(MmsStoreInformation storeEntity) {
        this.storeEntity = storeEntity;
    }

    public List<MmsSiv> getSivList() {
        if (sivList == null) {
            sivList = new ArrayList<>();
        }
        return sivList;
    }

    public void setSivList(List<MmsSiv> sivList) {
        this.sivList = sivList;
    }

    public MmsSiv getSiv() {
        if (siv == null) {
            siv = new MmsSiv();
        }
        return siv;
    }

    public void setSiv(MmsSiv siv) {
        this.siv = siv;
    }

    public DataModel<MmsSivDetail> getSivDetailsModel() {
        if (sivDetailsModel == null) {
            sivDetailsModel = new ListDataModel<>();
        }
        return sivDetailsModel;
    }

    public void setSivDetailsModel(DataModel<MmsSivDetail> sivDetailsModel) {
        this.sivDetailsModel = sivDetailsModel;
    }

    public MmsBinCard getMmsBinCard() {
        if (mmsBinCard == null) {
            mmsBinCard = new MmsBinCard();
        }
        return mmsBinCard;
    }

    public void setMmsBinCard(MmsBinCard mmsBinCard) {
        this.mmsBinCard = mmsBinCard;
    }

    public MmsItemRegistration getItemRegistration() {
        if (ItemRegistration == null) {
            ItemRegistration = new MmsItemRegistration();
        }
        return ItemRegistration;
    }

    public void setItemRegistration(MmsItemRegistration ItemRegistration) {
        this.ItemRegistration = ItemRegistration;
    }

    public WfMmsProcessed getWfMmsProcessed() {
        if (wfMmsProcessed == null) {
            wfMmsProcessed = new WfMmsProcessed();
        }
        return wfMmsProcessed;
    }

    public void setWfMmsProcessed(WfMmsProcessed wfMmsProcessed) {
        this.wfMmsProcessed = wfMmsProcessed;
    }

    public MmsStorereq getStorereq() {
        if (storereq == null) {
            storereq = new MmsStorereq();
        }
        return storereq;
    }

    public void setStorereq(MmsStorereq storereq) {
        this.storereq = storereq;
    }

    public List<MmsStoreInformation> getStoreList() {
        return StoreList;
    }

    public void setStoreList(List<MmsStoreInformation> StoreList) {
        this.StoreList = StoreList;
    }

    public void handleSelectStoreName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            storeEntity = (MmsStoreInformation) event.getNewValue();
            sivList = sivbeanLocal.searchBySivNosForRollBack(storeEntity);
//         

//         
        }
    }

    private void recreateSivDataModel() {
        sivDetailsModel = null;
        sivDetailsModel = new ListDataModel(new ArrayList<>(siv.getMmsSivDetailList()));

    }

    public void getSelectedSIVInfo(ValueChangeEvent event) {
        siv = (MmsSiv) event.getNewValue();
        recreateSivDataModel();
    }

    public void rollback_transaction_Siv() {
        System.out.println("=========rollback=========");       
        try {
//
            System.out.println("======try=====");
            for (int i = 0; i < siv.getMmsSivDetailList().size(); i++) {
                mmsBinCard = new MmsBinCard();
                ItemRegistration = new MmsItemRegistration();
                ItemRegistration = siv.getMmsSivDetailList().get(i).getItemId();
                mmsBinCard = mmsBinCardBeanLocal.getItemInfoByStoreIdAndItemId(ItemRegistration, storeEntity);
                System.out.println("============mmsBinCard=====" + mmsBinCard.getCurrentQuantity());
                BigDecimal updatedQuantity = mmsBinCard.getCurrentQuantity().add(siv.getMmsSivDetailList().get(i).getQuantity());
                mmsBinCard.setCurrentQuantity(updatedQuantity);
                System.out.println("============mmsBinCard2=====" + updatedQuantity);
                mmsBinCardBeanLocal.edit(mmsBinCard);
                sivDtlBeanLocal.remove(siv.getMmsSivDetailList().get(i));

            }
            System.out.println("=======outsidetry===");
            wfMmsProcessedBeanLocal.remove(siv.getWfMmsProcessedList().get(0));
            System.out.println("=============wf===="+siv.getWfMmsProcessedList().get(0));
            storereq = siv.getStoreReqId();
            storereq.setStatus(Constants.APPROVE_VALUE);
            storeReqBeanLocal.edit(storereq);
            sivbeanLocal.remove(siv);
            System.out.println("===========remov=====");

            JsfUtil.addSuccessMessage("Transaction Rollback is successfull");
            clearPage();

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Something Error Occured on Data created");
        }
    }

    public void clearPage() {
        siv = null;
        sivDetailsModel = null;
        storeEntity = null;
        storereq = null;
    }
}
