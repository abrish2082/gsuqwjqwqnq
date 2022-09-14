/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.mapper.PrmsInsuranceregistrationFacade;
import et.gov.eep.prms.mapper.PrmsServiceProviderDetailFacade;
import et.gov.eep.prms.mapper.PrmsServiceProviderFacade;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

/**
 *
 * @author user
 */
@Stateless
public class InsuranceRegisterationBean implements InsuranceRegisterationBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsInsuranceregistrationFacade prmsInsuranceregistrationFacade;
    @EJB
    PrmsServiceProviderDetailFacade prmsServiceProviderDetailFacade;
    @EJB
    private PrmsServiceProviderFacade papmsServiceProviderFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    @EJB
    FmsLuCurrencyFacade fmsLuCurrencyFacade;
    @EJB
    PrmsContractFacade prmsContractFacade;
    // </editor-fold>

    @Override
    public List<PrmsInsuranceRequisition> searchByInsuranceNo(PrmsInsuranceRequisition insuranceregistration) {
        return prmsInsuranceregistrationFacade.searchByInsuranceNo(insuranceregistration);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public PrmsInsuranceRequisition getSelectedRow(String insuranceNo) {
        return prmsInsuranceregistrationFacade.getSelectedRow(insuranceNo);
    }

    @Override
    public void create(PrmsInsuranceRequisition insuranceregistration) {
        prmsInsuranceregistrationFacade.create(insuranceregistration);
    }

    @Override
    public void update(PrmsInsuranceRequisition insuranceregistration) {
        prmsInsuranceregistrationFacade.edit(insuranceregistration);
    }

    @Override
    // to generate next Sequential Number of Insurance Number
    public String getNextInsuranceNo() {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        System.out.println("Current Ethiopian Year " + eYear);
        String newInsuNo = null;
        String prefix = "IR";
        int maxNo = 0;
        List<PrmsInsuranceRequisition> insuranceNoList = prmsInsuranceregistrationFacade.generateNextInsuranceNo(prefix, eYear);
        for (int rowCount = 0; rowCount < insuranceNoList.size(); rowCount++) {
            newInsuNo = insuranceNoList.get(rowCount).getInsuranceNo();
            String[] lastInsuranceNos = newInsuNo.split("-");
            String lastDatesPatern = lastInsuranceNos[1];

            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increament = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increament) {
                maxNo = increament;
            }
        }

        maxNo += 1;
        newInsuNo = "IR-" + maxNo + "/" + eYear;
        return newInsuNo;
    }

    @Override
    public ArrayList<PrmsServiceProvider> serviceProviderInsuranceList() {
        return papmsServiceProviderFacade.serviceProviderInsuranceList();
    }

    @Override
    public List<PrmsContract> contractNumList(int approvedStatus) {
        return prmsContractFacade.contractNumLists(approvedStatus);
    }

    @Override
    public ArrayList<PrmsServiceProvider> serviceProviderListFrom() {
        return papmsServiceProviderFacade.serviceProviderFrom();
    }

    @Override
    public ArrayList<PrmsServiceProviderDetail> fromServiceProDetail() {
        return prmsServiceProviderDetailFacade.toBranch();
    }

    @Override
    public FmsLuCurrency autoXRate(FmsLuCurrency currency) {
        return fmsLuCurrencyFacade.getFmsLuCurrencyInfo(currency);
    }

    @Override
    public List<FmsLuCurrency> fromLuCurrency() {
        return fmsLuCurrencyFacade.currencyNames();
    }

    @Override
    public PrmsServiceProvider updateServFrom(PrmsServiceProvider prmsServiceProvider) {
        return papmsServiceProviderFacade.serviceProviderInfoById(prmsServiceProvider);
    }

    @Override
    public PrmsServiceProviderDetail updateServBranch(PrmsServiceProviderDetail prmsServiceProviderBranch) {
        return prmsServiceProviderDetailFacade.findByServiceDtId(prmsServiceProviderBranch);
    }

    @Override
    public List<PrmsServiceProviderDetail> getBankBranches(PrmsServiceProvider prmsServiceProviderTo) {

        return prmsServiceProviderDetailFacade.getBranchByServiceProId(prmsServiceProviderTo);
    }

    @Override
    public List<PrmsInsuranceRequisition> getInsuRequestLists() {
        return prmsInsuranceregistrationFacade.getInsuRequestLists();
    }

    @Override
    public List<PrmsInsuranceRequisition> getParamNameList() {
        return prmsInsuranceregistrationFacade.getParamNameList();
    }

}
