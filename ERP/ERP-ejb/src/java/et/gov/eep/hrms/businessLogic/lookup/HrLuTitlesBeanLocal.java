/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuTitles;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Man
 */
@Local
public interface HrLuTitlesBeanLocal {

    /**
     *
     * @return
     */
    public List<HrLuTitles> findAll();
}
