/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import et.gov.eep.prms.mapper.PrmsLcRigistrationFacade;
import et.gov.eep.prms.mapper.PrmsServiceProviderFacade;
import et.gov.eep.commonApplications.mapper.ComLuCountryFacade;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsFileUpload;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.mapper.PrmsContractAmendmentFacade;
import et.gov.eep.prms.mapper.PrmsFileUploadFacade;
import et.gov.eep.prms.mapper.PrmsForeignExchangeFacade;
import et.gov.eep.prms.mapper.PrmsPortEntryFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

/**
 *
 * @author user
 */
@Stateless
public class LetterOfCreditRegiBean implements LetterOfCreditRegiBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsLcRigistrationFacade prmsLcRigistrationFacade;
    @EJB
    PrmsServiceProviderFacade prmsServiceProviderFacade;
    @EJB
    PrmsContractFacade prmsContractFacade;
    @EJB
    PrmsContractAmendmentFacade prmsContractAmendmentFacade;
    @EJB
    PrmsSupplyProfileFacade eepVendorRegFacade;
    @EJB
    PrmsPortEntryFacade prmsPortEntryFacade;
    @EJB
    ComLuCountryFacade comLuCountryFacade;
    @EJB
    FmsLuCurrencyFacade fmsLuCurrencyFacade;
    @EJB
    PrmsFileUploadFacade prmsFileUploadFacade;
    @EJB
    PrmsForeignExchangeFacade prmsForeignExchangeFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    // </editor-fold>

    @Override
    public void save(PrmsLcRigistration prmsLcRigistration) {
        prmsLcRigistrationFacade.create(prmsLcRigistration);
    }

    @Override
    public void update(PrmsLcRigistration prmsLcRigistration) {
        prmsLcRigistrationFacade.edit(prmsLcRigistration);
    }

    @Override
    public List<PrmsLcRigistration> searchByLCregNo(PrmsLcRigistration prmsLcRigistration) {
        return prmsLcRigistrationFacade.searchByLCregNo(prmsLcRigistration);
    }

    @Override
    public List<PrmsServiceProvider> listOfServiceNO() {
        return prmsServiceProviderFacade.findAll();
    }

    @Override
    public List<PrmsContract> listOfContractNO() {
        return prmsContractFacade.listOfContractNO();
    }

    @Override
    public List<PrmsSupplyProfile> listOflistOfSupplier() {
        return eepVendorRegFacade.findAll();
    }

    @Override
    public PrmsLcRigistration getSelectedLCReg(Integer lcId) {
        return prmsLcRigistrationFacade.getSelectedLCreg(lcId);
    }

    @Override
    public PrmsLcRigistration getlastLCRegNo() {
        return prmsLcRigistrationFacade.getlastLCRegNo();
    }

    @Override
    public List<ComLuCountry> getCountryList() {
        return comLuCountryFacade.getCountries();
    }

    @Override
    public List<PrmsPortEntry> listOfPorts() {
        return prmsPortEntryFacade.findAll();
    }

    @Override
    public List<FmsLuCurrency> getCurrencyName() {
        return fmsLuCurrencyFacade.findAll();
    }

    @Override
    public List<PrmsLcRigistration> findAllLcInfo() {
        return prmsLcRigistrationFacade.findAll();
    }

    @Override
    public List<PrmsLcRigistration> getLCLists() {
        return prmsLcRigistrationFacade.getLCLists();
    }

    @Override
    public void remove(PrmsFileUpload prmsFileUpload) {
        prmsFileUploadFacade.remove(prmsFileUpload);
    }

    @Override
    public List<PrmsForeignExchange> findForeign() {
        return prmsForeignExchangeFacade.findAll();
    }

    @Override
    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsCotract) {
        return prmsContractAmendmentFacade.checkAmendOrNot(prmsCotract);
    }

    @Override
    public PrmsContractAmendment getContractAmendment(PrmsContract prmsCotract) {
        return prmsContractAmendmentFacade.getContractAmendment(prmsCotract);
    }

    @Override
    public String getNextLcRegNo() {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String lcRegNo = null;
        String prefix = "LCReg-NO";
        int maxNo = 0;
        List<PrmsLcRigistration> lcRegNoList = prmsLcRigistrationFacade.getNextLcRegNo(prefix, eYear);
        for (int rowCount = 0; rowCount < lcRegNoList.size(); rowCount++) {
            lcRegNo = lcRegNoList.get(rowCount).getLcNo();
            String[] lastLcRegNoNos = lcRegNo.split("-");
            String lastDatesPatern = lastLcRegNoNos[2];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increament = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increament) {
                maxNo = increament;
            }
        }
        maxNo = maxNo + 1;
        lcRegNo = "LCReg-NO-" + maxNo + "/" + eYear;
        return lcRegNo;
    }

    @Override
    public List<PrmsLcRigistration> getParamNameList() {
         return prmsLcRigistrationFacade.getParamNameList();
    }

}
