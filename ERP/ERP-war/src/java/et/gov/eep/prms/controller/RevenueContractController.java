/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.businessLogic.RevenueContractBeanLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.inject.Inject;
import et.gov.eep.prms.entity.PrmsRevenueContarct;
import et.gov.eep.prms.entity.PrmsRevenueContractDetail;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.mapper.admin.FmsGeneralLedgerFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
// </editor-fold>

/**
 *
 * @author Bayisa
 */
//Revenue Contract page view scoped CDI Named Bean class
@Named(value = "revenueContractController")
@ViewScoped
public class RevenueContractController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    RevenueContractBeanLocal revenueContractBeanLocal;
    @EJB
    FmsGeneralLedgerFacade fmsGeneralLedgerFacade;
    @EJB
    subsidiaryBeanLocal subsidiarybeanLocal;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    SessionBean sessionBean;
    @Inject
    PrmsRevenueContarct prmsRevenueContarct;
    @Inject
    PrmsRevenueContractDetail prmsRevenueContractDetail;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
    @Inject
    FmsLuCurrency luCurrency;
    @Inject
    ColumnNameResolver columnNameResolver;
    List<ColumnNameResolver> columnNameResolverList = new ArrayList<>();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Variables">
    private String icon = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private String saveUpdateBundle = "Save";
    private String status = "";
    String lastRevContNo = "0";
    String newRevContNo;
    int statusInFigure;
    private boolean renderPnlManPage = true;
    private boolean renderPnlCreateParty = false;
    private boolean isRevContDataFilled = true;
    private boolean isRowEdited = false;
    BigDecimal totalStandAloneSellPrice = new BigDecimal(BigInteger.ZERO);
    BigDecimal allocatedPriceToPerfObl = new BigDecimal(BigInteger.ZERO);
    PrmsRevenueContarct revenueContarctSelection;
    PrmsRevenueContractDetail revenueContractDetailSelection;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declare Lists and Models">
    private List<FmsGeneralLedger> glLists;
    private List<FmsSubsidiaryLedger> subsLists;
    private List<FmsLuCurrency> currencyLists;
    private List<PrmsRevenueContarct> prmsRevenueContarctsList;
    private List<PrmsRevenueContractDetail> prmsRevenueContractDetailsList;
    private DataModel<PrmsRevenueContarct> revenueContarctsDModel;
    private DataModel<PrmsRevenueContractDetail> revenueContractDetailsDModel;
    // </editor-fold>

    //default non-argument contructor
    public RevenueContractController() {

    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Objects">
    public PrmsRevenueContarct getPrmsRevenueContarct() {
        if (prmsRevenueContarct == null) {
            prmsRevenueContarct = new PrmsRevenueContarct();
        }
        return prmsRevenueContarct;
    }

    public void setPrmsRevenueContarct(PrmsRevenueContarct prmsRevenueContarct) {
        this.prmsRevenueContarct = prmsRevenueContarct;
    }

    public PrmsRevenueContractDetail getPrmsRevenueContractDetail() {
        if (prmsRevenueContractDetail == null) {
            prmsRevenueContractDetail = new PrmsRevenueContractDetail();
        }
        return prmsRevenueContractDetail;
    }

    public void setPrmsRevenueContractDetail(PrmsRevenueContractDetail prmsRevenueContractDetail) {
        this.prmsRevenueContractDetail = prmsRevenueContractDetail;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public FmsSubsidiaryLedger getFmsSubsidiaryLedger() {
        if (fmsSubsidiaryLedger == null) {
            fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        }
        return fmsSubsidiaryLedger;
    }

    public void setFmsSubsidiaryLedger(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        this.fmsSubsidiaryLedger = fmsSubsidiaryLedger;
    }

    public FmsLuCurrency getLuCurrency() {
        if (luCurrency == null) {
            luCurrency = new FmsLuCurrency();
        }
        return luCurrency;
    }

    public void setLuCurrency(FmsLuCurrency luCurrency) {
        this.luCurrency = luCurrency;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        return columnNameResolverList = revenueContractBeanLocal.findColoumns();
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> columnNameResolverList) {
        this.columnNameResolverList = columnNameResolverList;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getter and setter of Variables">
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getSaveUpdateBundle() {
        return saveUpdateBundle;
    }

    public void setSaveUpdateBundle(String saveUpdateBundle) {
        this.saveUpdateBundle = saveUpdateBundle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }

    public boolean isIsRevContDataFilled() {
        return isRevContDataFilled;
    }

    public void setIsRevContDataFilled(boolean isRevContDataFilled) {
        this.isRevContDataFilled = isRevContDataFilled;
    }

    public boolean isIsRowEdited() {
        return isRowEdited;
    }

    public void setIsRowEdited(boolean isRowEdited) {
        this.isRowEdited = isRowEdited;
    }

    public BigDecimal getAllocatedPriceToPerfObl() {

        return allocatedPriceToPerfObl;
    }

    public void setAllocatedPriceToPerfObl(BigDecimal allocatedPriceToPerfObl) {
        this.allocatedPriceToPerfObl = allocatedPriceToPerfObl;
    }

    public PrmsRevenueContarct getRevenueContarctSelection() {
        return revenueContarctSelection;
    }

    public void setRevenueContarctSelection(PrmsRevenueContarct revenueContarctSelection) {
        this.revenueContarctSelection = revenueContarctSelection;
    }

    public PrmsRevenueContractDetail getRevenueContractDetailSelection() {
        return revenueContractDetailSelection;
    }

    public void setRevenueContractDetailSelection(PrmsRevenueContractDetail revenueContractDetailSelection) {
        this.revenueContractDetailSelection = revenueContractDetailSelection;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Lists and models">
    public List<FmsGeneralLedger> getGlLists() {
        if (glLists == null) {
            glLists = new ArrayList<>();
            glLists = fmsGeneralLedgerFacade.searchGlAccountCode();
        }
        return glLists;
    }

    public void setGlLists(List<FmsGeneralLedger> glLists) {
        this.glLists = glLists;
    }

    public List<FmsSubsidiaryLedger> getSubsLists() {
        if (subsLists == null) {
            subsLists = new ArrayList<>();
            subsLists = subsidiarybeanLocal.findSubLedger(fmsGeneralLedger);
        }
        return subsLists;
    }

    public void setSubsLists(List<FmsSubsidiaryLedger> subsLists) {
        this.subsLists = subsLists;
    }

    public List<FmsLuCurrency> getCurrencyLists() {
        if (currencyLists == null) {
            currencyLists = new ArrayList<>();
            currencyLists = revenueContractBeanLocal.currencyNameLists();
        }
        return currencyLists;
    }

    public void setCurrencyLists(List<FmsLuCurrency> currencyLists) {
        this.currencyLists = currencyLists;
    }

    public List<PrmsRevenueContarct> getPrmsRevenueContarctsList() {
        if (prmsRevenueContarctsList == null) {
            prmsRevenueContarctsList = new ArrayList<>();
        }
        return prmsRevenueContarctsList;
    }

    public void setPrmsRevenueContarctsList(List<PrmsRevenueContarct> prmsRevenueContarctsList) {
        this.prmsRevenueContarctsList = prmsRevenueContarctsList;
    }

    public List<PrmsRevenueContractDetail> getPrmsRevenueContractDetailsList() {
        if (prmsRevenueContractDetailsList == null) {
            prmsRevenueContractDetailsList = new ArrayList<>();
        }
        return prmsRevenueContractDetailsList;
    }

    public void setPrmsRevenueContractDetailsList(List<PrmsRevenueContractDetail> prmsRevenueContractDetailsList) {
        this.prmsRevenueContractDetailsList = prmsRevenueContractDetailsList;
    }

    public DataModel<PrmsRevenueContarct> getRevenueContarctsDModel() {
        if (revenueContarctsDModel == null) {
            revenueContarctsDModel = new ListDataModel<>();
        }
        return revenueContarctsDModel;
    }

    public void setRevenueContarctsDModel(DataModel<PrmsRevenueContarct> revenueContarctsDModel) {
        this.revenueContarctsDModel = revenueContarctsDModel;
    }

    public DataModel<PrmsRevenueContractDetail> getRevenueContractDetailsDModel() {
        if (revenueContractDetailsDModel == null) {
            revenueContractDetailsDModel = new ListDataModel<>();
        }
        return revenueContractDetailsDModel;
    }

    public void setRevenueContractDetailsDModel(DataModel<PrmsRevenueContractDetail> revenueContractDetailsDModel) {
        this.revenueContractDetailsDModel = revenueContractDetailsDModel;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event Changes">
    public void onRowSelect(SelectEvent event) {
        if (event.getObject() != null) {
            prmsRevenueContarct = (PrmsRevenueContarct) event.getObject();
            renderPnlManPage = false;
            renderPnlCreateParty = true;
            fmsGeneralLedger = prmsRevenueContarct.getGlId();
            luCurrency = prmsRevenueContarct.getCurrencyId();
            newRevContNo = prmsRevenueContarct.getRevContractNo();
            fmsSubsidiaryLedger = prmsRevenueContarct.getSubsidiaryIds();
            isRevContDataFilled = false;
            saveUpdateBundle = "Update";
            if (prmsRevenueContarct.getStatus().equals("5")) {
                setStatus("active");

            } else {
                setStatus("deactive");
            }
            prmsRevenueContractDetailsList = prmsRevenueContarct.getPrmsRevenueContractDetailList();
            recreateRevContractDetail();
        }

    }

    public void onCellEdit(CellEditEvent event) {
        if (event != null && !event.getNewValue().equals(event.getOldValue())) {
            prmsRevenueContractDetail = (PrmsRevenueContractDetail) event.getNewValue();
        }
    }

    public void onRowEdit(RowEditEvent event) {
        if (event.getObject() != null) {
            prmsRevenueContractDetail = (PrmsRevenueContractDetail) event.getObject();
            prmsRevenueContractDetailsList.add(prmsRevenueContractDetail);
            isRowEdited = true;
        }
    }

    public void checkStatus(ValueChangeEvent event) {
        System.out.println("methh");
        if (event.getNewValue().toString() != null) {
            status = event.getNewValue().toString();
            System.out.println("U selected " + status);
            if (status.equalsIgnoreCase("active")) {
                statusInFigure = Constants.ACTIVE;
            } else if (status.equalsIgnoreCase("deactive")) {
                statusInFigure = Constants.ACTIVE_REJECT_VALUE;
            }
        }
    }

    public void changeOnGL(ValueChangeEvent changeEvent) {
        if (changeEvent.getNewValue() != null) {
            fmsGeneralLedger = (FmsGeneralLedger) changeEvent.getNewValue();
            subsLists = subsidiarybeanLocal.findSubLedger(fmsGeneralLedger);
        }
    }

    String columnName;
    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(event.getNewValue().toString().toLowerCase() + ":"));
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        return col;
    }

    public List<PrmsRevenueContarct> searchByRevenuContract(String revenuContractId) {
        prmsRevenueContarct.setColumnValue(revenuContractId);
        prmsRevenueContarctsList = revenueContractBeanLocal.searchByCol_NameAndCol_Value(columnNameResolver, prmsRevenueContarct.getColumnValue());
        return prmsRevenueContarctsList;
    }

    public void getByFinancialInstColumn(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                prmsRevenueContarct = (PrmsRevenueContarct) event.getObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other methods">
    public void createSearchAction() {
        saveUpdateBundle = "Save";
        if (createOrSearchBundle.equals("New")) {
            icon = "ui-icon-search";
            createOrSearchBundle = "Search";
            renderPnlCreateParty = true;
            renderPnlManPage = false;
        } else if (createOrSearchBundle.equals("Search")) {
            icon = "ui-icon-plus";
            createOrSearchBundle = "New";
            renderPnlManPage = true;
            renderPnlCreateParty = false;
        }
    }

    public String revContNoAutogenarate() {

        if (saveUpdateBundle.equals("Update")) {
            newRevContNo = prmsRevenueContarct.getRevContractNo();
        } else {
            newRevContNo = revenueContractBeanLocal.generateRevContNo();
        }

        return newRevContNo;
    }

    public void findRevContractInfo() {
        prmsRevenueContarctsList = revenueContractBeanLocal.searchAllRevContract();
        recreateRevContract();
    }

    public void addRevContDetail() {
        prmsRevenueContractDetail.setRevenueContractId(prmsRevenueContarct);

        if (prmsRevenueContractDetail.getStandAloneSellingPrice() != null) {
            BigDecimal standAloneSellPrice = new BigDecimal(BigInteger.ZERO);
            if (prmsRevenueContarct.getEstimatedTotalCost() != null) {
                BigDecimal estimatedTc = prmsRevenueContarct.getEstimatedTotalCost();
                int detailSize = prmsRevenueContarct.getPrmsRevenueContractDetailList().size();
                if (detailSize > 0) {
                    for (int indexRow = 0; indexRow < prmsRevenueContarct.getPrmsRevenueContractDetailList().size(); indexRow++) {
                        standAloneSellPrice = standAloneSellPrice.add(prmsRevenueContarct.getPrmsRevenueContractDetailList().get(indexRow).getStandAloneSellingPrice());

                    }
                    standAloneSellPrice = standAloneSellPrice;
                    standAloneSellPrice = standAloneSellPrice.add(prmsRevenueContractDetail.getStandAloneSellingPrice());
                    totalStandAloneSellPrice = standAloneSellPrice;
                    for (int indexRow2 = 0; indexRow2 < prmsRevenueContarct.getPrmsRevenueContractDetailList().size(); indexRow2++) {
                        standAloneSellPrice = prmsRevenueContarct.getPrmsRevenueContractDetailList().get(indexRow2).getStandAloneSellingPrice();

                        allocatedPriceToPerfObl = (estimatedTc.multiply(standAloneSellPrice.divide(totalStandAloneSellPrice, MathContext.DECIMAL32)));

                    }
                    allocatedPriceToPerfObl = estimatedTc.multiply(prmsRevenueContractDetail.getStandAloneSellingPrice().divide(totalStandAloneSellPrice, MathContext.DECIMAL32));

                } else {
                    standAloneSellPrice = prmsRevenueContractDetail.getStandAloneSellingPrice();
                    totalStandAloneSellPrice = standAloneSellPrice;
                    allocatedPriceToPerfObl = estimatedTc.multiply(standAloneSellPrice.divide(totalStandAloneSellPrice, 2, RoundingMode.FLOOR));

                }
            }
        }
        onCheckPaymentPeriod();// is added feature if important but not finished & continue drom here next return

    }

    public void onCheckPaymentPeriod() {
        if (prmsRevenueContarct.getPaymentPeriod() != null) {
            BigInteger paymentAmount = prmsRevenueContarct.getPaymentAmount();
            BigDecimal estimatedTc = prmsRevenueContarct.getEstimatedTotalCost();
            String contractDuration = prmsRevenueContarct.getContractDuration();
            int roundOfPayment = 0;
            if (prmsRevenueContarct.getPaymentPeriod().equalsIgnoreCase("monthly")) {
                roundOfPayment = Integer.parseInt(prmsRevenueContarct.getContractDuration()) * 12;
            } else if (prmsRevenueContarct.getPaymentPeriod().equalsIgnoreCase("quarterly")) {
                roundOfPayment = Integer.parseInt(prmsRevenueContarct.getContractDuration()) * 3;
            } else if (prmsRevenueContarct.getPaymentPeriod().equalsIgnoreCase("6 month")) {
                roundOfPayment = Integer.parseInt(prmsRevenueContarct.getContractDuration()) * 2;
            } else if (prmsRevenueContarct.getPaymentPeriod().equalsIgnoreCase("year")) {
                roundOfPayment = Integer.parseInt(prmsRevenueContarct.getContractDuration());
            }
            //check starts
            String prodOrServName = prmsRevenueContractDetail.getPerformanceObligation();

            prmsRevenueContarct.getPrmsRevenueContractDetailList().add(prmsRevenueContractDetail);
            recreateRevContractDetail();
            isRowEdited = false;
            prmsRevenueContractDetail = new PrmsRevenueContractDetail();
//            }

        }
    }

    public void saveRevContDeatil() {
        if (revenueContractDetailsDModel.getRowCount() > 0) {
            prmsRevenueContractDetail.setRevenueContractId(prmsRevenueContarct);
            revenueContractBeanLocal.edit(prmsRevenueContarct);
            if (isRowEdited == true) {
                revenueContractBeanLocal.updateDetail(prmsRevenueContractDetail);
            }
            JsfUtil.addInformationMessage("Detail Data Successfully Saved");
            clearDetail();
        } else {
            JsfUtil.addFatalMessage("Please Add Detail Data Before Saving");
        }

    }

    public void recreateRevContract() {
        revenueContarctsDModel = null;
        revenueContarctsDModel = new ListDataModel<>(prmsRevenueContarctsList);
    }

    public void recreateRevContractDetail() {
        revenueContractDetailsDModel = null;
        revenueContractDetailsDModel = new ListDataModel(prmsRevenueContractDetailsList);
    }

    public void clearViewScoped() {
        prmsRevenueContarct = new PrmsRevenueContarct();
        fmsGeneralLedger = new FmsGeneralLedger();
        luCurrency = new FmsLuCurrency();
        fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        status = new String();
        createOrSearchBundle = "Search";
        icon = "ui-icon-search";
        clearDetail();
    }

    public void clearDetail() {
        prmsRevenueContractDetail = null;
        revenueContractDetailsDModel = null;
        renderPnlCreateParty = true;
        prmsRevenueContractDetailsList = null;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update methods">
    public void saveRevenueConractInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveRevenueConractInfo", dataset)) {
                revContNoAutogenarate();
                if (saveUpdateBundle.equals("Save")) {
                    prmsRevenueContarct.setRevContractNo(newRevContNo);
                    prmsRevenueContarct.setGlId(fmsGeneralLedger);
                    prmsRevenueContarct.setSubsidiaryIds(fmsSubsidiaryLedger);
                    prmsRevenueContarct.setCurrencyId(luCurrency);
                    prmsRevenueContarct.setStatus(String.valueOf(statusInFigure));
                    if (prmsRevenueContarct != null) {
                        isRevContDataFilled = false;
                    }
                    revenueContractBeanLocal.save(prmsRevenueContarct);
                    JsfUtil.addSuccessMessage("Data Successfully Saved");
                    clearViewScoped();
                } else {
                    isRevContDataFilled = false;
                    prmsRevenueContarct.setGlId(fmsGeneralLedger);
                    prmsRevenueContarct.setSubsidiaryIds(fmsSubsidiaryLedger);
                    prmsRevenueContarct.setStatus(String.valueOf(statusInFigure));
                    prmsRevenueContarct.setCurrencyId(luCurrency);
                    revenueContractBeanLocal.edit(prmsRevenueContarct);
                    JsfUtil.addSuccessMessage("Data Successfully Updated");
                    saveUpdateBundle = "Save";
                    clearViewScoped();
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
            ex.printStackTrace();
        }
    }
    // </editor-fold>

}
