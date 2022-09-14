/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.InsuranceNotificationToBankBeanLocal;
import et.gov.eep.prms.businessLogic.InsuranceRegisterationBeanLocal;
import et.gov.eep.prms.businessLogic.ServiceProviderBeanLocal;
import et.gov.eep.prms.businessLogic.ContainerAgreementBeanLocal;
import et.gov.eep.prms.entity.PrmsInsuranceNotToBank;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
import et.gov.eep.prms.entity.PrmsPortEntry;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.util.List;
import javax.faces.model.ListDataModel;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.SelectEvent;
import webService.AAA;
import webService.IAdministration;
import webService.EventEntry;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;

/**
 *
 * @author user
 */
@Named("insuranceNotificationToBankController")
@ViewScoped

public class InsuranceNotificationToBankController implements Serializable {

    @EJB
    InsuranceNotificationToBankBeanLocal insuranceNotificationToBankBeanLocal;
    @EJB
    ServiceProviderBeanLocal serviceProviderBeanLocal;
    @EJB
    InsuranceRegisterationBeanLocal insuranceRegisterationBeanLocal;
    @EJB
    ContainerAgreementBeanLocal containerAgreementBeanLocal;
    @Inject
    PrmsInsuranceNotToBank prmsInsuranceNotToBank;
    @Inject
    PrmsInsuranceRequisition insuranceregistration;
    @Inject
    PrmsPortEntry prmsPortEntryFrom;
    @Inject
    PrmsPortEntry prmsPortEntryVia;
    @Inject
    PrmsPortEntry prmsPortEntryTo;
    @Inject
    PrmsServiceProvider prmsServiceProviderTo;
    @Inject
    PrmsServiceProvider prmsServiceProvider;
    @Inject
    PrmsServiceProviderDetail prmsServiceProviderBranch;
    @Inject
    PrmsServiceProvider prmsServiceProviderforBehalf;
    @Inject
    PrmsServiceProviderDetail PrmsServiceProviderDetailonBehalf;
    int createStatus = 0;
    int updateStatus = 0;
    private String activeIndex;
    private String icone = "ui-icon-plus";
    private String newOrSearchBundle = "New";
    private String createOrUpdateButton = "Save";
    private boolean disablebBtnCreate = false;
    private boolean renderToSearching = true;
    private boolean renderToNotification = false;

    PrmsInsuranceNotToBank notificationDMSelection;
    private DataModel<PrmsInsuranceNotToBank> prmsInsuranceNotToBankModel;
    List<PrmsInsuranceNotToBank> prmsInsuranceNotToBankList;
    List<PrmsServiceProviderDetail> bankBranchs;
    List<PrmsServiceProvider> fromServiceProviderTo;
    List<PrmsPortEntry> voyageFrom;
    List<PrmsInsuranceNotToBank> insuNo;
    List<PrmsInsuranceNotToBank> insuranceToBanckSearchParameterLst;

    public PrmsInsuranceNotToBank getPrmsInsuranceNotToBank() {
        if (prmsInsuranceNotToBank == null) {
            prmsInsuranceNotToBank = new PrmsInsuranceNotToBank();
        }
        return prmsInsuranceNotToBank;
    }

    public void setPrmsInsuranceNotToBank(PrmsInsuranceNotToBank prmsInsuranceNotToBank) {
        this.prmsInsuranceNotToBank = prmsInsuranceNotToBank;
    }

    public PrmsInsuranceRequisition getInsuranceRequisition() {
        if (insuranceregistration == null) {
            insuranceregistration = new PrmsInsuranceRequisition();
        }
        return insuranceregistration;
    }

    public void setInsuranceRequisition(PrmsInsuranceRequisition insuranceRequisition) {
        this.insuranceregistration = insuranceRequisition;
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

    public PrmsPortEntry getPrmsPortEntryFrom() {
        if (prmsPortEntryFrom == null) {
            prmsPortEntryFrom = new PrmsPortEntry();
        }
        return prmsPortEntryFrom;
    }

    public void setPrmsPortEntryFrom(PrmsPortEntry prmsPortEntryFrom) {
        this.prmsPortEntryFrom = prmsPortEntryFrom;
    }

    public PrmsPortEntry getPrmsPortEntryVia() {
        if (prmsPortEntryVia == null) {
            prmsPortEntryVia = new PrmsPortEntry();
        }
        return prmsPortEntryVia;
    }

    public void setPrmsPortEntryVia(PrmsPortEntry prmsPortEntryVia) {
        this.prmsPortEntryVia = prmsPortEntryVia;
    }

    public PrmsPortEntry getPrmsPortEntryTo() {
        if (prmsPortEntryTo == null) {
            prmsPortEntryTo = new PrmsPortEntry();
        }
        return prmsPortEntryTo;
    }

    public void setPrmsPortEntryTo(PrmsPortEntry prmsPortEntryTo) {
        this.prmsPortEntryTo = prmsPortEntryTo;
    }

    public PrmsServiceProvider getPrmsServiceProviderforBehalf() {
        if (prmsServiceProviderforBehalf == null) {
            prmsServiceProviderforBehalf = new PrmsServiceProvider();
        }
        return prmsServiceProviderforBehalf;
    }

    public void setPrmsServiceProviderforBehalf(PrmsServiceProvider prmsServiceProviderforBehalf) {
        this.prmsServiceProviderforBehalf = prmsServiceProviderforBehalf;
    }

    public PrmsServiceProviderDetail getPrmsServiceProviderDetailonBehalf() {
        if (PrmsServiceProviderDetailonBehalf == null) {
            PrmsServiceProviderDetailonBehalf = new PrmsServiceProviderDetail();
        }
        return PrmsServiceProviderDetailonBehalf;
    }

    public void setPrmsServiceProviderDetailonBehalf(PrmsServiceProviderDetail PrmsServiceProviderDetailonBehalf) {
        this.PrmsServiceProviderDetailonBehalf = PrmsServiceProviderDetailonBehalf;
    }

    public int getCreateStatus() {
        return createStatus;
    }

    public void setCreateStatus(int createStatus) {
        this.createStatus = createStatus;
    }

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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getNewOrSearchBundle() {
        return newOrSearchBundle;
    }

    public void setNewOrSearchBundle(String newOrSearchBundle) {
        this.newOrSearchBundle = newOrSearchBundle;
    }

    public String getCreateOrUpdateButton() {
        return createOrUpdateButton;
    }

    public void setCreateOrUpdateButton(String createOrUpdateButton) {
        this.createOrUpdateButton = createOrUpdateButton;
    }

    public boolean isDisablebBtnCreate() {
        return disablebBtnCreate;
    }

    public void setDisablebBtnCreate(boolean disablebBtnCreate) {
        this.disablebBtnCreate = disablebBtnCreate;
    }

    public boolean isRenderToSearching() {
        return renderToSearching;
    }

    public void setRenderToSearching(boolean renderToSearching) {
        this.renderToSearching = renderToSearching;
    }

    public boolean isRenderToNotification() {
        return renderToNotification;
    }

    public void setRenderToNotification(boolean renderToNotification) {
        this.renderToNotification = renderToNotification;
    }

    public DataModel<PrmsInsuranceNotToBank> getPrmsInsuranceNotToBankModel() {
        if (prmsInsuranceNotToBankModel == null) {
            prmsInsuranceNotToBankModel = new ListDataModel<>();

        }
        return prmsInsuranceNotToBankModel;
    }

    public void setPrmsInsuranceNotToBankModel(DataModel<PrmsInsuranceNotToBank> prmsInsuranceNotToBankModel) {
        this.prmsInsuranceNotToBankModel = prmsInsuranceNotToBankModel;
    }

    public List<PrmsInsuranceNotToBank> getPrmsInsuranceNotToBankList() {
        if (prmsInsuranceNotToBankList == null) {
            prmsInsuranceNotToBankList = new ArrayList<>();

        }
        return prmsInsuranceNotToBankList;
    }

    public void setPrmsInsuranceNotToBankList(List<PrmsInsuranceNotToBank> prmsInsuranceNotToBankList) {
        this.prmsInsuranceNotToBankList = prmsInsuranceNotToBankList;
    }

    public PrmsInsuranceNotToBank getNotificationDMSelection() {
        return notificationDMSelection;
    }

    public void setNotificationDMSelection(PrmsInsuranceNotToBank notificationDMSelection) {
        this.notificationDMSelection = notificationDMSelection;
    }

    public List<PrmsInsuranceNotToBank> getInsuranceToBanckSearchParameterLst() {
         if (insuranceToBanckSearchParameterLst == null) {
            insuranceToBanckSearchParameterLst = new ArrayList<>();
            insuranceToBanckSearchParameterLst = insuranceNotificationToBankBeanLocal.getParamNameList();
        }
        return insuranceToBanckSearchParameterLst;
    }

    public void setInsuranceToBanckSearchParameterLst(List<PrmsInsuranceNotToBank> insuranceToBanckSearchParameterLst) {
        this.insuranceToBanckSearchParameterLst = insuranceToBanckSearchParameterLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsInsuranceNotToBank.setColumnName(event.getNewValue().toString());
            prmsInsuranceNotToBank.setColumnValue(null);
        }
    }

    public void switchedOnNew() {
        System.out.println("inside of Switch to New or search");
        createOrUpdateButton = "Save";
        disablebBtnCreate = false;
        if (newOrSearchBundle.equals("New")) {
            renderToNotification = true;
            renderToSearching = false;
//            newOrSearchBundle = "Search";
//            setIcone("ui-icon-search");

        }
//        else if (newOrSearchBundle.equals("Search")) {
//            renderToNotification = false;
//            renderToSearching = true;
//            newOrSearchBundle = "New";
//            setIcone("ui-icon-plus");

//        }
    }

    public void switchedSearchBtnAction() {
        clearRegistered();
        renderToNotification = false;
        renderToSearching = true;
    }

    public String searchBankNotificationNo() {
        System.out.println("===inside search");
        prmsInsuranceNotToBankList = insuranceNotificationToBankBeanLocal.searchBankNotificationNo(prmsInsuranceNotToBank);
        recreateBankNotificationModel();
        prmsInsuranceNotToBank=new PrmsInsuranceNotToBank();
        return null;
    }

    private void recreateBankNotificationModel() {
        prmsInsuranceNotToBankModel = null;
        prmsInsuranceNotToBankModel = new ListDataModel<>(new ArrayList<>(getPrmsInsuranceNotToBankList()));
    }
    String lastNotificatioNo = "0";
    String nextNotificationNo;

    public String generateNotificationNo() {
        System.out.println("inside Notification No Sequential");
        if (createOrUpdateButton.equals("Update")) {
            nextNotificationNo = prmsInsuranceNotToBank.getInsuranceNotificationNo();
        } else {
            PrmsInsuranceNotToBank lastNotificatioNoObj = insuranceNotificationToBankBeanLocal.generateNotificationNo();
            if (lastNotificatioNoObj != null) {
                lastNotificatioNo = lastNotificatioNoObj.getInsuranceNotificationId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            System.out.println("last Bank Notification No is==" + lastNotificatioNo);

            int newNotification = 0;
            if (lastNotificatioNo.equals("0")) {
                newNotification = 1;
                nextNotificationNo = "BN-" + newNotification + "/" + f.format(now);
            } else {
                String[] lastNotificationNos = lastNotificatioNo.split("-");
                String lastDatesPatern = lastNotificationNos[0];

                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newNotification = Integer.parseInt(lastDatesPaterns[0]);
                System.out.println("new last  Bank Notification No is===" + newNotification);
                newNotification = newNotification + 1;
                nextNotificationNo = "BN-" + newNotification + "/" + f.format(now);
            }
            System.out.println("New Bank Notification No is==" + nextNotificationNo);
        }

        return nextNotificationNo;

    }

    public void onRowSelection(SelectEvent event) {
        prmsInsuranceNotToBank = (PrmsInsuranceNotToBank) event.getObject();
        prmsInsuranceNotToBank.setInsuranceNotificationNo(prmsInsuranceNotToBank.getInsuranceNotificationNo());

        insuranceregistration = prmsInsuranceNotToBank.getInsuranceRegId();
//        insuranceregistration.setPolicyNo(insuranceregistration.getPolicyNo());
//        System.out.println("----in police no----" + insuranceregistration.getPolicyNo());

        prmsInsuranceNotToBank = insuranceNotificationToBankBeanLocal.getSelectedRow(prmsInsuranceNotToBank.getInsuranceNotificationNo());
        renderToNotification = true;
        renderToSearching = false;

        prmsServiceProviderTo = prmsInsuranceNotToBank.getServiceProId();
        prmsServiceProviderBranch = prmsInsuranceNotToBank.getServiceDtId();
        prmsServiceProviderforBehalf = prmsInsuranceNotToBank.getForBehalf();
        PrmsServiceProviderDetailonBehalf = prmsInsuranceNotToBank.getOnBehalf();
        prmsPortEntryFrom = prmsInsuranceNotToBank.getPortId();
        prmsPortEntryVia = prmsInsuranceNotToBank.getPortIdVia();
        prmsPortEntryTo = prmsInsuranceNotToBank.getPortIdTo();

        createOrUpdateButton = "Update";
        recreateprmsInsuranceNotToBankModel();
    }

    private void recreateprmsInsuranceNotToBankModel() {
        prmsInsuranceNotToBankModel = null;
        prmsInsuranceNotToBankModel = new ListDataModel<>(new ArrayList<>(getPrmsInsuranceNotToBankList()));
    }

    public void automateGateOthers(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            System.out.println("Selected IR Number==" + event.getNewValue().toString());
            insuranceregistration.setInsuranceNo(event.getNewValue().toString());
            insuranceregistration = insuranceNotificationToBankBeanLocal.autoGenerate(insuranceregistration);
        }
    }

    public SelectItem[] fromInsuranceReg() {
        return JsfUtil.getSelectItems(insuranceNotificationToBankBeanLocal.fromInsuranceReg(), true);
    }

    public List<PrmsServiceProviderDetail> getBankBranchs() {
        if (bankBranchs == null) {
            bankBranchs = new ArrayList<>();
        }
        bankBranchs = insuranceRegisterationBeanLocal.fromServiceProDetail();
        return bankBranchs;
    }

    public void setBankBranchs(List<PrmsServiceProviderDetail> bankBranchs) {
        this.bankBranchs = bankBranchs;
    }

    public List<PrmsServiceProvider> getFromServiceProviderTo() {
        if (fromServiceProviderTo == null) {
            fromServiceProviderTo = new ArrayList<>();
        }
        fromServiceProviderTo = insuranceRegisterationBeanLocal.serviceProviderInsuranceList();
        return fromServiceProviderTo;
    }

    public void setFromServiceProviderTo(List<PrmsServiceProvider> fromServiceProviderTo) {
        this.fromServiceProviderTo = fromServiceProviderTo;
    }

    public List<PrmsPortEntry> getVoyageFrom() {
        if (voyageFrom == null) {
            voyageFrom = new ArrayList<>();
        }
        voyageFrom = insuranceNotificationToBankBeanLocal.fromPortEntry();
        return voyageFrom;
    }

    public void setVoyageFrom(List<PrmsPortEntry> voyageFrom) {
        this.voyageFrom = voyageFrom;
    }

//    public List<PrmsInsuranceNotToBank> fromInsuranceReg() {
//        insuNo = insuranceNotificationToBankBeanLocal.fromInsuranceReg();
//        return insuNo;
//    }
//    public List<PrmsServiceProvider> getFromServiceProviderTo() {
//        if (fromServiceProviderTo == null) {
//            fromServiceProviderTo = new ArrayList<>();
//        }
//        fromServiceProviderTo = serviceProviderBeanLocal.getServiceProvider(prmsServiceProvider)
//        return fromServiceProviderTo;
//    }
//
//    public void setFromServiceProviderTo(List<PrmsServiceProvider> fromServiceProviderTo) {
//        this.fromServiceProviderTo = fromServiceProviderTo;
//    }
//        try {
//    AAA securityService = new AAA();
//        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//         String systemBundle = "et/gov/eep/commonApplications/securityServer";
//         String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet"); 
//         if(security.checkAccess(SessionBean.getUserName(), "createBankNotificationInfo", dataset)){
//             //goes here
//         }else{
//             EventEntry eventEntry = new EventEntry();
//             eventEntry.setSessionId(SessionBean.getSessionID());
//             eventEntry.setUserId(SessionBean.getUserId());
//             QName qualifiedName = new QName("", "project");
//              JAXBElement<String> test = new JAXBElement<String> (qualifiedName,String.class,null,SessionBean.getUserName());
//              eventEntry.setUserLogin(test);
////              ..... add more information by calling fields defined in the object 
//              security.addEventLog(eventEntry, dataset);
//         }
//    }catch (Exception ex){
//    
//}    
    public String createBankNotificationInfo() {
        System.out.println("inside create");
        if (createOrUpdateButton.equals("Save")) {
            if (updateStatus == 0) {
                try {
                    prmsInsuranceNotToBank.setInsuranceNotificationNo(nextNotificationNo);

                    prmsInsuranceNotToBank.setServiceProId(prmsServiceProviderTo);
                    prmsInsuranceNotToBank.setServiceDtId(prmsServiceProviderBranch);
                    prmsInsuranceNotToBank.setPortId(prmsPortEntryFrom);
                    prmsInsuranceNotToBank.setPortIdVia(prmsPortEntryVia);
                    prmsInsuranceNotToBank.setPortIdTo(prmsPortEntryTo);
                    prmsInsuranceNotToBank.setForBehalf(prmsServiceProviderforBehalf);
                    prmsInsuranceNotToBank.setOnBehalf(PrmsServiceProviderDetailonBehalf);

                    insuranceNotificationToBankBeanLocal.create(prmsInsuranceNotToBank);
                    JsfUtil.addSuccessMessage("bank notification saved at" + nextNotificationNo);
                    return clearRegistered();
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addErrorMessage("S/thing is error during Creating" + e);
                    System.out.println("-----erro---" + e);
                    clearRegistered();
                }
            }
        } else {
            try {
                prmsInsuranceNotToBank.getInsuranceNotificationNo();

                prmsInsuranceNotToBank.setServiceProId(prmsServiceProviderTo);
                prmsInsuranceNotToBank.setServiceDtId(prmsServiceProviderBranch);
                prmsInsuranceNotToBank.setPortId(prmsPortEntryFrom);
                prmsInsuranceNotToBank.setPortIdVia(prmsPortEntryVia);
                prmsInsuranceNotToBank.setPortIdTo(prmsPortEntryTo);
                prmsInsuranceNotToBank.setForBehalf(prmsServiceProviderforBehalf);
                prmsInsuranceNotToBank.setOnBehalf(PrmsServiceProviderDetailonBehalf);

                insuranceNotificationToBankBeanLocal.edit(prmsInsuranceNotToBank);
                JsfUtil.addSuccessMessage("Notification information is modified at" + prmsInsuranceNotToBank.getInsuranceNotificationNo());
                createOrUpdateButton = "Save";
                return clearRegistered();

            } catch (Exception e) {
                e.printStackTrace();
                JsfUtil.addErrorMessage("S/thing is error during editing");
                clearRegistered();
            }
        }
        return null;
    }

    public void updateTo(ValueChangeEvent ev) {
        if (!ev.getNewValue().toString().isEmpty()) {
            prmsServiceProviderTo.setServiceProId(new BigDecimal(ev.getNewValue().toString()));
            prmsServiceProviderTo = insuranceRegisterationBeanLocal.updateServFrom(prmsServiceProvider);
        }
    }

    public void updateForBehalf(ValueChangeEvent ev) {
        if (!ev.getNewValue().toString().isEmpty()) {
            prmsServiceProviderforBehalf.setServiceProId(new BigDecimal(ev.getNewValue().toString()));
            prmsServiceProviderforBehalf = insuranceRegisterationBeanLocal.updateServFrom(prmsServiceProviderforBehalf);
        }
    }

    public void updateBranch(ValueChangeEvent ev) {
        if (!ev.getNewValue().toString().isEmpty()) {
            prmsServiceProviderBranch.setServiceDtId(new BigDecimal(ev.getNewValue().toString()));
            prmsServiceProviderBranch = insuranceRegisterationBeanLocal.updateServBranch(prmsServiceProviderBranch);
        }
    }

    public void updateOnBehalf(ValueChangeEvent ev) {
        if (!ev.getNewValue().toString().isEmpty()) {
            PrmsServiceProviderDetailonBehalf.setServiceDtId(new BigDecimal(ev.getNewValue().toString()));
            PrmsServiceProviderDetailonBehalf = insuranceRegisterationBeanLocal.updateServBranch(PrmsServiceProviderDetailonBehalf);
        }
    }

    public void updatePortFrom(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsPortEntryFrom.setPortId(new BigDecimal(event.getNewValue().toString()));
            prmsPortEntryFrom = containerAgreementBeanLocal.portNameUpdate(prmsPortEntryFrom);
        }
    }

    public void updatePortVia(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsPortEntryVia.setPortId(new BigDecimal(event.getNewValue().toString()));
            prmsPortEntryVia = containerAgreementBeanLocal.portNameUpdate(prmsPortEntryFrom);
        }
    }

    public void updatePortTo(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsPortEntryTo.setPortId(new BigDecimal(event.getNewValue().toString()));
            prmsPortEntryTo = containerAgreementBeanLocal.portNameUpdate(prmsPortEntryFrom);
        }
    }

    public String clearRegistered() {
        prmsInsuranceNotToBank = null;
        insuranceregistration = null;
        prmsPortEntryFrom = null;
        prmsPortEntryVia = null;
        prmsPortEntryTo = null;
        prmsServiceProviderTo = null;
        prmsServiceProviderBranch = null;
        PrmsServiceProviderDetailonBehalf = null;
        prmsServiceProviderforBehalf = null;
        prmsInsuranceNotToBankList = null;
        prmsInsuranceNotToBankModel = null;
        return null;

    }

    public String reset() {
        prmsInsuranceNotToBank = new PrmsInsuranceNotToBank();
        insuranceregistration = new PrmsInsuranceRequisition();
//        prmsInsuranceNotToBank.setAddress(null);
//        prmsInsuranceNotToBank.setCargoClouses(null);
//        prmsInsuranceNotToBank.setNotifiedDate(null);
        return null;
    }

}
