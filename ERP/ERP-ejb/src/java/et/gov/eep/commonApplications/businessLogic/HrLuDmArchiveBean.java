/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.HrLuDmArchive;
import et.gov.eep.commonApplications.mapper.HrLuDmArchiveFacade;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi
 */
@Stateless
public class HrLuDmArchiveBean implements HrLuDmArchiveBeanLocal {

    @EJB
    HrLuDmArchiveFacade hrLuDmArchiveFacade;

    @Override
    public void create(HrLuDmArchive hrLuDmArchive) {
        hrLuDmArchiveFacade.create(hrLuDmArchive);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void edit(HrLuDmArchive hrLuDmArchive) {
        hrLuDmArchiveFacade.edit(hrLuDmArchive);
    }
}
