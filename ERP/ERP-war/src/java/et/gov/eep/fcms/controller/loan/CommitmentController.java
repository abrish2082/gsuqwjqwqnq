/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.loan;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.loan.FmsLoanBeanLocal;
import et.gov.eep.fcms.businessLogic.loan.FmsLoanCommitmentBeanLocal;
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsLoanCommitment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>



/**
 *
 * @author Binyam
 */
@Named(value = "commitmentController")
@ViewScoped
public class CommitmentController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject">
        @Inject
    SessionBean SessionBean;

    @Inject
    FmsLoan fmsLoan;
    @Inject
    FmsLoanCommitment fmsLoanCommitment;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
    
    @EJB
    FmsLoanBeanLocal fmsLoanBeanLocal;
    @EJB
    FmsLoanCommitmentBeanLocal fmsLoanCommitmentBeanLocal;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    DataModel<FmsLoanCommitment> commitmentDModel;
    List<FmsLoanCommitment> commitmentList;

    FmsLoan selectedLoan;
    DataModel<FmsLoan> loanDatamodel;
    List<FmsLoan> loanList;

    private String createOrSearchBundle = "New";
    private String headerTitle = "Commitment Fee";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean renderNewSrcBtn = false;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CommitmentController">
      public CommitmentController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setters">
      public FmsLoan getSelectedLoan() {
        return selectedLoan;
    }

    public void setSelectedLoan(FmsLoan selectedLoan) {
        this.selectedLoan = selectedLoan;
    }

    public DataModel<FmsLoan> getLoanDatamodel() {
        return loanDatamodel;
    }

    public void setLoanDatamodel(DataModel<FmsLoan> loanDatamodel) {
        this.loanDatamodel = loanDatamodel;
    }

    public List<FmsLoan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<FmsLoan> loanList) {
        this.loanList = loanList;
    }

    public boolean isRenderNewSrcBtn() {
        return renderNewSrcBtn;
    }

    public void setRenderNewSrcBtn(boolean renderNewSrcBtn) {
        this.renderNewSrcBtn = renderNewSrcBtn;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
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

    public FmsLoanCommitment getFmsLoanCommitment() {
        if (fmsLoanCommitment == null) {
            fmsLoanCommitment = new FmsLoanCommitment();
        }
        return fmsLoanCommitment;
    }

    /**
     *
     * @param fmsLoanCommitment
     */
    public void setFmsLoanCommitment(FmsLoanCommitment fmsLoanCommitment) {
        this.fmsLoanCommitment = fmsLoanCommitment;
    }

    /**
     *
     * @return
     */
    public FmsLoanCommitmentBeanLocal getFmsLoanCommitmentBeanLocal() {
        return fmsLoanCommitmentBeanLocal;
    }

    /**
     *
     * @param fmsLoanCommitmentBeanLocal
     */
    public void setFmsLoanCommitmentBeanLocal(FmsLoanCommitmentBeanLocal fmsLoanCommitmentBeanLocal) {
        this.fmsLoanCommitmentBeanLocal = fmsLoanCommitmentBeanLocal;
    }

    /**
     *
     * @return
     */
    public DataModel<FmsLoanCommitment> getCommitmentDModel() {
        return commitmentDModel;
    }

    /**
     *
     * @param commitmentDModel
     */
    public void setCommitmentDModel(DataModel<FmsLoanCommitment> commitmentDModel) {
        this.commitmentDModel = commitmentDModel;
    }

    /**
     *
     * @return
     */
    public List<FmsLoanCommitment> getCommitmentList() {
        return commitmentList;
    }

    /**
     *
     * @param commitmentList
     */
    public void setCommitmentList(List<FmsLoanCommitment> commitmentList) {
        this.commitmentList = commitmentList;
    }

    /**
     *
     * @return
     */
    public FmsLoan getFmsLoan() {
        if (fmsLoan == null) {
            fmsLoan = new FmsLoan();
        }
        return fmsLoan;
    }

    /**
     *
     * @param fmsLoan
     */
    public void setFmsLoan(FmsLoan fmsLoan) {
        this.fmsLoan = fmsLoan;
    }

    /**
     *
     * @return
     */
    public FmsLoanBeanLocal getFmsLoanBeanLocal() {
        return fmsLoanBeanLocal;
    }

    /**
     *
     * @param fmsLoanBeanLocal
     */
    public void setFmsLoanBeanLocal(FmsLoanBeanLocal fmsLoanBeanLocal) {
        this.fmsLoanBeanLocal = fmsLoanBeanLocal;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods">
    public ArrayList<FmsLoan> SearchLoan(String loanNo) {
        ArrayList<FmsLoan> loans = null;
        fmsLoan.setLoanNo(loanNo);
        loans = fmsLoanBeanLocal.searchFmsLoanByLoanNo(fmsLoan);
        return loans;
    }

    public void loanSearch() {
        loanList = fmsLoanBeanLocal.searchFmsLoanByLoanNo(fmsLoan);
        loanDatamodel = new ListDataModel<>(loanList);
    }

    public void getByLoanNO(SelectEvent event) {
        fmsLoan = (FmsLoan) event.getObject();
        fmsLoan = fmsLoanBeanLocal.getFmsLoanInfo(fmsLoan);
        fmsLoanCommitment.setLoanId(fmsLoan);
        CommitmentLister(fmsLoanCommitment);

        renderPnlCreateGatePass = true;
        renderNewSrcBtn = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        headerTitle = "Commitment Fee";
        setIcone("ui-icon-search");
    }

    /**
     *
     * @param loanCommitment
     */
    public void CommitmentLister(FmsLoanCommitment loanCommitment) {
        if (fmsLoanCommitment != null) {
            commitmentList = fmsLoanCommitmentBeanLocal.fetchCommitment(fmsLoanCommitment);
            commitmentDModel = new ListDataModel(new ArrayList(commitmentList));
        }
    }

    /**
     *
     */
    public void followUpEditor() {
        fmsLoanCommitment = getCommitmentDModel().getRowData();
    }

    /**
     *
     */
    public void addPayment() {
        if (fmsLoanCommitment.getStatus().equalsIgnoreCase("Unpaid")) {
            fmsLoanCommitment.setPaymentDate(fmsLoanCommitment.getPaymentDate());
            fmsLoanCommitment.setStatus("Paid");
            getCommitmentList().add(fmsLoanCommitment);
        } else {
            JsfUtil.addFatalMessage("Payment has already been made.");
        }
    }

    /**
     *
     */
    public void reset() {
        fmsLoan = new FmsLoan();
        fmsLoanCommitment = new FmsLoanCommitment();
        commitmentList = new ArrayList();
        commitmentDModel = new ListDataModel();
    }

    /**
     *
     */
    public void paymentEditor() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "paymentEditor", dataset)) {

                commitmentList.stream().forEach((schedule) -> {
                    fmsLoanCommitmentBeanLocal.edit(schedule);
                });
                JsfUtil.addSuccessMessage("Commitment fee is saved");

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

    public void createNewView() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderNewSrcBtn = false;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Commitment  Fee";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderNewSrcBtn = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            headerTitle = "Commitment  Fee";
            setIcone("ui-icon-plus");
        }
        reset();
    }
//</editor-fold>
    

    
}
