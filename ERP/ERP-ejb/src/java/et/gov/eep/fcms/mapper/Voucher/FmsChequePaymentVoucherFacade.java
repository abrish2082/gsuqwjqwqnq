package et.gov.eep.fcms.mapper.Voucher;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//</editor-fold>

@Stateless
public class FmsChequePaymentVoucherFacade extends AbstractFacade<FmsChequePaymentVoucher> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    //<editor-fold defaultstate="collapsed" desc="NamedQuery ">
    public ArrayList<FmsChequePaymentVoucher> getChequePaymentLists(FmsChequePaymentVoucher chequePaymentVoucher) {
        String status = "2";
        Query query = em.createNamedQuery("FmsChequePaymentVoucher.getByStatus");
        query.setParameter("status", status);
        try {
            ArrayList<FmsChequePaymentVoucher> pettyCashList = new ArrayList(query.getResultList());
            return pettyCashList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param chequePaymentVoucher
     * @return
     */
    public ArrayList<FmsChequePaymentVoucher> getChequePaymentforCapitalLists(FmsChequePaymentVoucher chequePaymentVoucher) {
        Query query = em.createNamedQuery("FmsChequePaymentVoucher.getByStatusForCapitalbgt");
        query.setParameter("status", "2");
        try {
            ArrayList<FmsChequePaymentVoucher> pettyCashList = new ArrayList(query.getResultList());
            return pettyCashList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param chequePaymentVoucher
     * @return
     */
    public FmsChequePaymentVoucher getApprovedChequePayment(FmsChequePaymentVoucher chequePaymentVoucher) {
        Query query = em.createNamedQuery("FmsChequePaymentVoucher.findByVOUCHERID");
        query.setParameter("fmsVOUCHERID", chequePaymentVoucher.getFmsVOUCHERID());
        System.out.println("query.getResultList()   " + query.getResultList().size());
        try {
            FmsChequePaymentVoucher chequePVoucher = (FmsChequePaymentVoucher) query.getResultList().get(0);
            return chequePVoucher;
        } catch (Exception ex) {

            return null;
        }

    }

    /**
     *
     * @param approveStatus
     * @return
     */
    public List<String> getPrepareList(int approveStatus) {

        List<String> ChequePaymentList = null;
        try {
            Query query = em.createNamedQuery("FmsChequePaymentVoucher.findByStatus", FmsChequePaymentVoucher.class);
            query.setParameter("status", approveStatus);
            ChequePaymentList = (List<String>) query.getResultList();
            return ChequePaymentList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<FmsChequePaymentVoucher> fetchChequePaymentLists() {
        Query query = em.createNamedQuery("FmsChequePaymentVoucher.getByStatus");
        query.setParameter("status", "1");
        try {
            ArrayList<FmsChequePaymentVoucher> checkList = new ArrayList(query.getResultList());
            return checkList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public FmsChequePaymentVoucher getbycheqno(FmsChequePaymentVoucher chequePaymentVoucherEnty) {
        Query query = em.createNamedQuery("FmsChequePaymentVoucher.findByChequePaymentVoucherId");
        query.setParameter("chequePaymentVoucherId", chequePaymentVoucherEnty.getChequePaymentVoucherId());
        System.out.println("query.getResultList()   " + query.getFirstResult());
        try {
            FmsChequePaymentVoucher selectEmp = (FmsChequePaymentVoucher) query.getResultList().get(0);
            return selectEmp;

        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public FmsChequePaymentVoucherFacade() {
        super(FmsChequePaymentVoucher.class);
    }

    //</editor-fold>
    
    
  public List<String> findAllItemStatuses() {
      System.out.println("bbbb===");
        Query query = em.createNativeQuery("select DISTINCT TYPE_ from FMS_VOUCHER where TYPE_ is not null");
        return query.getResultList();
    }
    
    public int ConutBYItemType(String get) {
        System.out.println("vvv===");
        Query query = em.createNativeQuery("select * from FMS_VOUCHER where TYPE_='" + get + "'");
        return query.getResultList().size();
    }
}
