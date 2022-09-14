package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.businessLogic.SpcDetailBeanLocal;
import et.gov.eep.prms.businessLogic.SpecificationBeanLocal;
import et.gov.eep.prms.businessLogic.SupplierSpecficationBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.prms.entity.PrmsSuppSpecification;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;

/**
 *
 * @author pc
 */
@Named(value = "SpecificationController")
@ViewScoped
public class SpecificationController implements Serializable {

    @Inject
    SessionBean SessionBean;
    @EJB
    private SpcDetailBeanLocal spcDetailBean;

    @EJB
    private SpecificationBeanLocal specificationBeanLocal;
    @EJB
    private SupplierSpecficationBeanLocal supplierSpecficationBeanLocal;
    @Inject
    private PrmsSpecification prmsSpecification;
    private String saveorUpdateBundle = "Save";
    int saveStatus = 0;
    @Inject
    private PrmsSuppSpecification prmsSuppSpecification;

    @Inject
    private PrmsBid prmsBid;
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private String activeIndex;
    int updateStatus = 0;
    private String selectOptPartyName;
    List<PrmsBidDetail> prmsBidDetails;
    List<PrmsSpecification> prmsSpecificationList;
    private PrmsSpecification PrmsSpecificationSelection;
    private String icone = "ui-icon-document";
    private DataModel<PrmsSpecification> prmsSpecificationsdataModel;
    Set<String> addressCheck = new HashSet<>();
    @Inject
    PrmsBidDetail prmsBidDetail;
    @Inject
    MmsItemRegistration itemRegistration;
    ArrayList<MmsItemRegistration> itemList;

    public ArrayList<MmsItemRegistration> getItemList() {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        return itemList;
    }

    public MmsItemRegistration getItemRegistration() {
        if (itemRegistration == null) {
            itemRegistration = new MmsItemRegistration();
        }
        return itemRegistration;
    }

    public void setItemRegistration(MmsItemRegistration itemRegistration) {
        this.itemRegistration = itemRegistration;
    }

    public PrmsSpecification getPrmsSpecificationSelection() {
        return PrmsSpecificationSelection;
    }

    public void setPrmsSpecificationSelection(PrmsSpecification PrmsSpecificationSelection) {
        this.PrmsSpecificationSelection = PrmsSpecificationSelection;
    }

    public SpecificationController() {

    }

    /**
     *
     * @return
     */
    public PrmsSpecification getPrmsSpecification() {
        if (prmsSpecification == null) {
            prmsSpecification = new PrmsSpecification();
        }
        return prmsSpecification;
    }

    /**
     *
     * @param prmsSpecification
     */
    public void setPrmsSpecification(PrmsSpecification prmsSpecification) {
        this.prmsSpecification = prmsSpecification;
    }

    /**
     *
     * @return
     */
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    /**
     *
     * @param saveorUpdateBundle
     */
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    /**
     *
     * @return
     */
    public int getSaveStatus() {
        return saveStatus;
    }

    /**
     *
     * @param saveStatus
     */
    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    /**
     *
     * @return
     */
    public PrmsSuppSpecification getPrmsSuppSpecification() {
        if (prmsSuppSpecification == null) {
            prmsSuppSpecification = new PrmsSuppSpecification();
        }
        return prmsSuppSpecification;
    }

    /**
     *
     * @param prmsSuppSpecification
     */
    public void setPrmsSuppSpecification(PrmsSuppSpecification prmsSuppSpecification) {
        this.prmsSuppSpecification = prmsSuppSpecification;
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

    /**
     *
     * @return
     */
    public PrmsBid getPrmsBid() {
        if (prmsBid == null) {
            prmsBid = new PrmsBid();
        }
        return prmsBid;
    }

    /**
     *
     * @param prmsBid
     */
    public void setPrmsBid(PrmsBid prmsBid) {
        this.prmsBid = prmsBid;
    }

    /**
     *
     * @return
     */
    public String getAddressInfoButton() {
        return addressInfoButton;
    }

    /**
     *
     * @param addressInfoButton
     */
    public void setAddressInfoButton(String addressInfoButton) {
        this.addressInfoButton = addressInfoButton;
    }

    /**
     *
     * @return
     */
    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    /**
     *
     * @param createOrSearchBundle
     */
    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    /**
     *
     * @return
     */
    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    /**
     *
     * @param disableBtnCreate
     */
    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    /**
     *
     * @return
     */
    public String getDuplicattion() {
        return duplicattion;
    }

    /**
     *
     * @param duplicattion
     */
    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    /**
     *
     * @return
     */
    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    /**
     *
     * @param renderPnlCreateParty
     */
    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }

    /**
     *
     * @return
     */
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    /**
     *
     * @param renderPnlManPage
     */
    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    /**
     *
     * @return
     */
    public String getActiveIndex() {
        return activeIndex;
    }

    /**
     *
     * @param activeIndex
     */
    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    /**
     *
     * @return
     */
    public int getUpdateStatus() {
        return updateStatus;
    }

    /**
     *
     * @param updateStatus
     */
    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    /**
     *
     * @return
     */
    public String getSelectOptPartyName() {
        return selectOptPartyName;
    }

    /**
     *
     * @param selectOptPartyName
     */
    public void setSelectOptPartyName(String selectOptPartyName) {
        this.selectOptPartyName = selectOptPartyName;
    }

    /**
     *
     * @return
     */
    public List<PrmsBidDetail> getPrmsBidDetails() {
        if (prmsBidDetails == null) {
            prmsBidDetails = new ArrayList<>();
        }
        return prmsBidDetails;
    }

    /**
     *
     * @param prmsBidDetails
     */
    public void setPrmsBidDetails(List<PrmsBidDetail> prmsBidDetails) {
        this.prmsBidDetails = prmsBidDetails;
    }

    /**
     *
     * @return
     */
    public List<PrmsSpecification> getPrmsSpecificationList() {
        if (prmsSpecificationList == null) {
            prmsSpecificationList = new ArrayList<>();
        }
        return prmsSpecificationList;
    }

    /**
     *
     * @param prmsSpecificationList
     */
    public void setPrmsSpecificationList(List<PrmsSpecification> prmsSpecificationList) {
        this.prmsSpecificationList = prmsSpecificationList;
    }

    public String getIcone() {
        return icone;
    }

    /**
     *
     * @param icone
     */
    public void setIcone(String icone) {
        this.icone = icone;
    }

    /**
     *
     * @return
     */
    public DataModel<PrmsSpecification> getPrmsSpecificationsdataModel() {
        if (prmsSpecificationsdataModel == null) {
            prmsSpecificationsdataModel = new ListDataModel(new ArrayList<>(getPrmsSpecificationList()));
        }
        return prmsSpecificationsdataModel;
    }

    /**
     *
     * @param prmsSpecificationsdataModel
     */
    public void setPrmsSpecificationsdataModel(DataModel<PrmsSpecification> prmsSpecificationsdataModel) {
        this.prmsSpecificationsdataModel = prmsSpecificationsdataModel;
    }

    /**
     *
     * @return
     */
    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    /**
     *
     * @param addressCheck
     */
    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }

    /**
     *
     * @param event
     */
    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;

        int rowIndex = prmsSpecificationsdataModel.getRowIndex();
        prmsSpecification = prmsSpecificationList.get(rowIndex);

        recreateprmsSpecificationsdataModel();

    }

    /**
     *
     */
    public void recreateprmsSpecificationsdataModel() {
        prmsSpecificationsdataModel = null;
        prmsSpecificationsdataModel = new ListDataModel(new ArrayList<>(getPrmsSpecificationList()));
    }

    /**
     *
     */
    public void createNewParty() {

        saveorUpdateBundle = "Save";
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
            setIcone("ui-icon-document");
        }
    }

    /**
     *
     * @return
     */
    public String save() {

        if (saveorUpdateBundle.equals("Save")) {
            if (updateStatus == 0) {
                try {
                    generateCheckListNo();
                    prmsSpecification.setSpecficationNo(newcheckListNo);
                    prmsSpecification.setMaterialId(itemRegistration);
                    prmsSpecification.setBidId(prmsBid);
                    setPrmsSpecification(prmsSpecification);

                    specificationBeanLocal.create(prmsSpecification);

                    JsfUtil.addSuccessMessage(" Specification is rigistered!!");
                    return cleaprmsSpecification();

                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);

                    cleaprmsSpecification();
                    return null;
                }
            }
        } else {
            try {
                specificationBeanLocal.update(prmsSpecification);
                JsfUtil.addSuccessMessage("Specification is Modified at " + prmsSpecification.getSpecficationNo());
                prmsSpecification.getBidDetId();
                saveorUpdateBundle = "Save";
                return cleaprmsSpecification();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Something Error Occured on Data Modified");
                cleaprmsSpecification();
            }

        }
        return null;

    }

    private String cleaprmsSpecification() {
        prmsSpecification = null;
        prmsBid = null;
        itemRegistration = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        return null;
    }

    /**
     *
     */
    public void searchSupplierSpecification() {

        prmsSpecificationList = specificationBeanLocal.searchSpecification(prmsSpecification);
        recreateprmsSpecificationsdataModel();

    }

    /**
     *
     */
    public void deleteSuppSpecDetailList() {
        int rowIndex = prmsSpecificationsdataModel.getRowIndex();

        recreateprmsSpecificationsdataModel();

    }

    /**
     *
     * @return
     */
    public SelectItem[] getReferenceNo() {
        return JsfUtil.getSelectItems(specificationBeanLocal.referenceNo(), true);

    }

    /**
     *
     * @param event
     */
    public void changeEvent(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            String refNo = event.getNewValue().toString();;

            prmsBid = (PrmsBid) event.getNewValue();

            prmsBid.setRefNo(refNo);
            prmsBid = specificationBeanLocal.getBid(prmsBid);

        }
    }

    public void getItemName(ValueChangeEvent event) {

        prmsBid = (PrmsBid) event.getNewValue();
        int size = prmsBid.getPrmsBidDetailList().size();
        itemList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            PrmsBidDetail prDtl = new PrmsBidDetail();
            prDtl = prmsBid.getPrmsBidDetailList().get(i);
            MmsItemRegistration item = new MmsItemRegistration();
            item = prDtl.getItemRegId();
            itemList.add(item);
            prmsSpecification.setBidId(prDtl.getBidId());

        }

    }

    public void prmsBidDetailChanged(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsSpecification.setBidDetId(prmsBidDetail);

        }
    }

    public List<PrmsBidDetail> bidNolist() {

        setPrmsBidDetails(specificationBeanLocal.getBidNoSep());

//        specificationBeanLocal.getBidNoSep();
        return prmsBidDetails;
    }

    public List<PrmsBidDetail> getItemNamee() {

        setPrmsBidDetails(specificationBeanLocal.getItemName());

//        specificationBeanLocal.getBidNoSep();
        return prmsBidDetails;
    }

    public SelectItem[] getItemName() {
        return JsfUtil.getSelectItems(this.getPrmsBidDetails(), true);
    }

    public SelectItem[] getitemeList() {
        return JsfUtil.getSelectItems(specificationBeanLocal.getBidDet(prmsBidDetail), true);

    }

    List<MmsItemRegistration> itemsList;

    public List<MmsItemRegistration> getItemsList() {
        itemsList = null;
        if (itemsList == null) {
            itemsList = new ArrayList<>();
        }
        int size = prmsBid.getPrmsBidDetailList().size();
        for (int i = 0; i < size; i++) {
            itemsList.add(prmsBid.getPrmsBidDetailList().get(i).getItemRegId());
        }
        return itemsList;
    }

    public void setItemsList(List<MmsItemRegistration> itemsList) {
        this.itemsList = itemsList;
    }

    public SelectItem[] getSearchBy() {

        int size = prmsBid.getPrmsBidDetailList().size();
        for (int i = 0; i < size; i++) {
            itemsList.add(prmsBid.getPrmsBidDetailList().get(i).getItemRegId());
        }

        return JsfUtil.getSelectItems(itemsList, true);

    }

    public void handleItemRegistration(ValueChangeEvent event) {

        if (event.getNewValue() != null) {

            String itemIdStr = event.getNewValue().toString();
            Integer itemId = Integer.parseInt(itemIdStr);
            BigDecimal item = new BigDecimal(itemId);
            MmsItemRegistration itemObj = new MmsItemRegistration();
//            itemObj.setMaterialId(item);
            prmsBidDetail = new PrmsBidDetail();
            int size = prmsBid.getPrmsBidDetailList().size();
            for (int i = 0; i < size; i++) {

                if (prmsBid.getPrmsBidDetailList().get(i).getItemRegId() == itemObj) {
                    prmsBidDetail = prmsBid.getPrmsBidDetailList().get(i);

                }
            }

        }
    }

    public SelectItem[] searchBiddetailInfo() {
        if (prmsBidDetail.getItemRegId().getMatCode() != null) {
            SelectItem[] steps = JsfUtil.getSelectItems(specificationBeanLocal.searchBiddetailInfo(prmsBidDetail), true);
            return steps;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--First Select BId No --");
            return items;
        }
    }

    public void rowSelect(SelectEvent event) {
        prmsSpecification = (PrmsSpecification) event.getObject();
        prmsBid = prmsSpecification.getBidId();
//         prmsBid = prmsBidDetail.getBidId();
        itemRegistration = prmsSpecification.getMaterialId();
        prmsSpecification = specificationBeanLocal.getSelectedRequest(prmsSpecification.getSpecificationId());
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        recreateprmsSpecificationsdataModel();
    }
    String newcheckListNo;
    String LastcheckListNo = "0";

    public String generateCheckListNo() {

        PrmsSpecification LastCheckNo = specificationBeanLocal.LastNo();
        if (LastCheckNo != null) {
            LastcheckListNo = LastCheckNo.getSpecificationId().toString();
        }
        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();
        int newcheckListN = 0;
        if (LastcheckListNo.equals("0")) {
            newcheckListN = 1;
            newcheckListNo = "ItemSpec-" + newcheckListN + "/" + f.format(now);
        } else {
            String[] lastInspNos = LastcheckListNo.split("-");
            String lastDatesPatern = lastInspNos[0];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            newcheckListN = Integer.parseInt(lastDatesPaterns[0]);
            newcheckListN = newcheckListN + 1;
            newcheckListNo = "ItemSpec-" + newcheckListN + "/" + f.format(now);
        }
        return newcheckListNo;
    }

    public String generateSpecficationNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = prmsSpecification.getSpecficationNo();

        } else {
            PrmsSpecification LastCheckNo = specificationBeanLocal.LastNo();
            if (LastCheckNo != null) {
                LastcheckListNo = LastCheckNo.getSpecificationId().toString();
            }
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newBidNoLast = 0;
            if (LastcheckListNo.equals("0")) {
                newBidNoLast = 1;
                newcheckListNo = "ItemSpec-" + newBidNoLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
                newBidNoLast = newBidNoLast + 1;
                newcheckListNo = "ItemSpec-" + newBidNoLast + "/" + f.format(now);
            }
        }
        return newcheckListNo;

    }

}
