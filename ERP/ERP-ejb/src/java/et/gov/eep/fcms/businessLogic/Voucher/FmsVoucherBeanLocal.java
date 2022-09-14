package et.gov.eep.fcms.businessLogic.Voucher;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsLuPaymentType;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;
//</editor-fold>

@Local
public interface FmsVoucherBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">

    public List<FmsVoucher> searchAllVochNo(FmsVoucher fmsVoucher);

    public List<FmsVoucher> getFmsVoucherSearchingParameterList();

    public void create(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     */
    public void create_accountUse(FmsAccountUse fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheid(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheIdListCRVWithHold(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypeCRV(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public FmsVoucher getVoucherIDInfo(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public boolean getfmsVoucherDup(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     */
    public void edit(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypeCPO(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypeCHPV(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypePCPV(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypeJV(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchAllJvByType(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheidTypeSPV(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheIdListPCPVDailyCash(FmsVoucher fmsVoucher);

    public List<FmsVoucher> searchVoucheIdListCPODailyCash(FmsVoucher fmsVoucher);

    /**
     *
     * @param fmsVoucher
     * @return
     */
    public List<FmsVoucher> searchVoucheIdListPCPVDailyReg(FmsVoucher fmsVoucher);

    /**
     *
     * @param selectedChash
     * @return
     */
    public List<FmsVoucher> getChashRegVoucherInfo(String selectedChash);

    /**
     *
     */
    public void searchVoucherPostStatus();

    /**
     *
     * @return
     */
    public List<FmsVoucher> AllVoucherId();

    /**
     *
     * @return
     */
    public List<FmsVoucher> handleSearchPost();

    /**
     *
     * @return
     */
    public List<FmsVoucher> handleSearchPostCrv();

    /**
     *
     * @return
     */
    public List<FmsVoucher> handleSearchPostChpv();

    /**
     *
     * @return
     */
    public List<FmsVoucher> handleSearchPostJv();

    /**
     *
     * @param voucher
     * @return
     */
    public FmsVoucher getVoucher(FmsVoucher voucher);

    public Collection LedgerReport(HashMap hashMap);

    public List<FmsVoucher> CHPVouchers();

    /**
     *
     * @return
     */
    public List<FmsVoucher> findAllVATS();

    public List<FmsVoucher> findAllWITHHOLDSS();

    public List<FmsVoucher> findAllCRV();

    public List<FmsVoucher> findAllbytype(FmsVoucher fmsVoucher);

    public List<PrmsSupplyProfile> findBySuppProfile(PrmsSupplyProfile prmsSupplyProfile);

    public PrmsSupplyProfile getOthersBySuppId(PrmsSupplyProfile prmsSupplyProfile);

    public PrmsBid getBidInfoById(PrmsBid prmsBid);

    public List<PrmsBid> getBidNoLists();

    public FmsLuPaymentType getPaymentType(FmsLuPaymentType luPaymentType);

    public FmsVouchersNoRange getVoucherNo(FmsLuSystem fmsLuSystem);

    public List<FmsVoucher> searchVoucheidTypeWHV(FmsVoucher fmsVoucher);

    public List<FmsVoucher> searchVoucheidTypeVV(FmsVoucher fmsVoucher);

    public List<FmsVoucher> searchVoucheidTypechpvAndUnpaidVV(FmsVoucher fmsVoucher);
    //</editor-fold>

}
