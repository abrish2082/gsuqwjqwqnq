package et.gov.eep.fcms.businessLogic.Voucher;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.mapper.Voucher.FmsCashPaymentOrderFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>

@Stateless
public class CashPaymentOrderBean implements CashPaymentOrderBeanLocal {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs ">

    @EJB
    FmsCashPaymentOrderFacade cashPaymentOrderFacade;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="other methods ">
    @Override
    public List<String> getPrepareList(int approveStatus) {
        return cashPaymentOrderFacade.getPrepareList(approveStatus);
    }

    @Override
    public List<FmsCashPaymentOrder> getPreparedCPO() {
        return cashPaymentOrderFacade.getPreparedCPO();
    }

    /**
     *
     * @param cashPaymentOrder
     */
    @Override
    public void create(FmsCashPaymentOrder cashPaymentOrder) {
        cashPaymentOrderFacade.create(cashPaymentOrder);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void edit(FmsCashPaymentOrder fmsCashPaymentOrder) {
        cashPaymentOrderFacade.edit(fmsCashPaymentOrder);
    }

    @Override
    public FmsCashPaymentOrder getCashPaymentOrder(FmsCashPaymentOrder fmsCashPaymentOrder) {
        return cashPaymentOrderFacade.getCashPaymentOrder(fmsCashPaymentOrder);
    }
    //</editor-fold>

}
