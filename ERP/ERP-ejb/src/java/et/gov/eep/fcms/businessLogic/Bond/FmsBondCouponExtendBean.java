/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponExtend;
import et.gov.eep.fcms.mapper.Bond.FmsBondCouponExtendFacade;

/**
 *
 * @author mz
 */
@Stateless
public class FmsBondCouponExtendBean implements FmsBondCouponExtendBeanLocal {

    @EJB
    FmsBondCouponExtendFacade couponExtendFacade;

    @Override
    public void Create(FmsBondCouponExtend couponExtend) {
        couponExtendFacade.create(couponExtend);
    }

    @Override
    public void Edit(FmsBondCouponExtend couponExtend) {
        couponExtendFacade.edit(couponExtend);
    }

    @Override
    public ArrayList<FmsBondCouponExtend> searchCouponExtendId(FmsBondCouponExtend couponExtend) {
        return couponExtendFacade.searchCouponExtendId(couponExtend);
    }

}
