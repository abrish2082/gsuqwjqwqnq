/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.businessLogic;

import et.gov.eep.pms.entity.PmsCreateProjects;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface PmsCreateProjectBeanLocal {
    public List<PmsCreateProjects> findAll() ;
     public List<PmsCreateProjects> findAllProjects() ;
    public PmsCreateProjects findPojectID(PmsCreateProjects pmsCreateProjects) ;
}
