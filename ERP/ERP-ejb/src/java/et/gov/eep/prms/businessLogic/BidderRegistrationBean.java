/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.fcms.entity.FmsBidSale;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidderRegDetail;
import et.gov.eep.prms.entity.PrmsBidderRegistration;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsBidderRegDetailFacade;
import et.gov.eep.prms.mapper.PrmsBidderRegistrationFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

@Stateless
public class BidderRegistrationBean implements BidderRegistrationLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private PrmsBidderRegistrationFacade prmsBidderRegistrationFacade;
    @EJB
    private PrmsBidderRegDetailFacade prmsBidderRegDetailFacade;
    @EJB
    PrmsSupplyProfileFacade  prmsSupplyProfileFacade;
    // </editor-fold>

    @Override
    public PrmsBidderRegistration getSelectedRequest(BigDecimal id) {
        return prmsBidderRegistrationFacade.getSelectedRequest(id);
    }

    @Override
    public PrmsBidderRegistration LastCheckListNo() {
        return prmsBidderRegistrationFacade.LastCheckListNo();
    }

    @Override
    public List<PrmsBidderRegistration> searchBidderReg(PrmsBidderRegistration prmsBidderRegistration) {
        return prmsBidderRegistrationFacade.searchBidderReg(prmsBidderRegistration);
    }

    @Override
    public void create(PrmsBidderRegistration prmsBidderRegistration) {
        prmsBidderRegistrationFacade.create(prmsBidderRegistration);

    }

    @Override
    public void update(PrmsBidderRegistration prmsBidderRegistration) {
        prmsBidderRegistrationFacade.edit(prmsBidderRegistration);
    }

    @Override
    public void deleteContactPersonInfo(PrmsBidderRegDetail prmsBidderRegDetail) {
        prmsBidderRegDetailFacade.remove(prmsBidderRegDetail);
    }

    @Override
    public ArrayList<PrmsBid> BidNoFormBidSale() {
        return prmsBidderRegistrationFacade.BidNoFormBidSale();
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo) {
        return prmsSupplyProfileFacade.getsupplierlist(bidNo);
    }

    @Override
    public FmsBidSale getRecietNo(FmsBidSale fmsBidSale) {
        return prmsBidderRegistrationFacade.getRecietNo(fmsBidSale);
    }

    @Override
    public ArrayList<PrmsBid> getRistrictedBid() {
        return prmsBidderRegistrationFacade.getRistrictedBid();
    }

    @Override
    public PrmsSupplyProfile findBySubId(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsSupplyProfileFacade.findByVendorName(prmsSupplyProfile);
    }

    @Override
    public PrmsSupplyProfile getVendorForDoc(PrmsSupplyProfile prmsSupplyProfForDoc) {
        return prmsBidderRegistrationFacade.getVendorForDoc(prmsSupplyProfForDoc);
    }

    @Override
    public FmsBidSale getsuppList(String vendorName) {
        return prmsBidderRegistrationFacade.getsuppList(vendorName);
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getsuppVendor(PrmsBid bidNo) {
        return prmsSupplyProfileFacade.getSuppVendor(bidNo);
    }

    @Override
    public FmsBidSale getRecietNumber(FmsBidSale fmsBidSale) {
        return prmsBidderRegistrationFacade.getRecietNumber(fmsBidSale);
    }

    @Override
    public FmsVoucher getRecietNo(FmsVoucher fmsVoucher) {
        return prmsBidderRegistrationFacade.getRecietNo(fmsVoucher);
    }

   

    @Override
    public List<PrmsSupplyProfile> findSuppliers() {
        return prmsSupplyProfileFacade.findSuppliers();
    }

    @Override
    public ArrayList<PrmsBidderRegistration> searchBidderRegistration() {
        return prmsBidderRegistrationFacade.searchBidderRegistration();
    }

    @Override
    public PrmsBid getBidDates(PrmsBid eepBidReg) {
        return prmsBidderRegistrationFacade.getBidDates(eepBidReg);
    }

    @Override
    public List<PrmsBidderRegistration> getParamNameList() {
       return prmsBidderRegistrationFacade.getParamNameList();
    }

}
