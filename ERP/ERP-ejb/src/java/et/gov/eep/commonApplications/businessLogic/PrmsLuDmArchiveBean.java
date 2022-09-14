/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.mapper.PrmsLuDmArchiveFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mora1
 */
@Stateless
public class PrmsLuDmArchiveBean implements PrmsLuDmArchiveBeanLocal {

    @EJB
    PrmsLuDmArchiveFacade prmsLuDmArchiveFacade;

    @Override
    public void saveFileInfo(PrmsLuDmArchive prmsLuDmArchive) {
        prmsLuDmArchiveFacade.create(prmsLuDmArchive);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void updateFileInfo(PrmsLuDmArchive prmsLuDmArchive) {
        prmsLuDmArchiveFacade.edit(prmsLuDmArchive);
    }

    @Override
    public List<PrmsLuDmArchive> getFileLists(PrmsLuDmArchive prmsLuDmArchive) {
        return prmsLuDmArchiveFacade.getFileLists(prmsLuDmArchive);
    }
}
