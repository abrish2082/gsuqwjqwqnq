/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponExtend;

/**
 *
 * @author mz
 */
@Local
public interface FmsBondCouponExtendBeanLocal {

    public void Create(FmsBondCouponExtend couponExtend);

    public void Edit(FmsBondCouponExtend couponExtend);

    public ArrayList<FmsBondCouponExtend> searchCouponExtendId(FmsBondCouponExtend couponExtend);

}
