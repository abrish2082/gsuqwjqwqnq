/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.ContractInformationBeanLocal;
import et.gov.eep.prms.businessLogic.LCRequestBeanLocal;
import et.gov.eep.prms.businessLogic.ServiceProviderBeanLocal;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsLcRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Dege
 */
@Named(value = "lcrequestController")
@ViewScoped
public class lcrequestController implements Serializable {

    @EJB
    LCRequestBeanLocal lCRequestBeanLocal;
    @EJB
    ServiceProviderBeanLocal serviceProviderBeanLocal;
    @EJB
    ContractInformationBeanLocal contractInformationBeanLocal;
    @Inject
    PrmsLcRequest prmslcrequest;
    @Inject
    PrmsContract prmsContract;
    @Inject
    PrmsServiceProvider prmsServiceProvider;

    DataModel<PrmsLcRequest> prmsLcRequestsModel;
    List<PrmsLcRequest> prmslcrequestList;
    boolean btnaddvisibility = true;
    private String icone = "ui-icon-document";
    private String saveorUpdateBundle = "Save";
    int saveStatus = 0;
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private String headerTitle = "Search For LC Requests";
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private String renderpnlContrat = "false";
    private int updateStatus = 0;

//    @PostConstruct
//    public void init() {
//        generateLCReqNo();
//    }
    public PrmsServiceProvider getPrmsServiceProvider() {
        return prmsServiceProvider;
    }

    public void setPrmsServiceProvider(PrmsServiceProvider prmsServiceProvider) {
        this.prmsServiceProvider = prmsServiceProvider;
    }

    /**
     * Creates a new instance of lcrequestController
     */
    public lcrequestController() {
    }

    public PrmsLcRequest getPrmslcrequest() {
        if (prmslcrequest == null) {
            prmslcrequest = new PrmsLcRequest();
        }
        return prmslcrequest;
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

    public void setPrmslcrequest(PrmsLcRequest prmslcrequest) {
        this.prmslcrequest = prmslcrequest;
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

    public SelectItem[] listOfContractNO() {
        return JsfUtil.getSelectItems(lCRequestBeanLocal.listOfContractNO(), true);
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
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

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void rowSelect(SelectEvent event) {
        prmslcrequest = (PrmsLcRequest) event.getObject();
        prmslcrequest.setId(prmslcrequest.getId());
        prmslcrequest = lCRequestBeanLocal.getSelectedRequest(prmslcrequest.getId());
        renderPnlManPage = false;
        renderPnlCreateGatePass = true;
        saveorUpdateBundle = "Update";
        recreLCReqtModel();
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public SelectItem[] listOfServiceNO() {
        return JsfUtil.getSelectItems(lCRequestBeanLocal.listOfServiceNO(), true);
    }
    private PrmsLcRequest selectedlc;

    public PrmsLcRequest getSelectedlc() {
        return selectedlc;
    }

    public void setSelectedlc(PrmsLcRequest selectedlc) {
        this.selectedlc = selectedlc;
    }

    public void selectLCRequest(SelectEvent event) {
        saveorUpdateBundle = "Update";
        prmslcrequest = (PrmsLcRequest) event.getObject();
        prmslcrequest.setId(prmslcrequest.getId());
        prmslcrequest = lCRequestBeanLocal.getSelectedRequest(prmslcrequest.getId());
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;

    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public void clear() {
        prmslcrequest = null;
    }

    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Letter Of Credit Request";
            setIcone("ui-icon-search");
            updateStatus = 0;
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
//            updateStatus = 1;
//            headerTitle = "Letter Of Credit Request";
            setIcone("ui-icon-document");
        }
    }

    public DataModel<PrmsLcRequest> getPrmsLcRequestsModel() {
        if (prmsLcRequestsModel == null) {
            prmsLcRequestsModel = new ListDataModel<>();
        }
        return prmsLcRequestsModel;
    }

    public void setPrmsLcRequestsModel(DataModel<PrmsLcRequest> prmsLcRequestsModel) {
        this.prmsLcRequestsModel = prmsLcRequestsModel;
    }

    public List<PrmsLcRequest> getPrmslcrequestList() {
        if (prmslcrequestList == null) {
            prmslcrequestList = new ArrayList<>();
        }
        return prmslcrequestList;
    }

    public void setPrmslcrequestList(List<PrmsLcRequest> prmslcrequestList) {
        this.prmslcrequestList = prmslcrequestList;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    private void recreLCReqtModel() {
        prmsLcRequestsModel = null;
        prmsLcRequestsModel = new ListDataModel(new ArrayList<>(getPrmslcrequestList()));
    }

    public void findByReqid() {

        prmslcrequestList = lCRequestBeanLocal.findByRequestId(prmslcrequest);
        recreLCReqtModel();
    }

    String SaveOrUpdateButton = "Save";

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    String newCriteriaNo;
    String bidCriteriaNo = "0";

    public String generateLCreqNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newCriteriaNo = prmslcrequest.getRequestid();

        } else {
            PrmsLcRequest lastBidCriteriaNo = lCRequestBeanLocal.getlastLCReqNo();
            if (lastBidCriteriaNo != null) {
                bidCriteriaNo = lastBidCriteriaNo.getId().toString();
            }

            int newBidCriteriaList = 0;
            if (bidCriteriaNo.equals("0")) {
                newBidCriteriaList = 1;
                newCriteriaNo = "LCReq-NO-" + newBidCriteriaList;
            } else {
                String[] lastInspNos = bidCriteriaNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidCriteriaList = Integer.parseInt(lastDatesPaterns[0]);
                newBidCriteriaList = newBidCriteriaList + 1;
                newCriteriaNo = "LCReq-NO-" + newBidCriteriaList;
            }
        }
        return newCriteriaNo;

    }

    public String save() {
        System.out.println("in save area");

        try {
            if (saveorUpdateBundle.equals("Save")) {
                generateLCreqNo();
                setPrmslcrequest(prmslcrequest);
//                prmslcrequest.setRequestid(newCriteriaNo);
                lCRequestBeanLocal.create(prmslcrequest);

                JsfUtil.addSuccessMessage("LC request information is registered!!");
                clear();
            } else {
                System.out.println("----in update-----");
                lCRequestBeanLocal.update(prmslcrequest);
                JsfUtil.addSuccessMessage("LCrequest information is updated");
                saveorUpdateBundle = "Save";
                clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addSuccessMessage("some thing is going to error" + e);
            System.out.println("the error==" + e);
            clear();

        }

        return null;
    }
    private BigDecimal converted;

    public BigDecimal getConverted() {
        return converted;
    }

    public void setConverted(BigDecimal converted) {
        this.converted = converted;
    }

    String[] onesNames = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    String[] tensNames = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    String[] specialNames = {"", " thousand", " million", " billion", " trillion", " quadrillion", " quintillion"};
    private String str = "";

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String one_to_hund(int n) {
        System.out.println("ones & tens");
//        String str = "";

        while (n != 0) {
            if (n == 1) {
                str = str + "one" + " ";
                n = 0;
            } else if (n == 2) {
                str = str + "two" + " ";
                n = 0;
            } else if (n == 3) {
                str = str + "three" + " ";
                n = 0;
            } else if (n == 4) {
                str = str + "four" + " ";
                n = 0;
            } else if (n == 5) {
                str = str + "five" + " ";
                n = 0;
            } else if (n == 6) {
                str = str + "six" + " ";
                n = 0;
            } else if (n == 7) {
                str = str + "seven" + " ";
                n = 0;
            } else if (n == 8) {
                str = str + "eight" + " ";
                n = 0;
            } else if (n == 9) {
                str = str + "nine" + " ";
                n = 0;
            } else if (n == 10) {
                str = str + "ten" + " ";
                n = 0;
            } else if (n == 11) {
                str = str + "eleven" + " ";
                n = 0;
            } else if (n == 12) {
                str = str + "twelve" + " ";
                n = 0;
            } else if (n == 13) {
                str = str + "thirteen" + " ";
                n = 0;
            } else if (n == 14) {
                str = str + "fourteen" + " ";
                n = 0;
            } else if (n == 15) {
                str = str + "fifteen" + " ";
                n = 0;
            } else if (n == 16) {
                str = str + "sixteen" + " ";
                n = 0;
            } else if (n == 17) {
                str = str + "seventeen" + " ";
                n = 0;
            } else if (n == 18) {
                str = str + "eighteen" + " ";
                n = 0;
            } else if (n == 19) {
                str = str + "nineteen" + " ";
                n = 0;
            } else if (n >= 20 && n < 30) {
                str = str + "Twenty" + "-";
                n = n - 20;
            } else if (n >= 30 && n < 40) {
                str = str + "" + "Thirty" + "-";
                n = n - 30;
            } else if (n >= 40 && n < 50) {
                str = str + "Fourty" + "-";
                n = n - 40;
            } else if (n >= 50 && n < 60) {
                str = str + "Fifty" + "-";
                n = n - 50;
            } else if (n >= 60 && n < 70) {
                str = str + "Sixty" + "-";
                n = n - 60;
            } else if (n >= 70 && n < 80) {
                str = str + "Seventy" + "-";
                n = n - 70;
            } else if (n >= 80 && n < 90) {
                str = str + "Eighty" + "-";
                n = n - 80;
            } else if (n >= 90 && n < 100) {
                str = str + "Ninety" + "-";
                n = n - 90;
                System.out.println("special");
            }
        }
        return str;
    }

    public String convert(int n) {
        System.out.println("specials");
        while (n != 0) {
            if (n >= 1 && n < 100) {
                str = one_to_hund(n) + " ";
                n = 0;
            } else if (n >= 100 && n < 1000) {
                str = one_to_hund(n / 100);//since n/100 is between 0 and 9
                str = str + "hundred" + " ";
                n = n % 100;
            } else if (n >= 1000 && n < 100000) {
                str = one_to_hund(n / 1000);
                str = str + "thousand" + " ";
                n = n % 1000;
            } else if (n >= 100000 && n < 1000000) {
                System.out.println("hhtttt");
                str = one_to_hund(n / 100000);
                str = str + "hunderd" + " ";
                n = n % 100000;
            } else if (n >= 1000000 && n < 100000000) {
                str = one_to_hund(n / 1000000);
                str = str + "million" + " ";
                n = n % 1000000;
            } else if (n >= 100000000 && n < 1000000000) {
                str = one_to_hund(n / 100000000);
                str = str + "hundred" + " ";
                n = n % 100000000;
            } //            else if (n >= 1000000000 && n < 100000000000) {
            //                str = one_to_hund(n / 1000000000);
            //                str = str + "billion" + " ";
            //                n = n % 1000000000;
            //            } 
            else {
                str = "Your value is out of the range";
            }

        }
        return (str);
    }

    public String toWords() {
        System.out.println("to word");
        int n;
        lcrequestController t = new lcrequestController();
        System.out.println("before LC decimal part rounded:" + converted);
        n = (int) Math.floor(converted.doubleValue());
//        n = (int) converted;
//        n = converted.intValueExact();
        double cents = converted.doubleValue() - n;
        int centsAsInt = (int) Math.floor((cents) * 100);
        System.out.println("Your LC in number is:" + converted.doubleValue());
//        int centsAsInt = (int) (100 * cents);
        str = t.convert(n) + "Birr and " + one_to_hund(centsAsInt) + "cents only";
        System.out.println("You're Registered into Words!:" + "" + str);
        return ((str));

    }

}
