package et.gov.eep.fcms.businessLogic.Voucher;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsLuPaymentType;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import et.gov.eep.fcms.mapper.FmsAccountUseFacade;
import et.gov.eep.fcms.mapper.Voucher.FmsVoucherFacade;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
//</editor-fold>

@Stateless
public class FmsVoucherBean implements FmsVoucherBeanLocal {

    //<editor-fold defaultstate="collapsed" desc="EJB ">
    @EJB
    private FmsVoucherFacade fmsVoucherFacadeLocal;

    @EJB
    private FmsAccountUseFacade accountUseFacade;
    @EJB
    PrmsSupplyProfileFacade prmsSupplyProfileFacade;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
    
    @Override
    public List<FmsVoucher> searchAllVochNo(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.getVoch1No(fmsVoucher );
    }

    @Override
    public List<FmsVoucher> getFmsVoucherSearchingParameterList() {
        return fmsVoucherFacadeLocal.getFmsVoucherSearchingParameterList();
    }

    @Override
    public void create(FmsVoucher fmsVoucher) {
        fmsVoucherFacadeLocal.create(fmsVoucher);

    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public List<FmsVoucher> searchVoucheIdListCRVWithHold(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheIdListCRVWithHold(fmsVoucher);
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public List<FmsVoucher> searchVoucheid(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheid(fmsVoucher);
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public List<FmsVoucher> searchVoucheidTypeCRV(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheidTypeCRV(fmsVoucher);
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public FmsVoucher getVoucherIDInfo(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.getVoucherIDInfo(fmsVoucher);
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public boolean getfmsVoucherDup(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.getfmsVoucherDup(fmsVoucher);
    }

    /**
     *
     * @param fmsVoucher
     */
    @Override
    public void edit(FmsVoucher fmsVoucher) {
        fmsVoucherFacadeLocal.edit(fmsVoucher);
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public List<FmsVoucher> searchVoucheidTypePCPV(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheidTypePCPV(fmsVoucher);

    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public List<FmsVoucher> searchVoucheidTypeJV(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheidTypeJV(fmsVoucher);
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public List<FmsVoucher> searchVoucheidTypeSPV(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheidTypeSPV(fmsVoucher);

    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public List<FmsVoucher> searchVoucheidTypeCPO(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheidTypeCPO(fmsVoucher);
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public List<FmsVoucher> searchVoucheidTypeCHPV(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheidTypeCHPV(fmsVoucher);
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public List<FmsVoucher> searchVoucheIdListPCPVDailyCash(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheIdListPCPVDailyCash(fmsVoucher);
    }

    @Override
    public List<FmsVoucher> searchVoucheIdListCPODailyCash(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheIdListCPODailyCash(fmsVoucher);
    }

    /**
     *
     * @param fmsVoucher
     * @return
     */
    @Override
    public List<FmsVoucher> searchVoucheIdListPCPVDailyReg(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheIdListPCPVDailyReg(fmsVoucher);
    }

    /**
     *
     * @param selectedChash
     * @return
     */
    @Override
    public List<FmsVoucher> getChashRegVoucherInfo(String selectedChash) {
        return fmsVoucherFacadeLocal.getChashRegVoucherInfo(selectedChash);
    }

    /**
     *
     */
    @Override
    public void searchVoucherPostStatus() {
        fmsVoucherFacadeLocal.searchVoucherPostStatus();
    }

    /**
     *
     * @param fmsVoucher
     */
    @Override
    public void create_accountUse(FmsAccountUse fmsVoucher) {
        accountUseFacade.create(fmsVoucher);
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsVoucher> AllVoucherId() {
        return fmsVoucherFacadeLocal.findAllCPOs();
    }

    /**
     *
     * @param voucher
     * @return
     */
    @Override
    public FmsVoucher getVoucher(FmsVoucher voucher) {
        return fmsVoucherFacadeLocal.getPittyCash(voucher);
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsVoucher> handleSearchPost() {
        return fmsVoucherFacadeLocal.searchVoucherStates();
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsVoucher> handleSearchPostCrv() {
        return fmsVoucherFacadeLocal.searchVoucherStatesCrv();
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsVoucher> handleSearchPostChpv() {
        return fmsVoucherFacadeLocal.searchVoucherStatesChpv();
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsVoucher> handleSearchPostJv() {
        return fmsVoucherFacadeLocal.searchVoucherStatesJv();
    }

    @Override
    public Collection LedgerReport(HashMap hashMap) {
        return fmsVoucherFacadeLocal.LedgerReport(hashMap);
    }

    @Override
    public List<FmsVoucher> searchAllJvByType(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchAllJvByType();
    }

    @Override
    public List<FmsVoucher> CHPVouchers() {
        return fmsVoucherFacadeLocal.findAllCHPVs();
    }

    @Override
    public List<FmsVoucher> findAllVATS() {
        return fmsVoucherFacadeLocal.findAllVATS();
    }

    @Override
    public List<FmsVoucher> findAllWITHHOLDSS() {
        return fmsVoucherFacadeLocal.findAllWITHHOLDSS();
    }

    @Override
    public List<FmsVoucher> findAllCRV() {
        return fmsVoucherFacadeLocal.findAllCRV();
    }

    @Override
    public List<FmsVoucher> findAllbytype(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.findAllbytype(fmsVoucher);
    }

    @Override
    public List<PrmsSupplyProfile> findBySuppProfile(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsSupplyProfileFacade.findBySuppProfile(prmsSupplyProfile);
    }

    @Override
    public PrmsSupplyProfile getOthersBySuppId(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsSupplyProfileFacade.getBySuppId(prmsSupplyProfile);
    }

    @Override
    public PrmsBid getBidInfoById(PrmsBid prmsBid) {
        return fmsVoucherFacadeLocal.getBidInfoById(prmsBid);
    }

    @Override
    public List<PrmsBid> getBidNoLists() {
        return fmsVoucherFacadeLocal.getBidNoLists();
    }

    @Override
    public FmsLuPaymentType getPaymentType(FmsLuPaymentType luPaymentType) {
        return fmsVoucherFacadeLocal.getPaymentType(luPaymentType);
    }

    @Override
    public FmsVouchersNoRange getVoucherNo(FmsLuSystem fmsLuSystem) {
        return fmsVoucherFacadeLocal.getVoucherNo(fmsLuSystem);
    }

    @Override
    public List<FmsVoucher> searchVoucheidTypeWHV(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheidTypeWHV(fmsVoucher);
    }

    @Override
    public List<FmsVoucher> searchVoucheidTypeVV(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheidTypeVV(fmsVoucher);
    }

    @Override
    public List<FmsVoucher> searchVoucheidTypechpvAndUnpaidVV(FmsVoucher fmsVoucher) {
        return fmsVoucherFacadeLocal.searchVoucheidTypechpvAndUnpaidVV(fmsVoucher);
    }
    //</editor-fold>

}
