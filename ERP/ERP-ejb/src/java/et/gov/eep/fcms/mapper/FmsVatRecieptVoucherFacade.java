package et.gov.eep.fcms.mapper;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsVatRecieptVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class FmsVatRecieptVoucherFacade extends AbstractFacade<FmsVatRecieptVoucher> {
        @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsVatRecieptVoucherFacade() {
        super(FmsVatRecieptVoucher.class);
    }

//    public List<FmsChequePaymentVoucher> getchequePaymentNoList() {
//        Query q = em.createNamedQuery("FmsChequePaymentVoucher.findAll");
//        List<FmsChequePaymentVoucher> cheqPayNoList = new ArrayList<>();
//        cheqPayNoList = q.getResultList();
//        return cheqPayNoList;
//    }
    

}
