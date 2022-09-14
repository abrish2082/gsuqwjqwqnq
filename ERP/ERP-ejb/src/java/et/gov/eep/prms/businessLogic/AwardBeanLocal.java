/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsFailsupplerAward;
import et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AwardBeanLocal {

    ArrayList<PrmsSupplyProfile> searchVendorName(PrmsSupplyProfile eepVendorReg);

//    public List< EepVendorReg> findAll();
    public ArrayList<PrmsAward> searchAwardByAwardId(PrmsAward papmsAward);

    void update(PrmsAward papmsAward);

    public List<PrmsSupplyProfile> findAll();

    public List<PrmsBid> findBidNo();

    void create(PrmsAward papmsAward);

    public PrmsAward searchAwardNo(PrmsAward papmsAward);

    public PrmsAward getAwardNo(PrmsAward papmsAward);

    public PrmsAward getLastPaymentNo();

    public void deleteAwardDetail(PrmsAwardDetail prmsAwardDetail);

    public void deleteprmsFailsupplerAward(PrmsFailsupplerAward prmsFailsupplerAward);

    List<PrmsAward> searchAward(PrmsAward prmsAward);

    public PrmsAward getSelectedRequest(String id);

    public ArrayList<PrmsBid> BidNoFormFinancialResult();

    public ArrayList<PrmsSupplyProfile> getsupplierNameFromResult(PrmsBid bidNo);

    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo);

    public ArrayList<PrmsSupplyProfile> getsupplierNameFromPostQual(PrmsBid bidNo);

    public ArrayList<PrmsFinancialEvaluaDetail> getQuantityAndUnitPrice(PrmsSupplyProfile eepVendorReg);

    public ArrayList<PrmsFinancialEvaluaDetail> getQuantityAndUnitPriceForAward(PrmsAward prmsAward);

    public ArrayList<PrmsSupplyProfile> getfailedSupplierNameFromFinc(PrmsBid bidNo);

    public ArrayList<PrmsSupplyProfile> getfailedSupplierNameFromPost(PrmsBid bidNo);

    public ArrayList<PrmsSupplyProfile> getFfailSupplierTechnicalEval(PrmsBid bidNo);
      public ArrayList<PrmsAward> searchawardBystatus() ;

    public List<PrmsAward> getParamNameList();
}
