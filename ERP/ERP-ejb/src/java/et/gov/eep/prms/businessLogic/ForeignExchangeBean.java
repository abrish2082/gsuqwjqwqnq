/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import et.gov.eep.prms.mapper.PrmsForeignExchangeFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import et.gov.eep.prms.mapper.PrmsPortEntryFacade;
import et.gov.eep.prms.mapper.PrmsPaymentRequisitionFacade;
import et.gov.eep.hrms.mapper.address.HrAddressesFacade;
import et.gov.eep.mms.mapper.MmsItemRegistrationFacade;
import et.gov.eep.fcms.mapper.loan.FmsLoanFacade;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.mms.entity.MmsItemRegistration;
import java.util.List;
// </editor-fold>

/**
 *
 * @author user
 */
@Stateless
public class ForeignExchangeBean implements ForeignExchangeBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsForeignExchangeFacade prmsForeignExchangeFacade;
    @EJB
    private PrmsSupplyProfileFacade supplyProfileFacade;
    @EJB
    private PrmsPortEntryFacade prmsPortEntryFacade;
    @EJB
    PrmsPaymentRequisitionFacade prmsPaymentRequisitionFacade;
    @EJB
    FmsLoanFacade fmsLoanFacade;
    @EJB
    HrAddressesFacade hrAddressesFacade;
    @EJB
    MmsItemRegistrationFacade mmsItemRegistrationFacade;
    @EJB
    FmsLuCurrencyFacade fmsLuCurrencyFacade;
    // </editor-fold>

    @Override
    public void create(PrmsForeignExchange prmsForeignExchange) {
        prmsForeignExchangeFacade.create(prmsForeignExchange);
    }

    @Override
    public void update(PrmsForeignExchange prmsForeignExchange) {
        prmsForeignExchangeFacade.edit(prmsForeignExchange);
    }

    @Override
    public PrmsForeignExchange findByfenumberObj(PrmsForeignExchange prmsForeignExchange) {
        return prmsForeignExchangeFacade.findByfenumberObj(prmsForeignExchange);
    }

    @Override
    public List<PrmsForeignExchange> findByfenumber(PrmsForeignExchange prmsForeignExchange) {
        return prmsForeignExchangeFacade.findByfenumber(prmsForeignExchange);
    }

    @Override
    public List<PrmsSupplyProfile> VendorName() {
        return supplyProfileFacade.findAll();
    }

    @Override
    public List<PrmsPortEntry> fromPortEntry() {
        return prmsPortEntryFacade.findAll();
    }

    @Override
    public PrmsForeignExchange generateForeignExNo() {
        return prmsForeignExchangeFacade.generateNextForeignExNo();
    }

    @Override
    public List<HrAddresses> fromHrAddress() {
        return hrAddressesFacade.findAll();
    }

    @Override
    public List<MmsItemRegistration> itemNameLists() {
        return mmsItemRegistrationFacade.findAll();
    }

    @Override
    public List<MmsItemRegistration> getItmNameList() {
        return mmsItemRegistrationFacade.findAll();

    }

    @Override
    public HrAddresses addressUpdate(HrAddresses hrAddresses) {
        return hrAddressesFacade.getAddressInfoByAddressId(hrAddresses);
    }

    @Override
    public List<HrAddresses> listofHrAddress() {
        return hrAddressesFacade.findAllCountryFromDescription();
    }

    @Override
    public FmsLuCurrency updateCurrencies(FmsLuCurrency freightCurrency) {
        return fmsLuCurrencyFacade.getByCurrencyId(freightCurrency);
    }

    @Override
    public List<PrmsForeignExchange> getForeignExchReqlist() {
        return prmsForeignExchangeFacade.getForeignExchReqlist();
    }

    @Override
    public List<PrmsForeignExchange> getParamNameList() {
        return prmsForeignExchangeFacade.getParamNameList();
    }
}
