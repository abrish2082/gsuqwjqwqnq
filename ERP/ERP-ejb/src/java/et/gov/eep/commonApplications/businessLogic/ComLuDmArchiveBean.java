/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.ComLuDmArchive;
import et.gov.eep.commonApplications.mapper.ComLuDmArchiveFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author mora1
 */
@Stateless
public class ComLuDmArchiveBean implements ComLuDmArchiveBeanLocal {

    @EJB
    ComLuDmArchiveFacade comLuDmArchiveFacade;

    @Override
    public void saveFileInfo(ComLuDmArchive comLuDmArchive) {
        comLuDmArchiveFacade.create(comLuDmArchive);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<ComLuDmArchive> getFileLists(ComLuDmArchive comLuDmArchive) {
        return comLuDmArchiveFacade.getFileLists(comLuDmArchive);
    }

    @Override
    public void updateFileInfo(ComLuDmArchive comLuDmArchive) {
        comLuDmArchiveFacade.edit(comLuDmArchive);
    }
}
