/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.ComLuDmArchive;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mora1
 */
@Local
public interface ComLuDmArchiveBeanLocal {

    public void saveFileInfo(ComLuDmArchive comLuDmArchive);

    public List<ComLuDmArchive> getFileLists(ComLuDmArchive comLuDmArchive);

    public void updateFileInfo(ComLuDmArchive comLuDmArchive);
    
}
