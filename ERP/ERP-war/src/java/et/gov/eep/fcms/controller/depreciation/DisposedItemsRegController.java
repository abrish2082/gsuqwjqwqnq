/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.depreciation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.DisposedItemsBeanLocal;
import et.gov.eep.fcms.businessLogic.fixedasset.FmsDprOfficeAssetBeanLocal;
import et.gov.eep.fcms.entity.fixedasset.FmsDisposedItems;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.mms.entity.MmsDisposalDtl;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsLostFixedAssetDetail;
import et.gov.eep.mms.integration.MmsDisposalDtlIntegrationBeanLocal;
import et.gov.eep.mms.integration.MmsLostFixedAssetIntegrationBeanLocal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Me
 */
@Named(value = "disposedItemsRegController")
@ViewScoped
public class DisposedItemsRegController implements Serializable {

    @Inject
    SessionBean SessionBean;

    @Inject
    MmsDisposalDtl mmsDiposalDtl;
    @Inject
    FmsDisposedItems fmsDisposedItems;
    @Inject
    FmsDprOfficeAsset fmsDprOfficeAsset;
    @Inject
    MmsFixedassetRegstDetail mmsFixedassetRegstDetail;
    @Inject
    MmsLostFixedAssetDetail mmsLostFixedAssetDetail;
    @EJB
    MmsDisposalDtlIntegrationBeanLocal mmsDisposalDtlIntegrationBeanLocal;
    @EJB
    DisposedItemsBeanLocal disposedItemsBeanLocal;
    @EJB
    FmsDprOfficeAssetBeanLocal fmsDprOfficeAssetBeanLocal;
    @EJB
    MmsLostFixedAssetIntegrationBeanLocal mmsLostFixedAssetIntegrationBeanLocal;

    private String createOrSearchBundle = "New";
    private String headerTitle = "Disposed And Lost Items Search";
    private String icone = "ui-icon-document";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;

    public MmsDisposalDtl getMmsDiposalDtl() {
        if (mmsDiposalDtl == null) {
            mmsDiposalDtl = new MmsDisposalDtl();
        }
        return mmsDiposalDtl;
    }

    public void setMmsDiposalDtl(MmsDisposalDtl mmsDiposalDtl) {
        this.mmsDiposalDtl = mmsDiposalDtl;
    }

    public FmsDisposedItems getFmsDisposedItems() {
        if (fmsDisposedItems == null) {
            fmsDisposedItems = new FmsDisposedItems();
        }
        return fmsDisposedItems;
    }

    public void setFmsDisposedItems(FmsDisposedItems fmsDisposedItems) {
        this.fmsDisposedItems = fmsDisposedItems;
    }

    public FmsDprOfficeAsset getFmsDprOfficeAsset() {
        if (fmsDprOfficeAsset == null) {
            fmsDprOfficeAsset = new FmsDprOfficeAsset();
        }
        return fmsDprOfficeAsset;
    }

    public void setFmsDprOfficeAsset(FmsDprOfficeAsset fmsDprOfficeAsset) {
        this.fmsDprOfficeAsset = fmsDprOfficeAsset;
    }

    public MmsLostFixedAssetDetail getMmsLostFixedAssetDetail() {
        if (mmsLostFixedAssetDetail == null) {
            mmsLostFixedAssetDetail = new MmsLostFixedAssetDetail();
        }
        return mmsLostFixedAssetDetail;
    }

    public void setMmsLostFixedAssetDetail(MmsLostFixedAssetDetail mmsLostFixedAssetDetail) {
        this.mmsLostFixedAssetDetail = mmsLostFixedAssetDetail;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public List<MmsDisposalDtl> fetchNewDisposedItems() {
        return mmsDisposalDtlIntegrationBeanLocal.fetchNewDisposedItems();
    }

    public void disposedItemListener(ValueChangeEvent event) {
        mmsDiposalDtl.setTagNo(event.getNewValue().toString());
        mmsDiposalDtl = mmsDisposalDtlIntegrationBeanLocal.getDisposedItem(mmsDiposalDtl);
        fmsDprOfficeAsset.setTagNo(mmsDiposalDtl.getTagNo());
        fmsDprOfficeAsset = fmsDprOfficeAssetBeanLocal.findByTagandStatus(fmsDprOfficeAsset);
    }

    public void saveDisposedItems() {

        try { 
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveDisposedItems", dataset)) {
                fmsDisposedItems = new FmsDisposedItems();
                fmsDisposedItems.setMmsDisposedFk(mmsDiposalDtl);
                fmsDisposedItems.setStatus(BigInteger.ZERO);
                fmsDisposedItems.setFmsDepreciationFk(fmsDprOfficeAsset);
                disposedItemsBeanLocal.save(fmsDisposedItems);
                fmsDprOfficeAsset.setStatus(2);
                fmsDprOfficeAsset.setNetBookValue(BigDecimal.TEN);
                fmsDprOfficeAssetBeanLocal.edit(fmsDprOfficeAsset);
                resetItems();

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
        }
    }

    public void resetItems() {
        mmsDiposalDtl = new MmsDisposalDtl();
        mmsLostFixedAssetDetail = new MmsLostFixedAssetDetail();
        fmsDprOfficeAsset = new FmsDprOfficeAsset();
    }

    public List<MmsLostFixedAssetDetail> lostItemsList() {
        return mmsLostFixedAssetIntegrationBeanLocal.fetchLostItems();
    }

    public void lostItemListener(ValueChangeEvent event) {
        mmsLostFixedAssetDetail.setTagNo(event.getNewValue().toString());
        mmsLostFixedAssetDetail = mmsLostFixedAssetIntegrationBeanLocal.getLostItem(mmsLostFixedAssetDetail);
        fmsDprOfficeAsset.setTagNo(mmsLostFixedAssetDetail.getTagNo());
        fmsDprOfficeAsset = fmsDprOfficeAssetBeanLocal.findByTagandStatus(fmsDprOfficeAsset);
    }

    public void updateLostItems() {

//        try {
//            AAA securityService = new AAA();
//            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//            String systemBundle = "et/gov/eep/commonApplications/securityServer";
//            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
//            if (security.checkAccess(SessionBean.getUserName(), "saveAppealRequest", dataset)) {
        fmsDprOfficeAsset.setStatus(2);
        fmsDprOfficeAsset.setNetBookValue(BigDecimal.TEN);
        fmsDprOfficeAssetBeanLocal.edit(fmsDprOfficeAsset);
        resetItems();

//            } else {
//                EventEntry eventEntry = new EventEntry();
//                eventEntry.setSessionId(sessionBean.getSessionID());
//                eventEntry.setUserId(sessionBean.getUserId());
//                QName qualifiedName = new QName("", "project");
//                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
//                eventEntry.setUserLogin(test);
////..... add more information by calling fields defined in the object 
//                security.addEventLog(eventEntry, dataset);
//
//            }
//        } catch (Exception ex) {
//        }
    }

    public void createNewView() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Disposed And Lost Items Registration";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Disposed And Lost Items Search";
            setIcone("ui-icon-document");
        }
    }
}
