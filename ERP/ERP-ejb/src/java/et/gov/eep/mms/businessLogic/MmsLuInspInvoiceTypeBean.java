/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsLuInspectionInvoiceType;
import et.gov.eep.mms.mapper.MmsLuInspectionInvoiceTypeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsLuInspInvoiceTypeBean implements MmsLuInspInvoiceTypeBeanLocal {
@EJB
MmsLuInspectionInvoiceTypeFacade luInspectionInvoiceTypeFacade;
    @Override
    public List<MmsLuInspectionInvoiceType> findAll() {
        return luInspectionInvoiceTypeFacade.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
