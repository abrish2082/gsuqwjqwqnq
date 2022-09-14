/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsProject;
import et.gov.eep.mms.mapper.MmsProjectFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class MmsProjectBean implements MmsProjectBeanLocal {

   @EJB
   MmsProjectFacade projectFacade;

    /**
     *
     * @param jobNo
     * @return
     */
    @Override
   public ArrayList<MmsProject> searchProjectJobNo(MmsProject jobNo){
       return projectFacade.searchByjobNo(jobNo);
   }
}
