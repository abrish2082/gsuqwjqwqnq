/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.HrLuDmArchive;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author Abdi
 */
@Local
public interface HrLuDmArchiveBeanLocal {

    public void create(HrLuDmArchive hrLuDmArchive);

    public void edit(HrLuDmArchive hrLuDmArchive);
    
}
