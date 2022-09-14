/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsLuVouchersType;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;

/**
 *
 * @author userPCAdmin
 */
@Local
public interface FmsVouchersNoRangeBeanLocal {

    public FmsVouchersNoRange getCurrentVoucherNumber(FmsVouchersNoRange vouchersNoRange);

    public void create(FmsVouchersNoRange vouchersNoRange);

    public void edit(FmsVouchersNoRange vouchersNoRange);

    public List<FmsVouchersNoRange> searchByVoucherType(FmsVouchersNoRange vouchersNoRangeSearch);

    public List<FmsLuVouchersType> getLuVouchersType();

    public FmsAccountingPeriod getFiscalYr();

    public FmsLuVouchersType searchPaymentType(FmsLuVouchersType vouchersType);

    public FmsVouchersNoRange getCurrentVoucherById(FmsVouchersNoRange fmsVouchersNoRange);
}
