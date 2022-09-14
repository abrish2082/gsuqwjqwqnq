/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.mms_lu_delivery_option;
import et.gov.eep.mms.mapper.mms_lu_delivery_optionFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsLuDeliveryOptionBean implements MmsLuDeliveryOptionBeanLocal {
@EJB
    private mms_lu_delivery_optionFacade deliveryOptionFacade;
    @Override
    public List<mms_lu_delivery_option> findAll() {
        return deliveryOptionFacade.findAll();
    }

    
}
