
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.prms.businessLogic.VendorRegBeanLocal;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.entity.PrmsSupplyProfileDetail;
import java.io.Serializable;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.controller.adminstrator.systemRegistrationController;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import et.gov.eep.prms.mapper.PrmsVatTypeLookupFacade;
import javax.annotation.PostConstruct;
import webService.AAA;
import webService.IAdministration;
import securityBean.SessionBean;
import webService.EventEntry;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBElement;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import securityBean.WorkFlow;

/**
 *
 * @author user
 */
@Named("vendorRegController")
@ViewScoped
public class VendorRegController implements Serializable {

    @EJB
    VendorRegBeanLocal vendorRegBeanLocal;
    @Inject
    PrmsSupplyProfile eepVendorReg;
    @Inject
    PrmsSupplyProfileDetail prmsSupplyProfileDetail;
    @Inject
    WorkFlow workFlow;
    @Inject
    SessionBean SessionBean;
    @Inject
    private PrmsLuVatTypeLookup prmsLuVatTypeLookup;
//    @EJB
//    PrmsVatTypeLookupFacade prmsVatTypeLookupFacade;       

    List<PrmsSupplyProfile> prmsSupplyProfileLst;
    List<PrmsSupplyProfileDetail> prmsSupplyProfileDetailList = new ArrayList<>();
    List<ComLuCountry> countryList;
    private List<PrmsLuVatTypeLookup> prmsLuVatTypeLookupList;
    List<PrmsSupplyProfile> supplierProSearchParameterLst;
    DataModel<PrmsSupplyProfile> prmsSupplyProfileMdel;
    DataModel<PrmsSupplyProfileDetail> PrmsSupplyProfileDetailModel;

    ComLuCountry comLuCountry;

    private boolean disableBtnCreate = false;
    private boolean disablerbtn = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;

    private int selectedRowIndex;
    private int update = 0;
    int updateStatus = 0;
    int saveStatus = 0;

    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String duplicattion = null;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String Contractor = "Contractor";
    private String Consultant = "Consultant";
    private String Supplier = "Supplier";
    private String renderSupplier = "true";
    private String renderContractor = "false";
    private String renderConsultant = "false";
    private String renderWhenLocal = "true";
    private String renderVat = "true";
    private String renderOthers = "false";
    private String rendername;
    private boolean hideWhenActive = false;
    PrmsSupplyProfile selectSupplyProfile;
 //PostConstruct
    @PostConstruct
    public void init() {
       ItemStausBarChartCreator();
    }
    
    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public DataModel<PrmsSupplyProfile> getPrmsSupplyProfileMdel() {
        if (prmsSupplyProfileMdel == null) {
            prmsSupplyProfileMdel = new ListDataModel<>();
        }

        return prmsSupplyProfileMdel;
    }

    public PrmsSupplyProfile getSelectSupplyProfile() {
        return selectSupplyProfile;
    }

    public void setSelectSupplyProfile(PrmsSupplyProfile selectSupplyProfile) {
        this.selectSupplyProfile = selectSupplyProfile;
    }

    public void setPrmsSupplyProfileMdel(DataModel<PrmsSupplyProfile> prmsSupplyProfileMdel) {
        this.prmsSupplyProfileMdel = prmsSupplyProfileMdel;
    }

    public List<PrmsSupplyProfile> getPrmsSupplyProfileLst() {
        if (prmsSupplyProfileLst == null) {
            prmsSupplyProfileLst = new ArrayList<>();
        }
        return prmsSupplyProfileLst;
    }

    public void setPrmsSupplyProfileLst(List<PrmsSupplyProfile> prmsSupplyProfileLst) {
        this.prmsSupplyProfileLst = prmsSupplyProfileLst;
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

    Set<String> supplierProCheck = new HashSet<>();

    public Set<String> getSupplierProCheck() {
        return supplierProCheck;
    }

    /**
     *
     * @param supplierProCheck
     */
    public void setSupplierProCheck(Set<String> supplierProCheck) {
        this.supplierProCheck = supplierProCheck;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
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

    public PrmsSupplyProfile getEepVendorReg() {
        if (eepVendorReg == null) {
            eepVendorReg = new PrmsSupplyProfile();
        }
        return eepVendorReg;
    }

    /**
     *
     * @param eepVendorReg
     */
    public void setEepVendorReg(PrmsSupplyProfile eepVendorReg) {
        this.eepVendorReg = eepVendorReg;
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

    public DataModel<PrmsSupplyProfileDetail> getPrmsSupplyProfileDetailModel() {
        if (PrmsSupplyProfileDetailModel == null) {
            PrmsSupplyProfileDetailModel = new ListDataModel<>();
        }
        return PrmsSupplyProfileDetailModel;
    }

    public void setPrmsSupplyProfileDetailModel(DataModel<PrmsSupplyProfileDetail> PrmsSupplyProfileDetailModel) {
        this.PrmsSupplyProfileDetailModel = PrmsSupplyProfileDetailModel;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    /**
     *
     * @return
     */
    public int getUpdate() {
        return update;
    }

    /**
     *
     * @param update
     */
    public void setUpdate(int update) {
        this.update = update;
    }

    public String getRenderSupplier() {
        return renderSupplier;
    }

    public void setRenderSupplier(String renderSupplier) {
        this.renderSupplier = renderSupplier;
    }

    public String getRenderContractor() {
        return renderContractor;
    }

    public void setRenderContractor(String renderContractor) {
        this.renderContractor = renderContractor;
    }

    public String getRenderConsultant() {
        return renderConsultant;
    }

    public void setRenderConsultant(String renderConsultant) {
        this.renderConsultant = renderConsultant;
    }

    public List<PrmsSupplyProfileDetail> getPrmsSupplyProfileDetailList() {
        return prmsSupplyProfileDetailList;
    }

    public void setPrmsSupplyProfileDetailList(List<PrmsSupplyProfileDetail> prmsSupplyProfileDetailList) {
        this.prmsSupplyProfileDetailList = prmsSupplyProfileDetailList;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public String getContractor() {
        return Contractor;
    }

    public void setContractor(String Contractor) {
        this.Contractor = Contractor;
    }

    public String getConsultant() {
        return Consultant;
    }

    public void setConsultant(String Consultant) {
        this.Consultant = Consultant;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String Supplier) {
        this.Supplier = Supplier;
    }

    public String getLastsuppProNo() {
        return lastsuppProNo;
    }

    public void setLastsuppProNo(String lastsuppProNo) {
        this.lastsuppProNo = lastsuppProNo;
    }

    public String getNewsuppProNo() {
        return newsuppProNo;
    }

    public void setNewsuppProNo(String newsuppProNo) {
        this.newsuppProNo = newsuppProNo;
    }

    public String getRenderWhenLocal() {
        return renderWhenLocal;
    }

    public void setRenderWhenLocal(String renderWhenLocal) {
        this.renderWhenLocal = renderWhenLocal;
    }

    public boolean isHideWhenActive() {
        return hideWhenActive;
    }

    public void setHideWhenActive(boolean hideWhenActive) {
        this.hideWhenActive = hideWhenActive;
    }

    public String getRendername() {
        return rendername;
    }

    public void setRendername(String rendername) {
        this.rendername = rendername;
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

    public List<ComLuCountry> getCountryList() {
        if (countryList == null) {
            countryList = new ArrayList<>();
            countryList = vendorRegBeanLocal.getCountryList();
        }
        return countryList;
    }

    public void setCountryList(List<ComLuCountry> countryList) {
        this.countryList = countryList;
    }

    /**
     *
     * @param saveupdate
     */
//    public void setSaveupdate(String saveupdate) {
//        this.saveupdate = saveupdate;
//    }
    /**
     *
     */
    public String clear() {
        eepVendorReg = null;
        renderSupplier = "true";
        renderConsultant = "false";
        renderContractor = "falsse";
        prmsLuVatTypeLookup = null;
        comLuCountry = null;
        return null;
    }

    /**
     *
     * @return
     */
    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public List<PrmsSupplyProfile> getSupplierProSearchParameterLst() {
        if (supplierProSearchParameterLst == null) {
            supplierProSearchParameterLst = new ArrayList<>();
            supplierProSearchParameterLst = vendorRegBeanLocal.getParamNameList();
        }
        return supplierProSearchParameterLst;
    }

    public void setSupplierProSearchParameterLst(List<PrmsSupplyProfile> supplierProSearchParameterLst) {
        this.supplierProSearchParameterLst = supplierProSearchParameterLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            eepVendorReg.setColumnName(event.getNewValue().toString());
            eepVendorReg.setColumnValue(null);
        }
    }
    String lastsuppProNo = "0";
    String newsuppProNo;
    int newSupProNoList;

    public String generateSuppProNo() {

        if (saveorUpdateBundle.equals("Update")) {
            newsuppProNo = eepVendorReg.getProfileFor();
            if (eepVendorReg.getProfileFor().equalsIgnoreCase("Supplier")) {
                newsuppProNo = eepVendorReg.getVendorCode();
            } else if (eepVendorReg.getProfileFor().equalsIgnoreCase("Contractor")) {
                newsuppProNo = eepVendorReg.getVendorCode();
            } else if (eepVendorReg.getProfileFor().equalsIgnoreCase("Consultant")) {
                newsuppProNo = eepVendorReg.getVendorCode();
            }
        } else {
            PrmsSupplyProfile lastSupmNo = vendorRegBeanLocal.generatelastSuppNo();
            if (lastSupmNo != null) {
                lastsuppProNo = lastSupmNo.getId();

            }
            DateFormat df = new SimpleDateFormat("yyyy");
            Date now = new Date();

            if (lastsuppProNo.equals("0")) {
                newSupProNoList = 1;
                if (eepVendorReg.getProfileFor().equalsIgnoreCase("Supplier")) {
                    newsuppProNo = "SP-" + newSupProNoList + "/" + df.format(now);
                } else if (eepVendorReg.getProfileFor().equalsIgnoreCase("Contractor")) {
                    newsuppProNo = "CON-" + newSupProNoList + "/" + df.format(now);
                } else if (eepVendorReg.getProfileFor().equalsIgnoreCase("Consultant")) {
                    newsuppProNo = "CNS-" + newSupProNoList + "/" + df.format(now);
                }
            } else {
                String[] lastInspNos = lastsuppProNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newSupProNoList = Integer.parseInt(lastDatesPaterns[0]);
                newSupProNoList = newSupProNoList + 1;
                if (selected.equalsIgnoreCase("Supplier")) {
                    newsuppProNo = "SP-" + newSupProNoList + "/" + df.format(now);
                    eepVendorReg.setVendorCode(newsuppProNo);
                } else if (selected.equalsIgnoreCase("Contractor")) {
                    newsuppProNo = "CON-" + newSupProNoList + "/" + df.format(now);
                    eepVendorReg.setVendorCode(newsuppProNo);

                } else if (selected.equalsIgnoreCase("Consultant")) {
                    newsuppProNo = "CNS-" + newSupProNoList + "/" + df.format(now);
                    eepVendorReg.setVendorCode(newsuppProNo);
                }

            }
        }

        if (eepVendorReg.getProfileFor() != null) {
            newsuppProNo = getSupplierID(eepVendorReg.getProfileFor());
        }

        return newsuppProNo;

    }

    public String getSupplierID(String profileType) {
        String supOrConOrConsNo = vendorRegBeanLocal.getSupOrConOrConsNo(profileType);
        return supOrConOrConsNo;
    }

    public String SaveSupplierProfile() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "SaveSupplierProfile", dataset)) {

                if (saveorUpdateBundle.equals("Save")) {
                    if (updateStatus == 0) {
                        try {
                            generateSuppProNo();
                            if (eepVendorReg.getVendorType().equalsIgnoreCase("Foreign")) {
                                eepVendorReg.setVendorCode(newsuppProNo);
                                eepVendorReg.setCountryId(comLuCountry);
                                eepVendorReg.setPreparedBy(workFlow.getUserName());
                            } else if (eepVendorReg.getVendorType().equalsIgnoreCase("Local")) {
                                eepVendorReg.setVendorCode(newsuppProNo);
                                eepVendorReg.setCountryId(comLuCountry);

                                eepVendorReg.setVatTypeId(prmsLuVatTypeLookup);
                                eepVendorReg.setPreparedBy(workFlow.getUserName());
                            }
                            vendorRegBeanLocal.save(eepVendorReg);
                            JsfUtil.addSuccessMessage("Supplier Profile information is registered!!");
                            return clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addFatalMessage("some thing is going to error" + e);
                            clear();
                            return null;
                        }
                    }
                } else if (saveorUpdateBundle.equalsIgnoreCase("Update")) {
                    try {
                        eepVendorReg.getProfileFor();
                        eepVendorReg.setPreparedBy(workFlow.getUserName());
                        eepVendorReg.setVatTypeId(prmsLuVatTypeLookup);
                        vendorRegBeanLocal.update(eepVendorReg);
                        JsfUtil.addSuccessMessage("Supplier Profile information is updated!!");
                        saveorUpdateBundle = "Save";
                        return clear();
                    } catch (Exception e) {
                        JsfUtil.addSuccessMessage("error== w/n data modification" + e);
                        clear();
                    }
                }

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

    /**
     *
     * @param vendorCode
     * @return
     */
    public List<PrmsSupplyProfile> searchVendorName(String vendorName) {

        List<PrmsSupplyProfile> eepVendorRegList = null;
        eepVendorReg.setVendorName(vendorName);
        eepVendorRegList = vendorRegBeanLocal.searchvendorName(eepVendorReg);

        return eepVendorRegList;
    }

    /**
     *
     * @param event
     */
    public void createNewParty() {
        countryList = vendorRegBeanLocal.getCountryList();
        disablerbtn = false;
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            eepVendorReg.setProfileFor(selected);
            renderSupplier = "true";
            renderConsultant = "false";
            renderContractor = "false";
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            clear();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
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

        int rowIndex = prmsSupplyProfileMdel.getRowIndex();
        eepVendorReg = prmsSupplyProfileLst.get(rowIndex);

        reCreateMdl();
    }

    public void clearSupDetail() {
        prmsSupplyProfileDetail = null;
    }

    public void edit() {
        prmsSupplyProfileDetail = PrmsSupplyProfileDetailModel.getRowData();
        selectedRowIndex = PrmsSupplyProfileDetailModel.getRowIndex();

    }

    public void addSupProTable() {
        prmsSupplyProfileDetail.setSuppId(eepVendorReg);
        eepVendorReg.getPrmsSupplyProfileDetailList().add(prmsSupplyProfileDetail);
        reCreatModel();
        clearSupDetail();

    }

    public void rowSelect(SelectEvent event) {
        eepVendorReg = (PrmsSupplyProfile) event.getObject();
        renderpnlToSearchPage = true;
        if (eepVendorReg.getVendorType().equalsIgnoreCase("Local")) {
            renderWhenLocal = "false";
        } else {
            renderWhenLocal = "true";
        }
        if (eepVendorReg.getVendorType().equalsIgnoreCase("Foreign")) {
            renderVat = "false";
        } else {
            renderVat = "true";
        }
        eepVendorReg.setId(eepVendorReg.getId());
        eepVendorReg = vendorRegBeanLocal.getSelectedRequest(eepVendorReg.getId());
        countryList = vendorRegBeanLocal.getCountryList();
        comLuCountry = eepVendorReg.getCountryId();
        prmsLuVatTypeLookup = eepVendorReg.getVatTypeId();
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        disablerbtn = true;
        if (eepVendorReg.getProfileFor().equals("Supplier")) {
            renderSupplier = "true";
            renderConsultant = "false";
            renderContractor = "false";
        } else if (eepVendorReg.getProfileFor().equals("Contractor")) {
            renderContractor = "true";
            renderSupplier = "false";
            renderConsultant = "false";

        } else if (eepVendorReg.getProfileFor().equals("Consultant")) {
            renderConsultant = "true";
            renderSupplier = "false";
            renderContractor = "false";
        }

        if (eepVendorReg.getStatus().equals("Active")) {
            hideWhenActive = true;
        } else {
            hideWhenActive = false;
        }
        setIcone("ui-icon-plus");
        createOrSearchBundle = "New";
        saveorUpdateBundle = "Update";
        reCreateMdl();

    }

    public void reCreateMdl() {
        prmsSupplyProfileMdel = null;
        prmsSupplyProfileMdel = new ListDataModel<>(new ArrayList(getPrmsSupplyProfileLst()));
    }

    private void reCreatModel() {
        PrmsSupplyProfileDetailModel = null;
        PrmsSupplyProfileDetailModel = new ListDataModel(new ArrayList<>(eepVendorReg.getPrmsSupplyProfileDetailList()));
    }

    public void searchBySupplProfCode() {
        eepVendorReg.setPreparedBy(String.valueOf(workFlow.getUserAccount()));
        prmsSupplyProfileLst = vendorRegBeanLocal.searchBySupplProfCode(eepVendorReg);
        reCreateMdl();
        eepVendorReg = new PrmsSupplyProfile();
    }

    String type = "";

    public void Local(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            type = event.getNewValue().toString();
            if (type.equalsIgnoreCase("Foreign")) {
                renderVat = "false";
            } else {
                renderVat = "true";
            }

            if (type.equalsIgnoreCase("Local")) {
                renderWhenLocal = "false";
            } else {
                renderWhenLocal = "true";
            }

        }
    }
    String select = "";

    public void Others(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            select = event.getNewValue().toString();
            if (select.equalsIgnoreCase("Others")) {
                renderOthers = "true";
            } else {
                renderOthers = "false";
            }
        }
    }

    public void statusList(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            if (event.getNewValue().equals("BlackList")) {
                rendername = "Black List";
            } else if (event.getNewValue().equals("Suspended")) {
                rendername = "Suspended";
            } else if (event.getNewValue().equals("Active")) {
                hideWhenActive = true;
                rendername = "";
            }
        }
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    String selected = "Supplier";

    public void profile_vlc(ValueChangeEvent event) {
        eepVendorReg.setProfileFor(selected);
        eepVendorReg.setProfileFor(Supplier);
        if (null != event.getNewValue()) {
            selected = event.getNewValue().toString();
            if (selected.equalsIgnoreCase("Supplier")) {
                renderSupplier = "true";
                renderContractor = "false";
                renderConsultant = "false";
            } else if (selected.equalsIgnoreCase("Contractor")) {
                renderContractor = "true";
                renderSupplier = "false";
                renderConsultant = "false";
            } else {
                renderConsultant = "true";
                renderContractor = "false";
                renderSupplier = "false";
            }
        }
    }

    /**
     * @return the disablerbtn
     */
    public boolean isDisablerbtn() {
        return disablerbtn;
    }

    /**
     * @param disablerbtn the disablerbtn to set
     */
    public void setDisablerbtn(boolean disablerbtn) {
        this.disablerbtn = disablerbtn;
    }

    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    /**
     * @return the prmsLuVatTypeLookup
     */
    public PrmsLuVatTypeLookup getPrmsLuVatTypeLookup() {
        if (prmsLuVatTypeLookup == null) {
            prmsLuVatTypeLookup = new PrmsLuVatTypeLookup();
        }
        return prmsLuVatTypeLookup;
    }

    public void setPrmsLuVatTypeLookup(PrmsLuVatTypeLookup prmsLuVatTypeLookup) {
        this.prmsLuVatTypeLookup = prmsLuVatTypeLookup;
    }

    public List<PrmsLuVatTypeLookup> getPrmsLuVatTypeLookupList() {
        if (prmsLuVatTypeLookupList == null) {
            prmsLuVatTypeLookupList = new ArrayList<>();
            prmsLuVatTypeLookupList = vendorRegBeanLocal.findVatType();
        }
        return prmsLuVatTypeLookupList;
    }

    public void setPrmsLuVatTypeLookupList(List<PrmsLuVatTypeLookup> prmsLuVatTypeLookupList) {
        this.prmsLuVatTypeLookupList = prmsLuVatTypeLookupList;
    }

    public String getRenderVat() {
        return renderVat;
    }

    public void setRenderVat(String renderVat) {
        this.renderVat = renderVat;
    }

    public String getRenderOthers() {
        return renderOthers;
    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    private BarChartModel ItemStatusBarModel;
    ChartSeries Chart = new ChartSeries();
    int totalItems = 0;
    List<String> ItemStatsuTypes = new ArrayList<>();
    List<ItemStatus> ItemStatusList = new ArrayList<>();
    ItemStatus itemStatus = new ItemStatus();

    public class ItemStatus implements Serializable {

        private String itemStatus;
        private int total;

        //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public String getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(String itemStatus) {
            this.itemStatus = itemStatus;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        //</editor-fold>
        public ItemStatus ItemByStatus(String status, int totalCount) {
            this.itemStatus = status;
            this.total = totalCount;
            return this;
        }

    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public BarChartModel getItemStatusBarModel() {
        return ItemStatusBarModel;
    }

    public void setItemStatusBarModel(BarChartModel ItemStatusBarModel) {
        this.ItemStatusBarModel = ItemStatusBarModel;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public ChartSeries getChart() {
        return Chart;
    }

    public void setChart(ChartSeries Chart) {
        this.Chart = Chart;
    }

    public List<String> getItemStatsuTypes() {
        return ItemStatsuTypes;
    }

    public void setItemStatsuTypes(List<String> ItemStatsuTypes) {
        this.ItemStatsuTypes = ItemStatsuTypes;
    }

    public List<ItemStatus> getItemStatusList() {
        return ItemStatusList;
    }

    public void setItemStatusList(List<ItemStatus> ItemStatusList) {
        this.ItemStatusList = ItemStatusList;
    }

    public void ItemStausBarChartCreator() {
        System.out.println("innnnn===");
        ItemStatsuTypes = vendorRegBeanLocal.findAllSupplierStatuses();
        System.out.println("ItemStatsuTypes===" + ItemStatsuTypes);
        for (int i = 0; i < ItemStatsuTypes.size(); i++) {
            int itmeCount = 0;
            itmeCount = vendorRegBeanLocal.ConutBYSupplierType(ItemStatsuTypes.get(i));
            ItemStatusList.add(itemStatus.ItemByStatus(ItemStatsuTypes.get(i), itmeCount));
            System.out.println("ItemStatsuTypes.get(i)==" + ItemStatsuTypes.get(i));
            System.out.println("itmeCount==" + itmeCount);
            Chart.setLabel("Item Status");
            Chart.set(ItemStatsuTypes.get(i), itmeCount);
            totalItems = totalItems + itmeCount;
        }
        createBarModel();
    }

    private void createBarModel() {
        
        ItemStatusBarModel = initBarModel1();
        ItemStatusBarModel.setTitle("Item Grouped By Their Status ");
        ItemStatusBarModel.setLegendPosition("ne");
        Axis xAxis = ItemStatusBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Item Status");
        xAxis.setTickAngle(0);
        Axis yAxis = ItemStatusBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total Item");
        yAxis.setMin(0);
        yAxis.setMax(totalItems);
         System.out.print("helooo");
    }

    private BarChartModel initBarModel1() {
        BarChartModel model = new BarChartModel();
        model.addSeries(Chart);
        return model;
    }
//</editor-fold>
}
