/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondApplication;

/**
 *
 * @author user
 */
@Local
public interface BondApplicationBeanLocals {

    public void Create(FmsBondApplication fmsBondApplication);

    public ArrayList<FmsBondApplication> searchBondType(FmsBondApplication bondApplication);

    public List<FmsBondApplication> searchBondmatured();
}
