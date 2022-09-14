/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.fcms.entity.FmsBidSale;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidderRegDetail;
import et.gov.eep.prms.entity.PrmsBidderRegistration;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

@Local
public interface BidderRegistrationLocal {

    public void create(PrmsBidderRegistration prmsBidderRegistration);

    public void update(PrmsBidderRegistration prmsBidderRegistration);

    public PrmsBidderRegistration getSelectedRequest(BigDecimal id);

    public PrmsBidderRegistration LastCheckListNo();

    public List<PrmsBidderRegistration> searchBidderReg(PrmsBidderRegistration prmsBidderRegistration);

    public void deleteContactPersonInfo(PrmsBidderRegDetail prmsBidderRegDetail);

    public ArrayList<PrmsBid> BidNoFormBidSale();

    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo);

    public FmsBidSale getRecietNo(FmsBidSale fmsBidSale);

    public ArrayList<PrmsBid> getRistrictedBid();

    public PrmsSupplyProfile findBySubId(PrmsSupplyProfile prmsSupplyProfile);

    public PrmsSupplyProfile getVendorForDoc(PrmsSupplyProfile prmsSupplyProfForDoc);

    public FmsBidSale getsuppList(String vendorName);

    public ArrayList<PrmsSupplyProfile> getsuppVendor(PrmsBid bidNo);

    public FmsVoucher getRecietNo(FmsVoucher fmsVoucher);

    public FmsBidSale getRecietNumber(FmsBidSale fmsBidSale);

    public PrmsBid getBidDates(PrmsBid eepBidReg);

    public List<PrmsSupplyProfile> findSuppliers();

    public ArrayList<PrmsBidderRegistration> searchBidderRegistration();

    public List<PrmsBidderRegistration> getParamNameList();

}
