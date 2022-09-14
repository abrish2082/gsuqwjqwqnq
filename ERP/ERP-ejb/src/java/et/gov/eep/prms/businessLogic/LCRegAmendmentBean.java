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
import et.gov.eep.prms.entity.PrmsLcRegDetailAmendment;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import et.gov.eep.prms.mapper.PrmsLcRigistrationAmendFacade;
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
import et.gov.eep.prms.mapper.PrmsLcRegDetailFacade;
import et.gov.eep.prms.mapper.PrmsLcRigistrationFacade;
import et.gov.eep.prms.mapper.PrmsPortEntryFacade;
import et.gov.eep.prms.mapper.PrmsServiceProviderFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

/**
 *
 * @author user
 */
@Stateless
public class LCRegAmendmentBean implements LCRegAmendmentBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsLcRigistrationAmendFacade prmsLcRigistrationAmendFacade;
    @EJB
    PrmsLcRegDetailFacade prmsLcRegDetailFacade;
    @EJB
    PrmsServiceProviderFacade prmsServiceProviderFacade;
    @EJB
    PrmsContractFacade prmsContractFacade;
    @EJB
    PrmsContractAmendmentFacade prmsContractAmendmentFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    @EJB
    PrmsLcRigistrationFacade prmsLcRigistrationFacade;
    @EJB
    ComLuCountryFacade comLuCountryFacade;
    @EJB
    PrmsPortEntryFacade prmsPortEntryFacade;
    @EJB
    FmsLuCurrencyFacade fmsLuCurrencyFacade;
    @EJB
    PrmsFileUploadFacade prmsFileUploadFacade;
    @EJB
    PrmsForeignExchangeFacade prmsForeignExchangeFacade;
    // </editor-fold>

    @Override
    public List<PrmsLcRigistrationAmend> searchLCNoLCAmendNo(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        return prmsLcRigistrationAmendFacade.getLCNoAndLCAmendNo(prmsLcRigistrationAmend);
    }

    @Override
    public List<PrmsLcRigistrationAmend> searchAmendedLCByLCNo(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        return prmsLcRigistrationAmendFacade.searchAmendedLCByLCNo(prmsLcRigistrationAmend);
    }

    @Override
    public PrmsLcRigistrationAmend getLCAmendNo() {
        return prmsLcRigistrationAmendFacade.getAmendNo();
    }

    @Override
    public List<PrmsLcRigistration> getLCNoLst() {
        return prmsLcRigistrationFacade.getLCNoLst();
    }

    @Override
    public List<PrmsLcRegDetailAmendment> getListOfLCAmendNum() {
        return prmsLcRegDetailFacade.getListOfLCAmendNo();
    }

    @Override
    public void save(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        prmsLcRigistrationAmendFacade.create(prmsLcRigistrationAmend);
    }

    @Override
    public void edit(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        prmsLcRigistrationAmendFacade.edit(prmsLcRigistrationAmend);
    }

    @Override
    public PrmsLcRigistrationAmend getSelectedAmedId(Integer id) {
        return prmsLcRigistrationAmendFacade.getSelectedLcregAmendID(id);
    }

    

    @Override
    public PrmsLcRigistration getLcNum(String lcNo) {
        return prmsLcRigistrationFacade.getLcNums(lcNo);
    }

    @Override
    public List<PrmsContract> listOfContractNOAmend() {
        return prmsContractFacade.findAmend();
    }

    @Override
    public List<PrmsServiceProvider> listOfServiceNO() {
        return prmsServiceProviderFacade.findAll();
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
    public PrmsLcRigistrationAmend getLCNumber(BigDecimal AmendedNo) {
        return prmsLcRigistrationAmendFacade.getLCNumbers(AmendedNo);
    }

    @Override
    public List<PrmsLcRigistrationAmend> getLCAmendLists() {
        return prmsLcRigistrationAmendFacade.getLCAmendLists();
    }

    @Override
    public List<PrmsLcRigistrationAmend> searchByAmendNo(PrmsLcRigistrationAmend prmsLcRigistrationAmend) {
        return prmsLcRigistrationAmendFacade.findLCAmentNOs(prmsLcRigistrationAmend);
    }

    @Override
    public List<PrmsLcRigistrationAmend> findLCAmendListByWfStatus(int status) {
        return prmsLcRigistrationAmendFacade.findLCAmendListByWfStatus(status);
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
    public String getNextLcRegAmendNo() {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String lcAmendNo = null;
        String prefix = "LcAmd-NO";
        int maxNo = 0;
        List<PrmsLcRigistrationAmend> lcRigistrationAmendsLists = prmsLcRigistrationAmendFacade.getNextLcRegAmendNo(prefix, eYear);
        for (int count = 0; count < lcRigistrationAmendsLists.size(); count++) {
            lcAmendNo = lcRigistrationAmendsLists.get(count).getLcAmendNo();
            String[] lastLcAmdNos = lcAmendNo.split("-");
            String lastDatesPatern = lastLcAmdNos[2];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increament = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increament) {
                maxNo = increament;
            }
        }
        maxNo += 1;
        lcAmendNo = prefix + "-" + maxNo + "/" + eYear;
        return lcAmendNo;
    }

    @Override
    public List<PrmsLcRigistrationAmend> getLCAmendedNoListByLcId(PrmsLcRigistration prmsLcRigistration) {
        return prmsLcRigistrationAmendFacade.getLCAmendedNoListByLcId(prmsLcRigistration);
    }

    @Override
    public PrmsLcRigistrationAmend getLcAmendInfoByAmendedNo(String amendedNo) {
        return prmsLcRigistrationAmendFacade.getLcAmendInfoByAmendedNo(amendedNo);
    }

    @Override
    public List<PrmsLcRigistrationAmend> getParamNameList() {
        return prmsLcRigistrationAmendFacade.getParamNameList();
    }

}
